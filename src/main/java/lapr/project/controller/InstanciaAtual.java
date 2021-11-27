package lapr.project.controller;

import lapr.project.data.ClientesHandler;
import lapr.project.model.Cliente;
import lapr.project.model.Farmacia;
import lapr.project.model.Produto;
import lapr.project.model.User;

import java.io.IOException;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;
import java.util.logging.Logger;

/**
 * Método que representa a sessão da aplicação a correr.
 */
public class InstanciaAtual {
    /**
     * User que está a utilizar a sessão
     */
    private User userLogado;

    /**
     * Carrinho de compras do user logado atualmente se for cliente
     */
    private Map<Farmacia,Map<Produto,Integer>> carrinhoCompras;

    /**
     *  Sessão atual
     */
    private static InstanciaAtual singleton = null;

    /**
     * Handler dos clientes.
     */
    private ClientesHandler handler;

    /**
     * Construtor que instância os handlers e o timer para pesquisar os ficheiros de estacionamento da aplicação.
     * @throws IOException
     */
    private InstanciaAtual() throws IOException {
        handler = new ClientesHandler();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            EstacionarTransporteController controller = new EstacionarTransporteController();
            @Override
            public void run() {
                controller.registarEstacionamentos();
            }
        }, 0, 10000);
    }

    /**
     *  Define o handler dos clientes com o handler passado como parametro (Apenas usado em testes unitarios).
     * @param handler Handler dos clientes.
     */
    public void setHandler(ClientesHandler handler) {
        this.handler = handler;
    }

    /**
     * Setter do user logado
     * @param userLogado User que esta a usar a plataforma.
     */
    public void setUserLogado (User userLogado){
        this.userLogado=userLogado;
        if(userLogado.getTipoUser().equals("cliente")) carrinhoCompras = new TreeMap<>();
    }

    /**
     * Método que devolve o cliente a utilizar a aplicação atualmente.
     * @return Cliente que esta a usar a aplicação atualmente.
     */
    public Cliente getClienteLogado (){
        return handler.getClienteLogado(userLogado);
    }

    /**
     * Método que devolve o utilizador a utilizar a aplicação atualmente.
     * @return User a atualizar a aplicação atualmente.
     */
    public User getCurrentUser(){
        return userLogado;
    }

    /**
     * Método que devolve o carrinho de compras da sessão atual.
     * @return Carrinho de compras da sessão atual.
     */
    public Map<Farmacia,Map<Produto,Integer>>getCarrinhoCompras () {
        return carrinhoCompras;
    }

    /**
     * Método que devolve a sessão atual a ser utilizada.
     * @return Sessão atual.
     */
    public static InstanciaAtual getInstance()
    {
        if(singleton == null)
        {
            synchronized(InstanciaAtual.class)
            {
                try{
                    singleton = new InstanciaAtual();
                }catch (IOException e){
                    Logger.getAnonymousLogger().info("Erro");
                }

            }
        }
        return singleton;
    }

    /**
     * Método que introduz um produto no carrinho de compras da sessão atual .
     * @param produto Produto a ser adicionado.
     * @param quantidade Quantidade do produto a ser adicionado.
     * @param farmacia Farmacia de qual se pretende esse produto.
     */
    public void introduzirProdutoNoCarrinho (Produto produto, int quantidade, Farmacia farmacia){
        Map<Produto,Integer> secondaryMap;
        if (this.carrinhoCompras == null || this.carrinhoCompras.isEmpty()){
            carrinhoCompras = new TreeMap<>();
            secondaryMap=new TreeMap<>();
        } else{
            secondaryMap = carrinhoCompras.get(farmacia);
        }
        secondaryMap.put(produto,quantidade);
        carrinhoCompras.put(farmacia,secondaryMap);
    }

    /**
     * Remove o produto relativo à farmácia do carrinho de compras.
     * @param farmacia a farmácia relativa ao produto a remover
     * @param produto o produto a remover
     * @return o valor associado ao produto removido caso este exista, se não devolve null.
     */
    public boolean removerProdutoCarrinho(Farmacia farmacia, Produto produto){
        if (carrinhoCompras.isEmpty()) return false;
        getCarrinhoCompras().get(farmacia).remove(produto);
        return true;
    }
}
