import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

/**
 @author Easton Herbon
 @version 10/18/2023

 @brief Class used to handle keeping track of the slider values and button color setting
 */

public class PixelButtonListener implements ActionListener {
    private final BitmapEditorGUI parent;
    private final int row;
    private final int col;

    public PixelButtonListener(BitmapEditorGUI parent, int row, int col) {
        this.parent = parent;
        this.row = row;
        this.col = col;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!parent.advancedCheckBox.isSelected()) {
            int red = parent.redSlider.getValue();
            int green = parent.greenSlider.getValue();
            int blue = parent.blueSlider.getValue();

            parent.icon.setPixel(row, col, red, green, blue);
            Color newColor = new Color(red, green, blue);
            parent.addColorToLastColors(newColor);
            parent.updateLastColorsList();
            parent.updateColorPreview();
        }
        // else portion for advanced mode was supposed to be  set here
        // never made a version that worked

        parent.updateButtonColor(row, col);
    }
}
