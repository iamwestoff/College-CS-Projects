import java.awt.*;
import java.util.Random;

/**
 * @author Easton Herbon
 * @version 12/5/2023
 * @brief Random color decorator generator class
 */

public class RandomColorBitmapEdit implements BitmapEdit {
    private final Random random = new Random();

    @Override
    public void applyEdit(BitmapEditorGUI gui, Icon icon, int row, int col) {
        int randomRedVar = random.nextInt(256);
        int randomGreenVar = random.nextInt(256);
        int randomBlueVar = random.nextInt(256);

        icon.setPixel(row, col, randomRedVar, randomGreenVar, randomBlueVar);

        gui.addColorToLastColors(new Color(randomRedVar, randomGreenVar, randomBlueVar));
        gui.updateLastColorsList();
    }
}
