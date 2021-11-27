package lapr.project.utils;

import lapr.project.model.Endereco;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FisicaAlgoritmosTest {
    @Test
    void distancia1Test(){
        //Expected values were obtained from http://www.movable-type.co.uk/scripts/latlong.html
        double lat1, lat2, lon1, lon2, alt1,alt2;
        double expected, result;
        double delta=1E3; //1Km

        lat1=lat2=-34.6131500;
        lon1=lon2=-58.3772300;
        alt1=alt2=0;
        expected=0;
        result=FisicaAlgoritmos.distancia ( lat1,lon1,alt1,lat2,lon2,alt2 );
        //distance from to itself is 0
        assertEquals ( expected,result,0.0d );

        lat2=-16.5000000;
        lon2=-68.1500000;
        expected=2236E3;
        result=FisicaAlgoritmos.distancia ( lat1,lon1,alt1,lat2,lon2,alt2 );
        assertEquals ( expected,result,delta );

        lat2=-15.7797200;
        lon2=-47.9297200;
        expected=2339E3;
        result=FisicaAlgoritmos.distancia ( lat1,lon1,alt1,lat2,lon2,alt2 );
        assertEquals ( expected,result,delta );

        lat1=41.33165;
        lon1=19.8318;
        lat2=42.672421;
        lon2=21.164539;
        expected=185.3E3;
        result=FisicaAlgoritmos.distancia ( lat1,lon1,alt1,lat2,lon2,alt2 );
        assertEquals ( expected,result,delta );
    }


    @Test
    void distancia2Teste(){
        Endereco e1 = new Endereco("rua1", "numPorta", "codPostal", "localidade", "pais", 250, 201, 1);
        Endereco e2 = new Endereco(2, "rua", "numPorta", "codPostal", "localidade", "pais", 200, 301, 10);

        double expected=8294954;
        double result=FisicaAlgoritmos.distancia ( e1,e2 );
        double delta=1;
        assertEquals ( expected,result,delta );
    }

    @Test
    void calculate2DDistanceTeste(){
        double lat1, lat2, lon1, lon2, alt1,alt2;
        double expected, result;
        double delta=1E3; //1Km

        lat1=lat2=-34.6131500;
        lon1=lon2=-58.3772300;
        alt1=alt2=0;
        expected=0;
        result=FisicaAlgoritmos.distancia ( lat1,lon1,alt1,lat2,lon2,alt2 );
        //distance from to itself is 0
        assertEquals ( expected,result,0.0d );

        lat2=-16.5000000;
        lon2=-68.1500000;
        expected=2236E3;
        result=FisicaAlgoritmos.distancia ( lat1,lon1,alt1,lat2,lon2,alt2 );
        assertEquals ( expected,result,delta );

        lat2=-15.7797200;
        lon2=-47.9297200;
        expected=2339E3;
        result=FisicaAlgoritmos.distancia ( lat1,lon1,alt1,lat2,lon2,alt2 );
        assertEquals ( expected,result,delta );

        lat1=41.33165;
        lon1=19.8318;
        lat2=42.672421;
        lon2=21.164539;
        alt1=10;
        alt2=15d;
        expected=185.3E3;
        result=FisicaAlgoritmos.distancia ( lat1,lon1,alt1,lat2,lon2,alt2 );
        assertEquals ( expected,result,delta );
    }

    @Test
    void angulo1Teste(){
        double lat1, lat2, lon1, lon2, alt1,alt2;
        double expected, result;
        double delta=1E-7;
        lat1=1;
        lat2=2;
        lon1=0;
        lon2=0;
        alt1=1;
        alt2=2;
        expected=8.99E-6;
        result=FisicaAlgoritmos.angulo ( lat1, lon1, alt1, lat2, lon2,alt2 );
        assertEquals ( expected,result,delta );
    }

    @Test
    void angulo2Teste(){
        Endereco e1 = new Endereco("rua1", "numPorta", "codPostal", "localidade", "pais", 250, 201, 150);
        Endereco e2 = new Endereco(2, "rua", "numPorta", "codPostal", "localidade", "pais", 200, 289, 1);

        double expected=1.89E-5;
        double result=FisicaAlgoritmos.angulo ( e1,e2 );
        double delta=0.1;
        assertEquals ( expected,result,delta );
    }

    @Test
    void energiaTotalTeste(){
        double expected, result, delta;

        double massa=90; //Kg
        double a=Math.PI/6; //rad
        double area=1.2;//m
        double velocidadeTransporte=20;//m/s
        double velocidadeVento=0;
        double anguloVento=1;
        double distancia=500; //m
        double Crr=0.03;

        expected=0.08;
        delta=0.01;
        result=FisicaAlgoritmos.energiaTotalScooter ( massa,a,area,velocidadeTransporte,velocidadeVento,anguloVento,distancia,Crr ); //W/h
        assertEquals ( expected,result,delta );
    }

    @Test
    void potenciaTotalTeste(){
        double expected, result, delta;
        double massa=90; //Kg
        double a=Math.PI/10; //rad
        double area=1.8;//m
        double velocidadeTransporte=20;//m/s
        double velocidadeVento=0;
        double anguloVento=1;
        double crr = 0.030;

        expected=9488;
        delta=1;
        result=FisicaAlgoritmos.potenciaTotalScooter ( massa,a,area,velocidadeTransporte,velocidadeVento,anguloVento,crr );
        assertEquals ( expected,result,delta );
    }

    @Test
    void tempoMedioTeste(){
        double expected, result, delta;
        double velocidade=20;//Km/h
        double distancia=50; //Km

        expected=2.5;
        delta=0.1;
        result=FisicaAlgoritmos.tempoMedio ( distancia,velocidade );
        assertEquals ( expected,result,delta );
    }

    @Test
    void forcaTotalTeste(){
        double expected, result, delta;

        double massa =80;
        double a=3;
        double area=5;
        double velocidade=20;
        double crr = 0.030;
        double velocidadeVento=1;
        double anguloVento=0;

        expected=529;
        result=FisicaAlgoritmos.forcaTotal ( massa,a,area,velocidade,velocidadeVento,anguloVento,crr );
        delta=1;
        assertEquals ( expected,result,delta );
    }

    @Test
    void forcaInclinacaoEstradaTeste(){
        double expected, result, delta;
        double area=5;
        double massa =80;

        expected=-752;
        result=FisicaAlgoritmos.forcaInclinacaoEstrada ( massa,area );
        delta=1;
        assertEquals ( expected,result,delta );
    }

    @Test
    void forcaCargaTeste(){
        double expected, result, delta;
        double area=5;
        double massa =80;
        double crr = 0.030;

        expected=6;
        result=FisicaAlgoritmos.forcaCarga ( massa,area,crr );
        delta=1;
        assertEquals ( expected,result,delta );
    }

    @Test
    void forcaAtritoTeste(){
       double expected, result, delta;
       double area=2;
       double velocidade=10;
       double velocidadeVento=1;
       double anguloVento=20;

       expected=40;
       result=FisicaAlgoritmos.forcaAtritoAerodinamico ( area,velocidade,velocidadeVento,anguloVento );
       delta=1;
       assertEquals ( expected,result,delta );
    }

    @Test
    void energiaTotalDroneTeste(){
        double massa=30;
        double area=2;
        double velocidade=10;
        double largura=30;
        double distancia=140;
        boolean levantamento=false;

        double expected=1.9;
        double result=FisicaAlgoritmos.energiaTotalDrone ( massa,velocidade,0d,0d,area,10,distancia,largura,levantamento );
        double delta=1;
        assertEquals ( 7.64E-4,result,0.01 );
    }

    @Test
    void potenciaHorizontalTeste(){
        double massa=30;
        double area=20;
        double velocidade=10;
        double largura=30;

        double expected=0.08;
        double result=FisicaAlgoritmos.potenciaHorizontal ( area,velocidade,10,0,massa,largura );
        double delta=0.01;
        assertEquals ( expected,result,delta );
    }

    @Test
    void potenciaAtritoParasitaTeste(){
        double area=2;
        double velocidade=10;

        double expected=6E-11;
        double result=FisicaAlgoritmos.potenciaAtritoParasita ( area,velocidade,10,0.01 );
        double delta=1E-10;
        assertEquals ( expected,result,delta );
    }

    @Test
    void potenciaLiftTeste(){
        double massa=30;
        double velocidade=10;
        double largura=30;

        double expected=0.08;
        double result=FisicaAlgoritmos.potenciaLift ( velocidade,0,0,massa,largura );
        double delta=0.01;
        assertEquals ( expected,result,delta );
    }

    @Test
    void forcaAtritoAerodinamicoTeste(){
        double area=30;
        double velocidadeTransporte=10;
        double velocidadeVento=0;
        double anguloVento=30;

        double expected=735;
        double result=FisicaAlgoritmos.forcaAtritoAerodinamico ( area,velocidadeTransporte,velocidadeVento,anguloVento );
        double delta=1;
        assertEquals ( expected,result,delta );
    }
}