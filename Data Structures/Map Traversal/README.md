## Assignment Description
This assignment asks you to use a stack to help find a path through a map.

Create a class called Cell. A Cell represents a square on a map. Every cell has an X and Y coordinate
and a cell type: FREE, BLOCKED, START, or END. A Free cell can be traversed, a Blocked cell cannot be
traversed, a Start cell is a free cell and an entry into the map, and an End cell is a free cell and the
final destination in the map. Each cell will maintain four Cell pointers to the cells immediately around it
to the north, south, east, and west. A cell keeps track if it has been traversed by a Tracker (see below)
and whether the cell is on a dead path.

Next create a Map class. The main job of the Map class is to read in the map shape from the file and
construct the linked cells. The format of the Map will come from a file, for example the file map.txt
might look like this (NOTE: The map you read from the file may not be rectangular):

``` txt
B S B F F F
F F B F B B
F B F F F E
F F F B F B
F B B F F B
F F F B F B
```

The goal is to eventually find a path through the map. The Map should be able to print the original
state and a path from the start to the end (the Tracker class will be responsible for finding the path).

``` txt
X O X X X X
O O X X X X
O X O O O O
O O O X X X
X X X X X X
X X X X X X
```

The Tracker class will maintain a pointer to a the current cell that it is in (initially, it will hold the start
cell) in order to find a path to the end. A Tracker will continue to move through the map until it
reaches the end cell. When a Tracker moves it will systematically select a direction and attempt to
move to the cell in that direction. If the cell to move to is a Blocked cell or if the Tracker previously
traversed that cell then it will not move in that direction, it will select another cell to move to. If the

Tracker is ever at a position where it cannot proceed any longer it will backtrack and try to move from
the previous cell. It will repeat this process until it finds the destination. Keeping track of the history
will involve maintaining a stack of pointers to previously visited cells.

The interface for the stack is:
``` C++
void push(const T& elem);
void pop(T& elem);
void top(T& elem);
bool isEmpty();
int size();
```
You must use the doubly linked list based stack from the last lab to implement the stack.

Your program should find a path from start to end and log all movement activities. For example here is
some output that describes how a path can be found:

``` output
Going South X: 1 Y: 1 Type: F
Going West X: 0 Y: 1 Type: F
Going South X: 0 Y: 2 Type: F
Going South X: 0 Y: 3 Type: F
Going South X: 0 Y: 4 Type: F
Going South X: 0 Y: 5 Type: F
Going East X: 1 Y: 5 Type: F
Going East X: 2 Y: 5 Type: F
Backtracking from: X: 2 Y: 5 Type: F to: X: 1 Y: 5 Type: F
Backtracking from: X: 1 Y: 5 Type: F to: X: 0 Y: 5 Type: F
Backtracking from: X: 0 Y: 5 Type: F to: X: 0 Y: 4 Type: F
Backtracking from: X: 0 Y: 4 Type: F to: X: 0 Y: 3 Type: F
Going East X: 1 Y: 3 Type: F
Going East X: 2 Y: 3 Type: F
Going North X: 2 Y: 2 Type: F
Going East X: 3 Y: 2 Type: F
Going North X: 3 Y: 1 Type: F
Going North X: 3 Y: 0 Type: F
Going East X: 4 Y: 0 Type: F
Going East X: 5 Y: 0 Type: F
Backtracking from: X: 5 Y: 0 Type: F to: X: 4 Y: 0 Type: F
Backtracking from: X: 4 Y: 0 Type: F to: X: 3 Y: 0 Type: F
Backtracking from: X: 3 Y: 0 Type: F to: X: 3 Y: 1 Type: F
Backtracking from: X: 3 Y: 1 Type: F to: X: 3 Y: 2 Type: F
Going East X: 4 Y: 2 Type: F
Going East X: 5 Y: 2 Type: E
```

Your driver should look like this:

``` C++
int main()
{
//create a map from a file
Map map("path/to/map/file");
//create a tracker capable of traversing through a map
Tracker tracker;
//send the tracker to find a path through the map
tracker.findPath(map);
//print the original cells from the file
map.printCells();
//print an X/O map from the start to the end
tracker.printRoute();
return 0;
}
```
