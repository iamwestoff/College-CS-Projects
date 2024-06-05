## Assignment Description

This assignment asks you to write a program that will read a sequence of words from the keyboard
and store them in a dynamic array of strings. Use the word 'quit' as the word that terminates the
input. Print the words back to the screen in the order in which they were entered each on its own line.
Do not store the same word twice

Up until now the size of an array has been determined at compile time, now that you know about
pointers and about the keyword 'new', write a program which is not restricted to selecting an upper
bound at compile time for the number of words which can be read in.

One way to do this is to use 'new' to create arrays of strings on the fly. Each time an array fills up,
dynamically create an array which is twice as large, copy over the contents of the existing array to the
new array, and continue (remember to delete the original array). Start with an array of 5 elements.

For simplicity you do not need to create any classes for this assignment although creating at least one
function might be a good idea (I might create a Search() function that checks to see if a word has
already been stored on the array).

Here is an example of the output:

``` output
Enter in some text and end with the word quit:
This assignment asks you to write a program that will read a sequence of words from the keyboard
and store them in a dynamic array of strings. Use the word 'quit' as the word that terminates the
input. Print the words back to the screen in the order in which they were entered each on its own line.
Do not store the same word twice. quit

output
Doubling Array from 5 to 10
Doubling Array from 10 to 20
Doubling Array from 20 to 40
Doubling Array from 40 to 80
1. This
2. lab
3. asks
4. you
5. to
6. write
7. a
8. program
9. that
10. will
11. read
12. sequence
13. of
14. words
15. from
16. the
17. keyboard
18. and
19. store
20. them
21. in
22. dynamic
23. array
24. strings.
25. Use
26. word
27. 'quit'
28. as
29. terminates
30. input.
31. Print
32. back
33. screen
34. order
35. which
36. they
37. were
38. entered
39. each
40. on
41. its
42. own
43. line.
44. Do
45. not
46. same
47. Twice.
```

**STARTING CODE**

``` C++
#include <iostream>
#include <string>
using namespace std;
int main()
{
//5 word max array of strings
string allWords[5];
//for storing new words, the position to add the next word
int nextWordPosition = 0;
//reads one word at a time into this string
string latestWord;
//prompt the user for a group of words
cout << "Enter in some text and end with the word quit:";
//cin separates words by spaces
cin >> latestWord;
//while we haven't reached the 'magic' end input word
while(latestWord != "quit")
{
//add the latest word
allWords[nextWordPosition] = latestWord;
//bump up the position for the next word
nextWordPosition++;
//read the next word from the stream of input
cin >> latestWord;
}
//print all of the words collected
for(int i = 0;i < nextWordPosition;i++)
{
cout<<(i + 1)<<"."<<allWords[i]<<endl;
}
return 0;
}
```
