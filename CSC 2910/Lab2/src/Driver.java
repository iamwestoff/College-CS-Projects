import javax.swing.JOptionPane; 
 
public class Driver 
{ 
    public static final int NUM_ALLOWED_GUESSES = 3; 
     
    public static void main(String[] args) 
    { 
        String word = JOptionPane.showInputDialog("Enter in a word to jumble"); 
 
        Jumble jumble = new Jumble(word.toLowerCase());
 
        while(jumble.getWasGuessedCorrectly() == false && jumble.getNumGuessesSoFar() < NUM_ALLOWED_GUESSES) 
        { 
            makeAGuess(jumble); 
        } 
 
        if(jumble.getWasGuessedCorrectly()) 
        { 
            JOptionPane.showMessageDialog(null, "Congratulations! You unjumbled: " + jumble.getOriginalWord()); 
        }  
        else 
        { 
            JOptionPane.showMessageDialog(null, "Sorry, you could not unjumble the word: " + jumble.getOriginalWord()); 
        } 
    } 
 
    public static void makeAGuess(Jumble jumble) 
    { 
        String guess = JOptionPane.showInputDialog("Unjumble this word: " + jumble.getJumbledWord());
        guess = guess.toLowerCase();
 
        if(jumble.guess(guess)) 
        { 
            JOptionPane.showMessageDialog(null, "The guess was correct!!!"); 
        } 
        else 
        { 
            JOptionPane.showMessageDialog(null, "The guess was NOT correct"); 
        } 
    } 
}