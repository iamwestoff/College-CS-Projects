/**
 @author Easton Herbon
 @version 9/11/2023
 */

public class Driver {
    public static void main(String[] args) {
        Icon icon = new Icon(5, 5);

        //set pixels with different colors
        icon.setPixel(0, 0, 255, 0, 0); // Red
        icon.setPixel(0, 4, 0, 255, 0); // Green
        icon.setPixel(4, 0, 0, 0, 255); // Blue
        icon.setPixel(4, 4, 0, 0, 0);// Black

        //print hex values of pixels
        System.out.println(icon);

        //generate bitmap image
        icon.createBitmapFile("icon.bmp");
    }
}