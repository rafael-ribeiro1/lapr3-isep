package lapr.project.controller;

import lapr.project.data.ProdutoHandler;
import lapr.project.model.Produto;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GerirProdutosControllerTest {

    private ProdutoHandler mock ;
    private GerirProdutosController controller;

    public GerirProdutosControllerTest() throws IOException {
        mock = Mockito.mock(ProdutoHandler.class);

           controller= new GerirProdutosController();
           controller.setHandler(mock);
    }


    @Test
    void addProduto() throws IOException {
        Produto sc = new Produto("Vacina Covid 11",13.54,10.5,0.5,"Vacina contra o covid");
        int result= controller.addProduto(sc);
        System.out.println(result);
        Mockito.when(mock.addProduto(sc)).thenReturn(1);
        Mockito.verify(mock, Mockito.times(1)).addProduto(sc);
        assertEquals(result,sc.getId());

    }


    @Test
    void addProduto2() throws IOException {
        Produto sc = new Produto("Vacina Covid 11",13.54,-10.5,0.5,"Vacina contra o covid");
        int result= controller.addProduto(sc);
        Mockito.verify(mock, Mockito.times(0)).addProduto(sc);
        assertEquals(-1,result);

    }

    @Test
    void addProduto3() throws IOException {
        Produto sc = new Produto("Vacina Covid 11",-13.54,10.5,0.5,"Vacina contra o covid");
        int result= controller.addProduto(sc);
        Mockito.verify(mock, Mockito.times(0)).addProduto(sc);
        assertEquals(-1,result);

    }

    @Test
    void addProduto4() throws IOException {
        Produto sc = new Produto("Vacina Covid 11",13.54,10.5,-0.5,"Vacina contra o covid");
        int result= controller.addProduto(sc);
        Mockito.verify(mock, Mockito.times(0)).addProduto(sc);
        assertEquals(-1,result);

    }

    @Test
    void addProduto5() throws IOException {
        Produto sc = new Produto("Vacina Covid 11",-13.54,-10.5,-0.5,"Vacina contra o covid");
        int result= controller.addProduto(sc);
        Mockito.verify(mock, Mockito.times(0)).addProduto(sc);
        assertEquals(-1,result);

    }

    @Test
    void addProduto6() throws IOException {
        Produto sc = new Produto("Vacina Covid 11",13.54,0,0.5,"Vacina contra o covid");
        Mockito.when(mock.addProduto(sc)).thenReturn(sc.getId());
        int result= controller.addProduto(sc);
        Mockito.verify(mock, Mockito.times(1)).addProduto(sc);
        assertEquals(sc.getId(),result);

    }

    @Test
    void addProduto7() throws IOException {
        Produto sc = new Produto("Vacina Covid 11",13.54,10.5,0,"Vacina contra o covid");
        Mockito.when(mock.addProduto(sc)).thenReturn(sc.getId());
        int result= controller.addProduto(sc);
        Mockito.verify(mock, Mockito.times(1)).addProduto(sc);
        assertEquals(sc.getId(),result);

    }

    @Test
    void addProduto8() throws IOException {
        Produto sc = new Produto("Vacina Covid 11",0,10.5,0.5,"Vacina contra o covid");
        Mockito.when(mock.addProduto(sc)).thenReturn(sc.getId());
        int result= controller.addProduto(sc);
        Mockito.verify(mock, Mockito.times(1)).addProduto(sc);
        assertEquals(sc.getId(),result);

    }

    @Test
    void addProduto9() throws IOException {
        Produto sc = new Produto("Vacina Covid 11",0,10.5,0.5,"Vacina contra o covid");
        Mockito.when(mock.addProduto(sc)).thenReturn(2);
        int result= controller.addProduto(sc);
        Mockito.verify(mock, Mockito.times(1)).addProduto(sc);
        assertEquals(2,result);

    }


    @Test
    void atualizarProduto() throws IOException {
        String[] info = new String[5];
        info[0] = "Vacina Covid 11";
        info[1] = "10.54";
        info[2] = "5.43";
        info[3] = "1";
        info[4]="Vacina contra o covid";
        Produto p = new Produto("Vacina 223",10,10,1,"Vacina contra o malária");
        Mockito.when(mock.updateProduto(info,p)).thenReturn(true);
        boolean obt = controller.atualizarProduto(info,p);
        Mockito.verify(mock, Mockito.times(1)).updateProduto(info,p);

        assertTrue(obt);
    }

    @Test
    void atualizarProdutoNulo1() throws IOException {
        String[] info = new String[5];
        info[0] = null;
        info[1] = "10.54";
        info[2] = "5.43";
        info[3] = "1";
        info[4]="Vacina contra o covid";
        Produto p = new Produto("Vacina 223",10,10,1,"Vacina contra o malária");
        Mockito.when(mock.updateProduto(info,p)).thenReturn(true);
        boolean obt = controller.atualizarProduto(info,p);
        Mockito.verify(mock, Mockito.times(1)).updateProduto(info,p);

        assertTrue(obt);
    }

    @Test
    void atualizarProdutoNulo2() throws IOException {
        String[] info = new String[5];
        info[0] = "Vacina Covid 11";
        info[1] = null;
        info[2] = "5.43";
        info[3] = "1";
        info[4]="Vacina contra o covid";
        Produto p = new Produto("Vacina 223",10,10,1,"Vacina contra o malária");
        Mockito.when(mock.updateProduto(info,p)).thenReturn(true);
        boolean obt = controller.atualizarProduto(info,p);
        Mockito.verify(mock, Mockito.times(1)).updateProduto(info,p);

        assertTrue(obt);
    }


    @Test
    void atualizarProdutoNulo3() throws IOException {
        String[] info = new String[5];
        info[0] = "Vacina Covid 11";
        info[1] = "10.54";
        info[2] = null;
        info[3] = "1";
        info[4]="Vacina contra o covid";
        Produto p = new Produto("Vacina 223",10,10,1,"Vacina contra o malária");
        Mockito.when(mock.updateProduto(info,p)).thenReturn(true);
        boolean obt = controller.atualizarProduto(info,p);
        Mockito.verify(mock, Mockito.times(1)).updateProduto(info,p);

        assertTrue(obt);
    }


    @Test
    void atualizarProdutoNulo4() throws IOException {
        String[] info = new String[5];
        info[0] = "Vacina Covid 11";
        info[1] = "10.54";
        info[2] = "5.43";
        info[3] = null;
        info[4]="Vacina contra o covid";
        Produto p = new Produto("Vacina 223",10,10,1,"Vacina contra o malária");
        Mockito.when(mock.updateProduto(info,p)).thenReturn(true);
        boolean obt = controller.atualizarProduto(info,p);
        Mockito.verify(mock, Mockito.times(1)).updateProduto(info,p);

        assertTrue(obt);
    }

    @Test
    void atualizarProdutoNulo5() throws IOException {
        String[] info = new String[5];
        info[0] = "Vacina Covid 11";
        info[1] = "10.54";
        info[2] = "5.43";
        info[3] = "1";
        info[4]=null;
        Produto p = new Produto("Vacina 223",10,10,1,"Vacina contra o malária");
        Mockito.when(mock.updateProduto(info,p)).thenReturn(true);
        boolean obt = controller.atualizarProduto(info,p);
        Mockito.verify(mock, Mockito.times(1)).updateProduto(info,p);

        assertTrue(obt);
    }

    @Test
    void atualizarProdutoMenor1() throws IOException {
        String[] info = new String[5];
        info[0] = "Vacina Covid 11";
        info[1] = "-10.5";
        info[2] = "5.43";
        info[3] = "1";
        info[4]="Vacina contra o covid";
        Produto p = new Produto("Vacina 223",10,10,1,"Vacina contra o malária");
        boolean obt = controller.atualizarProduto(info,p);
        assertFalse(obt);
    }

    @Test
    void atualizarProdutoMenor2() throws IOException {
        String[] info = new String[5];
        info[0] = "Vacina Covid 11";
        info[1] = "10.5";
        info[2] = "-5.43";
        info[3] = "1";
        info[4]="Vacina contra o covid";
        Produto p = new Produto("Vacina 223",10,10,1,"Vacina contra o malária");
        boolean obt = controller.atualizarProduto(info,p);
        assertFalse(obt);
    }

    @Test
    void atualizarProdutoMenor3() throws IOException {
        String[] info = new String[5];
        info[0] = "Vacina Covid 11";
        info[1] = "10.5";
        info[2] = "5.43";
        info[3] = "-1";
        info[4]="Vacina contra o covid";
        Produto p = new Produto("Vacina 223",10,10,1,"Vacina contra o malária");
        boolean obt = controller.atualizarProduto(info,p);
        assertFalse(obt);
    }

    @Test
    void getListaProdutos() {

        List<Produto> expected = new ArrayList<>();

        List<Produto> result = controller.getListaProdutos();
        assertEquals(expected,result);
        Produto produto = new Produto(1, "nome", 5, 5, 5,"descricao");
        expected.add(produto);
        Mockito.when(mock.getListaProdutos()).thenReturn(expected);
        result = controller.getListaProdutos();
        assertEquals(expected,result);
    }

    @Test
    void getProduto(){
        Produto expected = new Produto(1, "nome", 5, 5, 5,"descricao");;
        Produto produto = new Produto(1, "nome", 5, 5, 5,"descricao");
        Mockito.when(mock.getProduto(1)).thenReturn(produto);
        Produto result = controller.getProduto(1);
        assertEquals(expected,result);
    }

    @Test
    void getHandler() {
        assertEquals(mock, controller.getHandler());
    }
}