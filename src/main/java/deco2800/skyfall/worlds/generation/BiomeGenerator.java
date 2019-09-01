package deco2800.skyfall.worlds.generation;

import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.worlds.biomes.AbstractBiome;
import deco2800.skyfall.worlds.biomes.LakeBiome;
import deco2800.skyfall.worlds.biomes.OceanBiome;
import deco2800.skyfall.worlds.biomes.RiverBiome;
import deco2800.skyfall.worlds.generation.delaunay.NotEnoughPointsException;
import deco2800.skyfall.worlds.generation.delaunay.WorldGenNode;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Builds biomes from the nodes generated in the previous phase of the world generation.
 */
public class BiomeGenerator implements BiomeGeneratorInterface {
    /** The fraction of the original number of tiles that must remain in each biome after contiguity processing */
    private static final double CONTIGUOUS_TILE_RETENTION_THRESHOLD = 0.75;

    /** The `Random` instance being used for world generation. */
    private final Random random;

    /** The number of nodes to be generated in each biome. */
    private final int[] biomeSizes;

    /** The nodes generated in the previous phase of the world generation. */
    private final List<WorldGenNode> nodes;
    /** The edges generated in the previous phase of the world generation. */
    private final List<VoronoiEdge> voronoiEdges;
    /** The nodes that have already been assigned to */
    private HashSet<WorldGenNode> usedNodes;
    /** The nodes that are currently adjacent to a free node. */
    private ArrayList<WorldGenNode> borderNodes;

    /** The biomes generated during the generation process. */
    private ArrayList<BiomeInProgress> biomes;
    /** The actual biomes to fill after generation. */
    private final List<AbstractBiome> realBiomes;

    /** A map from a WorldGenNode to the BiomeInProgress that contains it. */
    private HashMap<WorldGenNode, BiomeInProgress> nodesBiomes;

    /** The node on the center of the map */
    private WorldGenNode centerNode;

    // The number of lakes and rivers
    private int noLakes;
    private int[] lakeSizes;
    private int noRivers;

    // Half the width of a river
    private int riverWidth;

    // TODO Remove `noLakes` parameter.

    /**
     * Creates a {@code BiomeGenerator} for a list of nodes (but does not start the generation).
     *
     * @param nodes      the nodes generated in the previous phase of the world generation
     * @param random     the random number generator used for deterministic generation
     * @param biomeSizes the number of nodes for each of the biomes (except ocean)
     * @param realBiomes the biomes to populate (ocean must be last)
     * @param lakeSizes  the number of nodes assigned to each of the lakes
     *
     * @throws NotEnoughPointsException if there are not enough non-border nodes from which to form the biomes
     */
    public BiomeGenerator(List<WorldGenNode> nodes, List<VoronoiEdge> voronoiEdges, Random random, int[] biomeSizes,
                          List<AbstractBiome> realBiomes,
                          int noLakes, int[] lakeSizes, int noRivers, int riverWidth)
            throws NotEnoughPointsException {
        Objects.requireNonNull(nodes, "nodes must not be null");
        Objects.requireNonNull(random, "random must not be null");
        Objects.requireNonNull(biomeSizes, "biomeSizes must not be null");
        Objects.requireNonNull(realBiomes, "realBiomes must not be null");
        for (AbstractBiome realBiome : realBiomes) {
            Objects.requireNonNull(realBiome, "Elements of realBiome must not be null");
        }

        if (Arrays.stream(biomeSizes).anyMatch(size -> size == 0)) {
            throw new IllegalArgumentException("All biomes must require at least one node");
        }

        if (biomeSizes.length != realBiomes.size()) {
            throw new IllegalArgumentException("The number of biomes must be equal to the number of biome sizes");
        }

        if (nodes.stream().filter(node -> !node.isBorderNode()).count() < Arrays.stream(biomeSizes).sum()) {
            throw new NotEnoughPointsException("Not enough nodes to build biomes");
        }

        this.nodes = nodes;
        this.voronoiEdges = voronoiEdges;
        this.random = random;
        this.biomeSizes = biomeSizes;
        this.realBiomes = realBiomes;
        this.centerNode = calculateCenterNode();
        this.noLakes = noLakes;
        this.noRivers = noRivers;
        this.riverWidth = riverWidth;
        this.lakeSizes = lakeSizes;
    }

