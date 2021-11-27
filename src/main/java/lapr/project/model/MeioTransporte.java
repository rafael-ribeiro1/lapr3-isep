package lapr.project.model;

import lapr.project.model.graph.adjacencyMap.Graph;
import lapr.project.utils.Label;
import lapr.project.utils.Tuple;

import java.util.List;
import java.util.Objects;

public abstract class MeioTransporte {

    /**
     * O valor default da carga
     */
    private static final int CARGA_DEFAULT = 100;

    /**
     * O id do transporte
     */
    private int id;

    /**
     * A capacidade da bateria
     */
    private int capacidadeBateria;

    /**
     * O estado do transporte
     */
    private Estado estado;

    /**
     * A carga atual
     */
    private int cargaAtual;

    /**
     * A altura do transporte
     */
    private double altura;

    /**
     * A largura do transporte
     */
    private double largura;

    /**
     * O peso do transporte
     */
    private double peso;

    /**
     * A velocidade máxima do transporte
     */
    private double velocidadeMaxima;

    /**
     * O slot de estacionamento do transporte
     */
    private SlotEstacionamento slotEstacionamento;

    /**
     * O Estado do Transporte
     */
    public enum Estado{
        FUNCIONAL("Funcional"),
        AVARIADA("Avariada");

        private String descricaoEstado;

        Estado(String descricao){
            this.descricaoEstado = descricao;
        }

        public String getDescricaoEstado() {
            return descricaoEstado;
        }
    }

    /**
     * Constrói uma instância de MeioTransporte recebendo por parâmetro:
     *
     * @param capacidadeBateria a capacidade da bateria
     * @param cargaAtual a carga atual do transporte
     * @param altura a altura do transporte
     * @param largura a largura do transporte
     * @param peso o peso do transporte
     * @param velocidadeMaxima a velocidade máxima do transporte
     */
    public MeioTransporte(int capacidadeBateria, int cargaAtual, double altura, double largura, double peso, double velocidadeMaxima) {
        this.capacidadeBateria = capacidadeBateria;
        this.cargaAtual = cargaAtual;
        this.altura = altura;
        this.largura = largura;
        this.peso = peso;
        this.velocidadeMaxima = velocidadeMaxima;
        this.estado = Estado.FUNCIONAL;
    }

    /**
     * Constrói uma instância recebendo por parâmetro:
     * @param id o id do transporte
     * @param capacidadeBateria a capacidade da bateria
     * @param estado o estado do transporte
     * @param cargaAtual a carga atual do transporte
     * @param altura a altura do transporte
     * @param largura a largura do transporte
     * @param peso o peso do transporte
     * @param velocidadeMaxima a velocidade máxima do transporte
     */
    public MeioTransporte(int id, int capacidadeBateria, String estado, int cargaAtual, double altura, double largura,
                          double peso, double velocidadeMaxima) {
        this.id = id;
        this.capacidadeBateria = capacidadeBateria;
        this.estado = Estado.valueOf(estado);
        this.cargaAtual = cargaAtual;
        this.altura = altura;
        this.largura = largura;
        this.peso = peso;
        this.velocidadeMaxima = velocidadeMaxima;
    }

    /**
     * Constrói uma instância de MeioTransporte recebendo por parâmetro:
     * @param capacidadeBateria a capacidade da bateria
     * @param altura a altura do transporte
     * @param largura a largura do transporte
     * @param peso o peso do transporte
     * @param velocidadeMaxima a velocidade máxima do transporte
     */
    public MeioTransporte(int capacidadeBateria, Double altura, Double largura, Double peso, Double velocidadeMaxima){
        this.capacidadeBateria = capacidadeBateria;
        this.cargaAtual = CARGA_DEFAULT;
        this.estado = Estado.FUNCIONAL;
        this.altura = altura;
        this.largura = largura;
        this.peso = peso;
        this.velocidadeMaxima = velocidadeMaxima;
    }

    /**
     * Constrói uma instância de MeioTransporte recebendo por parâmetro:
     * @param idTransporte id do meio de transporte
     * @param capacidadeBateria a capacidade da bateria
     * @param cargaAtual a carga atual
     * @param estado o estado do transporte
     */
    public MeioTransporte(int idTransporte, int capacidadeBateria, int cargaAtual, String estado) {
        this.id = idTransporte;
        this.capacidadeBateria = capacidadeBateria;
        this.cargaAtual = cargaAtual;
        this.estado = Estado.valueOf(estado);
    }

    /**
     * Constrói uma instância de MeioTransporte recebendo por parâmetro:
     * @param id o id do transporte
     * @param capacidadeBateria a capacidade da bateria
     * @param cargaAtual a carga atual
     * @param altura a altura do transporte
     * @param largura a largura do transporte
     * @param peso o peso do transporte
     * @param velocidadeMaxima a velocidade máxima do transporte
     */
    public MeioTransporte(int id, int capacidadeBateria, int cargaAtual, Double altura,
                          Double largura, Double peso, Double velocidadeMaxima) {
        this.id = id;
        this.estado = Estado.FUNCIONAL;
        this.capacidadeBateria = capacidadeBateria;
        this.cargaAtual = cargaAtual;
        this.altura = altura;
        this.largura = largura;
        this.peso = peso;
        this.velocidadeMaxima = velocidadeMaxima;
    }

