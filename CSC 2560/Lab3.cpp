#include <iostream>
#include "SafeArrayInt.h"

using namespace std;

int main()
{
    //create a safe array
    SafeArrayInt s1;

    //add 3 items
    s1.push_back(10);
    s1.push_back(20);
    s1.push_back(30);
    
    //print the 3 items
    cout<<"Printing all items:"<<endl;
    for(int i = 0;i < s1.size();i++)
    {
        cout<<"Index: "<<i<<" Value: "<<s1.at(i)<<endl;
    }
    cout<<endl;

    //update the first two with at()
    s1.at(0) = 50;
    s1.at(1) = 40;

    cout<<"Printing all items:"<<endl;
    for(int i = 0;i < s1.size();i++)
    {
        cout<<"Index: "<<i<<" Value: "<<s1.at(i)<<endl;
    }
    cout<<endl;

    //adding 100 random numbers
    for(int i = 0;i < 100;i++)
    {
        s1.push_back(rand());
    }

    cout<<"Printing all items:"<<endl;
    for(int i = 0;i < s1.size();i++)
    {
        cout<<"Index: "<<i<<" Value: "<<s1.at(i)<<endl;
    }
    cout<<endl;

    //removing the last 100 random numbers
    for(int i = 0;i < 100;i++)
    {
        cout<<"Removing: "<<s1.pop_back()<<endl;
    }
    cout<<endl;

    cout<<"Printing all items:"<<endl;
    for(int i = 0;i < s1.size();i++)
    {
        cout<<"Index: "<<i<<" Value: "<<s1.at(i)<<endl;
    }
    cout<<endl;

    //going outside the bounds, program should end
    s1.at(3) = 40;
    cout<<"You should not see this on the screen!"<<endl;
    
    return 0;
}

 //To be clear, you may not use the built-in vector class in this lab.