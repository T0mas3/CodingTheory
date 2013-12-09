package lt.tomas.bareikis.GUI;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class ImagePreviewWindow extends JFrame {

    private JPanel rootJPanel;
    private JLabel originalImageLabel;
    private JLabel transferedUncodedImageLabel;

    public ImagePreviewWindow() {
        super("Paveikslėlių peržiūra");

        rootJPanel.setPreferredSize(new Dimension(700, 600));

        this.setContentPane(rootJPanel);
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.setVisible(true);
    }

    public void showOriginalImage(Image image) {

        if (image != null) {
            final ImageIcon originalImageIcon = new ImageIcon(image);
            SwingUtilities.invokeLater(new Runnable()
            {
                public void run()
                {
                    originalImageLabel.setIcon(originalImageIcon);
                }
            });
        }
    }

    public void showTransferredUncodedImage(String transferredFullImageByteString, String fileExtension) {

        try {
            byte[] bytes = Helper.transformBitStringToBitArray(transferredFullImageByteString);

            FileOutputStream fileOutputStream = new FileOutputStream("temp." + fileExtension);
            fileOutputStream.write(bytes);
            fileOutputStream.close();

            File transferredUncodedBufferedImageFile = new File("temp." + fileExtension);
            BufferedImage transferredUncodedBufferedImage = ImageIO.read(transferredUncodedBufferedImageFile);

            final ImageIcon transferredUncodedImage = new ImageIcon(transferredUncodedBufferedImage);
            SwingUtilities.invokeLater(new Runnable()
            {
                public void run()
                {
                    transferedUncodedImageLabel.setIcon(transferredUncodedImage);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
