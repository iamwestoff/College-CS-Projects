/**
 * @author Easton Herbon
 * @version 9/11/2023
 */

/*
TODO
    modify setter methods -- DONE
    modify getter methods -- DONE
    toStringHex -- DONE
 */

public class Pixel {
    private int rgb;

    // constructor to set our rgb value
    public Pixel() {
        this.rgb = 0;
    }

    // separate constructor to set red, green, and blue values
    public Pixel(int red, int green, int blue) {
        setRed(red);
        setGreen(green);
        setBlue(blue);
    }

    // used 0xFF because it would fix the issue in toStringHex printing too much
    // getter red
    public int getRed() {
        return (rgb >> 16) & 0xFF;
    }

    // getter green
    public int getGreen() {
        return (rgb >> 8) & 0xFF;
    }

    // getter blue
    public int getBlue() {
        return rgb & 0xFF;
    }

    // setter red
    public void setRed(int r) {
        if (r >= 0 && r <= 256) {
            int redMask = 0b11111111000000001111111111111111;
            rgb = (rgb & redMask) + (r << 16);
        }
    }

    // setter green
    public void setGreen(int g) {
        if (g >= 0 && g <= 256) {
            int greenMask = 0b11111111111111110000000011111111;
            rgb = (rgb & greenMask) + (g << 8);
        }
    }

    // setter blue
    public void setBlue(int b) {
        if (b >= 0 && b <= 256) {
            int blueMask = 0b11111111111111111111111100000000;
            rgb = (rgb & blueMask) + b;
        }
    }

    // toStringHex used to convert color values to hexadecimal and print
    public String toStringHEX() {
        return String.format("#%02X%02X%02X", getRed(), getGreen(), getBlue());
    }

}// end class