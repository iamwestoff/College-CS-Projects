#include <string>
#include <iostream>
#include "Cell.hpp"

using namespace std;


Cell::Cell(int x, int y, char cell_type) {
    X = x;
    Y = y;
    type = cell_type;
    traversed = false;
    deadEnd = false;
    north = nullptr;
    east = nullptr;
    south = nullptr;
    west = nullptr;
}