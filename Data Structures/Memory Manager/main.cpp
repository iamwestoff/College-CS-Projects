/*
 * @brief Memory Manager Program used to emulate C the malloc functions.
 *
 * @author Easton H Herbon
 * @version 5.2.2023
 */

#include <iostream>
#include <ctime>
#include "MemoryManager.h"

using namespace std;

int main()
{
    srand(time(nullptr));

    // allocating space
    MemoryManager memoryManager(new char[1000], 1000);
    int* guessedNumber = (int*)memoryManager.allocate(sizeof(int));
    char* userResponse = (char*)memoryManager.allocate(sizeof(char));
    int* minGuess = (int*)memoryManager.allocate(sizeof(int));
    int* maxGuess = (int*)memoryManager.allocate(sizeof(int));
    *minGuess = 1;
    *maxGuess = 1000;

    int guess = rand() % (*maxGuess - *minGuess + 1) + *minGuess;
    *guessedNumber = guess;
    cout << "Is your number " << guess << "? (y/h/l): ";
    cin >> *userResponse;

    // keep looking while response is
    while (*userResponse != 'y') {
        if (*userResponse == 'h') {
            *minGuess = guess + 1;
        } else if (*userResponse == 'l') {
            *maxGuess = guess - 1;
        }

        guess = rand() % (*maxGuess - *minGuess + 1) + *minGuess;
        *guessedNumber = guess;
        cout << "Is your number " << guess << "? (y/h/l): ";
        cin >> *userResponse;
    }

    cout << "I guessed your number (" << *guessedNumber << ")!" << endl;

    // deallocating and dump
    memoryManager.deallocate(guessedNumber);
    memoryManager.deallocate(userResponse);
    memoryManager.deallocate(minGuess);
    memoryManager.deallocate(maxGuess);
    memoryManager.dump();
    return 0;
}
