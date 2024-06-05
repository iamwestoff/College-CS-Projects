/**
 @author Easton Herbon
 @version 9/11/2023
 */

import java.io.*;
import java.util.ArrayList;

/*
TODO
    40x40 default constructor -- DONE
    constructor -> rows and columns -- DONE
    setters and getters -- DONE
    toString -- DONE
    createBitmapFile functionality -- DONE
 */

public class Icon {
    private ArrayList<ArrayList<Pixel>> pixelSquare;

    // constructor for a default 40x40 Icon
    public Icon() {
        this(40, 40);
    }

    // constructor used to create the icon with rows and columns
    public Icon(int rows, int cols) {
        pixelSquare = new ArrayList<>(rows);
        for (int i = 0; i < rows; i++) {
            ArrayList<Pixel> row = new ArrayList<>(cols);
            for (int j = 0; j < cols; j++) {
                row.add(new Pixel());
            }
            pixelSquare.add(row);
        }
    }

    // getPixel to find r and c
    public Pixel getPixel(int row, int column) {
        if (row >= 0 && row < pixelSquare.size() && column >= 0 && column < pixelSquare.get(0).size()) {
            return pixelSquare.get(row).get(column);
        }
        return null;
    }

    // setter to set at specific rows and cols
    public void setPixel(int row, int column, int red, int green, int blue) {
        if (row >= 0 && row < pixelSquare.size() && column >= 0 && column < pixelSquare.get(0).size()) {
            Pixel pixel = pixelSquare.get(row).get(column);
            pixel.setRed(red);
            pixel.setGreen(green);
            pixel.setBlue(blue);
        }
    }

    // toString method
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (ArrayList<Pixel> row : pixelSquare) {
            for (Pixel pixel : row) {
                sb.append(pixel.toStringHEX()).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    // method used to create the bitmap file by utilizing bmp file formatting and little endian arithmetic
    public void createBitmapFile(String filename) {
        try {
            FileOutputStream fos = new FileOutputStream(filename);
            BufferedOutputStream bos = new BufferedOutputStream(fos);

            // header - 14 bytes
            // bm type header
            bos.write("BM".getBytes());

            // file size - 4 bytes
            int fileSize = 54 + (pixelSquare.size() * pixelSquare.get(0).size() * 3); // 54 bytes for header, 3 bytes per pixel
            writeIntLittleEndian(bos, fileSize);
            // reserved
            writeIntLittleEndian(bos, 0);
            // offset
            writeIntLittleEndian(bos, 54);
            // dib header
            // Header Size (4 bytes)
            writeIntLittleEndian(bos, 40);
            // width & height - 4x4 bytes
            writeIntLittleEndian(bos, pixelSquare.get(0).size());
            writeIntLittleEndian(bos, pixelSquare.size());
            // planes
            writeShortLittleEndian(bos, 1);
            // bits -> pixel
            writeShortLittleEndian(bos, 24);
            // compression
            writeIntLittleEndian(bos, 0);
            // size of image
            int imageSize = pixelSquare.size() * pixelSquare.get(0).size() * 3;
            writeIntLittleEndian(bos, imageSize);
            // resolution of image (width and height)
            writeIntLittleEndian(bos, 2835);
            writeIntLittleEndian(bos, 2835);
            // colors
            writeIntLittleEndian(bos, 0);
            // important colors
            writeIntLittleEndian(bos, 0);

            // data for pixel
            for (int i = pixelSquare.size() - 1; i >= 0; i--) {
                for (Pixel pixel : pixelSquare.get(i)) {
                    bos.write(pixel.getBlue());
                    bos.write(pixel.getGreen());
                    bos.write(pixel.getRed());
                }
                // row padding in multiple of 4 bytes
                int paddingSize = (4 - (pixelSquare.get(0).size() * 3) % 4) % 4;
                for (int p = 0; p < paddingSize; p++) {
                    bos.write(0);
                }
            }

            bos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // helper method for integers in little-endian order
    private void writeIntLittleEndian(OutputStream os, int value) throws IOException {
        os.write(value);
        os.write(value >> 8);
        os.write(value >> 16);
        os.write(value >> 24);
    }

    // helper method for shorts in little-endian order
    private void writeShortLittleEndian(OutputStream os, int value) throws IOException {
        os.write(value);
        os.write(value >> 8);
    }

}// end class