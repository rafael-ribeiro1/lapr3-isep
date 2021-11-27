package lapr.project.ui.Utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
/**
 *  Classe que possui algumas habilidades uteis para a UI
 */
public class UtilsRead {
    /**
     *  Mensagem a informar se introduzir um input errado
     */
    private static final String INPUT_INVALIDO = "Input errado!";
    /**
     * Construtor privado da classe
     */
    private UtilsRead(){};
    /**
     * Método que devolve uma string de input
     * @param strPrompt Mensagem a ser apresentada
     * @return String de input do utilizador
     */
    static public String readLineFromConsole(String strPrompt)
    {
        try
        {
            System.out.println("\n" + strPrompt);
            InputStreamReader converter = new InputStreamReader(System.in);
            BufferedReader in = new BufferedReader(converter);
            return in.readLine();
        } catch (InputMismatchException | IOException e)
        {
            System.out.println(INPUT_INVALIDO);
            return readLineFromConsole(strPrompt);
        }
    }

    /**
     * Método que devolve um int lida pela consola
     * @param strPrompt Mensagem a ser apresentada
     * @return Int de input
     */
    static public int readIntegerFromConsole(String strPrompt)
    {
        do
        {
            try
            {
                String strInt = readLineFromConsole(strPrompt);
                return Integer.parseInt(strInt);
            } catch (Exception ex)
            {
                System.out.println(INPUT_INVALIDO);
                return readIntegerFromConsole(strPrompt);
            }
        } while (true);
    }

    /**
     *  Método que devolve um double de input
     * @param strPrompt Mensagem a ser apresentada
     * @return Double de input;
     */
    static public double readDoubleFromConsole(String strPrompt)
    {
        do
        {
            try
            {
                String strDouble = readLineFromConsole(strPrompt);

                double dValor = Double.parseDouble(strDouble);

                return dValor;
            } catch (Exception ex)
            {
                System.out.println(INPUT_INVALIDO);
                return readDoubleFromConsole(strPrompt);
            }
        } while (true);
    }

    /**
     * Método que devolve um date de input
     * @param strPrompt Mensagem a ser apresentada
     * @return Date de input
     */
    static public LocalDate readDateFromConsole(String strPrompt)
    {
        do
        {
            try
            {
                String strDate = readLineFromConsole(strPrompt);

                String  [] contents = strDate.split("-");

                LocalDate date = LocalDate.of(Integer.parseInt(contents[0]),Integer.parseInt(contents[1]),Integer.parseInt(contents[2]));

                return date;
            } catch (Exception ex)
            {
                System.out.println(INPUT_INVALIDO);
                return readDateFromConsole(strPrompt);
            }
        } while (true);
    }

    /**
     * Método que pede ao utilizador para confirmar uma acao
     * @param sMessage Mensagem a ser apresentada
     * @return boolean a confirmar
     */
    static public boolean confirma(String sMessage) {
        String strConfirma;
        do {
            strConfirma = UtilsRead.readLineFromConsole("\n" + sMessage + "\n");
        } while (!strConfirma.equalsIgnoreCase("s") && !strConfirma.equalsIgnoreCase("n"));

        return strConfirma.equalsIgnoreCase("s");
    }

    /**
     *  Método que apresenta a lista passada como parametro
     * @param list Lista a ser apresentada
     * @param sHeader Mensagem a ser apresentada
     * @return objeto selecionado
     */
    static public Object apresentaESeleciona(List list, String sHeader)
    {
        apresentaLista(list,sHeader);
        return selecionaObject(list);
    }
    /**
     * @param list Lista a ser apresentado
     * @param sHeader Mensagem a ser apresentada
     * @return int que representa o index selecionada
     */
    static public int apresentaESelecionaIndex(List list,String sHeader)
    {
        apresentaLista(list,sHeader);
        return selecionaIndex(list);
    }
    /** Método que apresenta uma lista
     * @param list Lista a ser apresentada
     * @param sHeader Mensagem a ser apresentada
     */
    static public void apresentaLista(List list,String sHeader)
    {
        System.out.println(sHeader);
        int index = 0;
        for (Object o : list)
        {
            index++;

            System.out.println(index + ". " + o.toString());
        }
        System.out.println("");
        System.out.println("0 - Cancelar");
    }
    /**
     *  Método que apresenta uma lista de objeto
     * @param list lista a ser apresentada
     * @return um objeto selecionado
     */
    static public Object selecionaObject(List list)
    {
        String opcao;
        int nOpcao;
        do
        {
            opcao = UtilsRead.readLineFromConsole("Introduza opção: ");
            nOpcao = new Integer(opcao);
        } while (nOpcao < 0 || nOpcao > list.size());

        if (nOpcao == 0)
        {
            return null;
        } else
        {
            return list.get(nOpcao - 1);
        }
    }

    /**
     *  Método que devolve um index da lista
     * @param list Lista a ser apresentada
     * @return
     */
    static public int selecionaIndex(List list)
    {
        String opcao;
        int nOpcao;
        do
        {
            opcao = UtilsRead.readLineFromConsole("Introduza opção: ");
            nOpcao = new Integer(opcao);
        } while (nOpcao < 0 || nOpcao > list.size());
        return nOpcao - 1;
    }
}
