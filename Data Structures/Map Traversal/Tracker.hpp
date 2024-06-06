#pragma once
#include <string>
#include <iostream>
#include "Map.hpp"

using namespace std;

//tracker class
//maintain a pointer to the current cell that its in to find the path to the end
class Tracker {
public:
    void findPath(Map& m);
    void printRoute(Map& m);
};//end tracker