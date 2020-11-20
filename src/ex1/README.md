# ex1
Graph
    #---------->Description<---------------------------------------------------------------------------------------------------------------------------------------#

This project is about building an weighted and unintentional graph.
This project has a number of algorithms that compute copy, 
track length, shortest track , save and load file and whether the graph is connected.

#---------->Theory<--------------------------------------------------------------------------------------------------------------------------------------------#

graphs, which are mathematical structures used to model pairwise relations between objects. 
A graph in this context is made up of 
vertices (also called nodes or points) which are connected by edges (also called links or lines) and every edge has weight.
 undirected graphs, where edges link two vertices symmetrically.


#---------->Data Structure<------------------------------------------------------------------------------------------------------------------------------------#

1) HashMap<key ,node_info> ----> Stores all vertices of the graph by keys and each vertex contains data on it (node_info).

2)HashMap<Integer, HashMap<Integer, Double>> ---->Contains all the EDGES in the graph and their weights.
					The internal HASH MAP contains its neighboring vertices with which EDGE exists and the weight of each EDGE.

3)PriorityQueue<node_info> ---->A priority queue used to obtain the EDGE with the smallest weight.
			       This structure is used in the algorithm class.

4) LinkedList<node_data> ---->  This structure contains for us all the vertices (in order) in the shortest trajectory,
		                    When the first NODE is the start source and the last NODE is 
			     the end of the route the destination point.


#---------->Class and Interfaces<--------------------------------------------------------------------------------------------------------------------------------#

* class Node ----> implements node_info

* class WGraph_DS ----> implements weighted_graph and java.io.Serializable

* class WGraph_Algo ----> implements weighted_graph_algorithms and java.io.Serializable


#---------->(class)Node<---------------------------------------------#

Internal class is realized within a class WGraph_DS

---------------------------->Terminology of class

- int key =  Identify the vertex we want to get.
- int tag = Saves the length of the route from the starting point to the same vertex.
- string info = Responsible for marking whether we passed the Rover using a 'P'. ( 'P'  = pass ) .

#--------------------------->Realizations<--------------------------------#
 
--->constructor---> public NodeData() / / Initializes the entries to create a new map

--->public int compareTo(node_info o)--->This method is responsible for returning the smallest tag.
       				      It is used in an algorithm graph.

{-------------------Realizations of    Interfaces--------------------}

1)  @Override
         public int getKey() {return key; }  
	/ / Return the key (id) associated with this node.
---------------
2) @Override
    public String getInfo() / /return the remark (meta data) associated with this node.
---------------
3)     @Override
    public void setInfo(String s) / / Allows changing the remark (meta data) associated with this node.

---------------
4)    @Override
    public int getTag() / /  Temporal data (aka color: e,g, white, gray, black) ,which can be used be algorithms .

---------------
5)     @Override
    	public void setTag(int t) / /	 Allow setting the "tag" value for temporal marking an node - common 
				 practice for marking by algorithms.
				 @param t - the new value of the tag
------------------------------

#---------->(class)WGraph_DS<---------------------------------------------#

---------------------------->Terminology of class

* import java.util.Collection ---->The root interface in the collection hierarchy. A collection represents a group of objects,
			     known as its elements. Some collections allow duplicate elements and others .

* import java.util.HashMap ---->Hash table based implementation of the Map interface.
			    This implementation provides all of the optional map operations, 
			    and permits null values and the null key.


- int Mc - Responsible for the number of actions performed on the changes in the graph
- int countE - Count the edge in the graph
-HashMap<Integer, HashMap<Integer, Double>> weight -The map contains all the neighbors of each key that it represents edges in the graph and each edge contains weight.
- HashMap<Integer, node_info> vertices - The map contains all the vertices according to Map.

#--------------------------->Realizations<--------------------------------# 
    
   //constructor of class, Initialize the parameters.
   public WGraph_DS() 


{-------------------Realizations of    Interfaces--------------------} 

1)        @Override
    public node_info getNode(int key) / /  * return the node_data by the node_id,
			                  * @param key - the node_id
			                * @return the node_data by the node_id, null if none.	
			           / / use  get(key) - Returns the value to which the specified key is mapped, 
					         or null if this map contains no mapping for the key.
