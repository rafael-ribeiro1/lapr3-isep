package lapr.project.model.graph.adjacencyMap;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


/**
 *
 * @author DEI-ISEP
 */
public class GraphTest {
    
    Graph<String, String> instance = new Graph<>(true) ;
    
    public GraphTest() {
    }

    @Test
    void testAdjVertices(){
        assertNull(instance.adjVertices(null));
        instance.insertVertex("A");
        instance.insertVertex("B");
        instance.insertEdge("A", "B", "AB",4);
    }

    /**
     * Test of numVertices method, of class Graph.
     */
    @Test
     void testNumVertices() {
        System.out.println("Test numVertices");
                      
        assertTrue ( (instance.numVertices()==0), "result should be zero" );
        
        instance.insertVertex("A");
        assertTrue ( (instance.numVertices()==1), "result should be one" );
        
        instance.insertVertex("B");
        assertTrue ( (instance.numVertices()==2), "result should be two" );
        
        instance.removeVertex("A");
        assertTrue ( (instance.numVertices()==1), "result should be one" );
        
        instance.removeVertex("B");
        assertTrue ( (instance.numVertices()==0), "result should be zero" );
    }
    
    /**
     * Test of vertices method, of class Graph.
     */
    @Test
     void testVertices() {
        System.out.println("Test vertices");

        Iterator<String> itVerts = instance.vertices().iterator();

        assertFalse ( itVerts.hasNext (), "vertices should be empty" );
        
        instance.insertVertex("A");
        instance.insertVertex("B");
        	
        itVerts = instance.vertices().iterator();
                
        assertTrue ( (itVerts.next().compareTo("A")==0), "first vertice should be A" );
        assertTrue ( (itVerts.next().compareTo("B")==0), "second vertice should be B" );

        instance.removeVertex("A");
		
        itVerts = instance.vertices().iterator();
        assertEquals ( 0, (itVerts.next ().compareTo ( "B" )), "first vertice should now be B" );

	instance.removeVertex("B");
		
        itVerts = instance.vertices().iterator();
        assertEquals ( false, itVerts.hasNext (), "vertices should now be empty" );
    }

    /**
     * Test of numEdges method, of class Graph.
     */
    @Test
     void testNumEdges() {
        System.out.println("Test numEdges");
        
        assertTrue ( (instance.numEdges()==0), "result should be zero" );

        instance.insertEdge("A","B","Edge1",6);
        assertTrue ( (instance.numEdges()==1), "result should be one" );
        
        instance.insertEdge("A","C","Edge2",1);
        assertTrue ( (instance.numEdges()==2), "result should be two" );
        
        instance.removeEdge("A","B");
        assertTrue ( (instance.numEdges()==1), "result should be one" );

        instance.removeEdge("A","C");
        assertTrue ( (instance.numEdges()==0), "result should be zero" );
    }

    /**
     * Test of edges method, of class Graph.
     */
    @Test
     void testEdges() {
        System.out.println("Test Edges");

        Iterator<Edge<String,String>> itEdge = instance.edges().iterator();

        assertFalse ( (itEdge.hasNext ()), "edges should be empty" );

        instance.insertEdge("A","B","Edge1",6);
        instance.insertEdge("A","C","Edge2",1);
        instance.insertEdge("B","D","Edge3",3);
        instance.insertEdge("C","D","Edge4",4);
        instance.insertEdge("C","E","Edge5",1);
        instance.insertEdge("D","A","Edge6",2);
        instance.insertEdge("E","D","Edge7",1);
        instance.insertEdge("E","E","Edge8",1);

        itEdge = instance.edges().iterator();
        
        itEdge.next(); itEdge.next();
        assertEquals( "Edge3", itEdge.next().getElement(),"third edge should be B-D");

        itEdge.next(); itEdge.next();
        assertEquals( "Edge6", itEdge.next().getElement(),"sixth edge should be D-A");
        
        instance.removeEdge("A","B");

        itEdge = instance.edges().iterator();
        assertEquals("Edge2", itEdge.next().getElement(), "first edge should now be A-C");

        instance.removeEdge("A","C"); instance.removeEdge("B","D");
        instance.removeEdge("C","D"); instance.removeEdge("C","E");
        instance.removeEdge("D","A"); instance.removeEdge("E","D");
        instance.removeEdge("E","E");
        itEdge = instance.edges().iterator();
        assertTrue ( (itEdge.hasNext()==false), "edges should now be empty" );
    }

