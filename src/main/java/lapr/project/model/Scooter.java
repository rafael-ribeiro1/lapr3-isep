package lapr.project.model;

import lapr.project.controller.GerirEntregasController;
import lapr.project.model.graph.adjacencyMap.Graph;
import lapr.project.utils.Constantes;
import lapr.project.utils.FisicaAlgoritmos;
import lapr.project.utils.Label;
import lapr.project.utils.Tuple;

import java.util.List;
import java.util.Map;

/**
 * Classe que representa uma Scooter
 */
public class Scooter extends MeioTransporte {

    /**
     * Constrói uma instância de MeioTransporte recebendo por parâmetro:
     *
     * @param capacidadeBateria a capacidade da bateria
     * @param cargaAtual        a carga atual do transporte
     * @param comprimento       a altura do transporte
     * @param largura           a largura do transporte
     * @param peso              o peso do transporte
     * @param velocidadeMaxima  a velocidade máxima do transporte
     */
    public Scooter(int capacidadeBateria, int cargaAtual, double comprimento, double largura, double peso, double velocidadeMaxima) {
        super(capacidadeBateria, cargaAtual, comprimento, largura, peso, velocidadeMaxima);
    }

    /**
     * Constrói uma instância recebendo por parâmetro:
     *
     * @param id                o id do transporte
     * @param capacidadeBateria a capacidade da bateria
     * @param estado            o estado do transporte
     * @param cargaAtual        a carga atual do transporte
     * @param comprimento       a altura do transporte
     * @param largura           a largura do transporte
     * @param peso              o peso do transporte
     * @param velocidadeMaxima  a velocidade máxima do transporte
     */
    public Scooter(int id, int capacidadeBateria, String estado, int cargaAtual, double comprimento, double largura, double peso, double velocidadeMaxima) {
        super(id, capacidadeBateria, estado, cargaAtual, comprimento, largura, peso, velocidadeMaxima);
    }

    /**
     * Constrói uma instância de MeioTransporte recebendo por parâmetro:
     *
     * @param capacidadeBateria a capacidade da bateria
     * @param comprimento       a altura do transporte
     * @param largura           a largura do transporte
     * @param peso              o peso do transporte
     * @param velocidadeMaxima  a velocidade máxima do transporte
     */
    public Scooter(int capacidadeBateria, Double comprimento, Double largura, Double peso, Double velocidadeMaxima) {
        super(capacidadeBateria, comprimento, largura, peso, velocidadeMaxima);
    }

    /**
     * Constrói uma instância de MeioTransporte recebendo por parâmetro:
     *
     * @param idTransporte      id do meio de transporte
     * @param capacidadeBateria a capacidade da bateria
     * @param cargaAtual        a carga atual
     * @param estado            o estado do transporte
     */
    public Scooter(int idTransporte, int capacidadeBateria, int cargaAtual, String estado) {
        super(idTransporte, capacidadeBateria, cargaAtual, estado);
    }

    /**
     * Constrói uma instância de MeioTransporte recebendo por parâmetro:
     *
     * @param id                o id do transporte
     * @param capacidadeBateria a capacidade da bateria
     * @param cargaAtual        a carga atual
     * @param comprimento       a altura do transporte
     * @param largura           a largura do transporte
     * @param peso              o peso do transporte
     * @param velocidadeMaxima  a velocidade máxima do transporte
     */
    public Scooter(int id, int capacidadeBateria, int cargaAtual, Double comprimento, Double largura, Double peso, Double velocidadeMaxima) {
        super(id, capacidadeBateria, cargaAtual, comprimento, largura, peso, velocidadeMaxima);
    }

    /**
     *
     * @param c Caminho que o veículo irá percorrer. No caso da scooter, tem de ser uma instância de Rua ou subclasse.
     * @param massa
     * @param velocidadeTransporte
     * @param anguloVento
     * @return
     */
    @Override
    public double calcularGastosEnergeticos(Caminho c,double massa, double velocidadeTransporte, double anguloVento) {
        Rua r=(Rua)c;
        return FisicaAlgoritmos.energiaTotalScooter (
                this.getPeso () + massa, r.getInclinacao (),this.getArea (),
                    velocidadeTransporte,r.getVelocidadeVento (),anguloVento,r.getComprimento (),r.getCoeficienteResistencia ()
                ) /Constantes.EFICIÊNCIA_ENERGETICA_SCOOTER;
    }

    /**
     * O método refina os cálculos energéticos obtido aquando da obtenção do caminho mais curto/mais eficiente.
     * Este método tem em consideração as graduais perdas de peso das encomendas quando estas são entregues,
     * subtraindo o peso desta cada vez que é entregue, recalculando a energia gasta.
     * Devolve o tempo total de viagem em segundos.
     * @param grafo Grafo dos endereços com os gastos energéticos calculados.
     * @param metaDadosViagem Tuple com a lista de troços da viagem, energia total gasta na viagem e a distância total percorrida.
     * @param e Entrega respetiva
     * @param velocidade velocidade do meio de transporte, em m/s
     * @return tempo total de viagem em segundos.
     */

    @Override
    public double refinarCaminho(Graph<Endereco,? extends Caminho> grafo,Tuple<List<Label>,Double,Double> metaDadosViagem,
                                 Entrega e, double velocidade){
        double pesoTotal=e.getEstafeta ().getPeso() +this.getPeso () + e.getPeso ();
        double energiaRestanteTrocoAnterior=0;
        double tempoCarregamento;
        boolean recalcular=false;
        double energiaTotal=0;
        Map<Endereco, Encomenda> map= GerirEntregasController.getMapEnderecosEncomendas ( e.getListEncomendas () );

        for (Label label : metaDadosViagem.get1st ()) {
            label.setPeso ( pesoTotal );
            Rua r=(Rua)grafo.getEdge (label.getEnderecoInicio (),label.getEnderecoFim ()).getElement ();
            if(recalcular) {
                recalcularGastosEnergeticosScooter ( label, pesoTotal, r, e, velocidade, energiaRestanteTrocoAnterior );
            }
            Encomenda enc=map.get ( label.getEnderecoFim ());
            if(enc!=null){ //se o endereço de chegada corresponder a uma encomenda, no proximo troço recalcula a energia gasta sem o peso dessa mesma encomenda
                pesoTotal-=enc.getPeso ();
                energiaRestanteTrocoAnterior=label.getEnergiaRestante ();
                recalcular=true; // todos os troços a partir deste ponto serao recalculados devido à diminuição do peso da encomenda
            }
            energiaTotal+=label.getEnergiaGasta ();
            label.setTempo ( r.getComprimento ()/velocidade );
        }
        metaDadosViagem.set2nd ( energiaTotal );
        tempoCarregamento=GerirEntregasController.determinarTempoCarregamento ( e.getMeioTransporte (),energiaTotal );
        return (metaDadosViagem.get3rd ()/velocidade) + tempoCarregamento;//tempo = distancia/velocidade
    }

    private void recalcularGastosEnergeticosScooter(Label label, double pesoTotal, Rua r, Entrega e, double velocidade, double energiaRestanteTrocoAnterior){
        double energiaTroco=FisicaAlgoritmos.energiaTotalScooter (pesoTotal,r,e,velocidade);
        label.setEnergiaGasta ( energiaTroco );
        if(!label.getEnderecoFim ().isFarmacia ()){
            label.setEnergiaRestante ( energiaRestanteTrocoAnterior-energiaTroco );
        }
    }
}
