package lapr.project.utils;

import lapr.project.model.Caminho;
import lapr.project.model.Endereco;
import java.util.Objects;

/**
 * Classe que possui informacao acerca de um troco do percurso.
 */
public class Label {
    /**
     * Endereco inicial
     */
    private Endereco enderecoInicio;
    /**
     * Endereco fim .
     */
    private Endereco enderecoFim;
    /**
     *  Energia restante no meio transporte.
     */
    private Double energiaRestante;
    /**
     *  Energia gasta no meio transporte.
     */
    private Double energiaGasta;
    /**
     * Caminho associado a label
     */
    private Caminho caminho;
    /**
     *  Peso do transporte
     */
    private double peso;
    /**
     *  Tempo do percurso
     */
    private double tempo;

    /**
     * Construtor que inicializa a instancia.
     *
     * @param enderecoInicio  Endereco de onde sai .
     * @param enderecoFim     Endereco que tem destino .
     * @param energiaGasta    Energia gasta no percurso.
     * @param energiaRestante Energia restante no percurso.
     * @param caminho         Caminho
     */
    public Label(Endereco enderecoInicio,Endereco enderecoFim, Double energiaGasta,Double energiaRestante,Caminho caminho) {
        this.enderecoInicio = enderecoInicio;
        this.enderecoFim = enderecoFim;
        this.energiaGasta = energiaGasta;
        this.energiaRestante = energiaRestante;
        this.caminho = caminho;
    }

    /**
     * Getter do endereco inicial
     *
     * @return endereco inicial da label
     */
    public Endereco getEnderecoInicio() {
        return enderecoInicio;
    }

    /**
     * Getter do endereco final
     *
     * @return endereco finalda label
     */
    public Endereco getEnderecoFim() {
        return enderecoFim;
    }

    /**
     * Getter da energia gasta neste troco
     *
     * @return energia gasta no troco
     */
    public Double getEnergiaGasta() {
        return energiaGasta;
    }

    /**
     * Getter da energia restante neste troco
     *
     * @return energia restante após o troco
     */
    public Double getEnergiaRestante() {
        return energiaRestante;
    }

    /**
     * Getter da distancia deste troco
     *
     * @return Distancia do troco
     */
    public double getDistancia() {
        return caminho.getComprimento ();
    }

    public Caminho getCaminho() {
        return caminho;
    }

    /**
     * Setter de energia gasta
     *
     * @param energiaGasta the energia gasta
     */
    public void setEnergiaGasta(Double energiaGasta) {
        this.energiaGasta = energiaGasta;
    }

    /**
     * Sets energia restante.
     *
     * @param energiaRestante  energia restante
     */
    public void setEnergiaRestante(Double energiaRestante) {
        this.energiaRestante = energiaRestante;
    }

    /**
     * Sets peso.
     *
     * @param pesoTotal peso total
     */
    public void setPeso(double pesoTotal) {
        this.peso=peso;
    }

    /**
     * Sets tempo.
     *
     * @param tempo  tempo
     */
    public void setTempo(double tempo) {
        this.tempo = tempo;
    }

    /**
     * Adiciona a energia gasta.
     *
     * @param energia energia gasta
     */
    public void addEnergiaGasta(double energia){
        energiaRestante-=energia;
        energiaGasta+=energia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass () != o.getClass ()) return false;
        Label label = (Label) o;
        return Double.compare ( caminho.getComprimento (),label.caminho.getComprimento () ) == 0 && Objects.equals ( enderecoInicio, label.enderecoInicio ) && Objects.equals ( enderecoFim, label.enderecoFim ) && Objects.equals ( energiaGasta, label.energiaGasta ) && Objects.equals ( energiaRestante, label.energiaRestante );
    }

    @Override
    public int hashCode() {
        return Objects.hash ( enderecoInicio, enderecoFim, energiaGasta, energiaRestante, caminho );
    }

}