---------------
2)         @Override
    public boolean hasEdge(int node1, int node2)  / /  return true iff (if and only if) there is an edge between node1 and node2
	 				      Note: this method should run in O(1) time.
					/ / use containsKey - Returns true if this map contains a mapping for the specified key.
							 - Two-way bridal examination .
					//  Returns if exists between them a edge.
     				               	     Receive 2 vertices and check if there is
     					    a weight between the vertices which marks edge.

---------------
3)    @Override
    public void addNode(node_data n) / /  add a new node to the graph with the given node_data.
				    Note: this method should run in O(1) time.
			            / / use put.


---------------
4)        @Override
    public double getEdge(int node1, int node2) //return the weight if the edge (node1, node1). In case
     					there is no such edge .
				            // Returns if weight between them a nodes.
     					 Receive 2 vertices and check if there is
  					 a edge between the vertices and return the weight.

---------------
5)      @Override
    public void addNode(int key) // add a new node to the graph with the given key.
			   // Checks if it does not exist in the graph.
     			      Puts the key into the maps and thus puts the new vertex into the graph.

---------------
6)       @Override
    public void connect(int node1, int node2, double w) // Connect an edge between node1 and node2, with an edge with weight >=0.
					        / / A method that connects two vertices and creates edge.
						 A method that connects two vertices and creates edge.
     						 First check if there are any vertices in the graph,
    						 check that they are not equal and the data obtained is correct.
   						 Then insert the two-way insert into the two vertices of the connection
    
---------------
7)     @Override
    public Collection<node_info> getV() //  This method return a pointer (shallow copy) for a
   				    Collection representing all the nodes in the graph.
				//Returns all vertices in the graph (from the vertices map)	
---------------
 8)      @Override
    public Collection<node_info> getV(int node_id) / / This method returns a Collection containing all the
     					      nodes connected to node_id.
					     Note: this method can run in O(k) time, k - being the degree of node_id.
					/ / Returns all neighbors of the vertex.
---------------
9)       @Override
    public node_info removeNode(int key) / / Delete the node (with the given ID) from the graph -
     				        and removes all edges which starts or ends at this node.
				        this method run in O(n).
				   //Deletes the vertex from the graph.
     				      First it deletes the vertex from the list of all the
                                                                      neighbors and finally it deletes the vertex itself from the graph.

---------------
10)    @Override
    public void removeEdge(int node1, int node2)// Delete the edge from the graph,
     			                                  this method  run in O(1) time.
					//Erases a edge between two vertices.
     					   First check if there is a graph and there is a side and if it is not the same vertex.
  					   It then deletes the vertices from the list of neighbors of each vertex.

---------------
11)     public boolean equals(Object other) // A function that compares two existing graphs.

---------------
12)   @Override
    public int nodeSize() // return the number of vertices (nodes) in the graph.
    		         this method run in O(1) time.
---------------
13)    @Override
    public int edgeSize() // return the number of edges (undirectional graph).
     		         this method  run in O(1) time.

---------------
14)    @Override
    public int getMC() // return the Mode Count - for testing changes in the graph.
     		     Any change in the inner state of the graph should cause an increment in the ModeCount .

---------------

#---------->(class)WGraph_Algo<---------------------------------------------#

---------------------------->Terminology of class
import java.util.*- Contains the collections framework, some internationalization support classes, a service loader, properties.random number generation,
 		string parsing and scanning classes, base64 encoding and decoding, a bit array, and several miscellaneous utility classes.
		 This package also contains legacy collection classes and legacy date and time classes.

weighted_graph graph - Contains the graph we are working with
weighted_graph deepCopy- Contains the graph after deep copying
HashMap<Integer, Double>dist- Contains the keys of the vertices with their weight
			source to the vertex with the key.
HashMap<Integer, node_info> path -Contains the route from the source to the end point.
double  inf- A variable that contains infinity.

#---------------------------> Dijkstra Algorithm<--------------------------------# 
(From Wikipedia)
Dijkstra's algorithm (or Dijkstra's Shortest Path First algorithm, SPF algorithm) is an algorithm for finding the shortest paths between nodes in a graph,
For a given source node in the graph, the algorithm finds the shortest path between that node and every other.
It can also be used for finding the shortest paths from a single node to a single destination node by stopping the algorithm
 once the shortest path to the destination node has been determined. For example, if the nodes of the graph represent cities and edge path costs 
represent driving distances between pairs of cities connected by a direct road (for simplicity, ignore red lights, stop signs, toll roads and other obstructions),
 Dijkstra's algorithm can be used to find the shortest route between one city and all other cities. 


