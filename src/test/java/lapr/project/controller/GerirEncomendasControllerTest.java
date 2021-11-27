package lapr.project.controller;

import lapr.project.data.ClientesHandler;
import lapr.project.data.EncomendaDataHandler;
import lapr.project.data.ProdutosFarmaciaHandler;
import lapr.project.model.Encomenda;
import lapr.project.model.Produto;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class GerirEncomendasControllerTest {
    private GerirEncomendasController controller;
    private ClientesHandler mockCliente;
    private EncomendaDataHandler mockEncomenda;
    private GerirEntregasController  entregasController;
    private ProdutosFarmaciaHandler produtosFarmaciaHandler;
    public GerirEncomendasControllerTest() {
        try {
            this.controller=new GerirEncomendasController();
            mockCliente = Mockito.mock(ClientesHandler.class);
            mockEncomenda = Mockito.mock(EncomendaDataHandler.class);
            controller.setEncomendaHandler(mockEncomenda);
            controller.setClientesHandler(mockCliente);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getEncomenda() {

        Encomenda expected = new Encomenda(1,123456789,Encomenda.EstadoEncomenda.ENVIO_PRONTO);

        Encomenda result = controller.getEncomenda(1);
        assertNull(result);

        Mockito.when(mockEncomenda.getEncomenda(1)).thenReturn(expected);
        result = controller.getEncomenda(1);
        assertEquals(expected,result);
    }


    @Test
    void gerarCreditosCliente() {
        int expected = 0;
        int result = controller.gerarCreditosCliente(1);
        assertEquals(expected,result);

        Produto produto1 = new Produto(1, "a", 10, 11.3, 0.5, "desc");
        Map<Integer,Produto> mapa = new HashMap<>();
        mapa.put(1,produto1);
        Mockito.when(mockEncomenda.getProdutosEncomenda(1)).thenReturn(mapa);
        expected=40;
        Mockito.when(mockCliente.addCreditosCliente(1)).thenReturn(40);
        result = controller.gerarCreditosCliente(1);
        assertEquals(expected,result);


    }

    @Test
    void getTodasEncomendas(){
        List<Encomenda> expected = null;
        Mockito.when(mockEncomenda.getTodasEncomendas()).thenReturn(null);
        List<Encomenda> encomendaList = controller.getTodasEncomendas();
        assertEquals(expected,encomendaList);
         expected = new ArrayList<>();
        expected.add(new Encomenda(1,123456789));
        Mockito.when(mockEncomenda.getTodasEncomendas()).thenReturn(expected);
        encomendaList = controller.getTodasEncomendas();
        assertEquals(expected,encomendaList);

    }
}