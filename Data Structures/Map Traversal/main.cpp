/**
 * @author Easton Herbon
 * @version 11/3/22
 *
 * Assignment 2 : Map Traversal Project
 * For this project we use a stack to find our way through a map.
*/

#include <string>
#include <iostream>
#include <fstream>
#include <stack>
#include "Map.hpp"
#include "Tracker.hpp"

using namespace std;

int main()
{
    //create a map from a file || tested all files (passed)
    //Map map("C:\\Users\\ehh20\\OneDrive\\Documents\\simplemap.txt");
    //Map map("C:\\Users\\ehh20\\OneDrive\\Documents\\anothermap.txt");
    Map map("C:\\Users\\ehh20\\OneDrive\\Documents\\multipathmap.txt");
    //Map map("C:\\Users\\ehh20\\OneDrive\\Documents\\oddmap.txt");
    //create a tracker capable of traversing through a map
    Tracker tracker;
    //send the tracker to find a path through the map
    tracker.findPath(map);
    //print the original cells from the file
    map.printCells();
    //print an X/O map from the start to the end
    tracker.printRoute(map);
    return 0;
}
