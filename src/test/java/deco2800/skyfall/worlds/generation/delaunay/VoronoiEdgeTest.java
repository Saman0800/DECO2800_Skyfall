package deco2800.skyfall.worlds.generation.delaunay;

import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.worlds.generation.WorldGenException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

public class VoronoiEdgeTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void twoEdgeAndEndNodesTest() {
        Random random = new Random(0);
        final int WORLD_SIZE = 15;
        final int NODE_SPACING = 3;
        ArrayList<WorldGenNode> worldGenNodes = new ArrayList<>();
        ArrayList<Tile> tiles = new ArrayList<>();
        ArrayList<VoronoiEdge> voronoiEdges = new ArrayList<>();

        int nodeCount = Math.round((float) WORLD_SIZE * WORLD_SIZE * 4 / NODE_SPACING / NODE_SPACING);
        // TODO: if nodeCount is less than the number of biomes, throw an exception

        for (int i = 0; i < nodeCount; i++) {
            // Sets coordinates to a random number from -WORLD_SIZE to WORLD_SIZE
            float x = (float) (random.nextFloat() - 0.5) * 2 * WORLD_SIZE;
            float y = (float) (random.nextFloat() - 0.5) * 2 * WORLD_SIZE;
            worldGenNodes.add(new WorldGenNode(x, y));
        }

        // Apply Delaunay triangulation to the nodes, so that vertices and
        // adjacencies can be calculated. Also apply Lloyd Relaxation twice
        // for more smooth looking polygons
        try {
            WorldGenNode.calculateVertices(worldGenNodes, WORLD_SIZE);
            WorldGenNode.lloydRelaxation(worldGenNodes, 2, WORLD_SIZE);
        } catch (WorldGenException e) {
            fail();
        }

        for (int q = -WORLD_SIZE; q <= WORLD_SIZE; q++) {
            for (int r = -WORLD_SIZE; r <= WORLD_SIZE; r++) {
                // if (Cube.cubeDistance(Cube.oddqToCube(q, r), Cube.oddqToCube(0, 0)) <=
                // worldSize) {
                float oddCol = (q % 2 != 0 ? 0.5f : 0);

                Tile tile = new Tile(q, r + oddCol);
                tiles.add(tile);
                // }
            }
        }

        try {
            WorldGenNode.assignTiles(worldGenNodes, tiles, random, NODE_SPACING);
            WorldGenNode.removeZeroTileNodes(worldGenNodes, WORLD_SIZE);
            WorldGenNode.assignNeighbours(worldGenNodes, voronoiEdges);
        } catch (WorldGenException e) {
            fail();
        }

        VoronoiEdge.assignTiles(voronoiEdges, tiles, WORLD_SIZE);
        VoronoiEdge.assignNeighbours(voronoiEdges);

        for (VoronoiEdge edge : voronoiEdges) {
            assertEquals(2, edge.getEdgeNodes().size());
            assertTrue(edge.getEdgeNodes().size() >= 2);
        }
    }

    @Test
    public void otherVertexTest() {
        VoronoiEdge edge = new VoronoiEdge(new double[] {2, 6}, new double[] {-3.6, 1.7});

        assertNull(edge.otherVertex(new double[] {0, 0}));
        assertNull(edge.otherVertex(new double[] {-3.6, 6}));

        assertEquals(-3.6, edge.otherVertex(new double[] {2, 6})[0], 0.00001);
        assertEquals(1.7, edge.otherVertex(new double[] {2, 6})[1], 0.00001);
        assertEquals(2, edge.otherVertex(new double[] {-3.6, 1.7})[0], 0.00001);
        assertEquals(6, edge.otherVertex(new double[] {-3.6, 1.7})[1], 0.00001);
    }

    @Test
    public void assignNeighbours() {
        double[] point1 = {0, 0};
        double[] point2 = {-5, 2};
        double[] point3 = {1, 9};
        double[] point4 = {0.5, 1};

        VoronoiEdge edge1 = new VoronoiEdge(point1, point2);
        VoronoiEdge edge2 = new VoronoiEdge(point3, point2);
        VoronoiEdge edge3 = new VoronoiEdge(point1, point3);
        VoronoiEdge edge4 = new VoronoiEdge(point2, point4);
        VoronoiEdge edge5 = new VoronoiEdge(point4, point3);

        List<VoronoiEdge> edges = new ArrayList<>();
        edges.add(edge1);
        edges.add(edge2);
        edges.add(edge3);
        edges.add(edge4);
        edges.add(edge5);

        // Add this as an edge node to prevent a null pointer exception that isn't
        // being tested
        WorldGenNode node = new WorldGenNode(1, 1);

        for (VoronoiEdge edge : edges) {
            edge.addEdgeNode(node);
        }

        VoronoiEdge.assignNeighbours(edges);

        assertNull(edge1.getVertexSharingEdges(point3));
        assertNull(edge1.getVertexSharingEdges(new double[] {1, 1}));

        assertEquals(2, edge2.getVertexSharingEdges(point3).size());
        assertEquals(2, edge3.getVertexSharingEdges(point3).size());
        assertEquals(2, edge5.getVertexSharingEdges(point3).size());
        assertTrue(edge2.getVertexSharingEdges(point3).contains(edge3));
        assertTrue(edge3.getVertexSharingEdges(point3).contains(edge2));
        assertTrue(edge2.getVertexSharingEdges(point3).contains(edge5));
        assertTrue(edge5.getVertexSharingEdges(point3).contains(edge2));
        assertTrue(edge3.getVertexSharingEdges(point3).contains(edge5));
        assertTrue(edge5.getVertexSharingEdges(point3).contains(edge3));

        assertEquals(1, edge3.getVertexSharingEdges(point1).size());
        assertTrue(edge3.getVertexSharingEdges(point1).contains(edge1));
        assertTrue(edge1.getVertexSharingEdges(point1).contains(edge3));
    }
}
