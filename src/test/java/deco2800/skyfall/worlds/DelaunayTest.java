package deco2800.skyfall.worlds;

import deco2800.skyfall.worlds.delaunay.CollinearPointsException;
import deco2800.skyfall.worlds.delaunay.WorldGenException;
import deco2800.skyfall.worlds.delaunay.WorldGenNode;
import deco2800.skyfall.worlds.delaunay.WorldGenTriangle;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
    public void triangleCircumcircle() {
        WorldGenNode node1 = new WorldGenNode(0, 0);
        WorldGenNode node2 = new WorldGenNode(1, 0);
        WorldGenNode node3 = new WorldGenNode(0, 1);

        WorldGenNode node4 = new WorldGenNode(2, 5);
        WorldGenNode node5 = new WorldGenNode(3, 3);
        WorldGenNode node6 = new WorldGenNode(-2, 1);

        WorldGenTriangle triangle1 = new WorldGenTriangle(node1, node2, node3);
        WorldGenTriangle triangle2 = new WorldGenTriangle(node4, node5, node6);
        double[] circumcentre1 = {0, 0};
        double[] circumcentre2 = {0, 0};

        // Test that the exception is not thrown when it shouldn't be
        try{
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
        double[] circumcentre3 = {0, 0};
        double[] circumcentre4 = {0, 0};

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
        Random random = new Random();

        List<WorldGenNode> nodes = new ArrayList<>();
        for (int i = 0; i < nodeCount; i++) {
            // Random number from -500 to 500
            double x = (random.nextFloat() - 0.5) * 2 * worldSize;
            double y = (random.nextFloat() - 0.5) * 2 * worldSize;
            nodes.add(new WorldGenNode(x, y));
        }

        try {
            WorldGenNode.calculateVertices(nodes);
            WorldGenNode.assignNeighbours(nodes);
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

                if (node.isBorderNode() && neighbour.isBorderNode()) {
                    if (commonVertices == 0) {
                        fail();
                    }
                } else if (commonVertices == 1) {
                    fail();
                }
            }
        }

    }
}