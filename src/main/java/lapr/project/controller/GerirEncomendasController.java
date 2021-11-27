package lapr.project.controller;

import lapr.project.data.ClientesHandler;
import lapr.project.data.EncomendaDataHandler;
import lapr.project.model.Encomenda;

import java.io.IOException;
import java.util.List;

/**
 * Controller que trata das encomendas da plataforma.
 */
public class GerirEncomendasController {
    /**
     * Handler dos clientes.
     */
    private ClientesHandler clientesHandler;
    /**
     * Handler das encomendas do sistema.
     */
    private EncomendaDataHandler encomendaHandler;

    /**
     * Construtor que instância os handlers necessários.
     * @throws IOException
     */
    public GerirEncomendasController() throws IOException{
        encomendaHandler = new EncomendaDataHandler();
        clientesHandler = new ClientesHandler();
    }

    /**
     * Setter do handler dos clientes (Apenas utilizados para testes unitários).
     * @param clientesHandler Handler dos clientes.
     */
    public void setClientesHandler(ClientesHandler clientesHandler) {
        this.clientesHandler = clientesHandler;
    }

    /**
     * Setter do handler das encomendas(Apenas utilizados para testes unitários).
     * @param encomendaHandler handler das encomendas.
     */
    public void setEncomendaHandler(EncomendaDataHandler encomendaHandler) {
        this.encomendaHandler = encomendaHandler;
    }
    /**
     * Retorna encomenda fornecendo o seu ID
     * @return encomenda ou null se ocorrer um erro
     */
    public Encomenda getEncomenda(int idEncomenda) {
            return encomendaHandler.getEncomenda(idEncomenda);
    }

    /**
     * Retorna todas as encomendas no sistema
     * @return List de encomendas ou null
     */
    public List<Encomenda> getTodasEncomendas()
    {
        return encomendaHandler.getTodasEncomendas();
    }

    /**
     * Retorna o numero de creditos gerado ao cliente da encomenda com o id passado como parametro.
     * @return ArrayList de encomendas ou null se ocorrer um erro
     */
    public int gerarCreditosCliente(int idEncomenda) {
            return clientesHandler.addCreditosCliente(idEncomenda);
    }
}
