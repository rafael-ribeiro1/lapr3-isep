package lapr.project.utils;
/*
Fórmulas: https://x-engineer.org/automotive-engineering/vehicle/electric-vehicles/ev-design-energy-consumption/
Obter altitude a partir de coordenadas: https://www.advancedconverter.com/map-tools/find-altitude-by-coordinates
 API altitude: https://valhalla.readthedocs.io/en/latest/api/elevation/api-reference/
 */
import lapr.project.model.*;

public class FisicaAlgoritmos {
    /**
     * Raio do planeta Terra
     */
    public static final double RAIO_TERRA=6371E3;

    /**
     Constante de aceleração na Terra ao nível do mar
     */
    public static final double G=9.81;

    /**
     * Densidade atmosférica a 20ºC
     * Fonte: https://www.engineeringtoolbox.com/air-density-specific-weight-d_600.html
     */
    public static final double P=1.204;

    /**
     * Coeficiente de atrito do Drone
     * Fonte: https://en.wikipedia.org/wiki/Drag_coefficient
     */
    public static final double CD_DRONE =0.3;

    /**
     * Coeficiente de atrito da Scooter
     */
    public static final double CD_SCOOTER =0.4;

    /**
     * Densidade do ar, em Kg/m^3
     */
    public static final double D = 1.225;

    /**
     * Altura a que o drone sobe para fazer a entrega.
     * Altura relativa à altura média do mar.
     */
    public static final double ALTURA_LEVANTAMENTO_DRONE=150d; //m

    public static final double ALTURA_PLATAFORMA_ATERRAGEM=10; //m

    /**
     * Construtor privado para impedir instanciação
     */
    private FisicaAlgoritmos() {
    }

    /**
     * @param pesoTotal Peso total do drone.
     * @param velocidadeDrone Velocidade do drone.
     * @param e Entrega a ser realizada com o drone.
     * @param c Caminho a ser percorrido pelo drone.
     * @param levantamento Verdadeiro se o drone necessitar de levantamento.
     * @return Energia total necessária para o drone fazer o caminho.
     */

    public static double energiaTotalDrone(double pesoTotal, double velocidadeDrone, Entrega e, Caminho c, boolean levantamento){
        return FisicaAlgoritmos.energiaTotalDrone (pesoTotal,velocidadeDrone, c.getVelocidadeVento (),
                c.getAnguloVento () ,e.getMeioTransporte ().getArea (),((Drone) e.getMeioTransporte ()).getAreaTopo (),
                c.getComprimento (),e.getMeioTransporte ().getLargura (),levantamento);
    }

    /**
     * Calcular a energia gasta ao levantar o drone a 150 m da altura media do mar, em Joule
     * @param m Massa
     * @param velocidadeUAV velocidade de levantamento do drone.
     * @param velocidadeVento velocidade do vento.
     * @param anguloVento Angulo do vento relativo ao UAV.
     * @param areaFrontal Area do drone. Area de topo caso esteja a levantar voo. Area frontal caso contrário.
     * @param distancia A distancia a percorrer.
     * @param levantamento True se a energia a ser calculada e para o levantamento do drone. False caso contrário.
     * @return Energia gasta do drone
     */
    public static double energiaTotalDrone(double m, double velocidadeUAV, double velocidadeVento, double anguloVento, double areaFrontal,double areaTopo, double distancia,double largura,boolean levantamento){
        //1 KWh= 1/3600/1000 J
        if(levantamento){
            return potenciaLevantamento ( m,areaTopo ) * tempoMedio ( ALTURA_LEVANTAMENTO_DRONE-ALTURA_PLATAFORMA_ATERRAGEM,velocidadeUAV )/3_600_000;
        }return potenciaHorizontal ( areaFrontal,velocidadeUAV,velocidadeVento,anguloVento,m, largura )* tempoMedio ( distancia,velocidadeUAV )/3_600_000;
    }
    public static double energiaTotalScooter(double pesoTotal, Rua r, Entrega e, double velocidade){
        return energiaTotalScooter (pesoTotal, r.getInclinacao (),e.getMeioTransporte ().getArea (),
                velocidade,r.getVelocidadeVento (),r.getAnguloVento (),r.getComprimento (),r.getCoeficienteResistencia ());
    }
    /**
     * E = Potência * tempo (em W/h)
     * @param m Massa do(s) corpo(s)
     * @param a Angulo da inclinação da estrada em radianos
     * @param area Area da superfície em contacto com o ar
     * @param velocidadeTransporte Velocidade média esperada
     * @param velocidadeVento Velocidade do vento
     * @param anguloVento Angulo do vento
     * @param distancia Distancia a percorrer
     * @param crr Coeficiente de rolamento dos pneus no piso
     * @return Energia total necessária estimada em W/h
     */
    public static double energiaTotalScooter(double m, double a, double area, double velocidadeTransporte, double velocidadeVento, double anguloVento, double distancia, double crr) {
        //1 KWh= 1/(3600*1000) J
        return potenciaTotalScooter ( m,a,area,velocidadeTransporte,velocidadeVento,anguloVento,crr ) * tempoMedio ( distancia,velocidadeTransporte ) /3_600_000;
    }

