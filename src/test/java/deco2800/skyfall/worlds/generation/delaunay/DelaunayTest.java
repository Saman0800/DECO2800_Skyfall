package deco2800.skyfall.worlds.generation.delaunay;

import deco2800.skyfall.worlds.biomes.ForestBiome;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.worlds.generation.WorldGenException;
import deco2800.skyfall.worlds.generation.perlinnoise.NoiseGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.*;

public class DelaunayTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void compareToTest() {
        WorldGenNode node1 = new WorldGenNode(0, 0);
        WorldGenNode node2 = new WorldGenNode(1, 0);
        WorldGenNode node3 = new WorldGenNode(0, 1);
        WorldGenNode node4 = new WorldGenNode(1, 1);
        WorldGenNode node5 = new WorldGenNode(1, 1);

        try {
            node1.compareTo(null);
            fail();
        } catch (NullPointerException e) {
            // do nothing
        }

        assertEquals(-1, node1.compareTo(node2));
        assertEquals(1, node2.compareTo(node1));
        assertEquals(-1, node2.compareTo(node3));
        assertEquals(1, node3.compareTo(node2));
        assertEquals(-1, node3.compareTo(node4));
        assertEquals(1, node4.compareTo(node3));
        assertEquals(0, node4.compareTo(node5));
    }

    @Test
    public void triangleCircumcircleTest() {
        WorldGenNode node1 = new WorldGenNode(0, 0);
        WorldGenNode node2 = new WorldGenNode(1, 0);
        WorldGenNode node3 = new WorldGenNode(0, 1);

        WorldGenNode node4 = new WorldGenNode(2, 5);
        WorldGenNode node5 = new WorldGenNode(3, 3);
        WorldGenNode node6 = new WorldGenNode(-2, 1);

        WorldGenTriangle triangle1 = new WorldGenTriangle(node1, node2, node3);
        WorldGenTriangle triangle2 = new WorldGenTriangle(node4, node5, node6);
        double[] circumcentre1 = { 0, 0 };
        double[] circumcentre2 = { 0, 0 };

        // Test that the exception is not thrown when it shouldn't be
        try {
            circumcentre1 = triangle1.circumcentre();
            circumcentre2 = triangle2.circumcentre();
        } catch (CollinearPointsException e) {
            fail();
        }
        assertEquals(2, circumcentre1.length);
        assertEquals(0.5, circumcentre1[0], 0);
        assertEquals(0.5, circumcentre1[1], 0);
        assertEquals(2, circumcentre2.length);
        assertEquals(1.0d / 6.0d, circumcentre2[0], 0.0001);
        assertEquals(17.0d / 6.0d, circumcentre2[1], 0.0001);

        // Test that it correctly throws exception
        WorldGenNode node7 = new WorldGenNode(-1, 0);
        WorldGenNode node8 = new WorldGenNode(5, 5);
        WorldGenTriangle triangle3 = new WorldGenTriangle(node1, node2, node7);
        WorldGenTriangle triangle4 = new WorldGenTriangle(node1, node5, node8);
        double[] circumcentre3 = { 0, 0 };
        double[] circumcentre4 = { 0, 0 };

        try {
            circumcentre3 = triangle3.circumcentre();
            fail();
        } catch (CollinearPointsException e) {
            // Do nothing
        }
        try {
            circumcentre4 = triangle4.circumcentre();
            fail();
        } catch (CollinearPointsException e) {
            // Do nothing
        }
    }

    @Test
    public void testAllNeighboursShareAVertex() {

        int nodeCount = 200;
        int worldSize = 500;
        Random random = new Random(0);

        List<WorldGenNode> nodes = new ArrayList<>();
        for (int i = 0; i < nodeCount; i++) {
            // Random number from -500 to 500
            double x = (random.nextFloat() - 0.5) * 2 * worldSize;
            double y = (random.nextFloat() - 0.5) * 2 * worldSize;
            nodes.add(new WorldGenNode(x, y));
        }

        try {
            WorldGenNode.calculateVertices(nodes, worldSize);
            WorldGenNode.assignNeighbours(nodes, new ArrayList<>());
        } catch (WorldGenException e) {
            fail();
        }

        for (WorldGenNode node : nodes) {
            assertNotEquals(0, node.getVertices().size());
            assertNotEquals(0, node.getNeighbours().size());
            for (WorldGenNode neighbour : node.getNeighbours()) {
                int commonVertices = 0;

                for (double[] thisVertex : node.getVertices()) {
                    for (double[] otherVertex : neighbour.getVertices()) {
                        if (thisVertex[0] == otherVertex[0] && thisVertex[1]
                                == otherVertex[1]) {
                            commonVertices += 1;
                        }
                    }
                }

                if (commonVertices == 0) {
                    fail();
                }
            }
        }
    }

    @Test
    public void assignTilesTest() {
        // Keep these parameters small or this test will take a LONG time
        // (method has O(nodeCount*worldSize^2) time complexity)
        int nodeSpacing = 27;
        int worldSize = 30;
        int nodeCount = Math.round((float) worldSize * worldSize * 4 / nodeSpacing / nodeSpacing);
        Random random = new Random(1);

        List<WorldGenNode> nodes = new ArrayList<>();
        List<Tile> tiles = new ArrayList<>();
        for (int i = 0; i < nodeCount; i++) {
            // Random number from -30 to 30
            double x = (random.nextFloat() - 0.5) * 2 * worldSize;
            double y = (random.nextFloat() - 0.5) * 2 * worldSize;
            nodes.add(new WorldGenNode(x, y));
        }

        ForestBiome biome = new ForestBiome();

        // Fill world with tiles
        for (int q = -worldSize; q < worldSize; q++) {
            for (int r = -worldSize; r < worldSize; r++) {
                float oddCol = (q % 2 != 0 ? 0.5f : 0);

                // String type = "grass_" + elevation;
                Tile tile = new Tile(q, r + oddCol);
                tiles.add(tile);
                biome.addTile(tile);
            }
        }

        long noiseSeed = random.nextLong();
        Random noiseRandom1 = new Random(noiseSeed);
        Tile.setNoiseGenerators(noiseRandom1, nodeSpacing);
        for (Tile tile : tiles) {
            tile.assignNode(nodes, nodeSpacing);
        }

        Random noiseRandom2 = new Random(noiseSeed);
        int startPeriod = nodeSpacing * 2;
        int octaves = Math.max((int) Math.ceil(Math.log(startPeriod) / Math.log(2)) - 1, 1);
        double attenuation = Math.pow(0.9, 1d / octaves);
        NoiseGenerator xGen = new NoiseGenerator(noiseRandom2, octaves, startPeriod, attenuation);
        NoiseGenerator yGen = new NoiseGenerator(noiseRandom2, octaves, startPeriod, attenuation);

        // Check that nodes are sorted
        for (int i = 0; i < nodes.size() - 1; i++) {
            assertTrue(nodes.get(i).getY() <= nodes.get(i + 1).getY());
        }

        for (Tile tile : tiles) {
            double minDistanceToNode = Double.POSITIVE_INFINITY;
            int index = 0;

            double tileX =
                    tile.getCol() + xGen.getOctavedPerlinValue(tile.getCol(), tile.getRow()) * (double) nodeSpacing -
                            (double) nodeSpacing / 2;
            double tileY =
                    tile.getRow() + yGen.getOctavedPerlinValue(tile.getCol(), tile.getRow()) * (double) nodeSpacing -
                            (double) nodeSpacing / 2;

            // Find the closest node
            for (int i = 0; i < nodes.size(); i++) {
                double distance = nodes.get(i).distanceTo(tileX, tileY);
                if (minDistanceToNode > distance) {
                    minDistanceToNode = distance;
                    index = i;
                }
            }

            // Check that the tile is in the list of tiles for that node, and no
            // other node
            for (WorldGenNode node : nodes) {
                if (node == nodes.get(index)) {
                    assertTrue(node.getTiles().contains(tile));
                } else {
                    assertFalse(node.getTiles().contains(tile));
                }
            }
        }
    }

    @Test
    public void distanceCalcTest() {
        WorldGenNode node1 = new WorldGenNode(2, 5);
        WorldGenNode node2 = new WorldGenNode(-3.26, 1.00492);

        ForestBiome biome = new ForestBiome();
        Tile tile1 = new Tile(0, 2);
        Tile tile2 = new Tile(-1, 1.5f);

        // Expected values calculated using calculator
        assertEquals(13, node1.distanceToTile(tile1), 0);
        assertEquals(9, node1.yDistanceToTile(tile1), 0);
        assertEquals(21.25, node1.distanceToTile(tile2), 0);
        assertEquals(12.25, node1.yDistanceToTile(tile2), 0);
        // Use non-zero delta when many decimal places are involved and numbers
        // can't necessarily be exactly written in base 2
        assertEquals(11.61778, node2.distanceToTile(tile1), 0.00001);
        assertEquals(0.99018, node2.yDistanceToTile(tile1), 0.00001);
        assertEquals(5.35270, node2.distanceToTile(tile2), 0.00001);
        assertEquals(0.24510, node2.yDistanceToTile(tile2), 0.00001);
    }

    @Test
    public void nodeVectorAlgebraTest() {
        WorldGenNode node1 = new WorldGenNode(2, 5);
        WorldGenNode node2 = new WorldGenNode(-3.26, 1.00492);
        double[] addCoords = { node1.add(node2).getX(), node1.add(node2).getY() };
        double[] subCoords = { node1.subtract(node2).getX(),
                               node1.subtract(node2).getY() };
        double[] scaleCoords = { node2.scalarMultiply(2.453).getX(),
                                 node2.scalarMultiply(2.453).getY() };
        double magnitude = node2.magnitude();
        double dot = node1.dotProduct(node2);
        double cross = node1.crossProduct(node2);
        assertEquals(-1.26, addCoords[0], 0.00001);
        assertEquals(6.00492, addCoords[1], 0.00001);
        assertEquals(5.26, subCoords[0], 0.00001);
        assertEquals(3.99508, subCoords[1], 0.00001);
        assertEquals(-7.99678, scaleCoords[0], 0.00001);
        assertEquals(2.46507, scaleCoords[1], 0.00001);
        assertEquals(3.41137, magnitude, 0.00001);
        assertEquals(-1.4954, dot, 0.00001);
        assertEquals(18.30984, cross, 0.00001);
    }

    @Test
    public void centroidTest() {
        WorldGenNode node1 = new WorldGenNode(15.23, -42.72);

        // Test with no vertices
        assertEquals(15.23, node1.getCentroid()[0], 0.00001);
        assertEquals(-42.72, node1.getCentroid()[1], 0.00001);

        // Test with one vertex
        node1.addVertex(new double[] { 1, 1 });
        assertEquals(1, node1.getCentroid()[0], 0);
        assertEquals(1, node1.getCentroid()[1], 0);

        // Test with many vertices
        node1.addVertex(new double[] { -5.2837, 3.5838 });
        node1.addVertex(new double[] { 12.9318, 1.1243 });
        assertEquals(2.8827, node1.getCentroid()[0], 0.00001);
        assertEquals(1.9027, node1.getCentroid()[1], 0.00001);
    }

    @Test
    public void triangulatePointsTest() {
        List<WorldGenNode> nodes = new ArrayList<>();
        // No nodes
        try {
            WorldGenNode.triangulate(nodes);
            fail();
        } catch (NotEnoughPointsException e) {
            // do nothing
        }

        // 2 nodes
        nodes.add(new WorldGenNode(-1, 0));
        nodes.add(new WorldGenNode(1, 0));
        try {
            WorldGenNode.triangulate(nodes);
            fail();
        } catch (NotEnoughPointsException e) {
            // do nothing
        }

        // 3 nodes
        nodes.add(new WorldGenNode(0, 1));
        try {
            WorldGenNode.triangulate(nodes);
        } catch (NotEnoughPointsException e) {
            fail();
        }

        // Reset node values
        nodes.clear();
        nodes.add(new WorldGenNode(-1, 0));
        nodes.add(new WorldGenNode(1, 0));
        nodes.add(new WorldGenNode(0, 1));
        nodes.add(new WorldGenNode(0, -0.5));
        nodes.add(new WorldGenNode(0, 0));
        // Check that it works correctly when a node is on an edge
        try {
            WorldGenNode.triangulate(nodes);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void sharedVertexTest() {
        WorldGenNode node1 = new WorldGenNode(0, 0);
        WorldGenNode node2 = new WorldGenNode(1, 1);
        WorldGenNode node3 = new WorldGenNode(-1, -1);

        node1.addVertex(new double[] {2, 2});
        node2.addVertex(new double[] {2, 2});
        node1.addVertex(new double[] {2, 3});
        node2.addVertex(new double[] {2, 3});
        node1.addVertex(new double[] {-2, 2});
        node2.addVertex(new double[] {2, -2});
        node3.addVertex(new double[] {-2, -2});

        assertNull(WorldGenNode.sharedVertex(node1, node3, null));
        assertNull(WorldGenNode.sharedVertex(node2, node3, null));
        double[] sharedVertex1 = WorldGenNode.sharedVertex(node1, node2, null);
        assertNotNull(sharedVertex1);
        double[] sharedVertex2 = WorldGenNode.sharedVertex(node1, node2, sharedVertex1);
        assertNotNull(sharedVertex2);
        assertFalse(Arrays.equals(sharedVertex1, sharedVertex2));
        double[] sharedVertex3 = WorldGenNode.sharedVertex(node1, node2, sharedVertex2);
        assertNotNull(sharedVertex3);
        assertTrue(Arrays.equals(sharedVertex3, sharedVertex1));
    }

    @Test
    public void removeZeroTileNodesTest() {
        // TODO simulate noise
        Random random = new Random(0);
        List<Tile> tiles = new ArrayList<>();
        for (int q = -5; q <= 5; q++) {
            for (int r = -5; r <= 5; r++) {
                float oddCol = (q % 2 != 0 ? 0.5f : 0);

                Tile tile = new Tile(q, r + oddCol);
                tiles.add(tile);
            }
        }

        List<WorldGenNode> nodes = new ArrayList<>();
        WorldGenNode nodeToRemove = new WorldGenNode(0.5, 0.5);
        nodes.add(nodeToRemove);
        nodes.add(new WorldGenNode(0.6, 0.6));
        nodes.add(new WorldGenNode(0.4, 0.4));
        nodes.add(new WorldGenNode(0.55, 0.45));
        nodes.add(new WorldGenNode(0.5, 0.6));
        /*for (Tile tile : tiles) {
            tile.assignNode(nodes, );
        }
        WorldGenNode.assignTiles(nodes, tiles, random, 1);
        */
        try {
            WorldGenNode.removeZeroTileNodes(nodes, 5, 5);
        } catch  (WorldGenException e) {
            fail();
        }
        assertEquals(4, nodes.size());
        assertFalse(nodes.contains(nodeToRemove));
    }
}