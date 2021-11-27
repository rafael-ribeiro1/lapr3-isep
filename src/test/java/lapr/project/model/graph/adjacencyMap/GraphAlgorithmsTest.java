package lapr.project.model.graph.adjacencyMap;

import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author DEI-ISEP
 */
public class GraphAlgorithmsTest {
    
    Graph<String,String> completeMap = new Graph<>(false);
    Graph<String,String> incompleteMap = new Graph<>(false);
    
    public GraphAlgorithmsTest() {
    }

    @BeforeEach
    public void setUp() throws Exception {
        
        completeMap.insertVertex("Porto");
        completeMap.insertVertex("Braga");
        completeMap.insertVertex("Vila Real");
        completeMap.insertVertex("Aveiro");
        completeMap.insertVertex("Coimbra");
        completeMap.insertVertex("Leiria");

        completeMap.insertVertex("Viseu");
        completeMap.insertVertex("Guarda");
        completeMap.insertVertex("Castelo Branco");
        completeMap.insertVertex("Lisboa");
        completeMap.insertVertex("Faro");
                
        completeMap.insertEdge("Porto","Aveiro","A1",75);
        completeMap.insertEdge("Porto","Braga","A3",60);
        completeMap.insertEdge("Porto","Vila Real","A4",100);
        completeMap.insertEdge("Viseu","Guarda","A25",75);
        completeMap.insertEdge("Guarda","Castelo Branco","A23",100);
        completeMap.insertEdge("Aveiro","Coimbra","A1",60);
        completeMap.insertEdge("Coimbra","Lisboa","A1",200);
        completeMap.insertEdge("Coimbra","Leiria","A34",80);
        completeMap.insertEdge("Aveiro","Leiria","A17",120);
        completeMap.insertEdge("Leiria","Lisboa","A8",150);
       
        completeMap.insertEdge("Aveiro","Viseu","A25",85);
        completeMap.insertEdge("Leiria","Castelo Branco","A23",170);
        completeMap.insertEdge("Lisboa","Faro","A2",280);
        
        incompleteMap = completeMap.clone ();
        
        incompleteMap.removeEdge("Aveiro","Viseu");
        incompleteMap.removeEdge("Leiria","Castelo Branco");
        incompleteMap.removeEdge("Lisboa","Faro");
    }
    

    /**
     * Test of BreadthFirstSearch method, of class GraphAlgorithms.
     */
    @Test
    public void testBreadthFirstSearch() {
        System.out.println("Test BreadthFirstSearch");

        assertTrue ( AdjacencyMapAlgorithms.BreadthFirstSearch(completeMap, "LX").isEmpty (), "Should be null if vertex does not exist" );

        LinkedList<String> path = AdjacencyMapAlgorithms.BreadthFirstSearch(incompleteMap, "Faro");

        assertTrue ( path.size()==1, "Should be just one" );

        Iterator<String> it = path.iterator();
        assertTrue ( it.next().compareTo("Faro")==0, "it should be Faro" );
        
        path = AdjacencyMapAlgorithms.BreadthFirstSearch(incompleteMap, "Porto");
        assertTrue ( path.size()==7, "Should give seven vertices " );
        
        path = AdjacencyMapAlgorithms.BreadthFirstSearch(incompleteMap, "Viseu");
        assertTrue ( path.size()==3, "Should give 3 vertices" );
    }

    /**
    * Test of shortestPath method, of class GraphAlgorithms.
    */
    @Test
    public void testShortestPath() {
        System.out.println("Test of shortest path");
		
	LinkedList<String> shortPath = new LinkedList<String>();
	double lenpath=0;
        lenpath=AdjacencyMapAlgorithms.shortestPath(completeMap,"Porto","LX",shortPath);
        assertTrue ( lenpath == 0, "Length path should be 0 if vertex does not exist" );
	
        lenpath=AdjacencyMapAlgorithms.shortestPath(incompleteMap,"Porto","Faro",shortPath);
	    assertTrue ( lenpath == 0, "Length path should be 0 if there is no path" );
		
        lenpath=AdjacencyMapAlgorithms.shortestPath(completeMap,"Porto","Porto",shortPath);
        assertTrue ( shortPath.size() == 1, "Number of nodes should be 1 if source and vertex are the same" );
		
	lenpath=AdjacencyMapAlgorithms.shortestPath(incompleteMap,"Porto","Lisboa",shortPath);
        assertTrue ( lenpath == 335, "Path between Porto and Lisboa should be 335 Km" );
		
        Iterator<String> it = shortPath.iterator();

        assertTrue ( it.next().compareTo("Porto")==0, "First in path should be Porto" );
        assertTrue ( it.next().compareTo("Aveiro")==0, "then Aveiro" );
        assertTrue ( it.next().compareTo("Coimbra")==0, "then Coimbra" );
        assertTrue ( it.next().compareTo("Lisboa")==0, "then Lisboa" );

	lenpath=AdjacencyMapAlgorithms.shortestPath(incompleteMap,"Braga","Leiria",shortPath);
        assertTrue ( lenpath == 255, "Path between Braga and Leiria should be 255 Km" );
		
        it = shortPath.iterator();

        assertTrue ( it.next().compareTo("Braga")==0, "First in path should be Braga" );
        assertTrue ( it.next().compareTo("Porto")==0, "then Porto" );
        assertTrue ( it.next().compareTo("Aveiro")==0, "then Aveiro" );
        assertTrue ( it.next().compareTo("Leiria")==0, "then Leiria" );
        
        shortPath.clear();
        lenpath=AdjacencyMapAlgorithms.shortestPath(completeMap,"Porto","Castelo Branco",shortPath);
        assertTrue ( lenpath == 335, "Path between Porto and Castelo Branco should be 335 Km" );
        assertTrue ( shortPath.size() == 5, "N. cities between Porto and Castelo Branco should be 5 " );

        it = shortPath.iterator();

        assertTrue ( it.next().compareTo("Porto")==0, "First in path should be Porto" );
        assertTrue ( it.next().compareTo("Aveiro")==0, "then Aveiro" );
        assertTrue ( it.next().compareTo("Viseu")==0, "then Viseu" );
        assertTrue ( it.next().compareTo("Guarda")==0, "then Guarda" );
        assertTrue ( it.next().compareTo("Castelo Branco")==0, "then Castelo Branco" );

        //Changing Edge: Aveiro-Viseu with Edge: Leiria-C.Branco 
        //should change shortest path between Porto and Castelo Branco

        completeMap.removeEdge("Aveiro", "Viseu");
        completeMap.insertEdge("Leiria","Castelo Branco","A23",170);
	shortPath.clear();
        lenpath=AdjacencyMapAlgorithms.shortestPath(completeMap,"Porto","Castelo Branco",shortPath);
        assertTrue ( lenpath == 365, "Path between Porto and Castelo Branco should now be 365 Km" );
        assertTrue ( shortPath.size() == 4, "Path between Porto and Castelo Branco should be 4 cities" );

        it = shortPath.iterator();

        assertTrue ( it.next().compareTo("Porto")==0, "First in path should be Porto" );
        assertTrue ( it.next().compareTo("Aveiro")==0, "then Aveiro" );
        assertTrue ( it.next().compareTo("Leiria")==0, "then Leiria" );
        assertTrue ( it.next().compareTo("Castelo Branco")==0, "then Castelo Branco" );
		
    }
}

