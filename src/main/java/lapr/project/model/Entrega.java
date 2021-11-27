package lapr.project.model;
import java.time.LocalDate;
import java.util.*;

/**
 * The type Entrega.
 */
public class Entrega {
    /**
     * id da entrega
     */
    private int id;
    /**
     * Lista de encomenda
     */
    private List<Encomenda> listEncomendas;

    /**
     * A data de entrega
     */
    private LocalDate dataEntrega;

    /**
     * Estafeta associado a entrega
     */
    private Estafeta estafeta;
    /**
     * Meio de transporte da entrega
     */
    private MeioTransporte meioTransporte;
    /**
     * Peso da entrega
     */
    private double peso;

    /**
     * Instantiates a new Entrega.
     *
     * @param id          the id
     * @param dataEntrega the data entrega
     * @param estafeta    the estafeta
     */
    public Entrega(int id, LocalDate dataEntrega, Estafeta estafeta) {
        this.id = id;
        this.dataEntrega = dataEntrega;
        this.estafeta = estafeta;
        this.peso=0d;
        this.listEncomendas = new LinkedList<>();
    }

    /**
     * Constrói uma instância de Entrega, recebendo a taxa de entrega e a data de entrega por parâmetro
     *
     * @param dataEntrega A data de entrega
     * @param estafeta    the estafeta
     */
    public Entrega( LocalDate dataEntrega , Estafeta estafeta) {
        this.dataEntrega = dataEntrega;
        this.estafeta = estafeta;
        this.listEncomendas=new LinkedList<> ();
        this.peso=0d;
    }

    public Entrega(List<Encomenda> listEncomendas) {
        this.listEncomendas = new LinkedList<>();
        addEncomenda(listEncomendas);
        this.dataEntrega = LocalDate.now();
        this.peso=0d;
    }

    /**
     * Instantiates a new Entrega.
     *
     * @param id             the id
     * @param listEncomendas the list encomendas
     * @param dataEntrega    the data entrega
     * @param estafeta       the estafeta
     */
    public Entrega(int id, List<Encomenda> listEncomendas, LocalDate dataEntrega, Estafeta estafeta) {
        this.id = id;
        this.dataEntrega = dataEntrega;
        this.estafeta = estafeta;
        this.listEncomendas=new LinkedList<> ();
        addEncomenda ( listEncomendas );
        this.peso=0d;
    }

    /**
     * Instantiates a new Entrega.
     *

     * @param listEncomendas the list encomendas
     * @param dataEntrega    the data entrega
     * @param estafeta       the estafeta
     */
    public Entrega( List<Encomenda> listEncomendas, LocalDate dataEntrega, Estafeta estafeta) {
        this.dataEntrega = dataEntrega;
        this.estafeta = estafeta;
        this.peso=0d;
        this.listEncomendas=new LinkedList<> ();
        addEncomenda ( listEncomendas );
    }

    /**
     * Instantiates a new Entrega.
     *
     * @param estafeta       the estafeta
     * @param meioTransporte the meio transporte
     */
    public Entrega(Estafeta estafeta, MeioTransporte meioTransporte) {
        this.estafeta = estafeta;
        this.meioTransporte=meioTransporte;
        this.listEncomendas=new LinkedList<> ();
        this.peso=0d;

    }

    /**
     * Instantiates a new Entrega.
     *
     * @param estafeta the estafeta
     */
    public Entrega(Estafeta estafeta) {
        this.estafeta = estafeta;
        this.listEncomendas=new LinkedList<> ();
        this.peso=0d;
    }

    /**
     * Instantiates a new Entrega.
     *
     * @param meioTransporte  meio transporte da entrega
     */
    public Entrega(MeioTransporte meioTransporte) {
        this.meioTransporte = meioTransporte;
        this.listEncomendas=new LinkedList<> ();
        this.peso=0d;
    }
    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }
    /**
     * Gets list encomendas.
     *
     * @return the list encomendas
     */
    public List<Encomenda> getListEncomendas() {
        return new LinkedList<>(listEncomendas);
    }

    /**
     * Gets data entrega.
     *
     * @return the data entrega
     */
    public LocalDate getDataEntrega() {
        return dataEntrega;
    }

    /**
     * Gets estafeta.
     *
     * @return the estafeta
     */
    public Estafeta getEstafeta() {
        return estafeta;
    }

    /**
     * Gets peso da entrega
     *
     * @return  peso da encomenda
     */
    public double getPeso() {
        return peso;
    }

    /**
     * Gets meio transporte da entrega.
     *
     * @return meio transporte da entrega
     */
    public MeioTransporte getMeioTransporte() {
        return meioTransporte;
    }

    public void setEstafeta(Estafeta estafeta) {
        this.estafeta = estafeta;
    }

    public void setMeioTransporte(MeioTransporte meioTransporte) {
        this.meioTransporte = meioTransporte;
    }

    /**
     * Adiciona lista de encomendas a entrega.
     *
     * @param le Lista de encomendas
     * @return Verdadeiro se consegiu adicionar
     */
    public boolean addEncomenda(List<Encomenda> le){
        if(le.isEmpty())return false;
        for (Encomenda e : le) {
            listEncomendas.add ( e );
            this.peso+=e.getPeso ();
        }
        return true;
    }

    /**
     * Adiciona encomenda a entrega.
     *
     * @param e encomenda a adicionar.
     * @return  verdadeiro se adicionou
     */
    public boolean addEncomenda(Encomenda e){
        if(e.getPeso()==0) return false;
        listEncomendas.add ( e );
        this.peso+=e.getPeso ();
        return true;
    }
}
