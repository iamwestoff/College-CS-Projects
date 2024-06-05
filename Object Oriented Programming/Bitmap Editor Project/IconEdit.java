import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Easton Herbon
 * @version 12/5/2023
 * @brief IconEdit class used to handle adding and removing various decorators and observers,
 * and handling certain things to edit icons as well.
 */

public class IconEdit {
    private final List<BitmapEditorGUI> observers;
    private final HashMap<String, BitmapEdit> decorators;
    private EditInfo lastEditInfo;
    private final Icon icon;

    public IconEdit(Icon icon) {
        this.icon = icon;
        observers = new ArrayList<>();
        decorators = new HashMap<>();
    }

    public void addDecorator(int row, int col, BitmapEdit decorator) {
        String key = row + "-" + col;
        decorators.put(key, decorator);
    }

    public void addObserver(BitmapEditorGUI observer) {
        observers.add(observer);
        if (lastEditInfo != null) {
            observer.updateFromSubject(lastEditInfo);
        }
    }

    public void removeObserver(BitmapEditorGUI observer) {
        observers.remove(observer);
    }

    public void notifyObservers(int row, int col, Pixel pixel) {
        lastEditInfo = new EditInfo(row, col, pixel.getRed(), pixel.getGreen(), pixel.getBlue(), pixel);

        for (BitmapEditorGUI observer : observers) {
            observer.updateFromSubject(lastEditInfo);
        }
    }

    public void detachObserver(BitmapEditorGUI observer) {
        removeObserver(observer);
    }

    public void reattachObserver(BitmapEditorGUI observer) {
        addObserver(observer);
        if (lastEditInfo != null) {
            observer.updateFromSubject(lastEditInfo);
        }
    }

    public void applyDecorator(int row, int col, BitmapEdit decorator, BitmapEditorGUI gui) {
        String key = row + "-" + col;
        decorators.put(key, decorator);

        decorator.applyEdit(gui, icon, row, col);

        Pixel pixel = icon.getPixel(row, col);
        notifyObservers(row, col, pixel);
    }

    public static class EditInfo {
        int row;
        int col;
        int red;
        int green;
        int blue;
        Pixel pixel;

        public EditInfo(int row, int col, int red, int green, int blue, Pixel pixel) {
            this.row = row;
            this.col = col;
            this.red = red;
            this.green = green;
            this.blue = blue;
            this.pixel = pixel;
        }
    }
}
