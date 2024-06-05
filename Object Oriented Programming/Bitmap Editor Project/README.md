## Assignment Description
This assignment asks you to extend the bitmap editor to add two new features- drawing lines and previewing alternate edits.

The first feature allows the user to draw lines by moving the mouse over the buttons rather than clicking them. This functionality will be enabled any time that the 'shift' key is pressed on the keyboard. There is a listener, MouseListener, that will notify you when the mouse hovers over a widget (like a button). You will use this listener to set the hovered over button to the preview color. The MouseListener creates MouseEvent objects. MouseEvents have a method that will tell you if the shift key is pressed. You will use this to determine when the 'shift' button is pressed while moving the mouse.

The second feature allows the user to display multiple bitmap editor windows at the same time. When one is changed all of the others will reflect the change more or less instantaneously. In order to accomplish this you will use the Observer pattern. You will now have to capture some data about each edit. For example, you need to know the row and column of the pixel that is being edited along with the color that the pixel will be set to. You will create a class, IconEdit, that will be informed about each edit from any of the bitmap editors. This class will be the Subject in the Observer pattern. When one bitmap editor is being edited it will pass information about the most recent edit to the IconEdit and it will notify all of the observers so that they can make the same edit. The bitmap editors will be the Observers and they must react to being notified of an edit. Include a way for the user to detach or re-attach a GUI from the Subject.

In each bitmap editor there will be controls to alter an edit received from the IconEdit. An edit can be inverted either vertically or horizontally and each edit's color may be turned into a random color. Altering an edit's data will be accomplished with the Decorator pattern. The decorators will be called VerticalInvertBitmapEdit, HorizontalInvertBitmapEdit, and RandomColorBitmapEdit. When each bitmap editor is notified of a new edit it will check to see if any of the decorators should be applied (using some swing widgets like JCheckBox and JRadioButton). If so, the bitmap edit will be wrapped in a decorator to add the desired functionality. Multiple decorators can be applied to the same edit. Here is a description of each decorator:

VerticalInvertBitmapEdit
Invert the row number that the edit took place on. If the dimensions of the bitmap being edited are 10 rows and 18 columns and the edit happens on row 3 column 4 then the decorated edit will take place on row 7 column 4 (7 is calculated by taking the height, 10, and subtracting the row number of the edit, 3).

HorizontalInvertBitmapEdit
This is similar to the previous decorator except that the column will be updated. For example, in the previous example the new edit will take place on row 3 column 14 (14 is calculated by taking the width, 18, and subtracting the column number of the edit, 4).

RandomColorBitmapEdit
This decorator will generate a random color for an edit.
