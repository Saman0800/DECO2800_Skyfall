package deco2800.skyfall.worlds.generation;

import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.worlds.biomes.AbstractBiome;
import deco2800.skyfall.worlds.generation.delaunay.NotEnoughPointsException;
import deco2800.skyfall.worlds.generation.delaunay.WorldGenNode;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Builds biomes from the nodes generated in the previous phase of the world generation.
 */
public class BiomeGenerator {
    /** The `Random` instance being used for world generation. */
    private final Random random;

    /** The number of nodes to be generated in each biome. */
    private final int[] biomeSizes;

    /** The nodes generated in the previous phase of the world generation. */
    private final List<WorldGenNode> nodes;
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

    /**
     * Generates biomes and populates the provided {@link AbstractBiome} instances with tiles.
     *
     * @param nodes      the nodes generated in the previous phase of the world generation
     * @param random     the random number generator used for deterministic generation
     * @param biomeSizes the number of nodes for each of the biomes (except ocean)
     * @param biomes     the biomes to populate (ocean must be last)
     *
     * @throws NotEnoughPointsException if there are not enough non-border nodes from which to form the biomes
     */

    public static void generateBiomes(List<WorldGenNode> nodes, Random random, int[] biomeSizes,
                                         List<AbstractBiome> biomes) throws NotEnoughPointsException {
        BiomeGenerator biomeGenerator = new BiomeGenerator(nodes, random, biomeSizes, biomes);
        biomeGenerator.generateBiomesInternal();
    }

    /**
     * Creates a {@code BiomeGenerator} for a list of nodes (but does not start the generation).
     *
     * @param nodes      the nodes generated in the previous phase of the world generation
     * @param random     the random number generator used for deterministic generation
     * @param biomeSizes the number of nodes for each of the biomes (except ocean)
     * @param realBiomes the biomes to populate (ocean must be last)
     *
     * @throws NotEnoughPointsException if there are not enough non-border nodes from which to form the biomes
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

                if (centerNode != null) {
                    biome.addNode(centerNode);
                }

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
     * Represents a single biome during the biome-generation process. This is separate from {@link AbstractBiome}
     * because it contains extra data that is not needed after the generation process.
     */
    private class BiomeInProgress {
        /** The ID of the biome. */
        int id;

        /** The nodes contained within this biome. */
        ArrayList<WorldGenNode> nodes;

        /** The nodes on the border of the biome (for growing). */
        ArrayList<WorldGenNode> borderNodes;

        /** The tiles contained within this biome. */
        ArrayList<Tile> tiles;
        /** The tiles on the border of the biome (for edge fuzzing/noise). */
        ArrayList<Tile> edgeTiles;

        /**
         * Constructs a new {@code BiomeInProgress} with the specified id.
         *
         * @param id the id of the biome (to check the biome size)
         */
        BiomeInProgress(int id) {
            this.id = id;

            nodes = new ArrayList<>();
            borderNodes = new ArrayList<>();
        }

        /**
         * Expands the biome outwards randomly until it is the required size.
         *
         * @throws DeadEndGenerationException if a biome which needs to grow has no border nodes
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

        /**
         * Expands a biome to fill all contiguous nodes that are not already used.
         */
        void floodGrowBiome() {
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
