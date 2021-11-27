package lapr.project.model.graph.adjacencyMap;

import lapr.project.model.Endereco;
import lapr.project.model.Rua;
import lapr.project.utils.Label;
import lapr.project.utils.Tuple;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GPSAlgorithmsTest {

    @Test
    void shortPathUntilWeightListaTeste(){
        Graph<Endereco, Rua> g = new Graph<> ( true );
        Endereco e1 = new Endereco ( "r1", "n1", "42342", "234", "wetr", 234, 234, 1, true );
        Endereco e2 = new Endereco ( "r2", "n2", "42342", "234", "wetr", 235, 234, 1, false );
        Endereco e3 = new Endereco ( "r3", "n3", "42342", "234", "wetr", 236, 234, 1, true );
        Endereco e4 = new Endereco ( "r4", "n4", "42342", "234", "wetr", 237, 234, 1, false );
        Endereco e5 = new Endereco ( "r5", "n5", "42342", "234", "wetr", 238, 234, 1, false );
        Endereco e6 = new Endereco ( "r6", "n6", "42342", "234", "wetr", 239, 234, 1, false );

        Rua r1 = new Rua ( "rua1", 3d, 0,0, 1, 0 );
        Rua r2 = new Rua ( "rua2", 3d, 0,0, 1, 0 );
        Rua r3 = new Rua ( "rua3", 20d, 0,0, 1, 0 );
        Rua r4 = new Rua ( "rua4", 1d, 0,0, 1, 0 );
        Rua r5 = new Rua ( "rua5", 5d, 0,0, 1, 0 );
        Rua r6 = new Rua ( "rua6", 1d, 0,0, 1, 0 );
        Rua r7 = new Rua ( "rua7", 0.5d, 0,0, 1, 0 );
        Rua r8 = new Rua ( "rua8", 2d, 0,0, 1, 0 );
        Rua r9 = new Rua ( "rua9", 3d, 0,0, 1, 0 );

        g.insertEdge ( e1, e2, r1, 3);
        g.insertEdge ( e2, e1, r2, 3);
        g.insertEdge ( e1, e3, r3, 20);
        g.insertEdge ( e1, e4, r4, 1);
        g.insertEdge ( e2, e3, r5, 5);
        g.insertEdge ( e3, e5, r6, 2);
        g.insertEdge ( e3, e6, r7, 0.5);
        g.insertEdge ( e5, e4, r8, 2);
        g.insertEdge ( e4, e1, r9, 3);

        //g.insertEdge ( e4, e2, r9, 1);

        LinkedList<Endereco> le=new LinkedList<>();
        le.add ( e1 );
        le.add ( e2 );
        le.add ( e5 );
        le.add ( e4 );
        double maxEnergia=8;
        double energiaAtual=8d;
        Endereco inicio=e1;

        //System.out.println ("energia atual: "+energiaAtual+" KWh");

        List<Label> infoViagem=new LinkedList<> ();
        Tuple<Double,Double, Double> tent= GPSAlgorithms.shortestPathComEnergiaLimitada ( g,energiaAtual,maxEnergia,le, infoViagem,false );
        System.out.printf ( "energia a partir de %s: %.2f Wh\n",inicio.getNumPorta (), energiaAtual);
        System.out.println ();

        for (Label label : infoViagem) {
            StringBuilder s=new StringBuilder();
            s.append ( label.getEnderecoInicio ().getNumPorta () );
            if(label.getEnderecoInicio ().isFarmacia ()){
                s.append ( "(farmácia)" );
            }
            s.append ( "->" ).append ( label.getEnderecoFim ().getNumPorta () );

            if(label.getEnderecoFim ().isFarmacia ()){
                s.append ( "(farmácia)" );
            }
            s.append ( "\n\tEnergia gasta:" ).append ( String.format("%.2f",label.getEnergiaGasta ()) ).append ( " Wh;\n\tEnergia Restante: " )
                    .append (String.format("%.2f", label.getEnergiaRestante () )).append ( " Wh;" ).append ( "\n\tDistância percorrida: " )
                    .append ( String.format("%.2f",label.getDistancia ()) ).append ( " m" );

            System.out.println (s.toString ());

        }
        System.out.println ("\nDados da viagem:");
        System.out.printf ("%.2f Wh gastos\n",tent.get1st ());
        System.out.printf ("energia restante: %.2f Wh\n",tent.get2nd ());
        System.out.printf ("distância percorrida: %.2f m\n",tent.get3rd ());

        assertEquals ( 15,tent.get1st (),1 );
        assertEquals (1, tent.get2nd (),1 );
        assertEquals ( 14,tent.get3rd (),100 );
    }

    @Test
    void shortestPathDistanciaTeste(){
        Graph<Endereco, Rua> g = new Graph<> ( true );
        Endereco e1 = new Endereco ( "r1", "n1", "42342", "234", "wetr", 234, 234, 1, true );
        Endereco e2 = new Endereco ( "r2", "n2", "42342", "234", "wetr", 235, 234, 1, false );
        Endereco e3 = new Endereco ( "r3", "n3", "42342", "234", "wetr", 236, 234, 1, true );
        Endereco e4 = new Endereco ( "r4", "n4", "42342", "234", "wetr", 237, 234, 1, false );
        Endereco e5 = new Endereco ( "r5", "n5", "42342", "234", "wetr", 238, 234, 1, false );
        Endereco e6 = new Endereco ( "r6", "n6", "42342", "234", "wetr", 239, 234, 1, false );

        Rua r1 = new Rua ( "rua1", 3d, 0,0, 1, 0 );
        Rua r2 = new Rua ( "rua2", 3d, 0,0, 1, 0 );
        Rua r3 = new Rua ( "rua3", 20d, 0,0, 1, 0 );
        Rua r4 = new Rua ( "rua4", 1d, 0,0, 1, 0 );
        Rua r5 = new Rua ( "rua5", 5d, 0,0, 1, 0 );
        Rua r6 = new Rua ( "rua6", 1d, 0,0, 1, 0 );
        Rua r7 = new Rua ( "rua7", 0.5d, 0,0, 1, 0 );
        Rua r8 = new Rua ( "rua8", 2d, 0,0, 1, 0 );
        Rua r9 = new Rua ( "rua9", 3d, 0,0, 1, 0 );

        g.insertEdge ( e1, e2, r1, 3089898);
        g.insertEdge ( e2, e1, r2, 3);
        g.insertEdge ( e1, e3, r3, 1);
        g.insertEdge ( e1, e4, r4, 40);
        g.insertEdge ( e2, e3, r5, 45000);
        g.insertEdge ( e3, e5, r6, 346546);
        g.insertEdge ( e3, e6, r7, 0.5);
        g.insertEdge ( e5, e4, r8, 20045);
        g.insertEdge ( e4, e1, r9, 39999);

        //g.insertEdge ( e4, e2, r9, 1);

        LinkedList<Endereco> le=new LinkedList<>();
        le.add ( e1 );
        le.add ( e2 );
        le.add ( e5 );
        le.add ( e4 );
        double maxEnergia=99999999;
        double energiaAtual=99999999;
        Endereco inicio=e1;

        //System.out.println ("energia atual: "+energiaAtual+" KWh");

        List<Label> infoViagem=new LinkedList<> ();
        Tuple<Double,Double, Double> tent= GPSAlgorithms.shortestPathComEnergiaLimitada ( g,energiaAtual,maxEnergia,le, infoViagem,true );
        System.out.printf ( "energia a partir de %s: %.2f Wh\n",inicio.getNumPorta (), energiaAtual);
        System.out.println ();

        for (Label label : infoViagem) {
            StringBuilder s=new StringBuilder();
            s.append ( label.getEnderecoInicio ().getNumPorta () );
            if(label.getEnderecoInicio ().isFarmacia ()){
                s.append ( "(farmácia)" );
            }
            s.append ( "->" ).append ( label.getEnderecoFim ().getNumPorta () );

            if(label.getEnderecoFim ().isFarmacia ()){
                s.append ( "(farmácia)" );
            }
            s.append ( "\n\tEnergia gasta:" ).append ( String.format("%.2f",label.getEnergiaGasta ()) ).append ( " Wh;\n\tEnergia Restante: " )
                    .append (String.format("%.2f", label.getEnergiaRestante () )).append ( " Wh;" ).append ( "\n\tDistância percorrida: " )
                    .append ( String.format("%.2f",label.getDistancia ()) ).append ( " m" );

            System.out.println (s.toString ());

        }
        System.out.println ("\nDados da viagem:");
        System.out.printf ("%.2f Wh gastos\n",tent.get1st ());
        System.out.printf ("energia restante: %.2f Wh\n",tent.get2nd ());
        System.out.printf ("distância percorrida: %.2f m\n",tent.get3rd ());

        assertEquals ( 3541488,tent.get1st (),1 );
        assertEquals (9.9633405E7, tent.get2nd (),1E6 );
        assertEquals ( 14,tent.get3rd (),100 );
    }

    @Test
    void shortestPathComEdgesNegativasCicloNegativoTeste(){
        Graph<Endereco, Rua> g = new Graph<> ( true );
        Endereco e1 = new Endereco ( "r1", "n1", "42342", "234", "wetr", 234, 234, 1, true );
        Endereco e2 = new Endereco ( "r2", "n2", "42342", "234", "wetr", 235, 234, 1, false );
        Endereco e3 = new Endereco ( "r3", "n3", "42342", "234", "wetr", 236, 234, 1, true );
        Endereco e4 = new Endereco ( "r4", "n4", "42342", "234", "wetr", 237, 234, 1, false );
        Endereco e5 = new Endereco ( "r5", "n5", "42342", "234", "wetr", 238, 234, 1, false );
        Endereco e6 = new Endereco ( "r6", "n6", "42342", "234", "wetr", 239, 234, 1, false );

        Rua r1 = new Rua ( "rua1", 3d, 0,0, 1, 0 );
        Rua r2 = new Rua ( "rua2", 3d, 0,0, 1, 0 );
        Rua r3 = new Rua ( "rua3", 20d, 0,0, 1, 0 );
        Rua r4 = new Rua ( "rua4", 1d, 0,0, 1, 0 );
        Rua r5 = new Rua ( "rua5", 5d, 0,0, 1, 0 );
        Rua r6 = new Rua ( "rua6", 1d, 0,0, 1, 0 );
        Rua r7 = new Rua ( "rua7", 0.5d, 0,0, 1, 0 );
        Rua r8 = new Rua ( "rua8", 2d, 0,0, 1, 0 );
        Rua r9 = new Rua ( "rua9", 3d, 0,0, 1, 0 );

        g.insertEdge ( e1, e2, r1, 3);
        g.insertEdge ( e2, e1, r2, 3);
        g.insertEdge ( e1, e3, r3, -20);
        g.insertEdge ( e1, e4, r4, 3);
        g.insertEdge ( e2, e3, r5, -5);
        g.insertEdge ( e3, e5, r6, 2);
        g.insertEdge ( e3, e6, r7, 0.5);
        g.insertEdge ( e5, e4, r8, 2);
        g.insertEdge ( e4, e1, r9, 3);
        g.insertEdge ( e5, e2, r9, -3);

        LinkedList<Endereco> le=new LinkedList<>();
        le.add ( e1 );
        //le.add ( e2 );
        le.add ( e5 );
        //le.add ( e4 );
        double maxEnergia=30;
        double energiaAtual=20;
        Endereco inicio=e1;

        //System.out.println ("energia atual: "+energiaAtual+" KWh");

        List<Label> infoViagem=new LinkedList<> ();
        Tuple<Double,Double, Double> tent= GPSAlgorithms.shortestPathComEnergiaLimitada ( g,energiaAtual,maxEnergia,le, infoViagem,false );
        System.out.printf ( "energia a partir de %s: %.2f Wh\n",inicio.getNumPorta (), energiaAtual);
        System.out.println ();

        assertEquals ( -18,tent.get1st (),1 );
        assertEquals (Double.MIN_VALUE, tent.get2nd (),1E6 );
        assertEquals ( 0d,tent.get3rd (),100 );
    }

    @Test
    void shortestPathComEdgesNegativasTeste(){
        Graph<Endereco, Rua> g = new Graph<> ( true );
        Endereco e1 = new Endereco ( "r1", "n1", "42342", "234", "wetr", 234, 234, 1, true );
        Endereco e2 = new Endereco ( "r2", "n2", "42342", "234", "wetr", 235, 234, 1, false );
        Endereco e3 = new Endereco ( "r3", "n3", "42342", "234", "wetr", 236, 234, 1, true );
        Endereco e4 = new Endereco ( "r4", "n4", "42342", "234", "wetr", 237, 234, 1, false );
        Endereco e5 = new Endereco ( "r5", "n5", "42342", "234", "wetr", 238, 234, 1, false );
        Endereco e6 = new Endereco ( "r6", "n6", "42342", "234", "wetr", 239, 234, 1, false );

        Rua r1 = new Rua ( "rua1", 3d, 0,0, 1, 0 );
        Rua r2 = new Rua ( "rua2", 3d, 0,0, 1, 0 );
        Rua r3 = new Rua ( "rua3", 20d, 0,0, 1, 0 );
        Rua r4 = new Rua ( "rua4", 1d, 0,0, 1, 0 );
        Rua r5 = new Rua ( "rua5", 5d, 0,0, 1, 0 );
        Rua r6 = new Rua ( "rua6", 1d, 0,0, 1, 0 );
        Rua r7 = new Rua ( "rua7", 0.5d, 0,0, 1, 0 );
        Rua r8 = new Rua ( "rua8", 2d, 0,0, 1, 0 );
        Rua r9 = new Rua ( "rua9", 3d, 0,0, 1, 0 );

        g.insertEdge ( e1, e2, r1, 3);
        g.insertEdge ( e2, e1, r2, 3);
        g.insertEdge ( e1, e3, r3, -20);
        g.insertEdge ( e1, e4, r4, 3);
        g.insertEdge ( e2, e3, r5, 5);
        g.insertEdge ( e3, e5, r6, 2);
        g.insertEdge ( e3, e6, r7, 0.5);
        g.insertEdge ( e5, e4, r8, 2);
        g.insertEdge ( e4, e1, r9, 3);

        LinkedList<Endereco> le=new LinkedList<>();
        le.add ( e1 );
        //le.add ( e2 );
        le.add ( e5 );
        le.add ( e4 );
        double maxEnergia=30;
        double energiaAtual=20;
        Endereco inicio=e1;

        //System.out.println ("energia atual: "+energiaAtual+" KWh");

        List<Label> infoViagem=new LinkedList<> ();
        Tuple<Double,Double, Double> tent= GPSAlgorithms.shortestPathComEnergiaLimitada ( g,energiaAtual,maxEnergia,le, infoViagem,false );
        System.out.printf ( "energia a partir de %s: %.2f Wh\n",inicio.getNumPorta (), energiaAtual);
        System.out.println ();

        for (Label label : infoViagem) {
            StringBuilder s=new StringBuilder();
            s.append ( label.getEnderecoInicio ().getNumPorta () );
            if(label.getEnderecoInicio ().isFarmacia ()){
                s.append ( "(farmácia)" );
            }
            s.append ( "->" ).append ( label.getEnderecoFim ().getNumPorta () );

            if(label.getEnderecoFim ().isFarmacia ()){
                s.append ( "(farmácia)" );
            }
            s.append ( "\n\tEnergia gasta:" ).append ( String.format("%.2f",label.getEnergiaGasta ()) ).append ( " Wh;\n\tEnergia Restante: " )
                    .append (String.format("%.2f", label.getEnergiaRestante () )).append ( " Wh;" ).append ( "\n\tDistância percorrida: " )
                    .append ( String.format("%.2f",label.getDistancia ()) ).append ( " m" );

            System.out.println (s.toString ());

        }
        System.out.println ("\nDados da viagem:");
        System.out.printf ("%.2f Wh gastos\n",tent.get1st ());
        System.out.printf ("energia restante: %.2f Wh\n",tent.get2nd ());
        System.out.printf ("distância percorrida: %.2f m\n",tent.get3rd ());

        assertEquals ( -13,tent.get1st (),1 );
        assertEquals (23, tent.get2nd (),1E6 );
        assertEquals ( 14,tent.get3rd (),100 );
    }
}