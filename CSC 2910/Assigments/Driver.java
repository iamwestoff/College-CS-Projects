import javax.swing.*;
import javax.swing.*;

public class Driver{
    public static void main(String [] args)
    {
        JOptionPane.showMessageDialog(null, "Welcome to the RGB Pixel Icon Assignment", "Created by: Easton Herbon", JOptionPane.PLAIN_MESSAGE);

        Icon icon = new Icon(5, 5);

        icon.takeUserInput();
        //icon.setPixel(0, 0, 255, 0, 0);
        //icon.setPixel(0, 4, 0, 255, 0);
        //icon.setPixel(0, 0, 255, 0, 0);
        //icon.setPixel(0, 0, 255, 0, 0);

        //icon.createBitmapFile();
        System.out.println(icon);
    } 
}
