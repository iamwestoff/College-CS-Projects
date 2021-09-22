/**
 * @author Easton Herbon
 * @version 9/14/21
 * 
 * Lab 4, Pixel java class using arrayLists
 */

class Pixel {
    private int red;
    private int green;
    private int blue;
    
    public static void main(String [] args){

    }

    public int getRed(){
        return red;
    }

    public int getGreen(){
        return green;
    }

    public int getBlue(){
        return blue;
    }

    public void setRed(int redPixel){
        red = redPixel;
    }

    public void setGreen(int greenPixel){
        green = greenPixel;
    }

    public void setBlue(int bluePixel){
        blue = bluePixel;
    }

    public String toString(){
        return ("Stuff and things");
    }
}

//class Icon{
//    Icon icon = new Icon();
//    icon.set(2,2,100,150,200);
//    System.out.println(icon.toString());
//}
