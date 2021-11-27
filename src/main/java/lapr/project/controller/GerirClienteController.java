package lapr.project.controller;

import lapr.project.data.*;
import lapr.project.model.CartaoCredito;
import lapr.project.model.Cliente;
import lapr.project.model.Endereco;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Classe controller que gere as informações relacionadas com um cliente.
 */
public class GerirClienteController {
    /**
     * Handler dosclientes
     */
    private ClientesHandler handler;
    /**
     * Handler dos emails
     */
    private EmailHandler emailHandler;

    /**
     * Construtor que instância os handlers
     * @throws IOException
     */
    public GerirClienteController() throws IOException{
            handler = new ClientesHandler();
            emailHandler = new EmailHandler();
    }

    /**
     * Método que faz set dos handlers de clientes (Apenas utilizados para testes unitários) .
     * @param handler handler dos clientes.
     */
    public void setHandler(ClientesHandler handler) {
        this.handler = handler;
    }
    /**
     * Método que faz set dos handlers dos emails  (Apenas utilizados para testes unitários) .
     * @param emailHandler handlers dos emails.
     */
    public void setEmailHandler(EmailHandler emailHandler) {
        this.emailHandler = emailHandler;
    }

    /**
     * Método que regista na base de dados um novo cliente
     * @param username username do cliente
     * @param password password do cliente
     * @param email email do cliente
     * @param nome nome do cliente
     * @param numeroCartao numero do cartao do cliente
     * @param ccv ccv do cartao do cliente
     * @param dataValidade data validade do cliente
     * @param rua rua em que vive o cliente
     * @param numPorta numPorta do cliente
     * @param codPostal codPostal do cliente
     * @param localidade localidade do cliente
     * @param pais pais do cliente
     * @param latitude latitude do cliente
     * @param longitude longitude do cliente
     * @return true se registou o cliente , false se nao registou
     */
    public boolean novoCliente(String username, String password, String email, String nome , String numeroCartao, int ccv , LocalDate dataValidade , String rua, String numPorta, String codPostal, String localidade,
                               String pais, double latitude, double longitude , double altitude) {
        CartaoCredito cartaoCredito = new CartaoCredito(numeroCartao,ccv,dataValidade);
        Endereco endereco = new Endereco(rua, numPorta, codPostal, localidade, pais, latitude, longitude, altitude);
        Cliente cliente = new Cliente(username,password,email,nome,endereco,cartaoCredito);
        boolean isRegistado = handler.addNovoCliente(cliente);
            if(isRegistado){
                String assunto = String.format("Registo na platforma - Senhor cliente %s",nome);
                String conteudo= String.format("Obrigado %s  pelo seu registo na platforma. %n------DADOS PESSOAIS REGISTADO------%n----------------------------------------%nUsername: %s%nemail: %s%npassword: %s%n----------------------------------------%n    Grupo 24 LAPR3",nome,username,email,password);
                emailHandler.sendEmail(email,assunto,conteudo);
            }
        return isRegistado;
    }

    /**
     * Atualiza o cartão de crédito do cliente
     * @param numero o número do cartão de crédito
     * @param ccv o ccv do cartão de crédito
     * @param dataValidade a data de validade do cartão de crédito
     * @param idCartao o id do cartão de crédito
     * @return true se a atualização for bem sucedida, caso contrário retorna false
     */
    public boolean atualizarCartaoCredito(String numero, int ccv, LocalDate dataValidade, int idCartao){
        if (dataValidade.isBefore(LocalDate.now())){
            return false;
        }else {
            return handler.atualizarCartaoCredito(numero, ccv, dataValidade, idCartao);
        }
    }
    /**
     * Método que fornecendo um id de um cliente fornece o objeto de cliente.
     * @param id id do cliente que se pretende obter.
     * @return Cliente que possui o id passado como parametro.
     */
    public Cliente getInformacaoCliente(int id){
        if (id <= 0) return null;
        return handler.getInformacaoCliente(id);
    }

    /**
     * Atualiza a informação do cliente recebendo por parâmetro os dados para identificar o cliente
     * e os dados a ser atualizados:
     * @param id o id do cliente
     * @param nome o nome do cliente
     * @param e o endereço do cliente
     * @return true se a atualização for bem sucedida, caso contrário, retorna false
     */
    public boolean atualizarInformacaoCliente(int id, String nome,Endereco e){
        if (id <= 0) return false;
        return handler.atualizarInformacaoCliente(id, nome, e);
    }

    /**
     * Remove o perfil do cliente, atualizando o estado para "Removido"
     * @param id o id do cliente
     * @return true se for bem sucedido, caso contrário, retorna false
     */
    public boolean removerPerfilCliente(int id){
        if (id <= 0)
            return false;
        else
            return handler.removerPerfilCliente(id);
    }


}