    /**
     * Test of getEdge method, of class Graph.
     */
    @Test
     void testGetEdge() {
        System.out.println("Test getEdge");
		        
        instance.insertEdge("A","B","Edge1",6);
        instance.insertEdge("A","C","Edge2",1);
        instance.insertEdge("B","D","Edge3",3);
        instance.insertEdge("C","D","Edge4",4);
        instance.insertEdge("C","E","Edge5",1);
        instance.insertEdge("D","A","Edge6",2);
        instance.insertEdge("E","D","Edge7",1);
        instance.insertEdge("E","E","Edge8",1);

        assertNull(instance.getEdge("A", "E"), "edge should be null");

        assertEquals( "Edge3", instance.getEdge("B", "D").getElement(),"edge between B-D");
        assertNull(instance.getEdge("D", "B"), "edge should be null");

	instance.removeEdge("D","A");
        assertNull(instance.getEdge("D", "A"), "edge should be null");

        assertEquals(true, instance.getEdge("E", "E").getElement().equals("Edge8"), "edge should be edge8");
    }

    /**
     * Test of endVertices method, of class Graph.
     */
    @Test
     void testEndVertices() {
        System.out.println("Test endVertices");
        			 
        instance.insertEdge("A","B","Edge1",6);
        instance.insertEdge("A","C","Edge2",1);
        instance.insertEdge("B","D","Edge3",3);
        instance.insertEdge("C","D","Edge4",4);
        instance.insertEdge("C","E","Edge5",1);
        instance.insertEdge("D","A","Edge6",2);
        instance.insertEdge("E","D","Edge7",1);
        instance.insertEdge("E","E","Edge8",1);
        
        Edge<String,String> edge0 = new Edge<>();
        
        String[] vertices = new String[2];
        
        //assertTrue("endVertices should be null", instance.endVertices(edge0)==null);

        Edge<String,String> edge1 = instance.getEdge("A","B");
        //vertices = instance.endVertices(edge1);
        assertEquals("A",instance.endVertices(edge1)[0],  "first vertex should be A");
        assertEquals( "B", instance.endVertices(edge1)[1],"second vertex should be B");
    }

    /**
     * Test of opposite method, of class Graph.
     */
    @Test
     void testOpposite() {
        System.out.println("Test opposite");
        		
        instance.insertVertex("A");
        instance.insertVertex("B");
        instance.insertVertex("C");
        instance.insertVertex("D");
        instance.insertVertex("E");
        
        instance.insertEdge("A","B","Edge1",6);
        instance.insertEdge("A","C","Edge2",1);
        instance.insertEdge("B","D","Edge3",3);
        instance.insertEdge("C","D","Edge4",4);
        instance.insertEdge("C","E","Edge5",1);
        instance.insertEdge("D","A","Edge6",2);
        instance.insertEdge("E","D","Edge7",1);
        instance.insertEdge("E","E","Edge8",1);
		     
        Edge<String,String> edge5 = instance.getEdge("C","E");
        String vert = instance.opposite("A", edge5);
        assertNull(vert, "opposite should be null");
        
        Edge<String,String> edge1 = instance.getEdge("A","B");
        vert = instance.opposite("A", edge1);
        assertEquals( "B", vert,"opposite should be B");
        
        Edge<String,String> edge8 = instance.getEdge("E","E");
        vert = instance.opposite("E", edge8);
        assertEquals( "E", vert,"opposite should be E");

        assertNull(instance.opposite(null, instance.getEdge(null, null)));
    }

