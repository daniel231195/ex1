
import java.util.Collection;
import java.util.HashMap;

/**
 * This class is responsible for various actions in
 * the graph like adding a vertex and deleting.
 */

public class WGraph_DS implements weighted_graph, java.io.Serializable {
    /**
     * @param weight    - save all links between edge and weights.
     * @param vertices- save all the node.
     * @param countE-   save the number of the edge in graph.
     * @param Mc-       save the number of all the chang.
     */

    private HashMap<Integer, HashMap<Integer, Double>> weight;
    private HashMap<Integer, node_info> vertices;
    private int Mc, countE;

    /**
     * A constructor that initializes the variables.
     */
    public WGraph_DS() {
        weight = new HashMap<Integer, HashMap<Integer, Double>>();
        vertices = new HashMap<Integer, node_info>();
        Mc = 0;
        countE = 0;
    }

    /**
     * Returns the NODE by key
     */

    @Override
    public node_info getNode(int key) {

        return vertices.get(key);
    }

    /**
     * Returns if exists between them a edge.
     * Receive 2 vertices and check if there is
     * a weight between the vertices which marks edge.
     *
     * @param node1 - this is key marks a vertex
     * @param node2 - this is key marks a vertex
     * @return
     */
    @Override
    public boolean hasEdge(int node1, int node2) {
        if (vertices.containsKey(node1) && vertices.containsKey(node2))
            return weight.get(node1).get(node2) != null;

        else
            return false;
    }

    /**
     * Returns if weight between them a nodes.
     * Receive 2 vertices and check if there is
     * a edge between the vertices and return the weight.
     *
     * @param node1 - this is key marks a vertex
     * @param node2 - this is key marks a vertex
     * @return -1
     */

    @Override
    public double getEdge(int node1, int node2) {
        if (!hasEdge(node1, node2))
            return -1;
        return weight.get(node1).get(node2);
    }

    /**
     * Checks if it does not exist in the graph.
     * Puts the key into the maps and thus puts the new vertex into the graph.
     *
     * @param key - this is key marks a vertex
     */
    @Override
    public void addNode(int key) {
        if (!vertices.containsKey(key) && !weight.containsKey(key) && key >= 0) {
            vertices.put(key, new Node(key));
            weight.put(key, new HashMap<Integer, Double>());
            Mc++;
        }
    }

    /**
     * A method that connects two vertices and creates edge.
     * First check if there are any vertices in the graph,
     * check that they are not equal and the data obtained is correct.
     * Then insert the two-way insert into the two vertices of the connection.
     *At the same and we got vertices that have a connection between them then just updating the weight
     * @param node1 -this is key marks a vertex
     * @param node2 -this is key marks a vertex
     * @param w     - weight of edge
     */
    @Override
    public void connect(int node1, int node2, double w) {
        if (weight.containsKey(node1) && weight.containsKey(node2) && node1 != node2
                && weight.get(node1).get(node2) == null && w > 0)//<- maybe i need that
        {
            //if (weight.get(node1).get(node2) == null)
            countE++;
            weight.get(node1).put(node2, w);
            weight.get(node2).put(node1, w);
            Mc++;
        }
        else if (weight.containsKey(node1) && weight.containsKey(node2) && node1 != node2
                && weight.get(node1).get(node2) != null && w > 0)//<- maybe i need that
        {
            //if (weight.get(node1).get(node2) == null)
            weight.get(node1).replace(node2, w);
            weight.get(node2).replace(node1, w);
            Mc++;
        }

    }

    /**
     * Returns all vertices in the graph (from the vertices map)
     *
     * @return all vertices
     */
    @Override
    public Collection<node_info> getV() {
        return vertices.values();
    }

    /**
     * Returns all neighbors of the vertex.
     *
     * @param node_id
     * @return
     */
    @Override
    public Collection<node_info> getV(int node_id) {
        HashMap<Integer, node_info> itretor = new HashMap<Integer, node_info>();
        for (int i : weight.get(node_id).keySet()) {
            itretor.put(i, vertices.get(i));
        }
        return itretor.values();
    }

    /**
     * Deletes the vertex from the graph.
     * First it deletes the vertex from the list of all the
     * neighbors and finally it deletes the vertex itself from the graph.
     *
     * @param key
     * @return
     */
    @Override
    public node_info removeNode(int key) {
        if (weight.containsKey(key)) {
            HashMap<Integer, Double> node = weight.get(key);
            for (int i : node.keySet()) {//Deleting the vertex from the neighbors
                if (node.get(i) != null) {
                    weight.get(i).remove(key);
                    countE--;
                    Mc++;
                }
            }
        } else
            return null;
        weight.get(key).clear();
        this.Mc++;
        return vertices.remove(key);
    }

