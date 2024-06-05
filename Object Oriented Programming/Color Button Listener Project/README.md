## Assignment Description

Programming Assignment 3 asks you to extend the Pixel assignment. Your job is to create a GUI to easily make bitmap files. You will reuse your Pixel and Icon classes and create a GUI to gather info about the pixels. The Pixel and Icon classes should not be aware of the GUI classes. In other words, do not make any changes to Pixel or Icon unless you need to fix something that was broken last time.

The application will prompt for the dimensions of the Icon on startup. Display a button for each pixel. There should be a way for the user to select a color. Once a button is clicked the Pixel associated with that button should have its color set to the appropriate levels and the color of the button should change. Look at the setBackground() method of the JButton class and the Color class to achieve this.

Below is a description of the required components:
grid- a 2D group of buttons. Click a button and that button will take on a color. The corresponding Pixel an Icon will get the same RGB value.

color chooser- allows you to choose a color. Three sliders will be sufficient to allow you to select a value from 0 - 255 for red, green, and blue. There should be a preview of the selected color every time the sliders change.

last five colors- show the last five colors used. When someone clicks on a previously used color, adjust the color sliders to take on that color. Make sure that duplicate colors are not stored in this widget.

create bitmap- clicking this button will show a file chooser to select where the file will be stored. Then allow the user to enter a name and save the bitmap file at that location.

advanced checkbox- when this is checked a selected button doesn't take on the color from the color chooser. Instead, it brings up an 'advanced' dialog.

advanced dialog part 1- ask for the number of rows and columns from the clicked button onward to fill with a color. The rows will always be from the selected button and rows beneath and the columns will always be from the selected button and to the right.

advanced dialog part 2- allow the user to open up any 24-bit bitmap and add the pixels inside the file to your bitmap. The top, left pixel of the selected bitmap will go in the selected button. Always show a preview of the selected file before adding it to the current bitmap.

These are the things I will be looking for when I am grading the assignment:
- does the program prompt with JOptionPanes for the dimensions of the bitmap
- are buttons created for each pixel
- can the user choose a color
- can a pixel button take on a color
- are the last five colors stored correctly
- can a bitmap file be saved to the disk using a file chooser
- can multiple pixels be colored with a single click
- can a bitmap file's pixels be imported into the editor
