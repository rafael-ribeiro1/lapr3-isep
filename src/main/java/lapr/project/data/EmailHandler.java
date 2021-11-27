package lapr.project.data;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Classe responsável por criar uma sessão a partir do ficheiro application.properties e enviar emails
 */
public class EmailHandler {

    private static Logger logger =Logger.getLogger ( EmailHandler.class.getName () );

    /**
     * Session responsável pelo envio do email
     */
    private Session session;
    /**
     * Email a partir do qual vão ser enviadas as mensagens
     */
    private String username;
    /**
     * Indicador de modo de testes
     */
    private boolean testMode;

    /**
     * Cria a session a partir dos dados no ficheiro application.properties
     * @throws IOException resulta numa exception se ocorrer um erro ao abrir ficheiro de propriedades
     */
    public EmailHandler() throws IOException {
        Properties appProps = new Properties();
        String propFileName = "application.properties";

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
        if (inputStream != null)
            appProps.load(inputStream);
        else
            throw new FileNotFoundException("Application property file not found in classpath");
        inputStream.close();

        username = appProps.getProperty("email.from");
        final String password = appProps.getProperty("email.password");
        String host = appProps.getProperty("email.host");
        String port = appProps.getProperty("email.port");
        String strTest = appProps.getProperty("email.testmode");
        testMode = strTest.equals("true");

        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        session = Session.getInstance(props,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
    }

    /**
     * Envia email
     * @param dest email de destino
     * @param subject assunto da mensagem
     * @param content conteúdo da mensagem
     * @return true se o email tiver sido enviado com sucesso, falso se ocorreu algum erro
     */
    public boolean sendEmail(String dest, String subject, String content) {
        if (testMode){
            File f = new File("email.txt");
            try {
                try(FileWriter fw = new FileWriter(f, true)){
                    fw.append("-------------------------------------------------------------\n\n")
                            .append(String.format("E-mail Destinatário: %s\nAssunto: %s\n\n%s\n\n", dest, subject, content))
                            .append("-------------------------------------------------------------\n\n");
                }
            } catch (IOException e) {
                logger.severe(e.getMessage());
            }
            return true;
        }
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(dest));
            message.setSubject(subject);
            message.setText(content);

            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

}
