/**
 * @brief       Assignment 1 : Dynamic String Arrays
 *
 * @author      Easton H Herbon
 * @version     2.23.2023
 */

#include <iostream>
#include <string>

using namespace std;

bool Search(string* words, string word, int size);

using namespace std;

int main()
{
    //hold initial max size of array
    int maxSize = 5;

    //5 word max array of strings
    string* allWords = new string[maxSize];

    //for storing new words, the position to add the next word
    int nextWordPosition = 0;

    //reads one word at a time into this string
    string latestWord;

    //prompt the user for a group of words
    cout << "Enter in some text and end with the word quit:"<< endl;
    //cin separates words by spaces
    cin >> latestWord;

    //while we haven't reached the 'magic' end input word
    while(latestWord != "quit") {
        //check to see if wor was previously stored
        if (!Search(allWords, latestWord, nextWordPosition)) {
            //if they've reached the current max size of the array you double it
            if (nextWordPosition == maxSize) {
                //double array size
                maxSize *= 2;
                string * newWords = new string[maxSize];

                //copy old array over
                for (int i = 0; i < nextWordPosition; i++) {
                    newWords[i] = allWords[i];
                }
                //delete old array
                delete[] allWords;
                //update the pointer to new array
                allWords = newWords;
                //outputting message to show doubling of array
                cout << "Doubling Array from " << maxSize / 2 << " to " << maxSize << endl;
            }
            //adds last word
            allWords[nextWordPosition] = latestWord;
            //move position for last word
            nextWordPosition++;
        }
        //read the next word from the stream of input
        cin >> latestWord;
    }

    //print all-of-the words collected
    for(int i = 0; i < nextWordPosition; i++)
    {
        cout<<(i + 1)<<"."<<allWords[i]<<endl;
    }
    //delete array
    delete[] allWords;
    return 0;
}

//search for word in the array
bool Search(string* words, string word, int size){
    for(int i = 0; i < size; i++) {
        if(words[i] == word)
            return true;
    }
    return false;
}