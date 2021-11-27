package lapr.project.utils;

import java.util.*;

/**
 * Classe que possui alguns algoritmos para calculos de trajetos
 */
public class Algoritmos {

    /**
     * Construtor privado para esconder o público implícito
     */
    private Algoritmos(){

    }
    /**
     * Algoritmo de Heap para calcular permutações em que todas as permutações tem o mesmo elemento inicial
     * @param l Lista de elementos para calcular as permutações
     * @param first O elemento inicial de todas as permutações
     * @param isLastSameAsFirst True se a permutação tem o mesmo elemento no início e fim. False caso contrário.
     * @param <T> Parâmetro genérico
     * @return Todas as permutações dos elementos da lista l
     */
    public static <T extends Comparable<T>> List<LinkedList<T>> permutacoesComComecoIgual(List<T>l, T first,boolean isLastSameAsFirst){
        if(l.isEmpty ()){
            return new ArrayList<> ();
        }
        List<LinkedList<T>> perms = new ArrayList<> ();
        if(l.size ()==1){
            perms.add (new LinkedList<> ());
            perms.get ( 0 ).add ( first );
            perms.get ( 0 ).add ( l.get ( 0 ) );
            return perms;
        }

        permutacoes ( l.size (),l, perms);

        if(isLastSameAsFirst){
            for (LinkedList<T> perm : perms) {
                perm.addFirst ( first);
            }
        }
        return perms;
    }

    /**
     * Algoritmo de Heap para calcular permutações
     * @param l Lista de elementos para calcular as permutações
     * @param <T> Parâmetro genérico
     * @return Todas as permutações dos elementos da lista l
     */
    public static <T extends Comparable<T>> List<LinkedList<T>> permutacoes(List<T> l){
        if(l.isEmpty ()){
            return new ArrayList<> ();
        }
        List<LinkedList<T>> perms = new LinkedList<> ();
        permutacoes ( l.size (),l, perms );
        return perms;
    }

    /**
     * Algoritmo de Heap para calcular permutações
     * @param k Tamanho da sublista
     * @param l Lista com elementos
     * @param perms Permutações calculadas
     * @param <T> Parâmetro genérico
     */
    private static <T extends Comparable<T>> void permutacoes(int k, List<T> l, List<LinkedList<T>> perms) {
        if (k == 0) return;
        if(k == 1 ){
            addPerm ( l, perms );
        } else {
            for(int i = 0; i < k-1; i++) {
                permutacoes (k - 1, l,perms );
                //é par
                if((k & 1) == 0) {
                    swap(l, i, k-1);
                //é ímpar
                } else {
                    swap(l, 0, k-1);
                }
            }
            permutacoes (k - 1, l,perms );
        }
    }

    /**
     * Adicionar permutação à lista de permutações
     * @param perm Permutação a adicionar
     * @param result Lista com a permutação adicionada
     * @param <T> Tipo genérico
     */
    public static<T extends Comparable<T>> void addPerm(List<T> perm, List<LinkedList<T>> result){
        result.add ( new LinkedList<> (perm) );
    }

    /**
     * Algoritmo de trocar valores entre duas variáveis
     * @param input Lista de elementos
     * @param a Elemento
     * @param b Elemento
     * @param <T> Tipo genérico
     */
    private static<T extends Comparable<T>>  void swap(List<T> input, int a, int b) {
        T tmp = input.get ( a );
        input.set (a,input.get ( b ));
        input.set ( b,tmp );
    }
}
