#pragma once
#include <string>
#include <iostream>
#include "Cell.hpp"

using namespace std;

//map class
//read in the map shape from the file and construct the linked cells
class Map {
    //setting cells
    Cell* start, * end;
    Cell*** grid;
    int rows;
    int columns;

public:
    Map(string file_path);
    Cell* get_start() const { return start; }
    Cell* get_end() const { return end; }

    void printCells();

    //getter methods for printRoute function
    int get_rows() { return rows; }
    int get_cols() { return columns; }
    Cell*** get_grid() { return grid; }

};//end map