    /**
     * Calcula o gasto energético do veículo segundo um caminho dado
     * @param c Caminho que o veículo irá percorrer.
     * @param massa
     * @param velocidadeTransporte
     * @param anguloVento
     * @return O gasto energético do veículo, em KWh
     */
    public abstract double calcularGastosEnergeticos(Caminho c,double massa, double velocidadeTransporte,double anguloVento);

    /**
     *
     * @param grafo
     * @param metaDadosViagem
     * @param e
     * @param velocidadeDrone
     * @return
     */
    public abstract double refinarCaminho(Graph<Endereco,? extends Caminho> grafo,Tuple<List<Label>,Double,Double> metaDadosViagem,
                                          Entrega e, double velocidadeDrone);
    /**
     * Devolve o id do meio de transporte
     * @return o id do meio de transporte
     */
    public int getId() {
        return id;
    }

    /**
     * Devolve o slot de estacionamento
     * @return o slot de estacionamento
     */
    public SlotEstacionamento getSlotEstacionamento() {
        return slotEstacionamento;
    }

    /**
     * Devolve o valor de capacidade da bateria
     * @return  capacidade da bateria
     */
    public int getCapacidadeBateria() {
        return capacidadeBateria;
    }
    /**
     * Devolve a energia eficaz da bateria
     */
    public double getCapacidadeEficaz(){
        return  ((double)this.getCargaAtual()/100)*this.getCapacidadeBateria();
    }
    /**
     * Devolve o valor do estado
     * @return o estado do meio de transporte
     */
    public Estado getEstado() {
        return estado;
    }

    /**
     * Devolve o valor da carga atual
     * @return a carga atual do transporte
     */
    public int getCargaAtual() {
        return cargaAtual;
    }
    /**
     * Devolve o valor da altura
     * @return a altura do transporte
     */
    public Double getAltura() {
        return altura;
    }
    /**
     * Devolve o valor da largura
     * @return a largura do transporte
     */
    public Double getLargura() {
        return largura;
    }

    /**
     * Devolve o peso do transporte
     * @return o peso do transporte
     */
    public Double getPeso() {
        return peso;
    }

    /**
     * Devolve a velocidade máxima do transporte
     * @return a velocidade máxima do transporte
     */
    public Double getVelocidadeMaxima() {
        return velocidadeMaxima;
    }

    /**
     * Devolve a área do Meio de Transporte
     * @return a área do Meio de Transporte
     */
    public double getArea(){
        return altura *largura;
    }

    /**
     * Modifica o slot de estacionamento
     * @param slotEstacionamento o slot de estacionamento
     */
    public void setSlotEstacionamento(SlotEstacionamento slotEstacionamento) {
        this.slotEstacionamento = slotEstacionamento;
    }

    /**
     * Modifica a capacidade da bateria
     * @param capacidadeBateria a capacidade da bateria
     */
    public void setCapacidadeBateria(int capacidadeBateria) {
        this.capacidadeBateria = capacidadeBateria;
    }

    /**
     * Modifica o estado do meio de transporte
     * @param estado o estado do transporte
     */
    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    /**
     * Modifica o id do meio de transporte
     * @param id o id do meio de transporte
     */
    public void setId(int id) {
        this.id = id;
    }


    /**
     * Verifica se o objeto passado por parâmetro é igual ao meio de transporte.
     * Se os objetos partilharem a mesma referência, devolve true. Se o objeto recebido
     * por parâmetro for nulo ou as classes dos objetos forem diferentes, devolve false.
     * Por fim, efetua um downcasting do objeto recebido por parâmetro para o tipo MeioTransporte,
     * comparando os diferentes atributos da classe.
     * @param o o objeto a comparar
     * @return true se forem iguais, caso contrário retorna false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MeioTransporte)) return false;
        MeioTransporte that = (MeioTransporte) o;
        return capacidadeBateria == that.capacidadeBateria && cargaAtual == that.cargaAtual &&
                Double.compare(that.altura, altura) == 0 && Double.compare(that.largura, largura) == 0 &&
                Double.compare(that.peso, peso) == 0 && Double.compare(that.velocidadeMaxima, velocidadeMaxima) == 0 &&
                estado.equals(that.estado) && Objects.equals(slotEstacionamento, that.slotEstacionamento);
    }

    /**
     * Devolve o valor do hash code para o objeto
     * @return o valor do hash code para o objeto
     */
    @Override
    public int hashCode() {
        return Objects.hash(capacidadeBateria, altura, largura, peso, velocidadeMaxima);
    }
}
