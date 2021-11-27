package lapr.project.utils;
/**
 * Classe que possui constantes que sao utilizadas em calculos na aplicacao
 */
public class Constantes {
    /**
     * Construtor privado para impedir instanciacao da classe
     */
    private Constantes() {
    }
    /**
     * Efeciencia energetica scooter
     */
    public final static double EFICIÊNCIA_ENERGETICA_SCOOTER = 0.70;
    /**
     * Eficiência energetica drone
     */
    public final static double EFICIÊNCIA_ENERGETICA_DRONE = 0.29;
    /**
     * Peso maximo permitdo para uma entrega
     */
    public static final double PESO_MAXIMO_ENTREGA=10d; //10 Kg
    /**
     * Peso médio de um estafeta em kilogramas.
     */
    public static final double PESO_MEDIO_ESTAFETA=80d; //70KG
    /**
     * Peso médio de um drone
     */
    public static final double PESO_MEDIO_DRONE=5d; //5KG
    /**
     * Peso médio de uma scooter
     */
    public static final double PESO_MEDIO_SCOOTER=180d; //180KG
    /**
     * Velocidade maxima legal
     */
    public static final double VELOCIDADE_MAXIMA_LEGAL = 8.3D  ;// 8.3 m/s

    public static final double POTENCIA_MEDIA_CARREGADOR = 3.52; //KWh

    public static final int CREDITOS_PAGAR_TAXA_ENTREGA = 10;

    public static final double CUSTO_ENTREGA = 15.0d;


    public static final String SCOOTER = "Scooter";
    public static final String DRONE = "Drone";
}
