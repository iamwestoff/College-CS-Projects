/**
 * Easton Herbon
 * version @9/13/21
 * 
 * a simple pointer class of notes to show how a pointer works in a C++ program
 */
#include <iostream>;
#include <string>;

using namespace std; 

int main() {
    //datatype * pointerName;

    double makesHeight = 71.5;
    
    double * p_height = &makesHeight;

    * p_height = 70.5;

    //allocates three points in the heap to nums
    double nums[3];
    //allocating different points of the array to different values
    nums[0] = 100.0;
    nums[1] = 200.0;

    //the +2 is the size of the double
    * (nums + 2) = 300.0;
    // VV this is the same as above
    //nums[2] = 300.0;

    double * p_num = new double;
    //the keyword 'new' allocates the new double to an area of memory in the heap (8 bytes)
        // ***the only possible way to access/dereference that pointer value is with a pointer declaration
    * p_num = 100.0;
    //...
    //...
    
    cout << *p_num << endl;
    string * p_names = new string[4];
    p_names[0] = "Mark";
    p_names[1] = "Laura";
    * (p_names + 2) = "Buddy";

    //Student *p_student = new Student("Mark", 3.2);
    //p_student -> printReportCard();
    //delete p_student; //destructor called
}