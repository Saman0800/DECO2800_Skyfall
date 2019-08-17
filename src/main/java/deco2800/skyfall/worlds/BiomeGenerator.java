package deco2800.skyfall.worlds;

import deco2800.skyfall.worlds.delaunay.WorldGenNode;
import deco2800.skyfall.worlds.delaunay.InvalidCoordinatesException;
import deco2800.skyfall.worlds.delaunay.WorldGenNode;

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
    final List<WorldGenNode> nodes;
    // The nodes that have already been assigned to
    HashSet<WorldGenNode> usedNodes;
    // The nodes that are currently adjacent to a free node.
    ArrayList<WorldGenNode> borderNodes;

    // The biomes generated during the generation process.
    ArrayList<BiomeInProgress> biomes;

    /**
     * Creates a {@code BiomeGenerator} for a list of nodes (but does not start the generation).
     * <p>
     * Behaviour is not defined if any of the arguments are externally modified in the duration of the lifetime of this
     * object.
     *
     * @param nodes the nodes generated in the previous phase of the world generation
     */
    public BiomeGenerator(List<WorldGenNode> nodes, Random random, int[] biomeSizes) {
        if (Arrays.stream(biomeSizes).anyMatch(size -> size == 0)) {
            throw new IllegalArgumentException("All biomes must require at least one node");
        }

        if (nodes.stream().filter(node -> !node.isBorderNode()).count() < Arrays.stream(biomeSizes).sum()) {
            throw new IllegalArgumentException("Not enough nodes to build biomes");
        }

        this.nodes = nodes;
        this.random = random;
        this.biomeSizes = biomeSizes;
    }

    /**
     * Runs the generation process.
     */
    public void generateBiomes() {
        while (true) {
            try {
                biomes = new ArrayList<>();
                usedNodes = new HashSet<>();

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
                            double[] centroid = {0, 0};
                            try {
                                centroid = node.getCentroid();
                            } catch (InvalidCoordinatesException e) {
                                // TODO handle this
                            }
                            double newCenterDistanceSquared = centroid[0] * centroid[0] + centroid[1] * centroid[1];
                            if (newCenterDistanceSquared < centerDistanceSquared) {
                                centerDistanceSquared = newCenterDistanceSquared;
                                centerNode = node;
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

                // TODO Remove.
                assert borderNodes.stream().allMatch(this::nodeIsBorder);

                // All nodes on the outer edge of the map are ocean nodes.
                // TODO Check that id being biomeSizes.length can never cause an IndexOutOfBoundsException.
                BiomeInProgress ocean = new BiomeInProgress(biomeSizes.length);
                for (WorldGenNode node : nodes) {
                    if (node.isBorderNode()) {
                        ocean.addNode(node);
                    }
                }
                ocean.floodGrowBiome();

                // Fill in any gaps.
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

                // TODO Return Biome[] once Biome is implemented.
                return;
            } catch (DeadEndGenerationException ignored) {
                // If the generation reached a dead-end, try again.
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

    // TODO Change `@code` to `@link` once the `Biome` class is implemented.

    /*
     * Represents a single biome during the biome-generation process. This is separate from {@code Biome} because it
     * contains extra data that is not needed after the generation process.
     */
    private class BiomeInProgress {
        // The ID of the biome.
        int id;

        // The nodes contained within this biome.
        ArrayList<WorldGenNode> nodes;

        // TODO Remove.
        // The nodes on the border of the biome (for growing).
        // ArrayList<WorldGenNode> borderNodes;

        // The tiles contained within this biome.
        ArrayList<Tile> tiles;
        // The tiles on the border of the biome (for edge fuzzing/noise).
        ArrayList<Tile> edgeTiles;

        BiomeInProgress(int id) {
            this.id = id;

            nodes = new ArrayList<>();
            // TODO Remove.
            // borderNodes = new ArrayList<>();
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
            usedNodes.add(node);

            // Reassess the border-node status of the surrounding nodes.
            for (WorldGenNode adjacentNode : node.getNeighbours()) {
                if (usedNodes.contains(adjacentNode) && borderNodes.contains(adjacentNode) &&
                        !nodeIsBorder(adjacentNode)) {
                    borderNodes.remove(adjacentNode);
                }
            }

            // Check if the current node is a border node.
            if (nodeIsBorder(node)) {
                borderNodes.add(node);
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
