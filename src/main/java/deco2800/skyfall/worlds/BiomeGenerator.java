package deco2800.skyfall.worlds;

import deco2800.skyfall.worlds.delaunay.NotEnoughPointsException;
import deco2800.skyfall.worlds.delaunay.WorldGenNode;
import deco2800.skyfall.worlds.delaunay.InvalidCoordinatesException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Builds biomes from the nodes generated in the previous phase of the world generation.
 */
class BiomeGenerator {
    // The `Random` instance being used for world generation.
    private final Random random;

    // The number of nodes to be generated in each biome.
    private final int[] biomeSizes;

    // The nodes generated in the previous phase of the world generation.
    private final List<WorldGenNode> nodes;
    // The nodes that have already been assigned to
    private HashSet<WorldGenNode> usedNodes;
    // The nodes that are currently adjacent to a free node.
    private ArrayList<WorldGenNode> borderNodes;

    // The biomes generated during the generation process.
    private ArrayList<BiomeInProgress> biomes;
    // The actual biomes to fill after generation.
    private final List<AbstractBiome> realBiomes;

    private HashMap<WorldGenNode, BiomeInProgress> nodesBiomes;

    public static void generateBiomes(List<WorldGenNode> nodes, Random random, int[] biomeSizes,
                                      List<AbstractBiome> biomes) throws NotEnoughPointsException {
        BiomeGenerator biomeGenerator = new BiomeGenerator(nodes, random, biomeSizes, biomes);
        biomeGenerator.generateBiomesInternal();
    }

    /**
     * Creates a {@code BiomeGenerator} for a list of nodes (but does not start the generation).
     * <p>
     * Behaviour is not defined if any of the arguments are externally modified in the duration of the lifetime of this
     * object.
     *
     * @param nodes the nodes generated in the previous phase of the world generation
     */
    private BiomeGenerator(List<WorldGenNode> nodes, Random random, int[] biomeSizes, List<AbstractBiome> realBiomes)
            throws NotEnoughPointsException {
        Objects.requireNonNull(nodes, "nodes must not be null");
        Objects.requireNonNull(nodes, "random must not be null");
        Objects.requireNonNull(nodes, "biomeSizes must not be null");
        Objects.requireNonNull(nodes, "realBiomes must not be null");
        for (AbstractBiome realBiome : realBiomes) {
            Objects.requireNonNull(realBiome, "Elements of realBiome must not be null");
        }

        if (Arrays.stream(biomeSizes).anyMatch(size -> size == 0)) {
            throw new IllegalArgumentException("All biomes must require at least one node");
        }

        if (biomeSizes.length + 1 != realBiomes.size()) {
            throw new IllegalArgumentException(
                    "The number of biomes must be one greater than the number of biome sizes");
        }

        if (nodes.stream().filter(node -> !node.isBorderNode()).count() < Arrays.stream(biomeSizes).sum()) {
            throw new NotEnoughPointsException("Not enough nodes to build biomes");
        }

        this.nodes = nodes;
        this.random = random;
        this.biomeSizes = biomeSizes;
        this.realBiomes = realBiomes;
    }

    /**
     * Runs the generation process.
     */
    private void generateBiomesInternal() {
        while (true) {
            try {
                biomes = new ArrayList<>(biomeSizes.length + 1);
                usedNodes = new HashSet<>(nodes.size());
                borderNodes = new ArrayList<>();
                nodesBiomes = new HashMap<>();

                growBiomes();
                // TODO Remove.
                assert borderNodes.stream().allMatch(this::nodeIsBorder);
                growOcean();
                fillGaps();
                populateRealBiomes();

                return;
            } catch (DeadEndGenerationException ignored) {
                // If the generation reached a dead-end, try again.
            }
        }
    }

