## Assignment Description

In this assignment you will be working to create a Language Model for natural language
processing. Hash Tables are useful for this project because we will need to insert and
retrieve large amounts of data quickly. In the case of this assignment you can use the
built-in C++ Hash Table implementation (unordered_map).

Sample Usage
``` C++
#include <unordered_map>
unordered_map<string,int> wordFrequency;
```

You will need to create a LanguageModel class which must include these two functions:
``` C++
void UpdateNgrams(const vector<string>& words, int ngramSize);
string GenerateText(int length);
```

You can include any other functions you may need.

#### UpdateNgrams
To create a Language Model you will need to call UpdateNgrams. UpdateNgrams will
take in a sentence in the form of a vector<string> and an int which represents the ngram
size. This function will be called multiple times with different sentences and ngram sizes
to make a more comprehensive model. (See sample main.cpp) UpdateNgrams will
store the found ngrams in an unordered_map. The key is the ngram and the value is an
unordered_map. This unordered_map key is the word that follows the current ngram
and the value is the frequency in which it follows the ngram in the provided text corpus.

#### What are ngrams?
An n-gram is a contiguous sequence of n items (or words) from a given text or data. In
the context of natural language processing (NLP) and language modeling, n-grams are
commonly used to represent and analyze the statistical properties of text data.

For example, in the sentence "The quick brown fox", the 2-grams (also known as
bigrams) would be: "The quick", "quick brown", and "brown fox". The 3-grams (also
known as trigrams) would be: "The quick brown", "quick brown fox".

#### GenerateText
GenerateText should pick a random starting ngram from the HashTable. Then the
function should iteratively append words to the generated text based on the word
frequency of the current ngram. If the current ngram does not exist in the Hash Table a
new ngram should be picked randomly. Words should be appended until the generated
text has the required number of words.

``` C++
#include "LanguageModel.h"
#include <iostream>
#include <ctime>
using namespace std;
int main()
{
srand(time(0));
// Create a LanguageModel object
LanguageModel lm;
// Train the LanguageModel with a text corpus
vector<string> words = { "I", "am", "learning", "C++", "and", "using", "hash", "tables",
"for", "a", "language", "model" };
lm.UpdateNgrams(words, 2);
lm.UpdateNgrams(words, 3);
lm.UpdateNgrams(words, 4);
lm.UpdateNgrams(words, 1);
// Generate text using the trained LanguageModel
string generatedText = lm.GenerateText(10);
cout << "Generated Text: " << generatedText << endl;
return 0;
}
``` 
