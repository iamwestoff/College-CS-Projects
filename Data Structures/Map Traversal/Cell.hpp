#pragma once
#include <string>
#include <iostream>

using namespace std;

//cell class
//cell represents each square on the map. each one has a coordinate of x and y with a different type for each
class Cell {
    //coordinates
    int X, Y;

    // Type of cell
    char type;
    Cell* north;
    Cell* east;
    Cell* south;
    Cell* west;
    bool traversed, deadEnd;

public:
    Cell(int x, int y, char cell_type);

    //setter methods
    void set_north(Cell* n) { north = n; }
    void set_east(Cell* e) { east = e; }
    void set_south(Cell* s) { south = s; }
    void set_west(Cell* w) { west = w; }
    void set_traversed(bool x) { traversed = x; }
    void set_deadEnd(bool x) { deadEnd = x; }

    //getter methods
    int get_X() const { return X; }
    int get_Y() const { return Y; }
    Cell* get_north() const { return north; }
    Cell* get_east() const { return east; }
    Cell* get_south() const { return south; }
    Cell* get_west() const { return west; }
    bool get_traversed() const { return traversed; }
    bool get_deadEnd() const { return deadEnd; }
    char get_type() const { return type; }
};//end class