    /**
     * Test of outDegree method, of class Graph.
     */
    @Test
     void testOutDegree() {
        System.out.println("Test outDegree");
        		
        instance.insertVertex("A");
        instance.insertVertex("B");
        instance.insertVertex("C");
        instance.insertVertex("D");
        instance.insertVertex("E");
        
        instance.insertEdge("A","B","Edge1",6);
        instance.insertEdge("A","C","Edge2",1);
        instance.insertEdge("B","D","Edge3",3);
        instance.insertEdge("C","D","Edge4",4);
        instance.insertEdge("C","E","Edge5",1);
        instance.insertEdge("D","A","Edge6",2);
        instance.insertEdge("E","D","Edge7",1);
        instance.insertEdge("E","E","Edge8",1);
		    
        int outdeg = instance.outDegree("G");
        assertEquals(-1, outdeg, "degree should be -1");
        
        outdeg = instance.outDegree("A");
        assertEquals(2, outdeg, "degree should be 2");
        
        outdeg = instance.outDegree("B");
        assertEquals(1, outdeg,  "degree should be 1");
         
        outdeg = instance.outDegree("E");
        assertEquals(2, outdeg, "degree should be 2");
    }

    /**
     * Test of inDegree method, of class Graph.
     */
    @Test
     void testInDegree() {
        System.out.println("Test inDegree");
        
        instance.insertVertex("A");
        instance.insertVertex("B");
        instance.insertVertex("C");
        instance.insertVertex("D");
        instance.insertVertex("E");
        
        instance.insertEdge("A","B","Edge1",6);
        instance.insertEdge("A","C","Edge2",1);
        instance.insertEdge("B","D","Edge3",3);
        instance.insertEdge("C","D","Edge4",4);
        instance.insertEdge("C","E","Edge5",1);
        instance.insertEdge("D","A","Edge6",2);
        instance.insertEdge("E","D","Edge7",1);
        instance.insertEdge("E","E","Edge8",1);
		       
        int indeg = instance.inDegree("G");
        assertEquals(-1, indeg, "in degree should be -1");
        
        indeg = instance.inDegree("A");
        assertEquals( 1, indeg,"in degree should be 1");
        
        indeg = instance.inDegree("D");
        assertEquals(3,indeg,  "in degree should be 3");
         
        indeg = instance.inDegree("E");
        assertEquals( 2, indeg,"in degree should be 2");
    }

    /**
     * Test of outgoingEdges method, of class Graph.
     */
    @Test
     void testOutgoingEdges() {
        System.out.println(" Test outgoingEdges");
        		
        instance.insertVertex("A");
        instance.insertVertex("B");
        instance.insertVertex("C");
        instance.insertVertex("D");
        instance.insertVertex("E");
        
        instance.insertEdge("A","B","Edge1",6);
        instance.insertEdge("A","C","Edge2",1);
        instance.insertEdge("B","D","Edge3",3);
        instance.insertEdge("C","D","Edge4",4);
        instance.insertEdge("C","E","Edge5",1);
        instance.insertEdge("D","A","Edge6",2);
        instance.insertEdge("E","D","Edge7",1);
        instance.insertEdge("E","E","Edge8",1);
		                        
        Iterator<Edge<String,String>> itEdge = instance.outgoingEdges("C").iterator();
        Edge<String,String> first = itEdge.next();
        Edge<String,String> second = itEdge.next();
        assertTrue ( ( (first.getElement().equals("Edge4")==true && second.getElement().equals("Edge5")==true) ||
                            (first.getElement().equals("Edge5")==true && second.getElement().equals("Edge4")==true) ),
                "Outgoing Edges of vert C should be Edge4 and Edge5" );
        
        instance.removeEdge("E","E");
        
        itEdge = instance.outgoingEdges("E").iterator();
        assertTrue ( (itEdge.next().getElement().equals("Edge7")==true), "first edge should be Edge7" );
        
        instance.removeEdge("E","D");

        itEdge = instance.outgoingEdges("E").iterator();
        assertTrue ( (itEdge.hasNext()==false), "edges should be empty" );

        assertNull(instance.outgoingEdges(null));
    }

