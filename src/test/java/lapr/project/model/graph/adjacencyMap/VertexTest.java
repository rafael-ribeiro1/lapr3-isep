/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.model.graph.adjacencyMap;

import java.util.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
 *
 * @author frodrigues
 */
public class VertexTest {
    
    Vertex<String, Integer> instance = new Vertex<>() ;
    
    public VertexTest() {
    }

    @Test
    public void constructorTeste(){
        Vertex<Object, Object> v=new Vertex<> ();
        v.setElement ( 1 );
        v.setKey ( 1 );
        Vertex<Object, Object> v2=new Vertex<> (v);
        assertNotNull(v2);
    }
    
    /**
     * Test of getKey method, of class Vertex.
     */
    @Test
    public void testGetKey() {
        System.out.println("getKey");
        
        int expResult = -1;
        assertEquals(expResult, instance.getKey());
        
        Vertex<String, Integer> instance1 = new Vertex<>(1,"Vertex1");
        expResult = 1;
        assertEquals(expResult, instance1.getKey());
    }

    /**
     * Test of setKey method, of class Vertex.
     */
    @Test
    public void testSetKey() {
        System.out.println("setKey");
        int k = 2;
        instance.setKey(k);
        int expResult = 2;
        assertEquals(expResult, instance.getKey());
    }

    /**
     * Test of getElement method, of class Vertex.
     */
    @Test
    public void testGetElement() {
        System.out.println("getElement");
        
        String expResult = null;
        assertEquals(expResult, instance.getElement());

        Vertex<String, Integer> instance1 = new Vertex<>(1,"Vertex1");
        expResult = "Vertex1";
        assertEquals(expResult, instance1.getElement());
        
    }

    /**
     * Test of setElement method, of class Vertex.
     */
    @Test
    public void testSetElement() {
        System.out.println("setElement");
        String vInf = "Vertex1";
        instance.setElement(vInf);
        assertEquals(vInf, instance.getElement());
    }

    /**
     * Test of addAdjVert method, of class Vertex.
     */
    @Test
    public void testAddAdjVert() {
        System.out.println("addAdjVert");
        
        assertTrue ( (instance.numAdjVerts()==0), "result should be zero" );
        
        String vAdj1 = "VAdj1"; 
        Edge<String,Integer> edge = new Edge<>();
        
        instance.addAdjVert(vAdj1,edge);
        assertTrue ( (instance.numAdjVerts()==1), "result should be one" );
        
        String vAdj2 = "VAdj2";  
        instance.addAdjVert(vAdj2,edge);
        assertTrue ( (instance.numAdjVerts()==2), "result should be two" );
    }

    /**
     * Test of getAdjVert method, of class Vertex.
     */
    @Test
    public void testGetAdjVert() {
        System.out.println("getAdjVert");

        Edge<String,Integer> edge = new Edge<>(); 
        Object expResult = null;
        assertEquals(expResult, instance.getAdjVert(edge));
        
        String vAdj = "VertexAdj";    
        instance.addAdjVert(vAdj,edge);
        assertEquals(vAdj, instance.getAdjVert(edge));
    }

    /**
     * Test of remAdjVert method, of class Vertex.
     */
    @Test
    public void testRemAdjVert() {
        System.out.println("remAdjVert");

        Edge<String,Integer> edge1 = new Edge<>(); 
        String vAdj = "VAdj1"; 
        instance.addAdjVert(vAdj,edge1);
        
        Edge<String,Integer> edge2 = new Edge<>(); 
        vAdj = "VAdj2"; 
        instance.addAdjVert(vAdj,edge2);
        
        instance.remAdjVert(vAdj);
        assertTrue ( (instance.numAdjVerts()==1), "result should be one" );
        
        vAdj = "VAdj1"; 
        instance.remAdjVert(vAdj);
        assertTrue ( (instance.numAdjVerts()==0), "result should be zero" );
    }

    /**
     * Test of getEdge method, of class Vertex.
     */
    @Test
    public void testGetEdge() {
        System.out.println("getEdge");
        
        Edge<String,Integer> edge1 = new Edge<>(); 
        String vAdj1 = "VAdj1"; 
        instance.addAdjVert(vAdj1,edge1);

        assertEquals(edge1, instance.getEdge(vAdj1));
        
        Edge<String,Integer> edge2 = new Edge<>(); 
        String vAdj2 = "VAdj2"; 
        instance.addAdjVert(vAdj2,edge2);
        
        assertEquals(edge2, instance.getEdge(vAdj2));
    }

