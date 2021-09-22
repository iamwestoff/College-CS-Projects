import java.util.*; 
 
public class Jumble 
{ 
    private String originalWord; 
    private String jumbledWord; 
    private int numGuessesSoFar; 
    private boolean wasGuessedCorrectly; 
 
    public Jumble(String wordToJumble) 
    { 
        setOriginalWord(wordToJumble); 
        setJumbledWord(jumbleString(wordToJumble));
        setNumGuessesSoFar(0); 
        setWasGuessedCorrectly(false);
    } 
 
    private String jumbleString(String word) 
    { 
        Random randomNumberGenerator = new Random(); 
 
        char[] letters = word.toCharArray(); 
 
        for(int i = 0;i < letters.length;i++) 
        { 
            int randPos = randomNumberGenerator.nextInt(letters.length); 
 
            char temp = letters[i]; 
            letters[i] = letters[randPos]; 
            letters[randPos] = temp; 
        } 
 
        return new String(letters); 
    } 
 
    public boolean guess(String guessedWord) 
    { 
        boolean retVal = false; 
 
        setNumGuessesSoFar(getNumGuessesSoFar() + 1); 
        if(guessedWord.equals(getOriginalWord())) 
        { 
            setWasGuessedCorrectly(true); 
            retVal = true; 
        } 
 
        return retVal; 
    } 
 
    //getters and setters 
    public void setOriginalWord(String word) 
    { 
        originalWord = word; 
    } 
 
    public String getOriginalWord() 
    { 
        return originalWord; 
    } 
 
    public void setJumbledWord(String word) 
    { 
        jumbledWord = word; 
    } 
 
    public String getJumbledWord() 
    { 
        return jumbledWord; 
    } 
 
    public void setNumGuessesSoFar(int guessNum) 
    { 
        numGuessesSoFar = guessNum; 
    } 
 
    public int getNumGuessesSoFar() 
    { 
        return numGuessesSoFar; 
    } 
 
    public void setWasGuessedCorrectly(boolean correct) 
    { 
        wasGuessedCorrectly = correct; 
    } 
 
    public boolean getWasGuessedCorrectly() 
    { 
        return wasGuessedCorrectly; 
    } 
}