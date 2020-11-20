
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class MyTestEx1 {

    private static Random rnd = null;

    /**
     *A method that builds a graph.
     * @param v - Amount of vertex
     * @param e - Amount of edge
     * @return
     */

    //---------------------------------------------------------------------------
    public weighted_graph grapCreator(int v, int e) {
        weighted_graph g = new WGraph_DS();
        for (int i = 0; i < v; i++) {
            g.addNode(i);
        }
        rnd = new Random(1);
        while (g.edgeSize() < e) {
            int a = nextRnd(0, g.nodeSize());
            int b = nextRnd(0, g.nodeSize());
            int w = nextRnd(1, 10);
            g.connect(a, b, w);
        }

        return g;
    }

    private static int nextRnd(int min, int max) {
        double v = nextRnd(0.0 + min, (double) max);
        int ans = (int) v;
        return ans;
    }

    private static double nextRnd(double min, double max) {
        double d = rnd.nextDouble();
        double dx = max - min;
        double ans = d * dx + min;
        return ans;
    }


    //----------------->test of the methods<-----------------------------------

    /**
     * Notebook Vertex and checks that it has been updated.
     */
    @Test
    void ConntectAndUpdate()
    {
        weighted_graph graph = grapCreator(2, 1);
        int sizeNode = graph.nodeSize();
        graph.connect(0,1,8);
        assertEquals(8, graph.getEdge(0,1));
    }

    /**
     *Tested adding a vertex and checking a vertex does not exist.
     */

    @Test
    void checkAddNodeExistent() {
        weighted_graph graph = grapCreator(10, 10);
        int sizeNode = graph.nodeSize();
        graph.addNode(2);
        assertNotNull(graph.getNode(2));
        graph.addNode(-1);
        assertEquals(sizeNode, graph.nodeSize());
    }


    /**
     * Check that the graph exists and check the runtime of the build.
     */
    @Test ()
    void checkGraphNotNullAndTime() {
        assertTimeout(Duration.ofSeconds(8) , () -> {

            weighted_graph g = grapCreator(1000000, 5000000);
            assertNotNull(g);}

            );


    }

    /**
     * Checks that the graph contains the number of vertices and the node.
     */
    @Test
    void getEdgeNodeSize() {
        weighted_graph g = grapCreator(10, 15);
        assertEquals(10, g.nodeSize());
        assertEquals(15, g.edgeSize());
    }

    /**
     * Checks that the vertex exists and checks that a vertex does not exist.
     */
    @Test
    void checkGetNode() {
        weighted_graph g = grapCreator(10, 15);
        int num = nextRnd(0, g.nodeSize());
        assertNotNull(g.getNode(num));

        //If a node does not exist
        assertNull(g.getNode(20));
    }


    /**
     * Checks that there is an edge and whether or not there is a side with a vertex that does not exist.
     */
    @Test
    void checkHasEdgeAndGetEdge() {
        weighted_graph g = grapCreator(10, 15);
        int num1 = nextRnd(0, g.nodeSize());
        int num2 = nextRnd(0, g.nodeSize());

        // check if has edge
        while (num1 == num2) {
            num2 = nextRnd(0, g.nodeSize());
        }
        boolean t = g.hasEdge(num1, num2);
        if (t) {
            g.removeEdge(num1, num2);
            assertFalse(g.hasEdge(num1, num2));
        } else {
            g.connect(num1, num2, nextRnd(1, 10));
            assertTrue(g.hasEdge(num1, num2));
        }
        // If a edge does not exist
        assertFalse(g.hasEdge(20 , 30));
        assertFalse(g.hasEdge(0 , 30));
    }

    /**
     * Tests deletion of vertex and deletion of vertex does not exist.
     */

    @Test
    void checkRemoveNode() {
        weighted_graph g = grapCreator(10, 15);
        int countE = 0;
        int saveCountE = g.edgeSize();
        int saveCountV = g.nodeSize();
        int num1 = nextRnd(0, g.nodeSize());

        //check if remove node work good
        if (g.getNode(num1) == null) {
            fail();
        }
        for (node_info i : g.getV(num1)) {
            countE++;
        }
        g.removeNode(num1);
        assertNull(g.getNode(num1));
        assertEquals((saveCountV - 1), g.nodeSize());
        assertEquals((saveCountE - countE), g.edgeSize());

        //   If a vertex does not exist
         saveCountE = g.edgeSize();
         saveCountV = g.nodeSize();
        g.removeNode(100);
        assertEquals(g.nodeSize() ,saveCountV);
        assertEquals(g.edgeSize() , saveCountE);
    }

    /**
     * Tests deletion of edge and deletion of edge does not exist.
     */

    @Test
    void checkRemoveEdge() {
        weighted_graph g = grapCreator(11, 15);
        int edge = g.edgeSize();
        int num1 = nextRnd(0, g.nodeSize());
        int nei = -1;

        // check if remove edge work good
        for (node_info i : g.getV(num1)) {
            nei = i.getKey();
            break;
        }
        if (g.hasEdge(num1, nei))
            g.removeEdge(num1, nei);
        if (!g.hasEdge(num1, nei)) {
            assertTrue(true);
            assertEquals((edge - 1), g.edgeSize());
        } else
            fail();

        // If a edge does not exist
        int ns=g.nodeSize();
        g.removeNode(30);
        assertEquals(g.nodeSize() , ns);

    }

    /**
     * Checks init and checks if there is an edge between the same vertices.
     */

    @Test
    void checkInitAndIsConnect() {
        weighted_graph graph = grapCreator(2, 1);
        WGraph_Algo g = new WGraph_Algo();
        g.init(graph);
        assertEquals(graph.edgeSize() , g.getGraph().edgeSize());
        assertEquals(graph.nodeSize(), g.getGraph().nodeSize());
        assertTrue(g.isConnected());
        int num1 = nextRnd(0, graph.nodeSize());
        for (node_info nei : graph.getV(num1)) {
            graph.removeEdge(num1, nei.getKey());
        }
        assertFalse(g.isConnected());
    }

    /**
     * Checking copy of graphs.
     */

    @Test
    void checkCopy() {
        weighted_graph graph = grapCreator(2, 1);
        WGraph_Algo g = new WGraph_Algo();
        g.init(graph);
        weighted_graph copy = g.copy();
        assertNotNull(copy);
        assertEquals(copy.edgeSize(), graph.edgeSize());
        assertEquals(copy.nodeSize(), graph.nodeSize());
        assertEquals(graph.getMC() , copy.getMC());
        assertEquals(graph, g.getGraph());
        assertEquals(g.getGraph(), copy);
    }

    /**
     *Tests loading of a graph.
     */

    @Test
    void checkSaveAndLoda() {
        weighted_graph graph = grapCreator(5, 6);
        WGraph_Algo g = new WGraph_Algo();
        WGraph_Algo c = new WGraph_Algo();
        g.init(graph);
        g.save("check");
        c.load("check");
        assertNotNull(c);
        assertEquals(graph.edgeSize() , c.getGraph().edgeSize());
        assertEquals(graph.nodeSize(), c.getGraph().nodeSize());
        assertEquals(g.getGraph() , c.getGraph());
    }

    /**
     * Checking the return of collections.
     */
    @Test
    void getV() {
        weighted_graph graph = new WGraph_DS();
        graph.addNode(3);
        Collection<node_info> nei = graph.getV(3);
        System.out.println(nei);
        assertNotNull(nei);
        graph.removeNode(3);
        nei = graph.getV(3);
        System.out.println(nei);
        assertNotNull(nei);
        nei = graph.getV();
        System.out.println(nei);
        assertNotNull(nei);
    }

    /**
     * Checks the return of the shortest route and its length.
     */

    @Test
    void shortesPathAndDist() {
        weighted_graph graph = new WGraph_DS();

        graph.addNode(0);
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);
        graph.addNode(4);
        graph.addNode(5);
        graph.addNode(6);
        graph.connect(0, 1, 3);
        graph.connect(0, 1, 3);
        graph.connect(0, 1, 2);
        graph.connect(0, 2, 2);
        graph.connect(0, 3, 10);
        graph.connect(2, 5, 4);
        graph.connect(2, 4, 12);
        graph.connect(4, 5, 4);
        graph.connect(5, 3, 8);
        graph.connect(3, 6, 1);
        graph.connect(1, 6, 2);
        WGraph_Algo g = new WGraph_Algo();
        g.init(graph);

        //check dist of point
        assertEquals(g.shortestPathDist(0, 4), 10);
        assertEquals(g.shortestPathDist(1, 4), 12);
        assertEquals(g.shortestPathDist(2, 4), 8);
        assertEquals(g.shortestPathDist(0, 3), 5);

        // check path of graph
        List<node_info> lis1 = g.shortestPath(0, 4);
        int[] key = {0, 2, 5, 4};
        int count = 0;
        for (node_info i = lis1.remove(0); !lis1.isEmpty(); i = lis1.remove(0)) {
            assertEquals(key[count], i.getKey());
            count++;
        }
        List<node_info> lis2 = g.shortestPath(1, 4);
        int[] key1 = {1, 0, 2, 5, 4};
        count = 0;
        for (node_info i = lis2.remove(0); !lis2.isEmpty(); i = lis2.remove(0)) {
            assertEquals(key1[count], i.getKey());
            count++;
        }
        List<node_info> lis3 = g.shortestPath(2, 4);
        int[] key2 = {2, 5, 4};
        count = 0;
        for (node_info i = lis3.remove(0); !lis3.isEmpty(); i = lis3.remove(0)) {
            assertEquals(key2[count], i.getKey());
            count++;
        }
        List<node_info> lis4 = g.shortestPath(0, 3);
        int[] key3 = {0, 1, 6, 3};
        count = 0;
        for (node_info i = lis4.remove(0); !lis4.isEmpty(); i = lis4.remove(0)) {
            assertEquals(key3[count], i.getKey());
            count++;
        }

    }

}