    /**
     * Test of numAdjVerts method, of class Vertex.
     */
    @Test
    public void testNumAdjVerts() {
        System.out.println("numAdjVerts");
        
        Edge<String,Integer> edge1 = new Edge<>(); 
        String vAdj1 = "VAdj1"; 
        instance.addAdjVert(vAdj1,edge1);
        
        assertTrue ( (instance.numAdjVerts()==1), "result should be one" );
        
        Edge<String,Integer> edge2 = new Edge<>(); 
        String vAdj2 = "VAdj2"; 
        instance.addAdjVert(vAdj2,edge2);
        
        assertTrue ( (instance.numAdjVerts()==2), "result should be two" );
        
        instance.remAdjVert(vAdj1);
        
        assertTrue ( (instance.numAdjVerts()==1), "result should be one" );
        
        instance.remAdjVert(vAdj2);
        assertTrue ( (instance.numAdjVerts()==0), "result should be zero" );
        
    }

    /**
     * Test of getAllAdjVerts method, of class Vertex.
     */
    @Test
    public void testGetAllAdjVerts() {
        System.out.println("getAllAdjVerts");
   
        Iterator<String> itVerts = instance.getAllAdjVerts().iterator();
        
        assertTrue ( itVerts.hasNext()==false, "Adjacency vertices should be empty" );
        
        Edge<String,Integer> edge1 = new Edge<>(); 
        String vAdj1 = "VAdj1"; 
        instance.addAdjVert(vAdj1,edge1);
        
        Edge<String,Integer> edge2 = new Edge<>(); 
        String vAdj2 = "VAdj2"; 
        instance.addAdjVert(vAdj2,edge2);
        
        itVerts = instance.getAllAdjVerts().iterator();
        
        assertTrue ( (itVerts.next().compareTo("VAdj1")==0), "first adjacency vertice should be VAdj1" );
        assertTrue ( (itVerts.next().compareTo("VAdj2")==0), "second adjacencyvertice should be VAdj2" );

        instance.remAdjVert(vAdj1);
		
        itVerts = instance.getAllAdjVerts().iterator();
        assertTrue ( (itVerts.next().compareTo("VAdj2"))==0, "first adjacency vertice should be VAdj2" );

	instance.remAdjVert(vAdj2);
		
        itVerts = instance.getAllAdjVerts().iterator();
	assertTrue ( itVerts.hasNext()==false, "Adjacency vertices should now be empty" );
    }

    /**
     * Test of getAllOutEdges method, of class Vertex.
     */
    @Test
    public void testGetAllOutEdges() {
        System.out.println("getAllOutEdges");
        
        Iterator<Edge<String,Integer>> itEdges = instance.getAllOutEdges().iterator();
        
        assertTrue ( itEdges.hasNext()==false, "Adjacency edges should be empty" );
        
        Edge<String,Integer> edge1 = new Edge<>(); 
        String vAdj1 = "VAdj1"; 
        instance.addAdjVert(vAdj1,edge1);
        
        Edge<String,Integer> edge2 = new Edge<>(); 
        String vAdj2 = "VAdj2"; 
        instance.addAdjVert(vAdj2,edge2);
        
        itEdges = instance.getAllOutEdges().iterator();
        
        assertTrue ( (itEdges.next().compareTo(edge1)==0), "first adjacency edge should be edge1" );
        assertTrue ( (itEdges.next().compareTo(edge2)==0), "second adjacency edge should be edge2" );

        instance.remAdjVert(vAdj1);
		
        itEdges = instance.getAllOutEdges().iterator();
        assertTrue ( (itEdges.next().compareTo(edge2))==0, "first adjacency edge should be edge2" );

	instance.remAdjVert(vAdj2);
		
        itEdges = instance.getAllOutEdges().iterator();
	assertTrue ( itEdges.hasNext()==false, "Adjacency edges should now be empty" );
    }

