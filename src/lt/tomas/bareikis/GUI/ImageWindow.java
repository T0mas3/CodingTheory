package lt.tomas.bareikis.GUI;


import lt.tomas.bareikis.GUI.customComponents.ProbabilityJTextField;
import lt.tomas.bareikis.GUI.customComponents.StringInputJTextArea;
import lt.tomas.bareikis.GUI.customComponents.VectorInputJTextArea;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageWindow extends JFrame {

    private JPanel rootJPanel;
    private JButton chooseImageButton;
    private JButton encodeButton;
    private VectorInputJTextArea encodedTextArea;
    private VectorInputJTextArea inputImageTextArea;
    private ProbabilityJTextField errorProbabilityTextField;
    private JButton sendChannelButton;
    private VectorInputJTextArea transferedVectorTextArea;
    private StringInputJTextArea transferedUncodedImageTextArea;
    private JButton decodeButton;
    private VectorInputJTextArea decodedImageVectorTextArea;

    private String originalFilePath;
    private BufferedImage originalBufferedImage;
    private String fullImageBitString;

    private ImagePreviewWindow imagePreviewWindow;
    private String encodedFullImageByteString;
    private String transferedFullEncodedImageByteString;
    private String decodedFullImageString;
    private String fileExtension;
    private File originalBufferedImageFile;
    private String transferedFullIUncodedImageByteString;

    public ImageWindow() {
        super("Paveikslėlio siuntimas");

        rootJPanel.setPreferredSize(new Dimension(700, 600));

        this.setContentPane(rootJPanel);
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.setVisible(true);

        chooseImageButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int fileChooseResult = fileChooser.showOpenDialog(ImageWindow.this);

                if (fileChooseResult == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    ImageWindow.this.originalFilePath = file.getAbsoluteFile().getPath();
                    ImageWindow.this.originalBufferedImage = null;

                    try {
                        originalBufferedImageFile = new File(originalFilePath);
                        originalBufferedImage = ImageIO.read(originalBufferedImageFile);

                        if (imagePreviewWindow == null) {
                            imagePreviewWindow = new ImagePreviewWindow();
                        }

                        imagePreviewWindow.showOriginalImage(ImageWindow.this.originalBufferedImage);

                        int extensionIndex = ImageWindow.this.originalFilePath.lastIndexOf(".");
                        fileExtension = ImageWindow.this.originalFilePath.substring(extensionIndex+1);
                        if (extensionIndex > 0) {

                            try {
                                fullImageBitString = Helper.transformImageFileToBitString(ImageWindow.this.originalBufferedImageFile, fileExtension);
                                System.out.println(fullImageBitString.length());
                                SwingUtilities.invokeLater(new Runnable()
                                {
                                    public void run()
                                    {
                                        ImageWindow.this.inputImageTextArea.setText(fullImageBitString.substring(0, 1000));
                                    }
                                });

                            } catch (IllegalArgumentException ex) {
                                JOptionPane.showMessageDialog(null, ex.getMessage());
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Pasirinktas netinkamas failas");
                        }


                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        encodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ImageWindow.this.encodedFullImageByteString = Helper.encodeVectorString(
                        ImageWindow.this.fullImageBitString
                );

                ImageWindow.this.encodedTextArea.setText(
                        Helper.encodeVectorString(ImageWindow.this.inputImageTextArea.getText())
                );
            }
        });

        sendChannelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                float errorProbability = ImageWindow.this.errorProbabilityTextField.getErrorProbability();

                ImageWindow.this.transferedFullEncodedImageByteString = Helper.transferVectorString(
                        ImageWindow.this.encodedFullImageByteString,
                        errorProbability
                );

                transferedFullIUncodedImageByteString = Helper.transferVectorString(
                        fullImageBitString.substring(0, 64),
                        0
                );

                transferedFullIUncodedImageByteString += Helper.transferVectorString(
                        fullImageBitString.substring(64, fullImageBitString.length()),
                        errorProbability
                );

                ImageWindow.this.transferedVectorTextArea.setText(
                        Helper.transferVectorString(
                                ImageWindow.this.encodedTextArea.getText(),
                                errorProbability
                        )
                );

                ImageWindow.this.transferedUncodedImageTextArea.setText(
                        Helper.transferVectorString(
                                ImageWindow.this.inputImageTextArea.getText(),
                                errorProbability
                        )
                );

                if (imagePreviewWindow == null) {
                    imagePreviewWindow = new ImagePreviewWindow();
                }

                System.out.println(transferedFullIUncodedImageByteString);
                imagePreviewWindow.showTransferredUncodedImage(ImageWindow.this.transferedFullIUncodedImageByteString, fileExtension);


            }
        });

        decodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ImageWindow.this.transferedVectorTextArea.isInputValid()) {

                    ImageWindow.this.decodedFullImageString =  Helper.decodeVectorString(
                            ImageWindow.this.transferedFullEncodedImageByteString
                    );

                    ImageWindow.this.decodedImageVectorTextArea.setText(
                            Helper.decodeVectorString(ImageWindow.this.transferedVectorTextArea.getText())
                    );
                } else {
                    ImageWindow.this.transferedVectorTextArea.showValidationError();
                }
            }
        });
    }
}
