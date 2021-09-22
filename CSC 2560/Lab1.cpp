/**
 * Easton Herbon
 * verision @9/13/21
 * 
 * Lab 1, CSC 2560
 */

#include <iostream>
#include <string>
using namespace std;

void getName(string* p_name);
void getGrade(double* p_grade);
double getAverage(double** grades, int numGrades);

int main()
{
    string* p_studentName = new string;
    getName(p_studentName);

    double grade1;
    double grade2;
    double grade3;

    getGrade(&grade1);
    getGrade(&grade2);
    getGrade(&grade3);

    double* grades[3];
    grades[0] = &grade1;
    grades[1] = &grade2;
    grades[2] = &grade3;
    
    double avg = getAverage(grades, 3);
    cout<<"The average of the grades for " <<*p_studentName<<" is "<< avg <<endl;

    return 0;
}
//--
void getName(string* p_name)
{
    cout<<"Enter in the student name: ";
    cin>>*p_name;
}
//--
void getGrade(double* p_grade)
{
    cout<<"Enter in a grade: ";
    cin>>*p_grade;
}
//--
double getAverage(double** grades, int numGrades)
{
    double sum = 0;
    for(int i = 0;i < numGrades;i++)
    {
        sum = sum + *grades[i];
    }
    
    return sum / (double)numGrades;
}