    private void growBiomes() throws DeadEndGenerationException {
        for (int biomeID = 0; biomeID < biomeSizes.length; biomeID++) {
            BiomeInProgress biome = new BiomeInProgress(biomeID);
            biomes.add(biome);

            if (biomeID == 0) {
                // Start the first biome at the node closest to the centre.
                WorldGenNode centerNode = null;
                // Keep track of the squared distance, because it saves calls to the relatively expensive
                // Math.sqrt().
                double centerDistanceSquared = Double.POSITIVE_INFINITY;
                for (WorldGenNode node : nodes) {
                    try {
                        double[] centroid = node.getCentroid();
                        double newCenterDistanceSquared = centroid[0] * centroid[0] + centroid[1] * centroid[1];
                        if (newCenterDistanceSquared < centerDistanceSquared) {
                            centerDistanceSquared = newCenterDistanceSquared;
                            centerNode = node;
                        }
                    } catch (InvalidCoordinatesException e) {
                        // TODO handle this
                    }
                }
                biome.addNode(centerNode);
            } else {
                // Pick a random point on the border to start the next biome from.
                WorldGenNode node = borderNodes.get(random.nextInt(borderNodes.size()));
                ArrayList<WorldGenNode> startNodeCandidates = node.getNeighbours().stream()
                        .filter(BiomeGenerator.this::nodeIsFree)
                        .collect(Collectors.toCollection(ArrayList::new));
                WorldGenNode startNode = startNodeCandidates.get(random.nextInt(startNodeCandidates.size()));
                biome.addNode(startNode);
            }

            biome.growBiome();
        }
    }

    private void growOcean() {
        // All nodes on the outer edge of the map are ocean nodes.
        // Since the id is `biomeSizes.length`,
        // TODO Check that id being biomeSizes.length can never cause an IndexOutOfBoundsException.
        BiomeInProgress ocean = new BiomeInProgress(biomeSizes.length);
        biomes.add(ocean);
        for (WorldGenNode node : nodes) {
            if (node.isBorderNode()) {
                ocean.addNode(node);
            }
        }
        ocean.floodGrowBiome();
    }

    private void fillGaps() {
        while (!borderNodes.isEmpty()) {
            WorldGenNode growFrom = borderNodes.get(random.nextInt(borderNodes.size()));

            // TODO Sort this out.
            @SuppressWarnings("OptionalGetWithoutIsPresent")
            BiomeInProgress expandingBiome =
                    biomes.stream().filter(biome -> biome.nodes.contains(growFrom)).findFirst().get();

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

    /*
     * Is the node adjacent to any free nodes?
     */
    private boolean nodeIsBorder(WorldGenNode node) {
        return node.getNeighbours().stream().anyMatch(this::nodeIsFree);
    }

    /*
     * Is the node free to be expanded into?
     */
    private boolean nodeIsFree(WorldGenNode node) {
        return !node.isBorderNode() && !usedNodes.contains(node);
    }

    /**
     * Represents a single biome during the biome-generation process. This is separate from {@link AbstractBiome}
     * because it contains extra data that is not needed after the generation process.
     */
    private class BiomeInProgress {
        // The ID of the biome.
        int id;

        // The nodes contained within this biome.
        ArrayList<WorldGenNode> nodes;

        // The nodes on the border of the biome (for growing).
        ArrayList<WorldGenNode> borderNodes;

        // The tiles contained within this biome.
        ArrayList<Tile> tiles;
        // The tiles on the border of the biome (for edge fuzzing/noise).
        ArrayList<Tile> edgeTiles;

        BiomeInProgress(int id) {
            this.id = id;

            nodes = new ArrayList<>();
            borderNodes = new ArrayList<>();
        }

        /**
         * Expands the biome outwards randomly until it is the required size.
         */
        void growBiome() throws DeadEndGenerationException {
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

        void floodGrowBiome() {
            while (borderNodes.size() > 0) {
                // It doesn't matter which node is grown from.
                WorldGenNode growFrom = borderNodes.get(0);

                for (WorldGenNode node : growFrom.getNeighbours()) {
                    if (nodeIsFree(node)) {
                        addNode(node);
                    }
                }
            }
        }

        // Adds the node to the nodes and updates the border-node states of nodes accordingly.
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

        void calculateTiles() {
            tiles = new ArrayList<>();
            for (WorldGenNode node : nodes) {
                tiles.addAll(node.getTiles());
            }

            // TODO Calculate edge tiles.
        }
    }
}
