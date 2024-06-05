import java.awt.*;

/**
 * @author Easton Herbon
 * @version 12/5/2023
 * @brief DualInverseDecorator class used to accommodate for when both horizonal and vertical are checked
 */

public class DualInverseDecorator implements BitmapEdit{
    @Override
    public void applyEdit(BitmapEditorGUI gui, Icon icon, int row, int col) {
        Pixel pixel = icon.getPixel(row, col);
        Color currentColor = gui.getButtonColor(row, col);
        Pixel temp = new Pixel(currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue());

        if (pixel != null) {
            int invertedCol = icon.getColumnCount() - col - 1;
            int invertedRow = icon.getRowCount() - row - 1;

            gui.updateButtonColor(invertedRow, invertedCol, pixel);
            gui.updateButtonColor(row, col, temp);
        }
    }
}
