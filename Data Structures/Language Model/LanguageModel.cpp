/*
 * ******************************
 * *** LANGUAGE MODEL PROGRAM ***
 * ******************************
 *
 * @brief Language Model program used to randomly generate the next word in a sentence
 * based on the frequency of the next word by stored sentence structures. Similar to how
 * many AI models find how to structure their responses.
 *
 * @author Easton H. Herbon
 * @version 4.18.23
 */

#include "LanguageModel.h"

using namespace std;

// UpdateNgrams:
// Function used to update the ngrams within a vector of strings
void LanguageModel::UpdateNgrams(const vector<string>& words, int ngramSize) {
    // NULL check
    if(words.size() < ngramSize)
        return;

    for(int i = 0; i < words.size() - ngramSize; i++) {
        string ngram = words[i];
        for(int j = i + 1; j < i + ngramSize; j++)
            ngram += " " + words[j];

        string nextWord = words[i + ngramSize];

        if(ngrams.count(ngram))
            ngrams[ngram][nextWord]++;
        else {
            unordered_map<string, int> nextWords;
            nextWords[nextWord] = 1;
            ngrams[ngram] = nextWords;
        }
    }
}// end UpdateNgrams

// GenerateText:
// Function used to generate the text to the correct length requested by the application.
string LanguageModel::GenerateText(int length){
    // NULL check
    if (ngrams.empty())
        return "";

    // selects random integer and sets to the text
    string currentNgram = GetRandomNgram();
    string generatedText = currentNgram;

    // to reach the current length
    int numOfWords = 0;
    while(numOfWords < length - 1) {
        unordered_map<string, int>& nextWords = ngrams[currentNgram];
        // NULL check
        if (nextWords.empty())
            break;

        // pick the next word based on frequency
        int totalFrequency = 0;
        // iterates until end of nextWords, the next word in the pair is then added to frequency
        for (auto i = nextWords.begin(); i != nextWords.end(); i++)
            totalFrequency += i->second;

        // randomly selects first word
        int randomNumber = rand() % totalFrequency;
        for (auto i = nextWords.begin(); i != nextWords.end(); i++) {
            int count = i->second;
            string word = i->first;
            randomNumber -= count;

            if (randomNumber < 0) {
                generatedText += " " + word;
                currentNgram = word;

                if (ngrams.find(word) == ngrams.end()){
                    string tempText = generatedText;
                    currentNgram = GetRandomNgram();
                    tempText += ".";
                    numOfWords++;
                    tempText += " " + currentNgram;

                    while(numOfWords >= length)
                        currentNgram = GetRandomNgram();

                    generatedText = tempText;
                }
            }
        }
        numOfWords = CountWords(generatedText);
    }

    // corrects length if it generates too much
    if(numOfWords >= length){
        for(int i = numOfWords; i > length - 1; i--){
            while(generatedText.substr(generatedText.length() - 1) != " ")
                generatedText.pop_back();

            // removes last space
            generatedText.pop_back();
        }
    }

    // output
    generatedText[0] = toupper(generatedText[0]);
    return generatedText;
}// end GenerateText

// GetRandomNgram
// Function used to randomly generate the next ngram
string LanguageModel::GetRandomNgram() {
    int index = rand() % ngrams.size();
    return next(ngrams.begin(), index)->first;
}// end GetRandomNGram

// Count Words:
// Function used to count the current number of words
int LanguageModel::CountWords(string text){
    int numOfWords = 0;
    // counts words
    for(char c : text) {
        if(c == ' ')
            numOfWords++;
    }
    return numOfWords;
}// end CountWords