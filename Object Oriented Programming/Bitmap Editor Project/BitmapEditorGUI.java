import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * @author Easton Herbon
 * @version 10/18/2023
 * @brief Class used to handle the creation of the entire GUI for the project
 */

public class BitmapEditorGUI {
    Icon icon;
    IconEdit iconEdit;
    JButton[][] pixelButtons;
    ArrayList<Color> lastColors;
    JSlider redSlider;
    JSlider greenSlider;
    JSlider blueSlider;
    JCheckBox verticalInvertCheckbox;
    JCheckBox horizontalInvertCheckBox;
    JCheckBox randomColorCheckBox;

    private int currentRow;
    private int currentCol;
    private final JLabel colorPreviewLabel;
    private final JButton[] colorButtons;
    private Boolean attached = true;

    public BitmapEditorGUI(int rows, int cols) {
        icon = new Icon(rows, cols);
        iconEdit = new IconEdit(icon);

        lastColors = new ArrayList<>();
        pixelButtons = new JButton[rows][cols];
        colorButtons = new JButton[5];

        JFrame frame = new JFrame("Bitmap Editor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel gridPanel = new JPanel(new GridLayout(rows, cols));
        JPanel colorChooserPanel = new JPanel(new GridLayout(0, 1));
        JPanel lastColorsPanel = new JPanel(new GridLayout(1, 5));
        redSlider = new JSlider(0, 255);
        greenSlider = new JSlider(0, 255);
        blueSlider = new JSlider(0, 255);
        colorPreviewLabel = new JLabel();
        JButton createBitmapButton = new JButton("Create Bitmap");

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(30, 30));
                button.addActionListener(new PixelButtonListener(this, row, col));
                button.addMouseListener(new PixelButtonListener(this, row, col));
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

        createBitmapButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showSaveDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                String filename = fileChooser.getSelectedFile().getAbsolutePath();
                icon.createBitmapFile(filename);
            }
        });

        // update -- check boxes
        verticalInvertCheckbox = new JCheckBox("Vertical Invert");
        horizontalInvertCheckBox = new JCheckBox("Horizontal Invert");
        randomColorCheckBox = new JCheckBox("Random Color");


        JPanel checkBoxPanel = new JPanel();
        checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.X_AXIS));
        checkBoxPanel.add(verticalInvertCheckbox);
        checkBoxPanel.add(horizontalInvertCheckBox);
        checkBoxPanel.add(randomColorCheckBox);

        JButton applyEditsButton = new JButton("Apply Edits");
        applyEditsButton.addActionListener(e -> {
            applyEdits();
        });

        JButton detachButton = new JButton("Detach");
        detachButton.addActionListener(e -> {
            detachObserver();
        });

        JButton reattachButton = new JButton("Reattach");
        reattachButton.addActionListener(e -> {
            reattachObserver();
        });

        JButton openNewEditorButton = new JButton("Open New Editor");
        openNewEditorButton.addActionListener(e -> openNewEditor());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.add(applyEditsButton);
        buttonPanel.add(detachButton);
        buttonPanel.add(reattachButton);
        buttonPanel.add(openNewEditorButton);

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
        frame.add(checkBoxPanel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.WEST);

        frame.pack();
        frame.setVisible(true);
    } // end method

    public void setCurrentRow(int row){
        this.currentRow = row;
    }

    public void setCurrentCol(int col){
        this.currentCol = col;
    }

    private void openNewEditor() {
        BitmapEditorGUI newEditor = new BitmapEditorGUI(icon.getRowCount(), icon.getColumnCount());
        iconEdit.addObserver(newEditor);
        newEditor.iconEdit.reattachObserver(newEditor);
    }

    void applyEdits() {
        int row = currentRow;
        int col = currentCol;

        iconEdit.notifyObservers(row, col, icon.getPixel(row, col));

        if(!horizontalInvertCheckBox.isSelected()){
            updateButtonColor(row, col, icon.getPixel(row, col));
        }
        if(!verticalInvertCheckbox.isSelected()){
            verticalInvertCheckbox.isSelected();
        }
    }


    public void updateFromSubject(IconEdit.EditInfo editInfo) {
        int row = editInfo.row;
        int col = editInfo.col;

        icon.setPixel(row, col, editInfo.red, editInfo.green, editInfo.blue);
        updateButtonColor(row, col, icon.getPixel(row, col));
    }

    private void detachObserver() {
        if (attached) {
            iconEdit.detachObserver(this);
            attached = false;
        }
    }

    private void reattachObserver() {
        if (!attached) {
            iconEdit.reattachObserver(this);
            attached = true;
        }
    }

    public Color getButtonColor(int row, int col) {
        Pixel pixel = icon.getPixel(row, col);
        if (pixel != null) {
            return new Color(pixel.getRed(), pixel.getGreen(), pixel.getBlue());
        } else {
            return null;
        }
    }

    // all needed to be public for pixelButtonListener
    public void updateButtonColor(int row, int col, Pixel pixel) {
        Color color = new Color(pixel.getRed(), pixel.getGreen(), pixel.getBlue());
        pixelButtons[row][col].setBackground(color);
    }


    public void updateColorPreview() {
        int red = redSlider.getValue();
        int green = greenSlider.getValue();
        int blue = blueSlider.getValue();
        colorPreviewLabel.setBackground(new Color(red, green, blue));
        colorPreviewLabel.setOpaque(true);
    } // end method

    public void updateLastColorsList() {
        for (int i = 0; i < lastColors.size(); i++) {
            Color color = lastColors.get(i);
            colorButtons[i].setBackground(color);
            colorButtons[i].addActionListener(new ColorButtonListener(this, color));
        }
        for (int i = lastColors.size(); i < 5; i++) {
            colorButtons[i].setBackground(Color.WHITE);
        }
    }// end method

    public void addColorToLastColors(Color color) {
        if (!lastColors.contains(color)) {
            if (lastColors.size() >= 5) {
                lastColors.remove(0);
            }
            lastColors.add(color);
        }
    } // end method
}// end class