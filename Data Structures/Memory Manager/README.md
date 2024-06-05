## Assignment Description

Programming Assignment 5 asks you to write a class called MemoryManager that simulates a memory
manager used for dynamic memory allocation in a programming language like C or C++. In C++,
when we use the keyword 'new' a block of memory is allocated from the heap. That memory location
is tracked by a memory manager until it is freed with a call to 'delete'. One can think of the heap as a
series of contiguous bytes available to a programmer to be used for dynamic memory allocation.

In the C language there are no 'new' or 'delete' operators but there are functions included to create
and destroy dynamic memory. malloc() is the C equivalent to new, it takes the number of bytes
requested. free() is the equivalent to delete. Your job will be to create similar functions to handle
dynamic memory allocations in a program.

In a C or C++ program the operating system sets aside some room for the heap. Since we are
simulating this functionality it will be up to us to set aside some number of bytes to be used as the
heap space. We will then be responsible for managing that memory by giving programs access to
available memory on request.

---

The MemoryManager class should have (at least) the following methods:

allocate()- takes an integer representing the number of bytes requested from the memory manager
and returns the address of the block on the heap.

deallocate()- takes a pointer that holds the address of the block of memory to be released.

dump()- prints information about all the blocks currently being tracked in the system.

A constructor that takes a pointer to a chunk of bytes to be managed by the memory manager and an
integer representing the size of that heap space in bytes

---

The algorithm to be used when allocating blocks of memory is called 'best fit', this means when a
request for a block of memory is received the heap space will be searched from the beginning for the
smallest unused slot that is large enough to fulfill the request. That memory location will then be
marked as 'used' and the address of that block of memory will be returned to be stored in a pointer. If
there is not a block of memory large enough to fulfill the request then the null pointer should be
returned.

When the memory location is no longer needed a call to deallocate() will mark the block of memory as
'free' to be used later by other calls to allocate(). deallocate() will take an address previously returned
by a call to allocate().

In order to manage a series of memory locations like this a doubly-linked list is required. Each node
should have a pointer that points to the previous node, a pointer that points to the next node, the size
of the block of memory and whether the block is free or used. When a block of memory is freed the
blocks of memory on either side of it need to be checked to see if one large block of memory can be
created, this is called 'coalescing'. The goal is to make optimal use of the available heap space by
coalescing contiguous free blocks into larger free blocks.

Hints: A char is exactly one byte so an array of char's should be created to simulate the heap (this will
be done in the driver and a pointer will be passed in the constructor). A void* is a generic pointer, this
type should be used to return the address of a block

``` C++
void* allocate(int s);
```

The sizeof operator will return the number of bytes that a data type takes up in memory. Remember,
the nodes of the linked list will take up space in the array of bytes. Casting pointers will be required.
Your code should be extremely well commented.

In the constructor create the first node and the last node. I am attaching a driver that shows how to
use the memory manager. Your class should have exactly the same interface as the driver (although I
may use another driver when I grade it).

**Part 2**

After you have tested your MemoryManager using the provided main.cpp. It’s time to use it. We’ll test
it with a simple program. Write a number guesser program where the user thinks of a number
between 1 and 1000. Your program should randomly select a number between 1 and 1000 trying to
guess the user’s number. The user responds saying the computer is correct or that the user's number
is lower or higher than the guessed number. The program keeps guessing narrowing it’s range until
the user states the correct number has been guessed. The program then ends. All variables must be
allocated using your MemoryManager.
