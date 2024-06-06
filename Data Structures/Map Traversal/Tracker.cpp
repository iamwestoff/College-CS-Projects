#pragma once
#include <string>
#include <iostream>
#include <fstream>
#include "Tracker.hpp"
#include "Map.hpp"
#include <stack>

using namespace std;

#define FREE 'F'
#define BLOCKED 'B'
#define START 'S'
#define END 'E'

void Tracker::findPath(Map& m) {
    Cell* start = m.get_start();

    stack<Cell*> st;
    st.push(start);
    while (!st.empty()) {
        Cell* current_cell = st.top();
        current_cell->set_traversed(true);

        if (current_cell == m.get_end())
            return;

        if (current_cell->get_north() && !current_cell->get_north()->get_traversed() &&
            !current_cell->get_north()->get_deadEnd() && current_cell->get_north()->get_type() != BLOCKED) {
            cout << "\nGoing North: X: " << current_cell->get_north()->get_X() << " Y: "
                << current_cell->get_north()->get_Y() << " Type: " << current_cell->get_north()->get_type();
            st.push(current_cell->get_north());
        }
        else if (current_cell->get_east() && !current_cell->get_east()->get_traversed() &&
            !current_cell->get_east()->get_deadEnd() && current_cell->get_east()->get_type() != BLOCKED) {
            cout << "\nGoing East: X: " << current_cell->get_east()->get_X() << " Y: "
                << current_cell->get_east()->get_Y() << " Type: " << current_cell->get_east()->get_type();
            st.push(current_cell->get_east());
        }
        else if (current_cell->get_south() && !current_cell->get_south()->get_traversed() &&
            !current_cell->get_south()->get_deadEnd() && current_cell->get_south()->get_type() != BLOCKED) {
            cout << "\nGoing South: X: " << current_cell->get_south()->get_X() << " Y: " << current_cell->get_south()->get_Y()
                << " Type: " << current_cell->get_south()->get_type();
            st.push(current_cell->get_south());
        }
        else if (current_cell->get_west() && !current_cell->get_west()->get_traversed() &&
            !current_cell->get_west()->get_deadEnd() && current_cell->get_west()->get_type() != BLOCKED) {
            cout << "\nGoing West: X: " << current_cell->get_west()->get_X() << " Y: " << current_cell->get_west()->get_Y()
                << " Type: " << current_cell->get_west()->get_type();
            st.push(current_cell->get_west());
        }

        else {
            cout << "\nBacktracking from: X: " << current_cell->get_X() << " Y: " << current_cell->get_Y()
                << " Type: " << current_cell->get_type();

            st.pop();
            current_cell->set_deadEnd(true);
            current_cell->set_traversed(false);
        }
    }
}
void Tracker::printRoute(Map& m) {
    //getting Map values
    int rows = m.get_rows();
    int cols = m.get_cols();
    Cell*** grid = m.get_grid();

    //prints route
    cout << "\nRoute:\n";
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            if (grid[i][j]->get_traversed()) cout << 'O' << ' ';
            else cout << 'X' << ' ';
        }
        cout << endl;
    }
}
