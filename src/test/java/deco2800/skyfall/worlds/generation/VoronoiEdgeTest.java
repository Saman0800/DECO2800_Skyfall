package deco2800.skyfall.worlds.generation;

import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.worlds.generation.VoronoiEdge;
import deco2800.skyfall.worlds.generation.WorldGenException;
import deco2800.skyfall.worlds.generation.delaunay.WorldGenNode;
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
    public void otherVertexTest() {
        VoronoiEdge edge = new VoronoiEdge(new double[] {2, 6}, new double[] {-3.6, 1.7}, null);

        assertArrayEquals(new double[0], edge.otherVertex(new double[] {0, 0}), 0);
        assertArrayEquals(new double[0], edge.otherVertex(new double[] {-3.6, 6}), 0);

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

        VoronoiEdge edge1 = new VoronoiEdge(point1, point2, null);
        VoronoiEdge edge2 = new VoronoiEdge(point3, point2, null);
        VoronoiEdge edge3 = new VoronoiEdge(point1, point3, null);
        VoronoiEdge edge4 = new VoronoiEdge(point2, point4, null);
        VoronoiEdge edge5 = new VoronoiEdge(point4, point3, null);

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

        assertTrue(edge1.getVertexSharingEdges(point3).isEmpty());
        assertTrue(edge1.getVertexSharingEdges(new double[] {1, 1}).isEmpty());

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
