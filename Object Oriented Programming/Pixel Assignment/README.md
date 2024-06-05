## Assignment Description
Programming assignment 1 asks you to write a program to create 24-bit bitmap images. You will start by creating a Pixel class. A Pixel represents a single dot of color on a computer screen. The color is determined by a combination of 3 values representing the intensity of Red, Green, and Blue (called an RGB value).

The range of values for each the colors is from 0-255 (where 0 represents no amount of color and 255 represents the most intense amount of color). Since Java has 32 bit integers a single int can and should represent all three values (you must use a single int to represent three groups of eight bits- one group for each color). The first eight bits will represent the amount of Red, the second eight the amount of Green, the third eight will represent the amount of Blue, and the last group of eight bits will be unused and should always be 0's.

The Pixel class should allow you to get and set each of the intensity levels by passing in an int. For example, your class should have the following methods:

``` C++
public void setRed(int r) //r should be between 0 - 255
public int getRed() //will return between 0 - 255 
public void setGreen(int g) //g should be between 0 - 255
public int getGreen() //will return between 0 -255
public void setBlue(int b) //b should be between 0 - 255
public int getBlue() //will return between 0 - 255
```

Do not allow the user to set a value outside the bounds of 0-255. You must manipulate the values to store the intensity values in the 32 bits that are stored for the RGB value. You can manipulate the bits by shifting using the shift operators << and >> or using bitwise AND (&) and bitwise OR (|).

Your class must also provide a toStringHex() method that returns a string with the hexadecimal representation in the form of #RRGGBB.

Next, create an Icon class that is a 2D collection of Pixels (use ArrayLists).

The Icon class should have two constructors. The first is the default constructor that will create a 40 X 40 pixel Icon. The second constructor will take two integers representing the number of rows and columns of pixels in the Icon. Create a black Pixel object for each column of each row.

The Icon class allows the user to specify a pixel by row and column and get/set it's RGB value. Do not allow the user to go outside the bounds of the Icon.

The Icon class should be able to create a string representation of the bitmap that shows the hex RGB values for all the pixels in it. This method must be called toString(). It should also be able to produce a 24-bit bitmap image of the Icon.

The format of bitmap images is described here:
http://en.wikipedia.org/wiki/BMP_file_format

In your driver, create a small 5X5 Icon object with different colored pixels (red top left, green top right, blue bottom left, black bottom right). Then, using the Icon, create a bitmap image file of your Icon. In addition, the Icon should print out the hex values of all the pixels in rows and columns to the command prompt.

These are the things I will be looking for when I am grading the assignment:
Pixel class
- single int to represent the color
- color manipulation algorithms
- has all the functionality specified above
- toStringHex() functionality

Icon class
- use ArrayLists to hold pixels
- ability to get and set pixels
- create a bitmap image file

Other
- well structured and commented code
- interface matches the methods in the description above
- driver that tests the functionality of the classes