#--------------------------->BFS Algorithm<--------------------------------# 

The algorithm uses a queue-type data structure to determine what the next vertex it is about to visit is. Every time he visits the summit he signals that they have passed through it, 
then checks on all his neighbors. If not yet tested, this vertex is added to the queue.
 This way the algorithm will scan all the vertices in the order determined by their distance from the initial node.
Time complexity is: o (E + V)
Is done in this complexity because it goes through all the vertices in the graph and in addition to that it goes through all the sides of the graph so the complexity is this.

#--------------------------->Realizations<--------------------------------# 

public WGraph_Algo() // A constructor that creates a new graph without values
---------------
need to delete 
public void dijk(int source) / /
	|     Function description
               V

An algorithm that updates all the vertices of the graph at their distance from the source.
Works this way. Each vertex, marked with it whether visited or not
And what is its distance from the vertex of the source, at first they are
 They are all defined as an infinite distance.
The beginning of the algorithm
Start from the source point you get and go to all the neighbors
As we go through it, we update it on the distance if we find that the distance that is calculated between it and its neighbor + the distance from the neighbor to the source which is in the tag is lower than the distance it holds, update the insect and mark that we passed it.
In addition to this we put in the given vertex his father in the distance list.
Each vertex we went through puts it in a queue that saves the vertices,
Used as a priority which always takes out the vertex with the lowest tag,
Once we have made the check we find the vertex in the low tag wave and continue the algorithm.
 This algorithm uses queue data structures with priority therefore.
This algorithm runs in complexity: O (| E | log V + | V | log V) because
 That we go through all the vertices and all the ribs and because we use as a priority which
Always takes out the vertex with the lowest distance so we do not pass V * V + E * E times and pass only LOG V.



---------------

    public void BFS(int src) / /
	|     Function description
                 V
First reset the values ​​associated with the vertex marking and distance from the source
Creating a QUEUE and inserting the source and marking that we have passed the original, c
A loop in which all the neighbors of the vertex are received and then ask if we have passed this vertex? If so you will move to the next neighbor if you do not enter the terms and perform.
Insert the vertex into the queue and you will know what the next vertex needs to go over according to the vertex of the vertex and mark that
we have passed over it and also its distance from the source according to the previous organ before it.
When the queue is empty we end the function and thus we went through all the vertices in the graph and also the sides and updated them the distance from the sourc

{-------------------Realizations of    Interfaces--------------------} 

1)         @Override
    public void init(weighted_graph g) / / Init the graph on which this set of algorithms operates on.,  

---------------

2)      @Override
    public weighted_graph getGraph() // Return the underlying graph of which this class works.
---------------
 3)       @Override
    public weighted_graph copy() // Compute a deep copy of this weighted graph.
---------------

4)       @Override
    public boolean isConnected() // eturns true if and only if (iff) there is a valid path from EVREY node to each
     			        other node.
			//This method tests whether it is possible to
    			 reach a different vertex from each vertex.


---------------
5)     @Override
    public double shortestPathDist(int src, int dest) //returns the length of the shortest path between src to dest
     					  if no such path --> returns -1	
					// Returns the lowest total weight from the source to the vertex.
					First check if the vertices exist in the graph.
    					Activate the algorithm of finding the distance between the vertices by weight.
 					 Finally return the distance from the source if there is no ,return -1.

---------------
6)    @Override
    public List<node_info> shortestPath(int src, int dest) // returns the the shortest path between src to dest - as an ordered List of nodes:
     						* src--> n1-->n2-->...dest
					      / / Returns a list of the route from the source to the final vertex.
					          First check if the vertices exist in the graph.
					         The algorithm is then performed to find the source distance from the end according to the smallest weight.
                                                                                         Inside the algorithm the path is kept in a path which for each vertex is kept in its key 
                                                                                          its father through which we can reach it and thus keep the path.
					  Within this function we find the route from the end to the beginning and insert
					 in the order of entry that the source is first and the final destination last.
					If not, return null.


---------------
7)    @Override
    public boolean save(String file) //Saves this weighted (undirected) graph to the given
  			        file name.
			     / / Saves a file by the name we received.
			          use in Serializable.



---------------
8)    @Override
    public boolean load(String file) // Loaded file, by what name we got.
			          use in Serializable.