    /**
     * Test of incomingEdges method, of class Graph.
     */
    @Test
     void testIncomingEdges() {
        		
        instance.insertVertex("A");
        instance.insertVertex("B");
        instance.insertVertex("C");
        instance.insertVertex("D");
        instance.insertVertex("E");
        
        instance.insertEdge("A","B","Edge1",6);
        instance.insertEdge("A","C","Edge2",1);
        instance.insertEdge("B","D","Edge3",3);
        instance.insertEdge("C","D","Edge4",4);
        instance.insertEdge("C","E","Edge5",1);
        instance.insertEdge("D","A","Edge6",2);
        instance.insertEdge("E","D","Edge7",1);
        instance.insertEdge("E","E","Edge8",1);
		      
        Iterator<Edge<String,String>> itEdge = instance.incomingEdges("D").iterator();
        
        assertTrue ( (itEdge.next().getElement().equals("Edge3")==true), "first edge should be edge3" );
        assertTrue ( (itEdge.next().getElement().equals("Edge4")==true), "second edge should be edge4" );
        assertTrue ( (itEdge.next().getElement().equals("Edge7")==true), "third edge should be edge7" );
        
        itEdge = instance.incomingEdges("E").iterator();
        
        assertTrue ( (itEdge.next().getElement().equals("Edge5")==true), "first edge should be Edge5" );
        assertTrue ( (itEdge.next().getElement().equals("Edge8")==true), "second edge should be Edge8" );
        
        instance.removeEdge("E","E");
        
        itEdge = instance.incomingEdges("E").iterator();
        
        assertTrue ( (itEdge.next().getElement().equals("Edge5")==true), "first edge should be Edge5" );
        
        instance.removeEdge("C","E");

        itEdge = instance.incomingEdges("E").iterator();
        assertTrue ( (itEdge.hasNext()==false), "edges should be empty" );
    }

    /**
     * Test of insertVertex method, of class Graph.
     */
    @Test
     void testInsertVertex() {
        System.out.println("Test insertVertex");
        
        instance.insertVertex("A");   
        instance.insertVertex("B");    
        instance.insertVertex("C");    
        instance.insertVertex("D");      
        instance.insertVertex("E");
             
        Iterator <String> itVert = instance.vertices().iterator();
		
        assertTrue ( (itVert.next().equals("A")==true), "first vertex should be A" );
        assertTrue ( (itVert.next().equals("B")==true), "second vertex should be B" );
        assertTrue ( (itVert.next().equals("C")==true), "third vertex should be C" );
        assertTrue ( (itVert.next().equals("D")==true), "fourth vertex should be D" );
        assertTrue ( (itVert.next().equals("E")==true), "fifth vertex should be E" );

        assertFalse(instance.insertVertex("A"));
    }
    
    /**
     * Test of insertEdge method, of class Graph.
     */
    @Test
     void testInsertEdge() {
        System.out.println("Test insertEdge");
        
        assertTrue ( (instance.numEdges()==0), "num. edges should be zero" );

        instance.insertEdge("A","B","Edge1",6);
        assertTrue ( (instance.numEdges()==1), "num. edges should be 1" );
        
        instance.insertEdge("A","C","Edge2",1);
        assertTrue ( (instance.numEdges()==2), "num. edges should be 2" );
        
        instance.insertEdge("B","D","Edge3",3);
        assertTrue ( (instance.numEdges()==3), "num. edges should be 3" );
        
        instance.insertEdge("C","D","Edge4",4);
        assertTrue ( (instance.numEdges()==4), "num. edges should be 4" );
        
        instance.insertEdge("C","E","Edge5",1);
        assertTrue ( (instance.numEdges()==5), "num. edges should be 5" );
        
        instance.insertEdge("D","A","Edge6",2);
        assertTrue ( (instance.numEdges()==6), "num. edges should be 6" );
        
        instance.insertEdge("E","D","Edge7",1);
        assertTrue ( (instance.numEdges()==7), "num. edges should be 7" );
        
        instance.insertEdge("E","E","Edge8",1);
        assertTrue ( (instance.numEdges()==8), "num. edges should be 8" );
        
        Iterator <Edge<String,String>> itEd = instance.edges().iterator();
		
        itEd.next(); itEd.next();
        assertTrue ( (itEd.next().getElement().equals("Edge3")==true), "third edge should be Edge3" );
        itEd.next(); itEd.next();
        assertTrue ( (itEd.next().getElement().equals("Edge6")==true), "sixth edge should be Edge6" );

        Graph<String, String> notDirected = new Graph<>(false);
        assertTrue(notDirected.insertEdge(null, null, "", 0));
        assertTrue(notDirected.insertEdge("A", "B", "AB", 5));
        assertFalse(notDirected.insertEdge("B", "A", "BA", 5));
        Graph<String, String> directed = new Graph<>(true);
        assertTrue(directed.insertEdge(null, null, "", 0));
        assertTrue(directed.insertEdge("A", "B", "AB", 5));
        assertTrue(directed.insertEdge("B", "A", "BA", 5));
    }

