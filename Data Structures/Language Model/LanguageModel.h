/*
 * @brief Header File for declaration of LanguageModel functions
 *
 * @author Easton H. Herbon
 * @version 4.18.23
 */

#include <unordered_map>
#include <iostream>
#include <ctime>
#include <vector>

using namespace std;

class LanguageModel{
public:
    void UpdateNgrams(const vector<string>& words, int ngramSize);
    string GenerateText(int length);
private:
    unordered_map<string, unordered_map<string,int>> ngrams;
    int CountWords(string text);
    string GetRandomNgram();
};