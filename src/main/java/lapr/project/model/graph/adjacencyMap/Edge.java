package lapr.project.model.graph.adjacencyMap;

import java.lang.reflect.Array;
import java.util.Objects;

/**
 * 
 * @author DEI-ESINF
 * @param <V>
 * @param <E>
 */
//@SuppressWarnings ( "unchecked" )
public class Edge<V,E> implements Comparable<Edge<V,E>> {
    
    private E element;           // Graph.AdjacencyMap.Edge information
    private double weight;       // Graph.AdjacencyMap.Edge weight
    private Vertex<V,E> vOrig;  // vertex origin
    private Vertex<V,E> vDest;  // vertex destination
    
    public Edge() {
        element = null; weight= 0.0; vOrig=null; vDest=null; } 
    
    public Edge(E eInf, double ew, Vertex<V,E> vo, Vertex<V,E> vd) {
        element = eInf; weight= ew; vOrig=vo; vDest=vd;}

    public Edge(Edge<V,E> copy){
        this.element= copy.element;
        this.weight=copy.weight;
        if(copy.vOrig!=null){
            this.vOrig=new Vertex<> (copy.vOrig);
        }
        if(copy.vDest!=null){
            this.vDest=new Vertex<> (copy.vDest);
        }

    }
  
    public E getElement() { return element; }	 
    public void setElement(E eInf) { element = eInf; }
    
    public double getWeight() { return weight; }	 
    public void setWeight(double ew) { weight= ew; }
    
    public V getVOrig() { 
        if (this.vOrig != null) 
            return vOrig.getElement(); 
        return null;
    }	 
    public void setVOrig(Vertex<V,E> vo) { vOrig= vo; }
    
    public V getVDest() { 
        if (this.vDest != null) 
            return vDest.getElement(); 
        return null; 
    }
    public void setVDest(Vertex<V,E> vd) { vDest= vd; }

    @SuppressWarnings ( "unchecked" )
    public V[] getEndpoints() { 
        
        V oElem=null;
        V dElem=null;
        V typeElem=null;
        
        if (this.vOrig != null) 
           oElem = vOrig.getElement();      
        
        if (this.vDest != null)
           dElem = vDest.getElement(); 
        
        if (oElem == null && dElem == null)
          return null;

        if (oElem != null)          // To get type
            typeElem = oElem;
        
        if (dElem != null)
            typeElem = dElem;
        
        V[] endverts = (V [])Array.newInstance(typeElem.getClass(), 2);  

        endverts[0]= oElem; 
        endverts[1]= dElem;
        
        return endverts; 
    }
           
    @Override
    @SuppressWarnings ( "unchecked" )
    public boolean equals(Object otherObj) {
        
        if (this == otherObj){
            return true;
        }
        
        if (otherObj == null || this.getClass() != otherObj.getClass()){
            return false;
        }
        
        Edge<V,E> otherEdge = (Edge<V,E>) otherObj;
        
        // if endpoints vertices are not equal
        if ((this.vOrig == null && otherEdge.vOrig != null) || (this.vOrig != null && otherEdge.vOrig == null))
            return false;
        
        if ((this.vDest == null && otherEdge.vDest!=null) || (this.vDest != null && otherEdge.vDest == null))
            return false;
        
        if (this.vOrig != null && !this.vOrig.equals ( otherEdge.vOrig ))
                return false;
        
        if (this.vDest != null && !this.vDest.equals ( otherEdge.vDest ))
            return false;
      
        if (this.weight != otherEdge.weight)
            return false;
        
        if (this.element != null && otherEdge.element != null) 
           return this.element.equals(otherEdge.element);
        
        return true;
    }
    
    @Override
    public int compareTo(Edge<V,E> other) {
        return Double.compare ( this.weight, other.weight );
    }

    @Override
    public String toString() {
        String st="";
        if (element != null)
           st= "      (" + element + ") - ";
        else
            st= "\t "; 
            
        if (weight != 0)
            st += weight +" - " +vDest.getElement()+ "\n";
        else
            st += vDest.getElement()+ "\n";

        return st;
    }

    @Override
    public int hashCode() {
        return Objects.hash ( element, weight, vOrig, vDest );
    }
}
