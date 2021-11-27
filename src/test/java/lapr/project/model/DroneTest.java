package lapr.project.model;

import lapr.project.controller.GerirEntregasController;
import lapr.project.model.graph.adjacencyMap.GPSAlgorithms;
import lapr.project.model.graph.adjacencyMap.Graph;
import lapr.project.utils.FisicaAlgoritmos;
import lapr.project.utils.Label;
import lapr.project.utils.Tuple;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class DroneTest {

    private static Drone d;
    private static Drone d1;
    private static Drone d2;
    private static Drone d3;
    private static Drone d4;

    @BeforeAll
    static void beforeAll() {
        d = new Drone(20, 100, 0.4,0.4, 20,34.4,
                0.6, 6.5);
        d1 = new Drone(1, 23, "FUNCIONAL", 65, 0.5, 0.5, 13,
                28.5, 4.5, 0.9);
        d2 = new Drone(14, 0.3, 0.3, 12.9, 180.0, 10.0,
                0.5);
        d3 = new Drone(2, 12,34, "AVARIADA");
        d4 = new Drone(3, 15, 78, 1.2, 0.6,11.0,23.4,
                12.1,3.1);
    }

    @Test
    void getVelocidadeLevantamento() {
        assertEquals(6.5, d.getVelocidadeLevantamento());
        assertNotEquals(-6.5, d.getVelocidadeLevantamento());
    }

    @Test
    void getComprimento() {
        assertEquals(0.9, d1.getComprimento());
        assertNotEquals(-0.9, d1.getComprimento());
    }

    @Test
    void testEquals() {
        assertEquals(d4, d4);
        assertEquals(d4, new Drone(3, 15, 78, 1.2, 0.6,11.0,23.4,
                12.1,3.1));
        assertNotEquals(null, d4);
        assertNotEquals(d4, new Scooter(1,12, 100, "FUNCIONAL"));
        assertNotEquals(d4, new Drone(3, 10, 78, 1.2, 0.6,11.0,23.4,
                12.1,3.1));
        assertNotEquals(d4, new Drone(3, 15, 43, 1.2, 0.6,11.0,23.4,
                12.1,3.1));
        assertNotEquals(d4, new Drone(3, 15, 78, 1.9, 0.6,11.0,23.4,
                12.1,3.1));
        assertNotEquals(d4, new Drone(3, 15, 78, 1.2, 0.9,11.0,23.4,
                12.1,3.1));
        assertNotEquals(d4, new Drone(3, 15, 78, 1.2, 0.6,12.0,23.4,
                12.1,3.1));
        assertNotEquals(d4, new Drone(3, 15, 78, 1.2, 0.6,11.0,20.4,
                12.1,3.1));
        assertNotEquals(d4, new Drone(3, 15, 78, 1.2, 0.6,11.0,23.4,
                0.9,3.1));
        assertNotEquals(d4, new Drone(3, 15, 78, 1.2, 0.6,11.0,23.4,
                12.1,6.7));

    }

    @Test
    void refinarCaminhoDroneTeste() throws IOException, SQLException {
        GerirEntregasController controller = new GerirEntregasController ();

        Graph<Endereco, Rua> g = new Graph<> ( true );
        Endereco e1 = new Endereco ( "Trindade", "n1", "42342", "234", "wetr", 	41.15227, -8.60929, 104, true );
        Endereco e2 = new Endereco ( "Cais da Ribeira", "n2", "42342", "234", "wetr", 41.14063, -8.61118, 25, false );

        Rua r1 = new Rua ( "Trindade - Cais da Ribeira", FisicaAlgoritmos.distancia ( e1,e2 ), 0,0, 0.004, FisicaAlgoritmos.angulo ( e1,e2 ) );

        Cliente c2=new Cliente ( 1,"c2","nome","nome","nome",e2,new CartaoCredito ( "cc",1, LocalDate.now () ) );

        Encomenda encomenda2=new Encomenda ( 2,2, Encomenda.EstadoEncomenda.ENVIO_PRONTO);

        Produto p= new Produto ( "produto",10 );
        encomenda2.addProduto ( p,2 );

        encomenda2.setCliente ( c2 );

        List<Encomenda> lEncomendas=new ArrayList<> (3);
        lEncomendas.add ( encomenda2 );

        Estafeta estafeta=new Estafeta ( 1,"estafeta",20,1,1,70 );

        int maxEnergia=200;
        double energiaAtual=200;

        Entrega entrega=new Entrega ( estafeta,d1 );
        entrega.addEncomenda (lEncomendas);

        LinkedList<Endereco> le=new LinkedList<>();
        //le.add ( e1 );
        le.add ( e2 );

        Endereco inicio=e1;
        double velocidade=10;
        double massa=entrega.getPeso ()+estafeta.getPeso ()+d1.getPeso ();

        //public static double energiaTotalDrone(double m, double velocidadeUAV, double velocidadeVento, double anguloVento, double areaFrontal,double areaTopo, double distancia,double largura,boolean levantamento){
        System.out.println (FisicaAlgoritmos.energiaTotalDrone ( massa,velocidade,r1.getVelocidadeVento (),r1.getAnguloVento (),d1.getArea (),d1.getAreaTopo (),50,d1.getLargura (),false  ));
        g.insertEdge ( e1, e2, r1, 0.05);
        g.insertEdge ( e2, e1, r1, 0.05);

        Tuple<List<Label>,Double, Double> t2 =controller.rotaMaisEficiente ( g,d1,le,inicio,false );
        double tempoViagem=d1.refinarCaminho ( g, t2,entrega,velocidade);

        for (Label label : t2.get1st ()) {
            StringBuilder s=new StringBuilder();
            s.append ( label.getEnderecoInicio ().getNumPorta () );
            if(label.getEnderecoInicio ().isFarmacia ()){
                s.append ( "(farmácia)" );
            }
            s.append ( "->" ).append ( label.getEnderecoFim ().getNumPorta () );

            if(label.getEnderecoFim ().isFarmacia ()){
                s.append ( "(farmácia)" );
            }
            s.append ( "\n\tEnergia gasta:" ).append ( String.format("%.3f",label.getEnergiaGasta ()) ).append ( " Wh;");
            System.out.println (s.toString ());
        }

        System.out.println ("\nDados da viagem:");
        System.out.printf ("\tEnergia gasta: %.3f KWh\n",t2.get2nd ());
        System.out.printf ("\tdistância percorrida: %.2f m\n",t2.get3rd ());
        System.out.printf ("\tvelocidade média: %.1f Km/h\n",velocidade*3.6);
        System.out.printf ("\tTempo de viagem: %.2f min\n",tempoViagem/60);
        System.out.print ( "\tNecessitou de carregamento: " );

        assertEquals ( 2,t2.get1st ().size () );
        assertEquals ( 114,t2.get2nd ()*1000,1 );
        assertEquals ( 2600,t2.get3rd (),100 );
    }
    @Test
    void calcularGastosEnergeticosTeste(){
        double expected, result, delta;
        int maxEnergia=200;
        double velocidade=5.5;
        double massa=50;
        double angulo=Math.PI/10;
        Rua r1 = new Rua ( "Trindade - Cais da Ribeira", 200, 0,0, 0.004, angulo );
        expected=0.05;
        result=d1.calcularGastosEnergeticos ( r1, massa,velocidade,angulo);
        delta=0.01;
        assertEquals ( expected,result,delta );
    }

    @Test
    void testHashCode() {
        MeioTransporte drone = new Drone(3, 15, 78, 1.2, 0.6,11.0,23.4,
                12.1,3.1);
        int superHash =  Objects.hash(drone.getCapacidadeBateria(), drone.getAltura(), drone.getLargura(), drone.getPeso(), drone.getVelocidadeMaxima());
        Drone d = (Drone) drone;
        int expected = Objects.hash(superHash,((Drone) drone).getVelocidadeLevantamento(), ((Drone) drone).getComprimento());
        assertEquals(expected, d.hashCode());
        assertNotEquals(-6, d.hashCode());
    }
}