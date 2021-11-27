package lapr.project.ui;

import lapr.project.controller.GerirEncomendasController;

import java.io.IOException;

public class GerirEncomendasUI {
    /**
     * Controller
     */
    private GerirEncomendasController controller;
    /**
     * Teclado
     */
    /**
     * Construtor que inicializa o controller e o scanner do teclado
     */
    public GerirEncomendasUI() {
        try{
            controller = new GerirEncomendasController();
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
