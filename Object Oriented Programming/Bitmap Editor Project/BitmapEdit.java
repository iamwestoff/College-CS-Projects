/**
 * @author Easton Herbon
 * @version 12/5/2023
 * @brief Bitmap Edit interface for applying edits for various decorators and observers
 */

public interface BitmapEdit {
    void applyEdit(BitmapEditorGUI gui, Icon icon, int row, int col);
}