    /**
     *
     * @param m Massa
     * @param area Area em face com o ar no momento do movimento vertical
     * @return Potência de levantamento, em W
     */
    public static double potenciaLevantamento(double m, double area){
        return Math.pow ( m*G,1.5 )/Math.sqrt ( 2*D*area );
    }

    /**
     * Potência horizontal
     * @param area Area da superfície frontal em contacto com o ar
     * @param velocidadeUAV velocidade de levantamento do drone.
     * @param velocidadeVento velocidade do vento.
     * @param anguloVento Ângulo do vento relativo ao UAV.
     * @param massa Massa do corpo
     * @param largura largura do UAV
     * @return Potência horizontal
     */
    public static double potenciaHorizontal(double area,double velocidadeUAV, double velocidadeVento, double anguloVento,double massa, double largura){
        return potenciaAtritoParasita ( area,velocidadeUAV, velocidadeVento, anguloVento ) + potenciaLift ( velocidadeUAV, velocidadeVento,anguloVento,massa, largura );
    }

    /**
     * Potência de atrito parasita
     * @param area Area em contacto com o ar
     * @param velocidadeUAV velocidade do drone
     * @param velocidadeVento Velocidade do vento
     * @param anguloVento Ângulo do vento
     * @return Potência de atrito parasita
     */
    public static double potenciaAtritoParasita(double area, double velocidadeUAV, double velocidadeVento, double anguloVento){
        return 0.5 * CD_DRONE * area * D * Math.pow ( velocidadeUAV-velocidadeVento*Math.cos ( anguloVento ),3 );
    }

    /**
     * Potência de lift do UAV
     * @param velocidadeDrone Velocidade do UAV
     * @param velocidadeVento
     * @param anguloVento
     * @param massa Massa total do UAV
     * @param largura Largura do UAV
     * @return Potência de lift do UAV
     */
    public static double potenciaLift(double velocidadeDrone,double velocidadeVento,double anguloVento, double massa, double largura){
        if(anguloVento<1 || anguloVento>-1){
            return (massa*massa)/(D*largura*largura*(velocidadeDrone));
        }
        return (massa*massa)/(D*largura*largura*(velocidadeDrone - velocidadeVento*Math.cos ( Math.toRadians ( anguloVento ) )));
    }

    /**
     * P = Força total * velocidade (em W)
     * Devolve a potencia total
     * @param m Massa
     * @param a Angulo
     * @param area Area da superficie frontal da scooter com o estafeta
     * @param velocidadeTransporte velocidade média da scooter
     * @return Potência total
     */
    public static double potenciaTotalScooter(double m, double a, double area, double velocidadeTransporte, double velocidadeVento, double anguloVento, double crr){
        return forcaTotal ( m,a,area,velocidadeTransporte,velocidadeVento,anguloVento,crr ) * velocidadeTransporte;
    }
    /**
     * Calcular o tempo médio de viagem
     * @param distancia Distância a percorrer
     * @param velocidade Velocidade média da viagem
     * @return Tempo médio de viagem
     */
    public static double tempoMedio(double distancia, double velocidade){
        return distancia/velocidade;
    }
    /**
     * Devolve a soma das forças
     * @param m massa
     * @param a angulo
     * @param area Area da superficie frontal da scooter com o estafeta
     * @param velocidadeTransporte velocidade média da scooter
     * @return Força total
     */
    public static double forcaTotal(double m, double a,double area, double velocidadeTransporte,double velocidadeVento,double anguloVento, double crr){
        return forcaInclinacaoEstrada ( m,a ) + forcaCarga ( m,a,crr ) + forcaAtritoAerodinamico ( area, velocidadeTransporte,velocidadeVento,anguloVento );
    }

