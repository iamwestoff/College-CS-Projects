import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 @author Easton Herbon
 @version 10/18/2023

 @brief Class used to handle the creation of the entire GUI for the project
 */

public class BitmapEditorGUI {
    final Icon icon;
    final JButton[][] pixelButtons;
    final ArrayList<Color> lastColors;
    final JSlider redSlider;
    final JSlider greenSlider;
    final JSlider blueSlider;
    final JCheckBox advancedCheckBox;
    private final JLabel colorPreviewLabel;
    private final JButton[] colorButtons;
    private final JFrame frame;


    public BitmapEditorGUI(int rows, int cols) {
        icon = new Icon(rows, cols);
        lastColors = new ArrayList<>();
        pixelButtons = new JButton[rows][cols];
        colorButtons = new JButton[5];

        JFrame frame = new JFrame("Bitmap Editor");
        this.frame = frame;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel gridPanel = new JPanel(new GridLayout(rows, cols));
        JPanel colorChooserPanel = new JPanel(new GridLayout(0, 1));
        JPanel lastColorsPanel = new JPanel(new GridLayout(1, 5));
        redSlider = new JSlider(0, 255);
        greenSlider = new JSlider(0, 255);
        blueSlider = new JSlider(0, 255);
        colorPreviewLabel = new JLabel();
        JButton createBitmapButton = new JButton("Create Bitmap");
        advancedCheckBox = new JCheckBox("Advanced");

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(30, 30));
                button.addActionListener(new PixelButtonListener(this, row, col));
                pixelButtons[row][col] = button;
                gridPanel.add(button);
            }
        }

        for (int i = 0; i < 5; i++) {
            colorButtons[i] = new JButton();
            colorButtons[i].setPreferredSize(new Dimension(20, 20));
            colorButtons[i].setBackground(Color.WHITE);
            lastColorsPanel.add(colorButtons[i]);
        }

        // TODO -- change lambda change listeners to the old ones
        createBitmapButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showSaveDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                String filename = fileChooser.getSelectedFile().getAbsolutePath();
                icon.createBitmapFile(filename);
            }
        });

        advancedCheckBox.addActionListener(e -> {
            if (advancedCheckBox.isSelected()) {
                openAdvancedDialog();
            } else {
                advancedCheckBox.setSelected(false);
            }
        });

        // attempted to reimport what i had before, but it would not work with what i have now
        /*
        redSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                updateColorPreview();
            }
        });

        this is what i had before and the reimplementation would not work
         */
        redSlider.addChangeListener(e -> updateColorPreview());

        greenSlider.addChangeListener(e -> updateColorPreview());

        blueSlider.addChangeListener(e -> updateColorPreview());

        colorChooserPanel.add(new JLabel("Red"));
        colorChooserPanel.add(redSlider);
        colorChooserPanel.add(new JLabel("Green"));
        colorChooserPanel.add(greenSlider);
        colorChooserPanel.add(new JLabel("Blue"));
        colorChooserPanel.add(blueSlider);

        colorPreviewLabel.setPreferredSize(new Dimension(20, 20));
        colorChooserPanel.add(colorPreviewLabel);

        lastColorsPanel.setBorder(BorderFactory.createTitledBorder("Last Five Colors"));
        colorChooserPanel.add(lastColorsPanel);

        frame.setLayout(new BorderLayout());
        frame.add(gridPanel, BorderLayout.CENTER);
        frame.add(colorChooserPanel, BorderLayout.EAST);
        frame.add(createBitmapButton, BorderLayout.SOUTH);
        frame.add(advancedCheckBox, BorderLayout.NORTH);

        frame.pack();
        frame.setVisible(true);
    }

    // all needed to be public for pixelButtonListener
    public void updateButtonColor(int row, int col) {
        Pixel pixel = icon.getPixel(row, col);
        Color color = new Color(pixel.getRed(), pixel.getGreen(), pixel.getBlue());
        pixelButtons[row][col].setBackground(color);
    }

    public void updateColorPreview() {
        int red = redSlider.getValue();
        int green = greenSlider.getValue();
        int blue = blueSlider.getValue();
        colorPreviewLabel.setBackground(new Color(red, green, blue));
        colorPreviewLabel.setOpaque(true);
    }

    public void updateLastColorsList() {
        for (int i = 0; i < lastColors.size(); i++) {
            Color color = lastColors.get(i);
            colorButtons[i].setBackground(color);
            colorButtons[i].addActionListener(new ColorButtonListener(this, color));
        }
        for (int i = lastColors.size(); i < 5; i++) {
            colorButtons[i].setBackground(Color.WHITE);
        }
    }

    public void addColorToLastColors(Color color) {
        if (!lastColors.contains(color)) {
            if (lastColors.size() >= 5) {
                lastColors.remove(0); // Remove the oldest color if the list is full
            }
            lastColors.add(color);
        }
    }


    // TODO -- never finished BUT they are able to import the bitmap (no preview)
    private void openAdvancedDialog() {
        JPanel dialogPanel = new JPanel(new GridLayout(3, 1));

        int numRows = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of rows to fill:"));
        int numCols = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of columns to fill:"));

        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            String filename = fileChooser.getSelectedFile().getAbsolutePath();
            openBitmapAndFill(numRows, numCols, filename, frame);        }

        // Add the dialog panel to the frame
        frame.getContentPane().add(dialogPanel);
        frame.pack();
    }

    private void openBitmapAndFill(int numRows, int numCols, String filename, JFrame frame) {
        try {
            // Load the selected bitmap image
            BufferedImage image = ImageIO.read(new File(filename));

            // Get the dimensions of the loaded image
            int imageWidth = image.getWidth();
            int imageHeight = image.getHeight();

            // Get the RGB values from the image and fill the Icon
            for (int row = 0; row < numRows; row++) {
                for (int col = 0; col < numCols; col++) {
                    if (row < imageHeight && col < imageWidth) {
                        int rgb = image.getRGB(col, row);
                        int red = (rgb >> 16) & 0xFF;
                        int green = (rgb >> 8) & 0xFF;
                        int blue = rgb & 0xFF;
                        icon.setPixel(row, col, red, green, blue);
                    }
                }
            }

            // Update the pixel buttons and color preview
            for (int row = 0; row < numRows; row++) {
                for (int col = 0; col < numCols; col++) {
                    updateButtonColor(row, col);
                }
            }
            updateColorPreview();
        } catch (IOException e) {
            // Handle any errors related to reading the image
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error reading the selected bitmap image.");
        }
    }
}