    /**
     * Test of removeVertex method, of class Graph.
     */
    @Test
     void testRemoveVertex() {
        System.out.println("Test removeVertex");
        
        instance.insertVertex("A");
        instance.insertVertex("B");
        instance.insertVertex("C");
        instance.insertVertex("D");
        instance.insertVertex("E");
 
        instance.removeVertex("C");
        assertTrue ( (instance.numVertices()==4), "Num vertices should be 4" );
      
        Iterator<String> itVert = instance.vertices().iterator();
        assertTrue ( (itVert.next().equals("A")==true), "first vertex should be A" );
        assertTrue ( (itVert.next().equals("B")==true), "second vertex should be B" );
        assertTrue ( (itVert.next().equals("D")==true), "third vertex should be D" );
        assertTrue ( (itVert.next().equals("E")==true), "fourth vertex should be E" );
        
        instance.removeVertex("A");
        assertTrue ( (instance.numVertices()==3), "Num vertices should be 3" );
   
        itVert = instance.vertices().iterator();
        assertTrue ( (itVert.next().equals("B")==true), "first vertex should be B" );
        assertTrue ( (itVert.next().equals("D")==true), "second vertex should be D" );
        assertTrue ( (itVert.next().equals("E")==true), "third vertex should be E" );

        instance.removeVertex("E");
        assertTrue ( (instance.numVertices()==2), "Num vertices should be 2" );

        itVert = instance.vertices().iterator();

        assertEquals("B", itVert.next(), "first vertex should be B");
        assertEquals("D", itVert.next(), "second vertex should be D");
        
        instance.removeVertex("B"); instance.removeVertex("D");
        assertTrue ( (instance.numVertices()==0), "Num vertices should be 4" );

        assertFalse(instance.removeVertex(null));
    }
    
    /**
     * Test of removeEdge method, of class Graph.
     */
    @Test
     void testRemoveEdge() {
        System.out.println("Test removeEdge");
        
        assertTrue ( (instance.numEdges()==0), "num. edges should be zero" );

        instance.insertEdge("A","B","Edge1",6);
        instance.insertEdge("A","C","Edge2",1);
        instance.insertEdge("B","D","Edge3",3);
        instance.insertEdge("C","D","Edge4",4);
        instance.insertEdge("C","E","Edge5",1);
        instance.insertEdge("D","A","Edge6",2);
        instance.insertEdge("E","D","Edge7",1);
        instance.insertEdge("E","E","Edge8",1);

        assertTrue ( (instance.numEdges()==8), "Num. edges should be 8" );
        
        instance.removeEdge("E","E");
        assertTrue ( (instance.numEdges()==7), "Num. edges should be 7" );
        
        Iterator <Edge<String,String>> itEd = instance.edges().iterator();
		
        itEd.next(); itEd.next();
        assertTrue ( (itEd.next().getElement().equals("Edge3")==true), "third edge should be Edge3" );
        itEd.next(); itEd.next(); 
        assertTrue ( (itEd.next().getElement().equals("Edge6")==true), "sixth edge should be Edge6" );
        
        instance.removeEdge("C","D");
        assertTrue ( (instance.numEdges()==6), "Num. edges should be 6" );
        
        itEd = instance.edges().iterator();	
        itEd.next(); itEd.next();
        assertTrue ( (itEd.next().getElement().equals("Edge3")==true), "third edge should be Edge3" );
        assertTrue ( (itEd.next().getElement().equals("Edge5")==true), "fourth edge should be Edge5" );
        assertTrue ( (itEd.next().getElement().equals("Edge6")==true), "fifth edge should be Edge6" );
        assertTrue ( (itEd.next().getElement().equals("Edge7")==true), "...last edge should be Edge7" );

        Graph<String, String> g = new Graph<>(false);
        assertFalse(g.removeEdge(null, null));
        g.insertVertex("A");
        g.insertVertex("B");
        assertFalse(g.removeEdge("A", null));
        assertFalse(g.removeEdge(null, "B"));
        assertFalse(g.removeEdge("A", "B"));
    }

