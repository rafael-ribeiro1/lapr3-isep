package lapr.project.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class AlgoritmosTest {

    @Test
    public void permutacoesTeste() {
        System.out.println ("permutacoesTeste");
        List<Integer> l = Arrays.asList ( 1, 2, 3, 4, 5);
        List<LinkedList<Integer>> result = Algoritmos.permutacoes ( l );
        int expectedSize=fact ( l.size ());
        Assertions.assertEquals(expectedSize,result.size ());

        List<Integer> lempty = new ArrayList<>();
        List<LinkedList<Integer>> result1 = Algoritmos.permutacoes ( lempty );
        int expectedEmpty = lempty.size();
        Assertions.assertEquals(expectedEmpty, result1.size());
    }

    @Test
    public void permutacoesComComecoIgualTeste() {
        System.out.println ("permutacoesComComecoIgualTeste");
        List<Integer> l = Arrays.asList ( 1, 2, 3, 4, 5);
        List<LinkedList<Integer>> result = Algoritmos.permutacoesComComecoIgual ( l, l.get ( 0 ),true );
        int expectedSize=120;
        Assertions.assertEquals(expectedSize,result.size ());

        List<Integer> lempty = new ArrayList<>();
        List<LinkedList<Integer>> result1 = Algoritmos.permutacoesComComecoIgual ( lempty, l.get(0), true );
        int expectedEmpty = lempty.size();
        Assertions.assertEquals(expectedEmpty, result1.size());

        List<Integer> l1 = new ArrayList<>();
        l1.add(1);
        List<LinkedList<Integer>> result2 = Algoritmos.permutacoesComComecoIgual ( l1, l.get(0), true );
        int expected = fact(l1.size());
        Assertions.assertEquals(expected, result2.size());
    }

    /**
     * Obter o fatorial de um número
     * @param n Número a obter o fatorial
     * @return O fatorial de n
     */
    private int fact(int n){ int f=1; for(int i=2; i<=n; i++) { f*=i; } return f; }
}