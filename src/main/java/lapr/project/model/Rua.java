package lapr.project.model;

import java.util.Objects;

/**
 * Classe que representa uma rua
 */
public class Rua extends Caminho {
    /**
     * Coeficiente de resistencia associado a essa rua
     */
    private double coeficienteResistencia;
    /**
     * Inclinacao darua
     */
    private double inclinacao;


    /**
     * Construtor da classe
     * @param descricao Descricao da rua
     * @param comprimento Comprimento da rua
     * @param velocidadeVento Velocidade do vento na rua
     * @param anguloVento Angulo do vento na rua
     * @param coeficienteResistencia Coeficiente de resistencia do pavimento da rua
     * @param inclinacao Inclinacao da rua
     */
    public Rua(String descricao,double comprimento, double velocidadeVento,double anguloVento, double coeficienteResistencia,double inclinacao){
        super(descricao, comprimento, velocidadeVento, anguloVento);
        this.coeficienteResistencia=coeficienteResistencia;
        this.inclinacao=inclinacao;
    }

    /**
     * Getter do coeficiente de resistencia da rua
     * @return
     */
    public double getCoeficienteResistencia() {
        return coeficienteResistencia;
    }

    /**
     * getter da inclincao da rua
     * @return
     */
    public double getInclinacao() {
        return inclinacao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass () != o.getClass ()) return false;
        if (!super.equals ( o )) return false;
        Rua rua = (Rua) o;
        return Double.compare ( rua.coeficienteResistencia, coeficienteResistencia ) == 0 && Double.compare ( rua.inclinacao, inclinacao ) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash ( super.hashCode (), coeficienteResistencia, inclinacao );
    }
}