    /**
     * Calcula a força de inclinação da estrada
     * @param m massa
     * @param a ângulo
     * @return Força de inclinação da estrada
     */
    public static double forcaInclinacaoEstrada(double m, double a){
        return m*G*Math.sin ( a );
    }

    /**
     * @param m massa
     * @param a ângulo
     * @return Força de carga
     */
    public static double forcaCarga(double m, double a, double crr){
        return m*G* crr *Math.cos(a);
    }

    /**
     * possivel api: https://rapidapi.com/community/api/open-weather-map
     * @return Força de atrito aerodinâmico
     */
    public static double forcaAtritoAerodinamico(double area, double velocidadeTransporte, double velocidadeVento, double anguloVento){
        return 0.5 * D * CD_SCOOTER * area *
                Math.pow (velocidadeTransporte-velocidadeVento*Math.cos (Math.toRadians(anguloVento)),2);
    }

    public static double distancia(Endereco e1, Endereco e2){
        return distancia ( e1.getLatitude (),e1.getLongitude (),e1.getAltitude (),
                e2.getLatitude (),e2.getLongitude (),e2.getAltitude ());
    }

    /**
     * Calcula a distância entre dois pontos 1 e 2, considerando a altidude.
     * @param lat1 latitude do ponto 1
     * @param lon1 longitude do ponto 1
     * @param alt1 altitude do ponto 1
     * @param lat2 latitude do ponto 2
     * @param lon2 longitude do ponto 2
     * @param alt2 altitude do ponto 2
     * @return Distância entre o ponto 1 e ponto 2
     */
    public static double distancia(double lat1, double lon1, double alt1, double lat2, double lon2,double alt2) {
        double distancia= distancia2D ( lat1,lon1,lat2,lon2 );
        double altura=alt1-alt2;
        distancia=Math.pow ( distancia,2 ) + Math.pow ( altura,2 );
        return Math.sqrt ( distancia );
    }

    /**
     * Calcula a distância entre dois pontos 1 e 2, desprezando a altidude.
     * @param lat1 latitude do ponto 1
     * @param lon1 longitude do ponto 1
     * @param lat2 latitude do ponto 2
     * @param lon2 longitude do ponto 2
     * @return Distância entre o ponto 1 e ponto 2
     */
    public static double distancia2D(double lat1, double lon1, double lat2, double lon2) {
        double phi1 = lat1 * Math.PI / 180;
        double phi2 = lat2 * Math.PI / 180;
        double deltaPhi = (lat2 - lat1) * Math.PI / 180;
        double deltaLambda = (lon2 - lon1) * Math.PI / 180;

        double a = Math.sin ( deltaPhi / 2 ) * Math.sin ( deltaPhi / 2 ) + Math.cos ( phi1 ) * Math.cos ( phi2 )
                * Math.sin ( deltaLambda / 2 ) * Math.sin ( deltaLambda / 2 );

        double c = 2 * Math.atan2 ( Math.sqrt ( a ), Math.sqrt ( 1 - a ) );

        return RAIO_TERRA * c;
    }

    /**
     * Calcular ângulo entre dois endereços em radianos
     * @param e1 Endereço inicial
     * @param e2 Endereço final
     * @return Ângulo entre dois endereços, em radianos
     */
    public static Double angulo(Endereco e1, Endereco e2){
        return angulo ( e1.getLatitude (),e1.getLongitude (),e1.getAltitude (),
                e2.getLatitude (),e2.getLongitude (),e2.getAltitude ());
    }

    /**
     * Calcula o ângulo de inclinação entre dois pontos 1 e 2.
     * @param lat1 latitude do ponto 1
     * @param lon1 longitude do ponto 1
     * @param alt1 altitude do ponto 1
     * @param lat2 latitude do ponto 2
     * @param lon2 longitude do ponto 2
     * @param alt2 altitude do ponto 2
     * @return Ângulo de inclinação entre o ponto 1 e ponto 2 em radianos
     */
    public static Double angulo(double lat1, double lon1, double alt1, double lat2, double lon2,double alt2){
        double distancia2D=Math.abs (distancia2D (lat1,lon1,lat2,lon2));
        double altura=alt2-alt1;
        return Math.atan ( altura/distancia2D );
    }
}