    /**
     * Calculates and returns the node which contains the point (0, 0).
     *
     * @return the node which contains (0, 0)
     */
    private WorldGenNode calculateCenterNode() {
        // Start the first biome at the node closest to the centre.
        WorldGenNode centerNode = null;
        // Keep track of the squared distance, because it saves calls to the relatively expensive
        // Math.sqrt().
        double centerDistanceSquared = Double.POSITIVE_INFINITY;
        for (WorldGenNode node : nodes) {
            double x = node.getX();
            double y = node.getY();
            double newCenterDistanceSquared = x * x + y * y;
            if (newCenterDistanceSquared < centerDistanceSquared) {
                centerDistanceSquared = newCenterDistanceSquared;
                centerNode = node;
            }
        }
        return centerNode;
    }

    /**
     * Runs the generation process.
     */
    public void generateBiomes() throws DeadEndGenerationException {
        for (int i = 0; ; i++) {
            try {
                biomes = new ArrayList<>(biomeSizes.length + noLakes + 1);
                usedNodes = new HashSet<>(nodes.size());
                borderNodes = new ArrayList<>();
                nodesBiomes = new HashMap<>();

                growBiomes();
                growOcean();
                fillGaps();
                generateLakes(lakeSizes, noLakes);
                populateRealBiomes();
                ensureContiguity();
                generateRivers(noRivers, riverWidth, random, voronoiEdges);

                return;
            } catch (DeadEndGenerationException e) {
                // Remove tiles from the biomes so they can be reassigned on the next iteration.
                for (AbstractBiome biome : realBiomes) {
                    for (Tile tile : biome.getTiles()) {
                        tile.setBiome(null);
                    }
                    biome.getTiles().clear();
                }
                // Remove all biomes that were added to the list during generation.
                while (realBiomes.size() > biomeSizes.length) {
                    realBiomes.remove(biomeSizes.length);
                }

                // If the generation reached a dead-end, try again.
                if (i >= 5) {
                    throw e;
                }
            }
        }
    }

    /**
     * Spawns and expands the biomes to meet the required number of nodes.
     *
     * @throws DeadEndGenerationException if a biome which needs to grow has no border nodes
     */
    private void growBiomes() throws DeadEndGenerationException {
        for (int biomeID = 0; biomeID < biomeSizes.length; biomeID++) {
            BiomeInProgress biome = new BiomeInProgress(biomeID);
            biomes.add(biome);

            if (biomeID == 0) {
                biome.addNode(centerNode);
            } else {
                // Pick a random point on the border to start the next biome from.
                WorldGenNode node = selectWeightedRandomNode(borderNodes, random);
                ArrayList<WorldGenNode> startNodeCandidates = node.getNeighbours().stream()
                        .filter(BiomeGenerator.this::nodeIsFree)
                        .collect(Collectors.toCollection(ArrayList::new));
                WorldGenNode startNode = startNodeCandidates.get(random.nextInt(startNodeCandidates.size()));
                biome.addNode(startNode);
            }

            biome.growBiome();
        }
    }

    /**
     * Grows the ocean biome from the outside of the world.
     */
    private void growOcean() {
        // All nodes on the outer edge of the map are ocean nodes.
        // Since the id is `biomeSizes.length`,
        BiomeInProgress ocean = new BiomeInProgress(biomeSizes.length);
        biomes.add(ocean);
        for (WorldGenNode node : nodes) {
            if (node.isBorderNode()) {
                ocean.addNode(node);
            }
        }
        ocean.floodGrowBiome();

        realBiomes.add(new OceanBiome());
    }

    /**
     * Fills in unassigned nodes within the continent with adjacent biomes.
     */
    private void fillGaps() {
        while (!borderNodes.isEmpty()) {
            WorldGenNode growFrom = borderNodes.get(random.nextInt(borderNodes.size()));
            BiomeInProgress expandingBiome = nodesBiomes.get(growFrom);

            // Pick a node adjacent to the border node to grow to.
            ArrayList<WorldGenNode> growToCandidates = growFrom.getNeighbours().stream()
                    .filter(BiomeGenerator.this::nodeIsFree)
                    .collect(Collectors.toCollection(ArrayList::new));
            WorldGenNode growTo = growToCandidates.get(random.nextInt(growToCandidates.size()));

            expandingBiome.addNode(growTo);
            for (WorldGenNode adjacentNode : growTo.getNeighbours()) {
                if (usedNodes.contains(adjacentNode) && borderNodes.contains(adjacentNode) &&
                        !nodeIsBorder(adjacentNode)) {
                    borderNodes.remove(adjacentNode);
                }
            }
        }
    }

