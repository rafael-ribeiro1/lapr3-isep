package lapr.project.utils;
/**
 * Classe genérica que possui 3 objetos
 * @param <R> Primeiro objeto da classe
 * @param <S> Segundo objeto da classe
 * @param <T> Terceiro objeto da classe
 */
public class Tuple<R,S,T> {
    /**
     * Primeiro objeto da classe.
     */
    private R first;
    /**
     * Segundo objeto da classe.
     */
    private S second;
    /**
     * Terceiro objeto da classe.
     */
    private T third;
    /**
     *  Construtor da classe genérica
     * @param first Primeiro objeto da classe.
     * @param second Segundo objeto da classe.
     * @param third Terceiro objeto da classe.
     */
    public Tuple(R first, S second, T third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }
    /**
     * Método estatico que cria um objeto
     * @param first Primeiro objeto da classe.
     * @param second Segundo objeto da classe.
     * @param third Terceiro objeto da classe.
     * @param <R> Tipo de objeto do primeiro objeto
     * @param <S> Tipo de objeto do segundo objeto
     * @param <T> Tipo de objeto do terceiro objeto
     * @return  Instancia de tuple com os tres objetos passados como parametro
     */
    public static<R,S,T> Tuple<R,S,T> create(R first, S second, T third){
        return new Tuple<> ( first,second,third );
    }
    /**
     ** Getter que devolve o primeiro objeto
     * @return primeiro objeto da classe.
     */
    public R get1st() {
        return first;
    }
    /**
     * Getter que devolve o segundo objeto da classe.
     * @return Segundo objeto da classe.
     */
    public S get2nd() {
        return second;
    }

    /**
     *  Getter que devolve o terceiro objeto da classe.
     * @return Terceiro objeto da classe.
     */
    public T get3rd() {
        return third;
    }
    /**
     * Setter da classe
     * @param copy  objeto de copia
     */
    public void set(Tuple<R,S,T> copy){
        this.first = copy.first;
        this.second = copy.second;
        this.third = copy.third;
    }
    /**
     * Setter do primeiro objeto da classe.
     * @param first Objeto a ser introduzido como primeiro.
     */
    public void set1st(R first) {
        this.first = first;
    }

    /**
     *  Setter do segundo objeto da classe.
     * @param second Objeto a ser introduzido como segundo.
     */
    public void set2nd(S second) {
        this.second = second;
    }

    /**
     *  Setter do terceiro objeto da classe.
     * @param third Objeto a ser introduzido como terceiro.
     */
    public void set3rd(T third) {
        this.third = third;
    }
}
