import java.util.ArrayList;
import javax.swing.*;

class Icon
{
    int Row, Column, red, green, blue;
    ArrayList<ArrayList<Integer>> pixelSquare = new ArrayList<ArrayList<Integer>>();
    ArrayList<Byte> bytes = new ArrayList<Byte>();

    public Icon(){
        Row = 0;
        Column = 0;
    }

    public int getIcon() {
        return 0;
    }
    
    public void setIcon(int row, int col, int r, int g, int b)
    {
        Row = row;
        Column = col;
        red = r;
        green = g;
        blue = b;
    }

    public void takeUserInput()
    {
        String redInput = JOptionPane.showInputDialog("Input you value for red");
        String greenInput = JOptionPane.showInputDialog("Input you value for green");
        String blueInput = JOptionPane.showInputDialog("Input you value for blue");

        red  = Integer.parseInt(redInput);
        green = Integer.parseInt(greenInput);
        blue = Integer.parseInt(blueInput);

        if((red < 0 || red >= 255) && (green < 0 || green >= 255) && (blue < 0 || blue >= 255)){
            JOptionPane.showMessageDialog(null, "Oops! Looks like one of your numbers is not between 0-255\nPlease try again!", "**Detected Incorrect Input**", JOptionPane.PLAIN_MESSAGE);
            takeUserInput();
        }
        else {
            JOptionPane.showMessageDialog(null, "r: " + red + "\ng: " + green + "\nb: " + blue, "Your RGB Inputs for the current pixel", JOptionPane.PLAIN_MESSAGE);
        }
    }

    
    //setPixel(row,col,r,g,b);

    //System.out.println(icon.toString());
}