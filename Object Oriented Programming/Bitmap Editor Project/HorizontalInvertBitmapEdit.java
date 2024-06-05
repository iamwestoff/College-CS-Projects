/**
 * @author Easton Herbon
 * @version 12/5/2023
 * @brief Horizontal Inversion logic and decorator class
 */

public class HorizontalInvertBitmapEdit implements BitmapEdit {
    @Override
    public void applyEdit(BitmapEditorGUI gui, Icon icon, int row, int col) {
        Pixel pixel = icon.getPixel(row, col);
        if (pixel != null) {
            int invertedCol = icon.getColumnCount() - col - 1;
            gui.updateButtonColor(row, invertedCol, pixel);
        }
    }
}