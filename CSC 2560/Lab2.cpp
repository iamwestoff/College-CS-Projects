/**
 * @file Lab2.cpp
 * @author Easton (you@domain.com)
 * @brief 
 * @version 0.1
 * @date 2021-09-17
 * 
 * @copyright Copyright (c) 2021
 * 
 */

#include <iostream>
#include <string>

using namespace std;

void doubleArray(string*& p_allWords, int& wordArrayLength);
bool wordPresent(const string& searchWord, string* p_allWords, int length);

int main()
{
    int wordArrayLength = 5;
    //dynamic array of strings start at length of 5
    string* p_allWords = new string[wordArrayLength];
    
    //for storing new words, the position to add the next word
    int nextWordPosition = 0;
    
    //reads one word at a time into this string
    string latestWord;
    
    //prompt the user for a group of words
    cout << "Enter in some text and end with the word quit:";
    //cin separates words by spaces
    cin >> latestWord;
    //while we haven't reached the 'magic' end input word
    while(latestWord != "quit")
    {
        //if the array is at maximum capacity
        if(nextWordPosition == wordArrayLength)
        {
            //double it
            doubleArray(p_allWords, wordArrayLength);
        }

        //if the words is NOT already present
        if(wordPresent(latestWord, p_allWords, nextWordPosition) == false)
        {
            //add the latest word
            p_allWords[nextWordPosition] = latestWord;
            //bump up the position for the next word
            nextWordPosition++;
        }

        //read the next word from the stream of input
        cin >> latestWord;
    }
    
    //print all of the words collected
    for(int i = 0;i < nextWordPosition;i++)
    {
        cout<<(i + 1)<<"."<<p_allWords[i]<<endl;
    }
    
    //make sure to delete the array
    delete [] p_allWords;

    return 0;
}
//--
void doubleArray(string*& p_allWords, int& wordArrayLength)
{
    //store the new length of the array
    int newArrayLength = 2 * wordArrayLength;
    //create a new array of strings twice as long as the original
    string* p_newArray = new string[newArrayLength];
    
    //copy the elements from the old to the new
    for(int i = 0;i < wordArrayLength;i++)
    {
        p_newArray[i] = p_allWords[i];
    }
    
    //get rid of the old array because it is not needed anymore
    delete [] p_allWords;
    
    //adjust the pointer
    p_allWords = p_newArray;

    cout<<"Doubling the array from "<<wordArrayLength<<" to "<<newArrayLength<<endl;
    
    //update the new size of the array
    wordArrayLength = 2 * wordArrayLength;
}
//--
bool wordPresent(const string& searchWord, string* p_allWords, int length)
{
    //assume the word is not present
    bool retVal = false;
    
    //linear search through the array
    for(int i = 0;i < length;i++)
    {
        //if the word is present
        if(p_allWords[i] == searchWord)
        {
            //indicate a match and stop looking
            retVal = true;
            break;
        }
    }
    return retVal;
}
