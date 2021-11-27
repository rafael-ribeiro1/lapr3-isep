package lapr.project.model;

import com.google.zxing.*;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

/**
 *  Classe que representa um codigoQr
 */
public class CodigoQR {
    /**
     * Construtor que impede a instanciação do metodo
     */
    private CodigoQR() {
    }

    /**
     * Método que gera a imagem do códigoQR.
     * @param mensagem mensagem introduzida no código QR
     * @param ficheiro o nome do ficheiro para onde vai ser gerada a imagen
     * @param largura a largura pretendida
     * @param altura a altura pretendida
     */
    public static void gerarCodigoQR(String mensagem, String ficheiro, int largura, int altura)
            throws IOException, WriterException {
        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix bitMatrix = writer.encode(mensagem, BarcodeFormat.QR_CODE, largura, altura);
        Path p = FileSystems.getDefault().getPath(ficheiro);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", p);
    }

    /**
     * Lê o código QR armazenado no ficheiro e obtém a mensagem contida neste.
     * @param ficheiro ficheiro que armazena o código QR
     * @return retorna a mensagem contida no código QR
     */
    public static String lerCodigoQR(String ficheiro) throws IOException, ChecksumException,
            NotFoundException, FormatException {
        BufferedImage imagem= ImageIO.read ( new File(ficheiro) );
        int[] pixeis=imagem.getRGB ( 0,0,imagem.getWidth (),imagem.getHeight (),
                null,0,imagem.getWidth () );
        RGBLuminanceSource source=new RGBLuminanceSource(imagem.getWidth (),imagem.getHeight (), pixeis);
        BinaryBitmap mapa=new BinaryBitmap ( new HybridBinarizer( source ) );
        return new QRCodeReader().decode (mapa).getText ();
    }
}