    /**
     * Erases a edge between two vertices.
     * First check if there is a graph and there is a side and if it is not the same vertex.
     * It then deletes the vertices from the list of neighbors of each vertex.
     *
     * @param node1
     * @param node2
     */
    @Override
    public void removeEdge(int node1, int node2) {
        if (weight.containsKey(node1) && weight.containsKey(node2) && node1 != node2
                && weight.get(node1).get(node2) != null) {
            weight.get(node1).remove(node2);
            weight.get(node2).remove(node1);
            this.countE--;
            this.Mc++;
        }

    }

    /**
     * A function that compares two existing graphs.
     *
     * @param other
     * @return - If equal then true if not then false.
     */

    public boolean equals(Object other) {

        if (!(other instanceof weighted_graph)) //Changes the object to a graph
            return false;
        weighted_graph g2 = (weighted_graph) other;

        for (node_info i : g2.getV()) { // initialization remark
            if (i.getInfo() != null)
                i.setInfo(null);
        }
        for (node_info ver : this.getV()) {
            if (!(g2.getNode(ver.getKey()) != null)) { //Checks whether it exists in the graph
                return false;
            }
            if (!(g2.getNode(ver.getKey()).getKey() == ver.getKey())) //Checks if the key is worth it
                return false;
            for (node_info edge : this.getV(ver.getKey())) {

                if (g2.hasEdge(g2.getNode(ver.getKey()).getKey(), g2.getNode(edge.getKey()).getKey())) //Checking for edge between them
                    if (!(g2.getEdge(g2.getNode(ver.getKey()).getKey()
                            , g2.getNode(edge.getKey()).getKey()) == this.getEdge(ver.getKey(), edge.getKey())))
                        return false;

                if (!(g2.getEdge(ver.getKey(), edge.getKey()) == this.getEdge(ver.getKey(), edge.getKey()))) { //Checks whether the weight of the EDGE is equal
                    return false;
                }
            }
            g2.getNode(ver.getKey()).setInfo("p"); //remark the node if ok
        }
        for (node_info i : g2.getV()) { //check if all the node remark
            if (i.getInfo() == null)
                return false;
        }
        return true;
    }


    /**
     * return the number of  vertices size of graph .
     *
     * @return
     */
    @Override
    public int nodeSize() {
        return vertices.size();
    }

    /**
     * return the number size of edge
     *
     * @return
     */
    @Override
    public int edgeSize() {
        return countE;
    }

    /**
     * return the Mode Count
     *
     * @return
     */
    @Override
    public int getMC() {
        return Mc;
    }


    public boolean ifVertexExists(int src)
    {
        if(!(vertices.containsKey(src)))
            return false;
        else
            return true;
    }

//--------------------------------> class Node <---------------------------------------------------------------------------

    /**
     * This class is responsible for storing the data of the vertex
     */
    private class Node implements node_info, java.io.Serializable, Comparable<node_info> {
        private int key;
        private double tag;
        private String info;

        /**
         * A constructor that initializes the node ny key.
         *
         * @param key
         */
        public Node(int key) {
            this.key = key;
            tag = 0;
            info = null;
        }

        /**
         * return the key of the node.
         *
         * @return
         */
        @Override
        public int getKey() {
            return key;
        }

        /**
         * return the remark (meta data) associated with this node.
         *
         * @return
         */
        @Override
        public String getInfo() {
            return info;
        }

        /**
         * set the remark (meta data) associated with this node.
         *
         * @param s
         */
        @Override
        public void setInfo(String s) {
            this.info = s;
        }

        /**
         * It is responsible for the weight from source to vertex
         *
         * @return
         */
        @Override
        public double getTag() {
            return tag;
        }

        /**
         * set the distance from source
         *
         * @param t - the new value of the tag
         */
        @Override
        public void setTag(double t) {
            this.tag = t;
        }

        /**
         * This method is responsible for returning the smallest tag.
         * It is used in an algorithm graph.
         * @param o
         * @return
         */
        @Override
        public int compareTo(node_info o) {
            node_info n = o; //(node_info) (o);
            if (this.tag - n.getTag() > 0)
                return 1;
            return -1;
        }
    }

//----------------------------------------------------------------------------------------------------------------------
}