    /**
     * Randomly generate lakes in landlocked locations (ie not next to the ocean or another lake)
     *
     * @param lakeSizes The number of WorldGenNodes to make each lake out of
     * @param noLakes   The number of lakes to genereate
     *
     * @throws DeadEndGenerationException If a valid position for a lake cannot be found
     */
    private void generateLakes(int[] lakeSizes, int noLakes) throws DeadEndGenerationException {
        List<List<WorldGenNode>> chosenNodes = new ArrayList<>();
        // A list of parent biomes for each lake
        List<BiomeInProgress> maxNodesBiomes = new ArrayList<>();
        // The nodes that have been flagged to be assigned as lakes, but haven't
        // yet
        List<WorldGenNode> tempLakeNodes = new ArrayList<>();
        // A biome for each lake
        List<BiomeInProgress> lakesFound = new ArrayList<>();
        for (int i = 0; i < noLakes; i++) {
            // Nodes found for this lake
            List<WorldGenNode> nodesFound = new ArrayList<>();
            int attempts = 0;
            while (true) {
                attempts++;
                // TODO implement something better than this
                // If there hasn't been a valid spot for a lake found after enough
                // attempts, assume there is no valid spot
                if (attempts > usedNodes.size()) {
                    throw new DeadEndGenerationException();
                }
                // Try to find a valid node to start a lake
                WorldGenNode chosenNode = nodes.get(random.nextInt(nodes.size()));
                if (!validLakeNode(chosenNode, tempLakeNodes)) {
                    continue;
                }

                // Add the initial node
                nodesFound.clear();
                nodesFound.add(chosenNode);

                // Find nodes to expand to
                for (int j = 1; j < lakeSizes[i]; j++) {
                    ArrayList<WorldGenNode> growToCandidates = new ArrayList<>();
                    // All neighbours of lake nodes that are valid via validLakeNode
                    // are possible candidates to grow to
                    for (WorldGenNode node : nodesFound) {
                        for (WorldGenNode neighbour : node.getNeighbours()) {
                            if (validLakeNode(neighbour, tempLakeNodes) && !nodesFound.contains(neighbour)) {
                                growToCandidates.add(neighbour);
                            }
                        }
                    }
                    // Don't attempt to add null
                    if (growToCandidates.size() == 0) {
                        break;
                    }
                    // Add a random candidate
                    WorldGenNode newNode = growToCandidates.get(random.nextInt(growToCandidates.size()));
                    nodesFound.add(newNode);
                }

                //findLakeLocation(nodes, nodes.get(0), lakeSize);

                if (nodesFound.size() < lakeSizes[i]) {
                    continue;
                }
                break;
            }

            // Add the lake
            lakesFound.add(new BiomeInProgress(biomes.size() + i));
            chosenNodes.add(nodesFound);
            tempLakeNodes.addAll(nodesFound);

            // Calculates how many nodes from each biome contribute to the lake
            // To determine the lake's parent biome
            HashMap<BiomeInProgress, Integer> nodesInBiome = new HashMap<>();
            for (WorldGenNode node : nodesFound) {
                BiomeInProgress biome = nodesBiomes.get(node);
                if (!nodesInBiome.containsKey(biome)) {
                    nodesInBiome.put(biome, 1);
                } else {
                    nodesInBiome.put(biome, nodesInBiome.get(biome) + 1);
                }
            }

            // Get the biome that contributes the most nodes
            BiomeInProgress maxNodesBiome = null;
            for (BiomeInProgress biome : nodesInBiome.keySet()) {
                if (maxNodesBiome == null || nodesInBiome.get(biome) > nodesInBiome.get(maxNodesBiome)) {
                    maxNodesBiome = biome;
                }
            }

            maxNodesBiomes.add(maxNodesBiome);

        }

        for (int i = 0; i < lakesFound.size(); i++) {
            BiomeInProgress lake = lakesFound.get(i);
            biomes.add(lake);
            // Add the lake to the list of real biomes in the same position in
            // the list
            realBiomes.add(new LakeBiome(realBiomes.get(maxNodesBiomes.get(i).id)));
            for (WorldGenNode node : chosenNodes.get(i)) {
                // Update the BiomeInProgress that the node is in
                nodesBiomes.get(node).nodes.remove(node);
                lake.addNode(node);
            }
        }
    }

