import javax.swing.*;

/**
 * @author Easton Herbon
 * @version 10/18/2023
 */

public class Driver {
    public static void main(String[] args) {
        int rows = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of rows:"));
        int cols = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of columns:"));
        SwingUtilities.invokeLater(() -> new BitmapEditorGUI(rows, cols));
    }
}