    /**
     * Test of equals method, of class Vertex.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        
        instance.setKey(1);
        instance.setElement("Vertex1");
        
        Vertex<String, Integer> instance2 = new Vertex<>(2,"Vertex2");
        
        Edge<String,Integer> edge1 = new Edge<>(null, 2, instance, instance2);
        instance.addAdjVert("vAdj2",edge1);

        assertFalse ( instance.equals(null), "should not be equal to null" );
		
	    assertTrue ( instance.equals(new Vertex<> (instance)), "should be equal to itself" );
		
	    assertTrue ( instance.equals(new Vertex<> (instance)), "should be equal to a clone" );
        
        Vertex<String,Integer> other = new Vertex<> (instance);
        other.remAdjVert("vAdj2");
        assertFalse ( instance.equals(other), "instance should not be equal to other" );
        
        other.addAdjVert("vAdj2",edge1);
        assertTrue ( instance.equals(other), "instance should be equal to other" );
        
        Vertex<String, Integer> instance3 = new Vertex<>(3,"Vertex3");
        Edge<String,Integer> edge2 = new Edge<>(null, 3, instance, instance3); 
        instance.addAdjVert("vAdj3",edge2);
        assertFalse ( instance.equals(other), "instance should not be equal to other" );
    }

    @Test
    public void equalsTeste2(){
        boolean result;

        Vertex<Integer,Integer> v1=new Vertex<> ();
        Vertex<Integer,Integer> v2=new Vertex<> ();

        result=v1.equals ( new ArrayList<> () );
        assertFalse ( result );

        v1.setKey ( 1 );
        v1.setElement ( 2 );

        v2.setKey ( 2 );
        v2.setElement ( 3 );
        result=v1.equals ( v2 );
        assertFalse ( result );

        Vertex<Integer,Integer> v3=new Vertex<> ();
        v3.setElement ( null );
        result=v3.equals ( v2 );
        assertFalse ( result );

        result=v2.equals ( v3 );
        assertFalse ( result );

        v3.setElement ( 3 );
        result=v2.equals ( v3 );
        assertFalse ( result );

        //bug fix L68
        v2=v1;
        result=v2.equals ( v1 );
        assertTrue (result );
    }
    /*
    Teste de bug fix L79 do sonarqube
     */
    @Test
    public void equalsTeste3(){
        boolean result;
        Vertex<Integer,Integer> v1=new Vertex<> ();
        Vertex<Integer,Integer> v2=new Vertex<> ();

        //caso 1 - v1.element é null e v2.element não é null
        v2.setElement ( 1 );
        result=v1.equals ( v2 );
        assertFalse ( result );
        v2.setElement ( null );
        //caso 2 - v1.element não é null e v2.element é null
        v1.setElement ( 1 );
        result=v1.equals ( v2 );
        assertFalse ( result );
        v1.setElement ( null );
        //caso 3 - ambos nulos
        result=v1.equals ( v2 );
        assertTrue ( result );

        //caso 4 - ambos diferentes
        v1.setElement ( 1 );
        v2.setElement ( 2 );

        result=v1.equals ( v2 );
        assertFalse ( result );
    }
    /*
    Bug fix L94
     */
    @Test
    public void equalsTeste4(){
        Vertex<Integer,Integer> v1=new Vertex<> ();
        Vertex<Integer,Integer> v2=new Vertex<> ();
        Vertex<Integer,Integer> v3=new Vertex<> ();
        Vertex<Integer,Integer> v4=new Vertex<> ();
        v1.setKey ( 1 );
        v1.setElement ( 1 );
        v2.setKey ( 1 );
        v2.setElement ( 1 );
        v3.setKey ( 3 );
        v3.setElement ( 3 );

        v1.addAdjVert ( 2, new Edge<> () );
        v2.addAdjVert ( 1, new Edge<> () );
        v1.equals ( v2 );

    }

    /**
     * Test of toString method, of class Vertex.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        
        instance.setKey(1);
        instance.setElement("Vertex1");
        
        Vertex<String, Integer> instance2 = new Vertex<>(2,"Vertex2");
        
        Edge<String,Integer> edge1 = new Edge<>(null, 2, instance, instance2); 
        String vAdj2 = "VAdj2"; 
        instance.addAdjVert(vAdj2,edge1);
        
        Vertex<String, Integer> instance3 = new Vertex<>(3,"Vertex3");
        Edge<String,Integer> edge2 = new Edge<>(null, 3, instance, instance3); 
        String vAdj3 = "VAdj3"; 
        instance.addAdjVert(vAdj3,edge2);
        
        System.out.println(instance);
    }

    @Test
    public void toStringTeste2(){
        String expected,result;
        Vertex<Integer, Integer> v=new Vertex<> ();

        expected="";
        result=v.toString ();
        assertEquals ( expected,result );
    }

    @Test
    public void hashCodeTest2(){
        int expected,result;
        int key, element;
        Vertex<Integer, Integer> v=new Vertex<> ();
        key=1;
        element=2;
        v.setKey ( key );
        v.setElement ( element );

        expected= 30814;
        result=v.hashCode ();
        assertEquals ( expected,result );
    }
}
