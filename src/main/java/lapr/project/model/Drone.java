package lapr.project.model;

import lapr.project.controller.GerirEntregasController;
import lapr.project.model.graph.adjacencyMap.Graph;
import lapr.project.utils.Constantes;
import lapr.project.utils.FisicaAlgoritmos;
import lapr.project.utils.Label;
import lapr.project.utils.Tuple;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Classe que representa um Drone.
 */
public class Drone extends MeioTransporte{
    /**
     * Velocidade com qual o drone se levanta
     */
    private double velocidadeLevantamento;
    /**
     * Comprimento do drone
     */
    private double comprimento;

    /**
     * Constrói uma instância de MeioTransporte recebendo por parâmetro:
     *
     * @param capacidadeBateria      a capacidade da bateria
     * @param cargaAtual             a carga atual do transporte
     * @param altura                 the altura
     * @param largura                a largura do transporte
     * @param peso                   o peso do transporte
     * @param velocidadeMaxima       a velocidade máxima do transporte
     * @param velocidadeLevantamento the velocidade levantamento
     * @param comprimento            a altura do transporte
     */
    public Drone(int capacidadeBateria, int cargaAtual, double altura, double largura, double peso,
                 double velocidadeMaxima,double comprimento, double velocidadeLevantamento) {
        super(capacidadeBateria, cargaAtual, altura, largura, peso, velocidadeMaxima);
        this.comprimento=comprimento;
        this.velocidadeLevantamento=velocidadeLevantamento;
    }

    /**
     * Constrói uma instância recebendo por parâmetro:
     *
     * @param id                     o id do transporte
     * @param capacidadeBateria      a capacidade da bateria
     * @param estado                 o estado do transporte
     * @param cargaAtual             a carga atual do transporte
     * @param altura                 the altura
     * @param largura                a largura do transporte
     * @param peso                   o peso do transporte
     * @param velocidadeMaxima       a velocidade máxima do transporte
     * @param velocidadeLevantamento the velocidade levantamento
     * @param comprimento            a altura do transporte
     */
    public Drone(int id, int capacidadeBateria, String estado, int cargaAtual, double altura,
                 double largura, double peso, double velocidadeMaxima,double velocidadeLevantamento,
                 double comprimento) {
        super( id, capacidadeBateria, estado, cargaAtual, altura, largura, peso, velocidadeMaxima);
        this.velocidadeLevantamento=velocidadeLevantamento;
        this.comprimento=comprimento;
    }

    /**
     * Constrói uma instância de MeioTransporte recebendo por parâmetro:
     *
     * @param capacidadeBateria      a capacidade da bateria
     * @param altura                 the altura
     * @param largura                a largura do transporte
     * @param peso                   o peso do transporte
     * @param velocidadeMaxima       a velocidade máxima do transporte
     * @param velocidadeLevantamento the velocidade levantamento
     * @param comprimento            a altura do transporte
     */
    public Drone(int capacidadeBateria, Double altura, Double largura, Double peso, Double velocidadeMaxima,double velocidadeLevantamento,double comprimento) {
        super(capacidadeBateria, altura, largura, peso, velocidadeMaxima);
        this.velocidadeLevantamento=velocidadeLevantamento;
        this.comprimento=comprimento;
    }

    /**
     * Constrói uma instância de MeioTransporte recebendo por parâmetro:
     *
     * @param idTransporte      id do meio de transporte
     * @param capacidadeBateria a capacidade da bateria
     * @param cargaAtual        a carga atual
     * @param estado            o estado do transporte
     */
    public Drone(int idTransporte, int capacidadeBateria, int cargaAtual, String estado) {
        super(idTransporte, capacidadeBateria, cargaAtual, estado);
    }

    /**
     * Constrói uma instância de MeioTransporte recebendo por parâmetro:
     *
     * @param id                     o id do transporte
     * @param capacidadeBateria      a capacidade da bateria
     * @param cargaAtual             a carga atual
     * @param altura                 the altura
     * @param largura                a largura do transporte
     * @param peso                   o peso do transporte
     * @param velocidadeMaxima       a velocidade máxima do transporte
     * @param velocidadeLevantamento the velocidade levantamento
     * @param comprimento            a altura do transporte
     */
    public Drone(int id, int capacidadeBateria, int cargaAtual, Double altura, Double largura, Double peso, Double velocidadeMaxima,double velocidadeLevantamento,double comprimento) {
        super(id, capacidadeBateria, cargaAtual, altura, largura, peso, velocidadeMaxima);
        this.velocidadeLevantamento=velocidadeLevantamento;
        this.comprimento=comprimento;
    }

