/*
* A collection of graph algorithms.
*/
package lapr.project.model.graph.adjacencyMap;

import java.util.LinkedList;
import java.util.List;

public class AdjacencyMapAlgorithms {
    /**
     * Construtor privado para esconder o público implícito
     */
    private AdjacencyMapAlgorithms() {
    }

    protected static<V, E> V getVertMinDist(Graph<V,E> g, double[] dist, boolean[] visited) {
        double minDist = Double.MAX_VALUE;
        V vert = null;
        for(V v : g.vertices()) {
            int key = g.getKey(v);
            if(!visited[key] && dist[key] < minDist) {
                minDist = dist[key];
                vert = v;
            }
        }
        return vert;
    }

    /**
   * Performs breadth-first search of a Graph starting in a Vertex 
   * @param g Graph instance
   * @param vert information of the Vertex that will be the source of the search
   * @return qbfs a queue with the vertices of breadth-first search 
   */
    public static<V,E> LinkedList<V> BreadthFirstSearch(Graph<V,E> g, V vert){
        if(!g.validVertex ( vert )) return new LinkedList<> ();
        LinkedList<V> qbfs=new LinkedList<> (); //guardar todos os vértices ao alcance do vértice vert
        LinkedList<V> qaux=new LinkedList<> (); //Queue auxiliar

        //Adicionar o vertice inicial às queues
        qbfs.add ( vert );
        qaux.add ( vert );
        //enquanto existirem vértices para visitar
        while(!qaux.isEmpty ()){
            Iterable<V> adjVerts=g.adjVertices ( qaux.poll () );
            for (V adjVert : adjVerts) {
                if(!qbfs.contains ( adjVert )){
                    qaux.add ( adjVert );
                    qbfs.add ( adjVert );
                }
            }
        }
        return qbfs;
    }

    public static<V,E> double shortestPath(Graph<V,E> g, List<V> vertices, LinkedList<V> shortPath){
        LinkedList<V> tempPath;
        double totalDistance=0;
        for (int i = 0; i < vertices.size ()-1; i++) {
            tempPath=new LinkedList<> ();
            totalDistance+=shortestPath ( g,vertices.get ( i ),vertices.get ( i+1 ),tempPath );
            if(!shortPath.isEmpty ()){
                shortPath.removeLast ();//remover duplicado, onde o fim de um caminho é igual ao inicio do seguinte
            }
            shortPath.addAll(tempPath);
        }
        tempPath=new LinkedList<>();
        totalDistance+=shortestPath ( g,vertices.get ( vertices.size ()-1 ),vertices.get ( 0 ),tempPath );
        if(!shortPath.isEmpty()) {
            shortPath.removeLast ();
            shortPath.addAll(tempPath);
        }
        return totalDistance;
    }

    /**
     * Shortest path using Dijkstra's Algorithm
     * @param g Graph
     * @param vOrig origin
     * @param vDest destination
     * @param shortPath List that will hold the vertices in the shortest path
     * @param <V> Generic type of the vertex
     * @param <E> Generic type of the edge
     * @return Total distance between vOrig and vDest
     */
    @SuppressWarnings ( "unchecked" )
    public static<V,E> double shortestPath(Graph<V,E> g, V vOrig, V vDest, LinkedList<V> shortPath){
        if(!g.validVertex(vOrig) || !g.validVertex(vDest)){
            return 0;
        }
        boolean[] visited = new boolean[g.numVertices()];
        double[] dist = new double[g.numVertices()];
        int[] path = new int[g.numVertices()];

        for(int i=0;i< g.numVertices ();i++){
            visited[i]=false;
            dist[i]=Double.MAX_VALUE;
        }

        dist[g.getKey ( vOrig )]=0;
        V v=vOrig;
        //get all the min distances to all the vertices in reach
        while(v!=null){
            int vIndex=g.getKey ( v );
            visited[vIndex]=true;
            Iterable<V> adjVertices=g.adjVertices ( v );
            for (V adjVertex : adjVertices) {
                Edge<V,E> edge=g.getEdge ( v,adjVertex );
                int adjIndex=g.getKey ( adjVertex );
                if(!visited[adjIndex] && dist[adjIndex]>dist[vIndex]+ edge.getWeight ()){
                    dist[adjIndex]=dist[vIndex]+ edge.getWeight ();
                    path[adjIndex]=vIndex;
                }
            }
            v=getVertMinDist ( g,dist,visited);
        }
        if(!visited[g.getKey ( vDest )]) return 0;
        double weight=getPath ( g,g.getKey ( vOrig ),g.getKey ( vDest ),path,dist,shortPath );
        shortPath.push ( vOrig );
        return weight;
    }

    /**
     * Gets the path generated from shortestPath
     * @param g Graph
     * @param origIndex Key of the origin
     * @param dIndex Key of the destination
     * @param path Array that holds the path
     * @param dist Array that holds the distances
     * @param shortestPath Reconstructed path
     * @param <V> Generic type of the vertex
     * @param <E> Generic type of the edge
     * @return Distance of the path
     */
    protected static<V,E> double getPath(Graph<V,E> g,int origIndex, int dIndex,int[]path, double[] dist, LinkedList<V> shortestPath){
        int i=dIndex;
        double weight=dist[i];
        while(i!=origIndex){
            shortestPath.push ( getVertex ( g,i ) );
            i=path[i];
        }
        return weight;
    }

    /**
     * Get the vertex from its key.
     * @param g Graph
     * @param i Key
     * @param <V> Generic type of the vertex
     * @param <E> Generic type of the edge
     * @return Vertex
     */
    public static<V,E> V getVertex(Graph<V,E> g, int i){
        Iterable<V> vertices=g.vertices ();
        for (V vertex : vertices) {
            if(g.getKey ( vertex )==i)
                return vertex;
        }
        return null;
    }
}