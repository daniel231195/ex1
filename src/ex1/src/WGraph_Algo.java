
import java.io.*;
import java.util.*;

/**
 * class performs algorithmic operations on the graph.
 */
public class WGraph_Algo implements weighted_graph_algorithms, java.io.Serializable {

    /**
     * @param graph - Contains the graph we are working with
     * @param deepCopy- Contains the graph after deep copying
     * @param dist- Contains the keys of the vertices with their weight
     * from the source to the vertex with the key.
     * @param path -Contains the route from the source to the end point.
     * @param inf- A variable that contains infinity.
     */
    private weighted_graph graph;
    private weighted_graph deepCopy = null;
    private HashMap<Integer, Double> dist = null;
    private HashMap<Integer, node_info> path = null;

    private double inf = Double.POSITIVE_INFINITY;

    /**
     * A constructor that creates a new graph without values
     */
    public WGraph_Algo() {
        this.graph = new WGraph_DS();
    }

    /**
     * Init the graph on which this set of algorithms operates on.
     *
     * @param g
     */
    @Override
    public void init(weighted_graph g) {
        graph = g;
    }

    /**
     * Returns the graph we are working with.
     *
     * @return
     */
    @Override
    public weighted_graph getGraph() {
        return graph;
    }

    /**
     * Returns the copied graph, after a deep copy.
     *
     * @return
     */
    @Override
    public weighted_graph copy() {
        deepCopy = new WGraph_DS();
        for (node_info ver : graph.getV()) { // Go over all the vertices in the graph
            deepCopy.addNode(ver.getKey());
            for (node_info nei : graph.getV(ver.getKey())) { // Go over all the neighbors of the vertex in the graph
                deepCopy.connect(ver.getKey(), nei.getKey(), graph.getEdge(ver.getKey(), nei.getKey())); // Connecting the vertices in the copied graph
            }
        }

        return deepCopy;
    }

    /**
     * An algorithm that updates all the vertices of the graph at their distance from the source.
     * Acts horizontally. Each vertex, marked with it whether visited or not
     * And what is its distance from the vertex of the source, at first they are
     * They are all defined as an infinite distance.
     * The beginning of the algorithm
     * Start from the source point you get and go to all the neighbors
     * As we go through it, we update it on the distance if we find that the distance that is
     * calculated between it and its neighbor + the distance from the neighbor to the source
     * which is in the tag is lower than the distance it holds, update the insect and mark that we passed it.
     * In addition to this we put in the given vertex his father in the distance list.
     * Each vertex we went through puts it in a queue that saves the vertices,
     * Used as a priority which always takes out the vertex with the lowest tag,
     * Once we have made the check we find the vertex in the low tag wave and continue the algorithm.
     * This algorithm uses queue data structures with priority therefore.
     * This algorithm runs in complexity: O (| E | log V + | V | log V) because
     * That we go through all the vertices and all the ribs and because we use as a priority which
     * Always takes out the vertex with the
     * lowest distance so we do not pass V^2 + E^2 times and pass only LOG V.
     *
     * @param source- first vertex
     */
    public void dijk(int source, int dest) {
        node_info mainVertex = null;
        double newDist = 0;
        path = new HashMap<Integer, node_info>();
        PriorityQueue<node_info> queue = new PriorityQueue<node_info>();
        dist = new HashMap<Integer, Double>();

        for (node_info i : graph.getV()) { //reset all elments
            i.setInfo(null);
            i.setTag(inf);
            dist.put(i.getKey(), inf);
        }

        //Inserting the first vertex
        graph.getNode(source).setTag(0);
        dist.put(source, 0.0);

        // Inserting the first organ in line
        queue.add(graph.getNode(source));
        while (queue.size() != 0) {
            mainVertex = queue.poll(); // Getting the first vertex in queue
            if(dest == mainVertex.getKey())
                return;
            if (mainVertex.getInfo() != null)
                mainVertex.setInfo("p"); // Marking the vertex we went through

            //Going over all the neighbors of the vertex the weights are
            // calculated and if you find a smaller weight update the vertex (neighbor) we are on.
            for (node_info nei : graph.getV(mainVertex.getKey())) {
                if (nei.getInfo() != "p")
                    newDist = dist.get(mainVertex.getKey()) +
                            graph.getEdge(mainVertex.getKey(), nei.getKey()); //Calculate the weight from the source to the vertex

                if (newDist < dist.get(nei.getKey()))//Checks whether the new weight is less than the weight of the vertex (neighbor)
                {
                    dist.put(nei.getKey(), newDist); // Inserting the vertex weight into the official weights from the source
                    nei.setTag(newDist);
                    path.put(nei.getKey(), mainVertex);
                    queue.add(nei);
                }
            }
        }
    }