    /**
     * Randomly generate rivers starting from lakes and ending at a lake or the ocean
     * <p>
     * Note: If the river width is not 0 there is a chance a river will terminate when meeting another river instead of
     * passing through it. Currently this is being treated as "it's not a bug it's a feature," as it still looks normal
     * and natural (arguably more natural than if the bug was fixed). I'm guessing the cause is that to get the biome of
     * the adjacent nodes, it gets the biome of node.getTiles().get(0), which can be a lake if some of the tiles have
     * already been overwritten by rivers. This method is still deterministic for a constant riverWidth
     *
     * @param noRivers   The number of rivers to generate
     * @param riverWidth The width of the rivers (the number of tiles wide is 2 * riverWidth + 1)
     * @param random     The random seed to generate the rivers with
     * @param edges      A list of edges that a river can use
     *
     * @throws DeadEndGenerationException If not enough valid rivers can be found
     */
    private void generateRivers(int noRivers, int riverWidth, Random random, List<VoronoiEdge> edges)
            throws DeadEndGenerationException {
        List<BiomeInProgress> lakes = new ArrayList<>();
        // Get a list of lake biomes
        for (BiomeInProgress biome : biomes) {
            // If the biome is a lake
            if (realBiomes.get(biome.id).getBiomeName().equals("lake")) {
                lakes.add(biome);
            }
        }
        // If there are no lakes, there can't be any rivers
        if (lakes.size() == 0) {
            return;
        }
        for (int i = 0; i < noRivers; i++) {
            // Choose a random lake
            BiomeInProgress chosenLake = lakes.get(random.nextInt(lakes.size()));

            VoronoiEdge startingEdge = null;
            double[] startingVertex = null;
            int attempts = 0;
            while (true) {
                // If too many unsuccessful attempts are taken, assume that they
                // world layout does not allow a river to be created
                if (attempts > chosenLake.nodes.size() * 2) {
                    throw new DeadEndGenerationException();
                }
                // Get a random node from the lake
                WorldGenNode node = chosenLake.nodes.get(random.nextInt(chosenLake.nodes.size()));
                attempts++;

                // Only allow the node if it is on the edge of the lake, and
                // has a protruding edge
                if (!hasNeighbourOfDifferentBiome(node, chosenLake)) {
                    continue;
                }
                startingEdge = edgeProtrudingFromBiome(edges, node, chosenLake);
                if (startingEdge == null) {
                    continue;
                }

                // Find which vertex the edge starts with
                if (node.getVertices().contains(startingEdge.getA())) {
                    startingVertex = startingEdge.getA();
                } else {
                    startingVertex = startingEdge.getB();
                }
                break;
            }

            // Generate the path for the river
            List<VoronoiEdge> riverEdges = VoronoiEdge.generatePath(edges, startingEdge, startingVertex, random, 2);

            // Create a river biome and add all tiles for each edge
            AbstractBiome river = new RiverBiome(realBiomes.get(chosenLake.id));
            List<Tile> riverTiles = new ArrayList<>();
            for (VoronoiEdge riverEdge : riverEdges) {
                riverTiles.addAll(riverEdge.getTiles());
            }

            // Expand the river
            for (int j = 0; j < riverWidth; j++) {
                List<Tile> newTiles = new ArrayList<>();
                // For each tile, expand to all it's non-river/ocean neighbours
                for (Tile tile : riverTiles) {
                    for (Integer neighbourID : tile.getNeighbours().keySet()) {
                        Tile neighbour = tile.getNeighbours().get(neighbourID);
                        // Don't expand to oceans or lakes
                        if (!neighbour.getBiome().getBiomeName().equals("ocean") &&
                                !neighbour.getBiome().getBiomeName().equals("lake") &&
                                !riverTiles.contains(neighbour)) {
                            newTiles.add(neighbour);
                        }
                    }
                }
                // Add the tiles found
                riverTiles.addAll(newTiles);
            }

            boolean onSpawn = false;
            // Add the river and all it's tiles
            for (Tile tile : riverTiles) {
                // If the river passes through the origin, it is invalid
                if (tile.getRow() == 0 && tile.getCol() == 0) {
                    onSpawn = true;
                    break;
                }
                river.addTile(tile);
            }
            if (onSpawn) {
                // Decrement i so that the program doesn't think the river has
                // been added
                i--;
                // Don't add the biome
                continue;
            }
            realBiomes.add(river);
        }
    }