    @Test
     void testHashCode() {
       int expected = Objects.hash ( instance.numVertices(), instance.numEdges(), true, instance.vertices());
       assertEquals(expected, instance.hashCode());
    }

    /**
     * Test of toString method, of class Graph.
     */
    @Test
     void testClone() {
	System.out.println("Test Clone");
         
        instance.insertEdge("A","B","Edge1",6);
        instance.insertEdge("A","C","Edge2",1);
        instance.insertEdge("B","D","Edge3",3);
        instance.insertEdge("C","D","Edge4",4);
        instance.insertEdge("C","E","Edge5",1);
        instance.insertEdge("D","A","Edge6",2);
        instance.insertEdge("E","D","Edge7",1);
        instance.insertEdge("E","E","Edge8",1);
        
        Graph<String,String> instClone = new Graph<> (instance);

        assertEquals(instClone.numVertices(), instance.numVertices(), "number of vertices should be equal");
        assertEquals(instClone.numEdges(), instance.numEdges(), "number of edges should be equal");
	
        //vertices should be equal
        Iterator<String> itvertClone = instClone.vertices().iterator();
        Iterator<String> itvertSource = instance.vertices().iterator();
	while (itvertSource.hasNext())
            assertTrue ( (itvertSource.next().equals(itvertClone.next())==true), "vertices should be equal " );

        Graph<String,String> noEdgesGraph = new Graph<> (true);
        noEdgesGraph.insertVertex("A");
        noEdgesGraph.insertVertex("B");
        noEdgesGraph.insertVertex("C");
        Graph<String,String> noEdgesClone = noEdgesGraph.clone ();

        assertEquals(noEdgesGraph.numVertices(), noEdgesGraph.numVertices(), "number of vertices should be equal");
        assertEquals(noEdgesGraph.numEdges(), noEdgesGraph.numEdges(), "number of edges should be equal");
    }

    @Test
     void testEquals() {
        System.out.println("Test Equals");
              
        instance.insertEdge("A","B","Edge1",6);
        instance.insertEdge("A","C","Edge2",1);
        instance.insertEdge("B","D","Edge3",3);
        instance.insertEdge("C","D","Edge4",4);
        instance.insertEdge("C","E","Edge5",1);
        instance.insertEdge("D","A","Edge6",2);
        instance.insertEdge("E","D","Edge7",1);
        instance.insertEdge("E","E","Edge8",1);

        assertNotEquals(null, instance, "should not be equal to null");
        assertNotEquals(new Vertex<>(), instance, "should not be equal to an object of another class");

        assertEquals(instance, instance, "should be equal to itself");

        assertEquals(new Graph<>(instance), instance, "should be equal to a clone");
        
        Graph<String,String> other = new Graph<> (instance);
       
        other.removeEdge("E","E");
        assertNotEquals(other, instance, "instance should not be equal to other");
        
        other.insertEdge("E","E","Edge8",1);
        assertEquals(other, instance, "instance should be equal to other");
        
        other.removeVertex("D");
        assertNotEquals(other, instance, "instance should not be equal to other");

        Graph<String,String> emptyOther = new Graph<> (true);
        assertEquals(emptyOther, emptyOther);
        assertNotEquals(other, emptyOther);
        assertNotEquals(instance, emptyOther);
    }
    
    
    /**
     * Test of toString method, of class Graph.
     */
    @Test
     void testToString() {
        instance.insertVertex("A");
        instance.insertVertex("B");
        instance.insertVertex("C");
        assertEquals("Graph.AdjacencyMap.Graph: " + 3 + " vertices, " + 0 + " edges\nA (0): \n\nB (1): \n\nC (2): \n\n", instance.toString());
        instance.removeVertex("A");
        instance.removeVertex("B");
        instance.removeVertex("C");
        assertEquals("\nGraph.AdjacencyMap.Graph not defined!!",instance.toString());
    }

    @Test
     void test(){
        List<Integer> l1=new ArrayList<> ();
        Integer i=1;
        l1.add ( i );
        List<Integer> l2=new ArrayList<> (l1);
        l2.set ( 0,2 );
        System.out.println (l1.get ( 0 ));
        System.out.println (l2.get ( 0 ));
    }
}

