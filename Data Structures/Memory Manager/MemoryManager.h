/*
 * @brief Memory Manager Header file
 *
 * @author Easton H Herbon
 * @version 5.2.2023
 */

#pragma once
#include <iostream>

using namespace std;

class MemoryManager
{
public:
    MemoryManager(char* p_mem, int s);
    void* allocate(int s);
    void deallocate(void* p_delete);
    void dump();
private:
    struct Node
    {
        int sizeOfNode;
        bool isFree;
        Node* p_next;
        Node* p_prev;
    };
    Node* p_head;
    Node* p_tail;
    char* p_memory;
};