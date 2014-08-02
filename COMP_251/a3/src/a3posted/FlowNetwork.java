/*
 * COMP 251 - Assignment 3
 * 
 * Alexander Reiff
 * 260504962
 * 
 * March 13, 2014
 */
package a3posted;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.HashSet;

public class FlowNetwork {

	//   The data structures follow what I presented in class.  Use three graphs which 
	//   represent the capacities, the flow, and the residual capacities.
	
	Graph capacities;      		// weights are capacities   (G)
	Graph flow;            		// weights are flows        (f)
	Graph residualCapacities;   // weights are determined by capacities (graph) and flow (G_f)
	
	//   Constructor.   The input is a graph that defines the edge capacities.
	
	public FlowNetwork(Graph capacities){
				
		this.capacities    = capacities;
		
		//  The flow and residual capacity graphs have the same vertices as the original graph.
		
		flow               = new Graph( capacities.getVertices() );
		residualCapacities = new Graph( capacities.getVertices() );
		
		//  Initialize the flow and residualCapacity graphs.   The flow is initialized to 0.  
		//  The residual capacity graph has only forward edges, with weights identical to the capacities. 

		for (String u : flow.getVertices()){
			for (String v : capacities.getEdgesFrom(u).keySet() ){
				
				//  Initialize the flow to 0 on each edge
				
				flow.addEdge(u, v, new Double(0.0));
				
				//	Initialize the residual capacity graph G_f to have the same edges and capacities as the original graph G (capacities).
				
				residualCapacities.addEdge(u, v, new Double( capacities.getEdgesFrom(u).get(v) ));
			}
		}
	}

	/*
	 * Here we find the maximum flow in the graph.    There is a while loop, and in each pass
	 * we find an augmenting path from s to t, and then augment the flow using this path.
	 * The beta value is computed in the augment method. 
	 */
	
	public void  maxFlow(String s,  String t){
		
		LinkedList<String> path;
		double beta;
		while (true){
			path = this.findAugmentingPath(s, t);
			if (path == null)
				break;
			else{
				beta = computeBottleneck(path);
				augment(path, beta);				
			}
		}	
	}
	
	/*
	 *   Use breadth first search (bfs) to find an s-t path in the residual graph.    
	 *   If such a path exists, return the path as a linked list of vertices (s,...,t).   
	 *   If no path from s to t in the residual graph exists, then return null.  
	 */
	
	public LinkedList<String>  findAugmentingPath(String s, String t){

		String curr = t;
		LinkedList<String> path = new LinkedList<String>();
		
		//compute a path from s to t
		residualCapacities.bfs(s);
		//traces the path backward from t to s
		while (!curr.equals(s)) {
			//add each vertex to the linked list
			path.addFirst(curr);
			curr = residualCapacities.getParent(curr);
			//stops the algorithm if there is no augmenting path (AKA max flow has been found)
			if (curr == null) {
				return null;
			}
		}
		//remember to add s to the path
		path.addFirst(s);
		return path;		
	}
	
	/*
	 *   Given an augmenting path that was computed by findAugmentingPath(), 
	 *   find the bottleneck value (beta) of that path, and return it.
	 */
	
	public double computeBottleneck(LinkedList<String>  path){

		double beta = Double.MAX_VALUE;

		//  Check all edges in the path and find the one with the smallest weight in the
		//  residual graph.   This will be the new value of beta.
		
		LinkedList<String> pathClone = (LinkedList<String>) path.clone();
		String curr = null;
		String prev = pathClone.pop();
		
		//check each edge in the path
		while (pathClone.size() > 0) {
			curr = pathClone.pop();
			Double weight = residualCapacities.getEdgesFrom(prev).get(curr);
			//find the smaller capacity edge
			if (weight < beta) {
				beta = weight;
			}
			prev = curr;
		}
		return beta;
	}
	
	//  Once we know beta for a path, we recompute the flow and update the residual capacity graph.

	public void augment(LinkedList<String>  path,  double beta){

		LinkedList<String> pathClone = (LinkedList<String>) path.clone();
		String curr = null;
		String prev = pathClone.pop();
		double newFlow;
		
		//update each edge in the path
		while (pathClone.size() > 0) {
			curr = pathClone.pop();
			//determine the type of edge it is
			Double flowWeight = flow.getEdgesFrom(prev).get(curr);			
			Double residual = residualCapacities.getEdgesFrom(prev).get(curr);
			//if it's a 'backward' edge, residual capacity is equal to the flow
			if (residual == flowWeight) {
				//update flow
				newFlow = flowWeight - beta;

			}
			//or it's a 'forward' edge
			else {
				newFlow = flowWeight + beta;
			}
			
			//update flow
			flow.addEdge(prev, curr, newFlow);
			//update backward edge in residual graph
			residualCapacities.addEdge(curr, prev, newFlow);
			//update forward edge in residual graph
			Double capacityWeight = capacities.getEdgesFrom(prev).get(curr);
			residualCapacities.addEdge(prev, curr, capacityWeight - newFlow);
			
			HashMap<String, Double> tempSet;
			//remove forward edge in residual graph if it has 0 capacity
			if (newFlow == capacityWeight) {
				tempSet = residualCapacities.getEdgesFrom(prev);
				tempSet.remove(curr);
			}
			//remove backward edge in residual graph if it has 0 capacity
			if (newFlow == 0) {
				tempSet = residualCapacities.getEdgesFrom(curr);
				tempSet.remove(prev);
			}
			prev = curr;
		}
	}

	//  This just dumps out the adjacency lists of the three graphs (original with capacities,  flow,  residual graph).
	
	public String toString(){
		return "capacities\n" + capacities + "\n\n" + "flow\n" + flow + "\n\n" + "residualCapacities\n" + residualCapacities;
	}
	
}
