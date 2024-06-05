/**
 * @author Easton Herbon
 * @version 12/5/2023
 * @brief Vertical Inversion logic and decorator class
 */

public class VerticalInvertBitmapEdit implements BitmapEdit {
    @Override
    public void applyEdit(BitmapEditorGUI gui, Icon icon, int row, int col) {
        Pixel pixel = icon.getPixel(row, col);
        if (pixel != null) {
            int invertedRow = icon.getRowCount() - row - 1;
            gui.updateButtonColor(invertedRow, col, pixel);
        }
    }
}
