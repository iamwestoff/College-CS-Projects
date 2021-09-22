import javax.swing.JOptionPane;

public class JOptionPaneExample {
    public static void main(String [] args){
        System.out.println("Here is a JOptionPane example...\n");

        JOptionPane.showMessageDialog(null, "This is a JOptionPane!");

        String name = JOptionPane.showInputDialog("What is your name?");
        JOptionPane.showMessageDialog(null, "So your name is...\n" + name);
    }//end main
}//end class
