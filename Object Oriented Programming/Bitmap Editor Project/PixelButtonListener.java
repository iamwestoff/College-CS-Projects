import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


/**
 * @author Easton Herbon
 * @version 10/18/2023
 *
 * @brief Class used to handle keeping track of the slider values and button color setting
 */

public class PixelButtonListener implements ActionListener, MouseListener {
    private final BitmapEditorGUI gui;
    private int row;
    private int col;

    public PixelButtonListener(BitmapEditorGUI gui, int row, int col) {
        this.gui = gui;
        this.row = row;
        this.col = col;
    }

    // update -- mouse listener update
    @Override
    public void mouseClicked(MouseEvent e) {
    } //do nothing

    @Override
    public void mousePressed(MouseEvent e) {
    } //do nothing

    public void mouseReleased(MouseEvent e) {
        gui.setCurrentRow(row);
        gui.setCurrentCol(col);

        if (gui.verticalInvertCheckbox.isSelected() && gui.horizontalInvertCheckBox.isSelected()) {
            applyDecoratorIfNeeded(row, col, new DualInverseDecorator(), gui);
        } else {
            if (gui.verticalInvertCheckbox.isSelected()) {
                applyDecoratorIfNeeded(row, col, new VerticalInvertBitmapEdit(), gui);
            }

            if (gui.horizontalInvertCheckBox.isSelected()) {
                applyDecoratorIfNeeded(row, col, new HorizontalInvertBitmapEdit(), gui);
            }
        }

        if (gui.randomColorCheckBox.isSelected()) {
            applyDecoratorIfNeeded(row, col, new RandomColorBitmapEdit(), gui);
        } else {
            if (!(gui.verticalInvertCheckbox.isSelected() || gui.horizontalInvertCheckBox.isSelected())) {
                int red = gui.redSlider.getValue();
                int green = gui.greenSlider.getValue();
                int blue = gui.blueSlider.getValue();

                gui.icon.setPixel(row, col, red, green, blue);
                Color newColor = new Color(red, green, blue);
                gui.addColorToLastColors(newColor);
                gui.updateLastColorsList();
                gui.updateColorPreview();
            }
        }

        gui.applyEdits();
    }

    private void applyDecoratorIfNeeded(int row, int col, BitmapEdit decorator, BitmapEditorGUI gui) {
        gui.iconEdit.applyDecorator(row, col, decorator, gui);
        gui.iconEdit.addDecorator(row, col, decorator);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.isShiftDown()) {
            handleShiftMouseEntered();
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
    } //do nothing

    private void handleShiftMouseEntered() {
        int red = gui.redSlider.getValue();
        int green = gui.greenSlider.getValue();
        int blue = gui.blueSlider.getValue();

        gui.icon.setPixel(row, col, red, green, blue);
        Color newColor = new Color(red, green, blue);
        gui.addColorToLastColors(newColor);
        gui.updateLastColorsList();
        gui.updateColorPreview();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int red = gui.redSlider.getValue();
        int green = gui.greenSlider.getValue();
        int blue = gui.blueSlider.getValue();

        if (gui.randomColorCheckBox.isSelected()) {
            applyDecoratorIfNeeded(row, col, new RandomColorBitmapEdit(), gui);
        } else {
            gui.icon.setPixel(row, col, red, green, blue);
            Color newColor = new Color(red, green, blue);
            gui.addColorToLastColors(newColor);
            gui.updateLastColorsList();
            gui.updateColorPreview();
        }
    }
}// end class