    /**
     * Finds whether or not a node has a neighbour with a different biome to it
     *
     * @param node      The node to check
     * @param nodeBiome The biome of the node
     *
     * @return whether or not the node has a neighbour with a different biome to it
     */
    private boolean hasNeighbourOfDifferentBiome(WorldGenNode node, BiomeInProgress nodeBiome) {
        // For each neighbour of the node, if it isn't in nodeBiome, return true
        for (WorldGenNode neighbour : node.getNeighbours()) {
            for (BiomeInProgress biome : biomes) {
                if (biome.nodes.contains(neighbour) && biome != nodeBiome) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Finds an edge protruding from a biome (one vertex is in the biome and the other is not)
     *
     * @param edges a list of edges to check
     * @param node  a node on the edge of the biome
     * @param biome the biome the edge is protruding from
     *
     * @return A VoronoiEdge that has exactly one vertex in the biome, null if there is no such edge
     */
    private VoronoiEdge edgeProtrudingFromBiome(List<VoronoiEdge> edges, WorldGenNode node, BiomeInProgress biome) {
        // TODO make this not loop through all edges every time
        for (VoronoiEdge edge : edges) {
            // If the edge is adjacent to the biome
            if (edge.getEndNodes().contains(node)) {
                boolean protruding = true;
                for (WorldGenNode edgeNode : edge.getEdgeNodes()) {
                    // If an edgeNode is in the biome, the edge is going along
                    // the border of the biome instead of protruding from it
                    // (not what we want)
                    if (biome.nodes.contains(edgeNode)) {
                        protruding = false;
                        break;
                    }
                }

                if (protruding) {
                    return edge;
                }
            }
        }
        return null;
    }

    /**
     * Adds the tiles from the {@code BiomeInProgress}es to the {@code Biome}s provided.
     */
    private void populateRealBiomes() {
        for (int i = 0; i < biomes.size(); i++) {
            BiomeInProgress biome = biomes.get(i);
            AbstractBiome realBiome = realBiomes.get(i);
            for (WorldGenNode node : biome.nodes) {
                for (Tile tile : node.getTiles()) {
                    realBiome.addTile(tile);
                }
            }
        }
    }

    /**
     * Ensures that biomes are contiguous. For small non-contiguous groups of tiles, they are just removed, but for
     * large groups, the generation must be restarted, as the biome size could lose too many tiles.
     *
     * @throws DeadEndGenerationException if too many tiles from a biome are lost
     */
    private void ensureContiguity() throws DeadEndGenerationException {
        // TODO Optimise search using border nodes only.

        HashSet<Tile> removedTiles = new HashSet<>();

        for (AbstractBiome biome : realBiomes) {
            // HashSet<Tile> biomeUncheckedTiles = new HashSet<>(biome.getTiles());
            HashSet<Tile> biomeUncheckedTiles = biome.getDescendantBiomes().stream()
                    .flatMap(descendant -> descendant.getTiles().stream())
                    .collect(Collectors.toCollection(HashSet::new));
            ArrayList<Tile> mainClusterTiles = new ArrayList<>();

            while (!biomeUncheckedTiles.isEmpty()) {
                // If there are fewer remaining tiles in the biome than the main cluster, than the largest cluster must
                // already be found, so break early.
                if (biomeUncheckedTiles.size() <= mainClusterTiles.size()) {
                    removedTiles.addAll(biomeUncheckedTiles);
                    break;
                }

                // The tiles to be checked.
                ArrayDeque<Tile> clusterCheckQueue = new ArrayDeque<>();
                // The tiles known to be in the cluster.
                ArrayList<Tile> clusterTiles = new ArrayList<>();

                // Get the first tile from the biome that hasn't been checked. Note that you can't just take a tile from
                // the unchecked tiles directly because the ordering is not deterministic, so it would break seeding.
                Tile clusterStart = biome.getTiles().stream().filter(biomeUncheckedTiles::contains).findFirst()
                        .orElseThrow(IllegalStateException::new);
                biomeUncheckedTiles.remove(clusterStart);

                clusterCheckQueue.add(clusterStart);

                while (!clusterCheckQueue.isEmpty()) {
                    Tile expandFrom = clusterCheckQueue.remove();
                    // Don't add tiles from sub-biomes to this cluster's tiles.
                    if (expandFrom.getBiome() == biome) {
                        clusterTiles.add(expandFrom);
                    }

                    // Expand the cluster to adjacent tiles in the same biome.
                    for (Tile neighbour : expandFrom.getNeighbours().values()) {
                        if (biomeUncheckedTiles.contains(neighbour) && neighbour.getBiome().isDescendedFrom(biome)) {
                            clusterCheckQueue.add(neighbour);
                            biomeUncheckedTiles.remove(neighbour);
                        }
                    }
                }

                // Keep the biggest cluster of tiles and mark the tile from the other cluster to be removed.
                if (clusterTiles.size() > mainClusterTiles.size()) {
                    removedTiles.addAll(mainClusterTiles);
                    mainClusterTiles = clusterTiles;
                } else {
                    removedTiles.addAll(clusterTiles);
                }
            }

            if (mainClusterTiles.size() < biome.getTiles().size() * CONTIGUOUS_TILE_RETENTION_THRESHOLD) {
                throw new DeadEndGenerationException();
            }
        }

        // Find the border tiles to expand from.
        ArrayList<Tile> borderTiles = new ArrayList<>();
        for (Tile tile : removedTiles) {
            for (Tile neighbour : tile.getNeighbours().values()) {
                if (!removedTiles.contains(neighbour) && !borderTiles.contains(neighbour)) {
                    borderTiles.add(neighbour);
                }
            }
        }
        // Sort this list to make the algorithm deterministic (so it doesn't rely on the order of the `HashSet`).
        borderTiles.sort((a, b) -> (int) Math.floor(a.getCol()) == (int) Math.floor(b.getCol())
                                   ? (int) Math.floor(a.getRow()) - (int) Math.floor(b.getRow())
                                   : (int) Math.floor(a.getCol()) - (int) Math.floor(b.getCol()));

        // Expand outwards from the border tiles into the non-contiguous tiles.
        while (!removedTiles.isEmpty()) {
            Tile expandFrom = borderTiles.get(random.nextInt(borderTiles.size()));
            ArrayList<Tile> expandToCandidates = expandFrom.getNeighbours().values().stream()
                    .filter(removedTiles::contains)
                    .collect(Collectors.toCollection(ArrayList::new));
            Tile expandTo = expandToCandidates.get(random.nextInt(expandToCandidates.size()));
            expandFrom.getBiome().addTile(expandTo);
            removedTiles.remove(expandTo);
            if (expandTo.getNeighbours().values().stream().anyMatch(removedTiles::contains)) {
                borderTiles.add(expandTo);
            }
            for (Tile neighbour : expandTo.getNeighbours().values()) {
                if (neighbour.getNeighbours().values().stream().noneMatch(removedTiles::contains)) {
                    borderTiles.remove(neighbour);
                }
            }
        }
    }

    /**
     * Returns whether the node is adjacent to any free nodes.
     *
     * @param node the node to check
     *
     * @return whether the node is adjacent to any free nodes
     */
    private boolean nodeIsBorder(WorldGenNode node) {
        return node.getNeighbours().stream().anyMatch(this::nodeIsFree);
    }

    /**
     * Returns whether the node is free to be expanded into.
     *
     * @param node the node to check
     *
     * @return whether the node is free to be expanded into.
     */
    private boolean nodeIsFree(WorldGenNode node) {
        return !node.isBorderNode() && !usedNodes.contains(node);
    }

    /**
     * Returns whether the node is a valid lake node
     *
     * @param node          the node to check
     * @param tempLakeNodes the nodes that have already been assigned as lakes
     *
     * @return whether the node is a valid lake node
     */
    private boolean validLakeNode(WorldGenNode node, List<WorldGenNode> tempLakeNodes) {
        // Don't allow the node the player spawns in to be a lake
        if (node == centerNode || tempLakeNodes.contains(node)) {
            return false;
        }

        // Don't allow nodes that are already in other lakes
        List<WorldGenNode> neighbours = node.getNeighbours();
        for (WorldGenNode neighbour : neighbours) {
            if (tempLakeNodes.contains(neighbour)) {
                return false;
            }
        }

        // Loop through each biome to find which one the node is in
        for (int i = 0; i < biomes.size(); i++) {
            String biomeName = realBiomes.get(i).getBiomeName();
            boolean invalidBiome = (biomeName.equals("ocean") || biomeName.equals("lake"));
            // If the node is in a lake or ocean, don't allow it
            if (biomes.get(i).nodes.contains(node) && invalidBiome) {
                return false;
            }
            // Don't allow nodes that are adjacent to the ocean or other lakes
            for (WorldGenNode nodeToTest : neighbours) {
                if (biomes.get(i).nodes.contains(nodeToTest) && invalidBiome) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Selects a random node from the provided list weighted towards nodes closer to (0, 0).
     *
     * @param nodes  the nodes from which to select
     * @param random the RNG used for the selection
     *
     * @return a random node from the provided list
     */
    private static WorldGenNode selectWeightedRandomNode(List<WorldGenNode> nodes, Random random) {
        double sum = nodes.stream().mapToDouble(node -> Math.pow(0.99, node.distanceTo(0, 0))).sum();
        double target = random.nextDouble() * sum;

        for (WorldGenNode node : nodes) {
            sum -= Math.pow(0.99, node.distanceTo(0, 0));
            if (sum < target) {
                return node;
            }
        }
        return nodes.get(nodes.size() - 1);
    }

    /**
     * Represents a single biome during the biome-generation process. This is separate from {@link AbstractBiome}
     * because it contains extra data that is not needed after the generation process.
     */
    private class BiomeInProgress {
        /** The ID of the biome. */
        private int id;

        /** The nodes contained within this biome. */
        private ArrayList<WorldGenNode> nodes;

        /** The nodes on the border of the biome (for growing). */
        private ArrayList<WorldGenNode> borderNodes;

        /**
         * Constructs a new {@code BiomeInProgress} with the specified id.
         *
         * @param id the id of the biome (to check the biome size)
         */
        private BiomeInProgress(int id) {
            this.id = id;

            nodes = new ArrayList<>();
            borderNodes = new ArrayList<>();
        }

        /**
         * Expands the biome outwards randomly until it is the required size.
         *
         * @throws DeadEndGenerationException if a biome which needs to grow has no border nodes
         */
        private void growBiome() throws DeadEndGenerationException {
            for (int remainingNodes = biomeSizes[id] - nodes.size(); remainingNodes > 0; remainingNodes--) {
                if (borderNodes.isEmpty()) {
                    throw new DeadEndGenerationException();
                }

                // Pick a border node to grow from.
                WorldGenNode growFrom = borderNodes.get(random.nextInt(borderNodes.size()));

                // Pick a node adjacent to the border node to grow to.
                ArrayList<WorldGenNode> growToCandidates = growFrom.getNeighbours().stream()
                        .filter(BiomeGenerator.this::nodeIsFree)
                        .collect(Collectors.toCollection(ArrayList::new));
                WorldGenNode growTo = growToCandidates.get(random.nextInt(growToCandidates.size()));

                addNode(growTo);
            }
        }

        /**
         * Expands a biome to fill all contiguous nodes that are not already used.
         */
        private void floodGrowBiome() {
            while (!borderNodes.isEmpty()) {
                // It doesn't matter which node is grown from.
                WorldGenNode growFrom = borderNodes.get(0);

                for (WorldGenNode node : growFrom.getNeighbours()) {
                    if (nodeIsFree(node)) {
                        addNode(node);
                    }
                }
            }
        }

        /**
         * Adds the node to the nodes and updates the border-node states of nodes accordingly.
         *
         * @param node the node to add to the biome
         */
        private void addNode(WorldGenNode node) {
            // Add the new node to the nodes in this biome
            nodes.add(node);
            nodesBiomes.put(node, this);
            usedNodes.add(node);

            // Reassess the border-node status of the surrounding nodes.
            for (WorldGenNode adjacentNode : node.getNeighbours()) {
                if (usedNodes.contains(adjacentNode) && BiomeGenerator.this.borderNodes.contains(adjacentNode) &&
                        !nodeIsBorder(adjacentNode)) {
                    nodesBiomes.get(node).borderNodes.remove(adjacentNode);
                    BiomeGenerator.this.borderNodes.remove(adjacentNode);
                }
            }

            // Check if the current node is a border node.
            if (nodeIsBorder(node)) {
                borderNodes.add(node);
                BiomeGenerator.this.borderNodes.add(node);
            }
        }
    }
}