    public double getAreaTopo(){
        return this.comprimento*this.getLargura ();
    }

    /**
     *
     * @param c Caminho que o veículo irá percorrer.
     * @param massa
     * @param velocidadeTransporte
     * @param anguloVento
     * @return
     */
    @Override
    public double calcularGastosEnergeticos(Caminho c, double massa,double velocidadeTransporte, double anguloVento) {
        return FisicaAlgoritmos.energiaTotalDrone(massa,velocidadeTransporte,c.getVelocidadeVento (),
                    anguloVento ,this.getArea (),this.getAreaTopo (),c.getComprimento(),
                   this.getLargura(),false)/ Constantes.EFICIÊNCIA_ENERGETICA_DRONE;
    }

    /**
     *
     * @param grafo
     * @param metaDadosViagem
     * @param e
     * @param velocidade
     * @return
     */
    @Override
    public double refinarCaminho(Graph<Endereco,? extends Caminho> grafo,Tuple<List<Label>,Double,Double> metaDadosViagem,
                                Entrega e, double velocidade){
        double pesoTotal=this.getPeso () + e.getPeso ();
        double energiaRestanteTrocoAnterior=0;
        double energiaLevantamento=0;
        double energiaPousoEncomenda;
        double tempoCarregamento;
        boolean recalcular=false;
        Double energiaTotal=0d;

        Map<Endereco, Encomenda> map= GerirEntregasController.getMapEnderecosEncomendas ( e.getListEncomendas () );

        for (Label label : metaDadosViagem.get1st ()) {
            label.setPeso ( pesoTotal );
            Caminho c= grafo.getEdge (label.getEnderecoInicio (),label.getEnderecoFim ()).getElement ();
            if(recalcular){
                recalcularGastosEnergeticosDrone ( label,pesoTotal,velocidade,e,c,energiaLevantamento,energiaRestanteTrocoAnterior );
            }
            Encomenda enc=map.get ( label.getEnderecoFim ());
            if(enc!=null){ //se o endereço de chegada corresponder a uma encomenda, no proximo troço recalcula a energia gasta sem o peso dessa mesma encomenda
                energiaRestanteTrocoAnterior=label.getEnergiaRestante ();
                energiaPousoEncomenda=FisicaAlgoritmos.energiaTotalDrone (pesoTotal,this.velocidadeLevantamento,e,c,true);
                label.addEnergiaGasta ( energiaPousoEncomenda );
                pesoTotal-=enc.getPeso ();
                energiaLevantamento=FisicaAlgoritmos.energiaTotalDrone (pesoTotal,this.velocidadeLevantamento, e,c,true);
                recalcular=true;
            }
            energiaTotal+=label.getEnergiaGasta ();
            label.setTempo ( c.getComprimento ()/velocidade );
        }
        metaDadosViagem.set2nd ( energiaTotal );
        tempoCarregamento=GerirEntregasController.determinarTempoCarregamento ( e.getMeioTransporte (),energiaTotal );
        return metaDadosViagem.get3rd ()/velocidade + tempoCarregamento;//tempo = distancia/velocidade
    }

    private void recalcularGastosEnergeticosDrone(Label label, double pesoTotal, double velocidadeDrone, Entrega e, Caminho c,double energiaLevantamento, double energiaRestanteTrocoAnterior){
        double energiaTroco=energiaLevantamento+FisicaAlgoritmos.energiaTotalDrone (pesoTotal,velocidadeDrone, e,c,false);
        label.setEnergiaGasta ( energiaTroco );
        if(!label.getEnderecoFim ().isFarmacia ()){
            label.setEnergiaRestante ( energiaRestanteTrocoAnterior-energiaTroco );
        }
    }

    /**
     * Gets velocidade levantamento.
     *
     * @return the velocidade levantamento
     */
    public double getVelocidadeLevantamento() {
        return velocidadeLevantamento;
    }

    /**
     * Gets comprimento.
     *
     * @return the comprimento
     */
    public double getComprimento() {
        return comprimento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Drone)) return false;
        if (!super.equals(o)) return false;
        Drone drone = (Drone) o;
        return Double.compare(drone.getVelocidadeLevantamento(), getVelocidadeLevantamento()) == 0 && Double.compare(drone.getComprimento(), getComprimento()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getVelocidadeLevantamento(), getComprimento());
    }
}
