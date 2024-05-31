import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.color.ColorSpace;

public class Imageviewer extends JFrame implements ActionListener {
    JButton buttonChooseImage, buttonShowImage, buttonBrightness, buttonGrayScale, buttonResize, buttonExit;
    JFileChooser fileChooser = new JFileChooser();
    File file;
    JTextField widthTextField = new JTextField();
    JTextField heightTextField = new JTextField();
    JTextField brightnessTextField = new JTextField();
    int w = 800, h = 600;
    float brightenFactor = 1.0f;

    public static void main(String[] args) {

        new Imageviewer().createAndShowGUI();
    }

    public void createAndShowGUI() {

        JFrame frame = new JFrame("Image Viewer");
        frame.setSize(800, 400);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // panel1(holds the main buttons)
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel textLabel = new JLabel("Image Viewer");
        textLabel.setBounds(360, 40, 250, 50);
        panel.add(textLabel);

        buttonChooseImage = new JButton("Choose Image");
        buttonShowImage = new JButton("Show Image");
        buttonBrightness = new JButton("Brightness");
        buttonGrayScale = new JButton("Gray Scale");
        buttonResize = new JButton("Resize");
        buttonExit = new JButton("Exit");

        buttonChooseImage.setBounds(200, 120, 200, 40);
        buttonShowImage.setBounds(400, 120, 200, 40);
        buttonBrightness.setBounds(200, 160, 200, 40);
        buttonGrayScale.setBounds(400, 160, 200, 40);
        buttonResize.setBounds(200, 200, 200, 40);
        buttonExit.setBounds(400, 200, 200, 40);

        panel.add(buttonChooseImage);
        panel.add(buttonShowImage);
        panel.add(buttonBrightness);
        panel.add(buttonGrayScale);
        panel.add(buttonResize);
        panel.add(buttonExit);


        buttonChooseImage.addActionListener(this);
        buttonShowImage.addActionListener(this);
        buttonBrightness.addActionListener(this);
        buttonGrayScale.addActionListener(this);
        buttonResize.addActionListener(this);
        buttonExit.addActionListener(this);


        frame.add(panel);
        frame.setVisible(true);
    }
    //shows if the file has been selected or not
    public void chooseFileImage() {
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
            if (file != null && file.exists()) {
                JOptionPane.showMessageDialog(this, "File selected: " + file.getName());
            } else {
                JOptionPane.showMessageDialog(this, "Failed to select file.");
            }
        }
    }

    public void showOriginalImage() {
        if (file != null && file.exists()) {
            try {
                BufferedImage image = ImageIO.read(file);
                if (image != null) {
                    JLabel picLabel = new JLabel(new ImageIcon(image));
                    JFrame tempFrame = new JFrame("Original Image");
                    tempFrame.add(new JScrollPane(picLabel));
                    tempFrame.pack();
                    tempFrame.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to read image from file.");
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error loading image: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "No file selected.");
        }
    }

    public void grayScaleImage() {
        if (file != null && file.exists()) {
            try {
                BufferedImage image = ImageIO.read(file);
                if (image != null) {
                    BufferedImage grayImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
                    ColorConvertOp op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
                    op.filter(image, grayImage);
                    JLabel picLabel = new JLabel(new ImageIcon(grayImage));
                    JFrame tempFrame = new JFrame("Grayscale Image");
                    tempFrame.add(new JScrollPane(picLabel));
                    tempFrame.pack();
                    tempFrame.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to read image from file.");
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error loading image: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "No file selected.");
        }
    }

    public void showResizeImage(int newWidth, int newHeight) {
        if (file != null && file.exists()) {
            try {
                BufferedImage image = ImageIO.read(file);
                if (image != null) {
                    Image resizedImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
                    BufferedImage bufferedResizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
                    Graphics2D g2d = bufferedResizedImage.createGraphics();
                    g2d.drawImage(resizedImage, 0, 0, null);
                    g2d.dispose();
                    JLabel picLabel = new JLabel(new ImageIcon(bufferedResizedImage));
                    JFrame tempFrame = new JFrame("Resized Image");
                    tempFrame.add(new JScrollPane(picLabel));
                    tempFrame.pack();
                    tempFrame.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to read image from file.");
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error loading image: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "No file selected.");
        }
    }

    public void showBrightnessImage(float brightnessFactor) {
        if (file != null && file.exists()) {
            try {
                BufferedImage image = ImageIO.read(file);
                if (image != null) {
                    RescaleOp rescaleOp = new RescaleOp(brightnessFactor, 0, null);
                    BufferedImage brightenedImage = rescaleOp.filter(image, null);
                    JLabel picLabel = new JLabel(new ImageIcon(brightenedImage));
                    JFrame tempFrame = new JFrame("Brightness Adjusted Image");
                    tempFrame.add(new JScrollPane(picLabel));
                    tempFrame.pack();
                    tempFrame.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to read image from file.");
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error loading image: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "No file selected.");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttonChooseImage) {
            chooseFileImage();
        } else if (e.getSource() == buttonShowImage) {
            showOriginalImage();
        } else if (e.getSource() == buttonBrightness) {
            String input = JOptionPane.showInputDialog("Enter brightness factor (between 0 and 1):");
            if (input != null) {
                try {
                    float brightnessFactor = Float.parseFloat(input);
                    if (brightnessFactor >= 0 && brightnessFactor <= 1) {
                        showBrightnessImage(brightnessFactor);
                    } else {
                        JOptionPane.showMessageDialog(this, "Brightness factor must be between 0 and 1.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid input for brightness factor.");
                }
            }
        } else if (e.getSource() == buttonGrayScale) {
            grayScaleImage();
        } else if (e.getSource() == buttonResize) {
            JPanel resizePanel = new JPanel(new GridLayout(2, 2));
            resizePanel.add(new JLabel("Width:"));
            resizePanel.add(widthTextField);
            resizePanel.add(new JLabel("Height:"));
            resizePanel.add(heightTextField);
            int result = JOptionPane.showConfirmDialog(null, resizePanel, "Resize Image", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    int newWidth = Integer.parseInt(widthTextField.getText());
                    int newHeight = Integer.parseInt(heightTextField.getText());
                    showResizeImage(newWidth, newHeight);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid input for width or height.");
                }
            }
        } else if (e.getSource() == buttonExit) {
            System.exit(0);
        }
    }
}
