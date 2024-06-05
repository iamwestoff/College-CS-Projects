import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Easton Herbon
 * @version 10/18/2023
 * @brief Class used to hande the last color button duplication
 */

public class ColorButtonListener implements ActionListener {
    private final BitmapEditorGUI parent;
    private final Color color;

    public ColorButtonListener(BitmapEditorGUI parent, Color color) {
        this.parent = parent;
        this.color = color;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        parent.redSlider.setValue(color.getRed());
        parent.greenSlider.setValue(color.getGreen());
        parent.blueSlider.setValue(color.getBlue());
        parent.updateColorPreview();
    }

}// end class
