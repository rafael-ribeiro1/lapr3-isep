package lapr.project.model;
import java.util.Objects;

/**
 * Classe que representa um caminho do percurso de entrega
 */
public class Caminho {
    /**
     * Descricao do caminho
     */
    private final String descricao;
    /**
     * Comprimento do caminho
     */
    private final double comprimento;
    /**
     * Velocidade do vento
     */
    private final double velocidadeVento;
    /**
     * Angulo do vento no caminho
     */
    private final double anguloVento;

    /**
     * Construtor da classe
     *
     * @param descricao       descricao do caminho
     * @param comprimento     comprimento do caminho
     * @param velocidadeVento velocidade do vento
     * @param anguloVento     angulo do vento
     */
    public Caminho(String descricao, double comprimento, double velocidadeVento, double anguloVento) {
        this.descricao = descricao;
        this.comprimento = comprimento;
        this.velocidadeVento = velocidadeVento;
        this.anguloVento= anguloVento;
    }

    /**
     * Gets descricao.
     *
     * @return the descricao
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Gets comprimento.
     *
     * @return the comprimento
     */
    public double getComprimento() {
        return comprimento;
    }

    /**
     * Gets velocidade do vento.
     *
     * @return the velocidade vento
     */
    public double getVelocidadeVento() {
        return velocidadeVento;
    }

    /**
     * Gets angulo vento.
     *
     * @return the angulo vento
     */
    public double getAnguloVento() {
        return anguloVento;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass () != o.getClass ()) return false;
        Caminho caminho = (Caminho) o;
        return Double.compare ( caminho.comprimento, comprimento ) == 0 &&
                Double.compare ( caminho.velocidadeVento, velocidadeVento ) == 0 &&
                Double.compare ( caminho.anguloVento, anguloVento ) == 0 &&
                Objects.equals ( descricao, caminho.descricao );
    }

    @Override
    public int hashCode() {
        return Objects.hash ( descricao, comprimento, velocidadeVento, anguloVento);
    }
}
