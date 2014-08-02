/* COMP 251 - Assignment 2
 * Feb 9, 2014
 * 
 * Alexander Reiff
 * 260504962
 */

package a2posted;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Dijkstra {

	private IndexedHeap  pq;	
	private static int edgeCount = 0;               //  Use this to give names to the edges.										
	private HashMap<String,Edge>  edges = new HashMap<String,Edge>();

	private HashMap<String,String>   parent;
	private HashMap<String,Double>   dist;  //  This is variable "d" in lecture notes
	private String 					 startingVertex;	
	
	HashSet<String>  setS      ;
	HashSet<String>  setVminusS;

	public Dijkstra(){
		pq    		= new IndexedHeap()  ;		
		setS        = new HashSet<String>();
		setVminusS  = new HashSet<String>();		
		parent  = new HashMap<String,String>();
		dist 	= new HashMap<String,Double>();
	}
	
	/*
	 * Run Dijkstra's algorithm from a vertex whose name is given by the string s.
	 */
	
	public void dijkstraVertices(Graph graph, String s){
		
		//  temporary variables
		
		String u;	
		double  distToU,
				costUV;		
		
		HashMap<String,Double>    uAdjList;		
		initialize(graph,s);
		
		parent.put( s, null );
		pq.add(s, 0.0);   // shortest path from s to s is 0.
		this.startingVertex = s;

		//  --------- BEGIN: ADD YOUR CODE HERE  -----------------------
		// (pretty much follows the algorithm from the notes)
		while (!pq.isEmpty()) {
			// gets the closest vertex from V/S
			u = pq.nameOfMin();
			distToU = pq.getMinPriority();
			dist.put(u, distToU);
			
			// and moves it from V/S to S
			setS.add(u);
			pq.removeMin();
			setVminusS.remove(u);
			
			// computes the new distances from S to V/S
			// only affects vertices adjacent to u
			uAdjList = graph.getAdjList().get(u);
			Set<String> currAdjVertices = uAdjList.keySet();
			for (String v : currAdjVertices) {
				// if the edge from u to v is a crossing edge
				if (setVminusS.contains(v)) {
					costUV = uAdjList.get(v);
					Double totalDistance = distToU + costUV;
					// verices are added to the priority queue as they are encountered
					if(!pq.contains(v)) {
						pq.add(v, totalDistance);
						parent.put(v, u);
					}
					
					// if v already exists in the priority queue, only update the
					// distance if this is a shorter path
					else if (totalDistance < pq.getPriority(v)) {
						pq.changePriority(v, totalDistance);
						parent.put(v, u);
					}
				}
			}
		}
		//  --------- END:  ADD YOUR CODE HERE  -----------------------
	}
	
	
	public void dijkstraEdges(Graph graph, String startingVertex){

		//  Makes sets of the names of vertices,  rather than vertices themselves.
		//  (Could have done it either way.)
		
		//  temporary variables
		
		Edge e;
		String u,v;
		double tmpDistToV;
		
		initialize(graph, startingVertex);

		//  --------- BEGIN: ADD YOUR CODE HERE  -----------------------
		// find all edges originating from the chosen vertex
		pqAddEdgesFrom(graph, startingVertex);
		int numLoop = 0;
		// worst case run time is |V|^2 if there is the maximal number of edges
		// this catches unreachable vertices
		while (setVminusS.size() > 0 && numLoop < Math.pow(graph.getVertices().size(),2)) {
			// get the shortest crossing edge
			tmpDistToV = pq.getMinPriority();
			String edgeName = pq.nameOfMin();
			e = edges.get(edgeName);
			v = e.getEndVertex();
			u = e.getStartVertex();
			// and removes it from the list of edges to inspect
			pq.removeMin();
			// if this new path is shorter than an existing path,
			// update the distance to the vertex
			if (tmpDistToV < dist.get(v)) {
				dist.put(v, tmpDistToV);
				parent.put(v, u);
			}
			// the vertex is reachable, so move it from V/S to S
			setS.add(v);
			setVminusS.remove(v);
			pqAddEdgesFrom(graph, v);
			// keep track of how many iterations
			numLoop++;
		}	
		//  --------- END:  ADD YOUR CODE HERE  -----------------------

	}
	
	/*
	 *   This initialization code is common to both of the methods that you need to implement so
	 *   I just factored it out.
	 */

	private void initialize(Graph graph, String startingVertex){
		//  initialization of sets V and VminusS,  dist, parent variables
		//

		for (String v : graph.getVertices()){
			setVminusS.add( v );
			dist.put(v, Double.POSITIVE_INFINITY);
			parent.put(v, null);
		}
		this.startingVertex = startingVertex;

		//   Transfer the starting vertex from VminusS to S and  

		setVminusS.remove(startingVertex);
		setS.add(startingVertex);
		dist.put(startingVertex, 0.0);
		parent.put(startingVertex, null);
	}

    /*  
	 *  helper method for dijkstraEdges:   Whenever we move a vertex u from V\S to S,  
	 *  add all edges (u,v) in E to the priority queue of edges.
	 *  
	 *  For each edge (u,v), if this edge gave a shorter total distance to v than any
	 *  previous paths that terminate at v,  then this edge will be removed from the priority
	 *  queue before these other vertices. 
	 *  
	 */
	
	public void pqAddEdgesFrom(Graph g, String u){
		double distToU = dist.get(u); 
		for (String v : g.getAdjList().get(u).keySet()  ){  //  all edges of form (u, v) 
			
			edgeCount++;
			Edge e = new Edge( edgeCount, u, v );
			edges.put( e.getName(), e );
			pq.add( e.getName() ,  distToU + g.getAdjList().get(u).get(v) );
		}
	}

	// -------------------------------------------------------------------------------------------
	
	public String toString(){
		String s = "";
		s += "\nRan Dijkstra from vertex " + startingVertex + "\n";
		for (String v : parent.keySet()){
			s += v + "'s parent is " +   parent.get(v) ;
			s += "   and pathlength is "  + dist.get(v) + "\n" ;
		}
		return s;
	}

	//  This class is used only to keep track of edges in the priority queue. 
	
	private class Edge{
		
		private String edgeName;
		private String u, v;
		
		Edge(int i, String u, String v){
			this.edgeName = "e_" + Integer.toString(i);
			this.u = u;
			this.v = v;
		}
		
		public String getName(){
			return edgeName;
		}
		
		String getStartVertex(){
			return u;
		}

		String getEndVertex(){
			return v;
		}
		
		public String toString(){
			return 	edgeName + " : " + u + " " + v;
		}
	}
	

}