    /**
     *First reset the values associated with
     * the vertex marking and distance from the source
     * Creating a QUEUE and inserting the source and
     * marking that we have passed the original, c
     * A loop in which all the neighbors of the vertex are received
     * and then ask if we have passed this vertex? If so you will move to the
     * next neighbor if you do not enter the terms and perform.
     * Insert the vertex into the queue and you will know what the next vertex
     * needs to go over according to the vertex of the vertex and mark that
     * we have passed over it and also its distance from the source according
     * to the previous organ before it.
     * When the queue is empty we end the function and thus we went through all
     * the vertices in the graph and also the sides and updated them the distance from the sourc
     * @param src - node start
     */
    public void BFS(int src) {
        for (node_info b : graph.getV()) {
            b.setInfo(null);
            // b.setTag(inf);
        }
        Queue<Integer> q = new PriorityQueue<Integer>();//Responsible for the NODE we will go through
        q.add(src);//Insertion of the first organ
        //Responsible for marking the NODE we passed
        //Inserting the track members into the array
        if (graph.getNode(src) != null)
            graph.getNode(src).setTag(0);//Update the distance from the ele ment we received
        graph.getNode(src).setInfo("p");
        while (!q.isEmpty()) {
            node_info node = graph.getNode(q.poll());
            //System.out.println(node.getKey());
            for (node_info next : graph.getV(node.getKey())) {//Go over the neighbors
                if (next.getInfo() == null) {//Update the distance
                    q.add(next.getKey());
                    next.setInfo("p");
                    // next.setTag(node.getTag() + 1);//Update the distance from the element we received
                }
            }
        }
    }

    /**
     * This method tests whether it is possible to
     * reach a different vertex from each vertex.
     *
     * @return
     */
    @Override
    public boolean isConnected() {
        for (node_info source : graph.getV()) {
            BFS(source.getKey()); //This function marks all the vertices in the graph passed by them
            break;
        }

        for (node_info i : graph.getV()) { //
            if (i.getInfo() == null)
                return false;
        }
        return true;
    }

    /**
     * Returns the lowest total weight from the source to the vertex.
     * First check if the vertices exist in the graph.
     * Activate the algorithm of finding the distance between the vertices by weight.
     * Finally return the distance from the source if there is no ,return -1.
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        if (graph.getNode(src) != null && graph.getNode(dest) != null) {
            dijk(src, dest);
            if (graph.getNode(dest).getTag() != inf)
                return dist.get(dest); // Runs the algorithm with the source and checks what the weight of the final vertex is
            else if (dest != src)
                return 0;
        }
        return -1;
    }

    /**
     * Returns a list of the route from the source to the final vertex.
     * First check if the vertices exist in the graph.
     * The algorithm is then performed to find the source distance from the end according to the smallest weight.
     * Inside the algorithm the path is kept in a path which for each vertex is kept in its key
     * its father through which we can reach it and thus keep the path.
     * Within this function we find the route from the end to the beginning and insert in the order of entry that the source is first and the final destination last.
     * If not, return null.
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public List<node_info> shortestPath(int src, int dest) {
        if (graph.getNode(src) != null && graph.getNode(dest) != null) {
            dijk(src, dest);
            List<node_info> list = new LinkedList<node_info>();
            int[] path2 = new int[graph.nodeSize() + 1];
            int count = 0;
            if (dist.get(dest) != null) {
                //Go over the vertices, from the final vertex to the beginning.
                //Inserting each vertex into an array that maintains the entire shortest vertex path.
                for (int i = dest; i != src; i = path.get(i).getKey()) {
                    if (count < path2.length && dist.get(dest) != null)
                        path2[count] = i;
                    count++;
                }
            }
            if (count <= graph.nodeSize()) {//Insertion of the first organ
                path2[count] = src;
            }
            for (int i = count; i >= 0; i--) { // Adding organs to the list from beginning to end
                list.add(graph.getNode(path2[i]));
            }
            return list;
        }
        return null;
    }


    /**
     * Saves a file by the name we received
     *
     * @param file - the file name (may include a relative path).
     * @return - true if save and false if not save .
     */
    @Override
    public boolean save(String file) {
        System.out.println("starting serialize to " + file + "\n");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream =
                    new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(this.graph);
            fileOutputStream.close();
            objectOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        System.out.println("end of serialize \n\n");
        return true;
    }

    /**
     * Loaded file, by what name we got.
     *
     * @param file - file name
     * @return- true if load and false if not load .
     */
    @Override
    public boolean load(String file) {
        System.out.println("Deserialize from : " + file);

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream
                    = new ObjectInputStream(fileInputStream);
            weighted_graph deseriallizedGraph = (weighted_graph) objectInputStream.readObject();
            fileInputStream.close();
            objectInputStream.close();
            graph = deseriallizedGraph;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
