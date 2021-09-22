//import java.util.*;
//import java.util.ArrayList;
//import javax.swing.*;

/**
 * @author Easton Herbon
 * @version 9/16/21
 * 
 * Pixel java class using arrayLists, Assignment 1
 */

class Pixel {
    //initialized variables
    private int red;
    private int green;
    private int blue;
    private int rgb = 255;

    //red = (x & redMask) >> 16;

    //getter methods
    public int getRed(){
        return red;
    }//end getRed

    public int getGreen(){
        return green;
    }//end getGreen

    public int getBlue(){
        return blue;
    }//end getBlue

    //setter methods
        //following setter values should range from 0-255
    public void setRed(int r){
        red = r;
    }//end setRed

    public void setGreen(int g){
        green = g;
    }//end setGreen

    public void setBlue(int b){
        blue = b;
    }//end setBlue

    
    public String toStringHEX(){
        return ("#" + Integer.toHexString(rgb));
    }//end toStringHex
}
