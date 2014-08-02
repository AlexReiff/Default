package a2posted;

public class TestDijkstra{
	public static void main(String[] args) {
		
		Graph graph;
		Dijkstra dijkstra;

		
//TEST 1
		GraphReader  reader	=	new GraphReader("src/a2solution/test_graph_1.sdot");
		String startingVertex = "4";
	
		graph = reader.getParsedGraph();
				
		dijkstra = new Dijkstra();
		dijkstra.dijkstraVertices( graph, startingVertex );
		System.out.println("dijkstraVertices: \n" + dijkstra );
		
		dijkstra = new Dijkstra();
		dijkstra.dijkstraEdges(    graph, startingVertex );
		System.out.println("dijkstraEdges: \n" + dijkstra );

//TEST 2
		reader	=	new GraphReader("src/a2solution/test_graph_2.sdot");
		startingVertex = "a";
		graph = reader.getParsedGraph();
		
		dijkstra = new Dijkstra();
		dijkstra.dijkstraVertices( graph, startingVertex );
		System.out.println("dijkstraVertices: \n" + dijkstra );
		
		dijkstra = new Dijkstra();
		dijkstra.dijkstraEdges(    graph, startingVertex );
		System.out.println("dijkstraEdges: \n" + dijkstra );
	}
}