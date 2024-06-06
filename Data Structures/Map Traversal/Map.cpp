#include "Map.hpp"
#include <string>
#include <iostream>
#include <fstream>
#include "Cell.hpp"

using namespace std;

#define FREE 'F'
#define BLOCKED 'B'
#define START 'S'
#define END 'E'

Map::Map(string file_path) {
    start = NULL;
    end = NULL;

    //read file
    fstream f;
    f.open(file_path, fstream::in);

    string l;
    rows = 0;
    columns = 0;

    while (!f.eof()) {
        getline(f, l);
        columns = l.length();
        rows++;
    }

    f.seekg(0);
    string* lines = new string[rows];
    int i = 0;

    while (!f.eof() && i < rows) {
        getline(f, l);
        lines[i] = l;
        i++;
    }

    //close file
    f.close();

    //making of the grid from file
    grid = new Cell * *[rows];
    for (int i = 0; i < rows; i++) {
        grid[i] = new Cell * [columns];

        for (int j = 0; j < columns; j++) {
            grid[i][j] = new Cell(i, j, lines[i][j]);

            if (lines[i][j] == START) start = grid[i][j];
            if (lines[i][j] == END) end = grid[i][j];
        }
    }

    //linking cells
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < columns; j++) {
            if (i - 1 >= 0) grid[i][j]->set_north(grid[i - 1][j]);
            if (j + 1 < columns) grid[i][j]->set_east(grid[i][j + 1]);
            if (i + 1 < rows) grid[i][j]->set_south(grid[i + 1][j]);
            if (j - 1 >= 0) grid[i][j]->set_west(grid[i][j - 1]);
        }
    }
}

void Map::printCells() {
    cout << "\nCell Map:\n";
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < columns; j++) {
            cout << grid[i][j]->get_type() << " ";
        }
        cout << endl;
    }
    cout << "\nFinding route...\n";
}
