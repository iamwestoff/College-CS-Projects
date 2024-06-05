/*
 * @brief Memory Manager Program used to emulate C the malloc functions.
 *
 * @author Easton H Herbon
 * @version 5.2.2023
 */

#include "MemoryManager.h"
#include <iostream>

using namespace std;

//constructor
MemoryManager::MemoryManager(char* p_mem, int s) {
    cout << "Inside MemoryManager constructor, the sizeof(Node) is: " << sizeof(Node) << " sizeof(Node*) is: " << sizeof(Node*) << endl;
    cout << "size of a bool is: " << sizeof(bool) << endl;

    p_memory = p_mem;

    p_head = (Node*)p_memory;
    p_head->sizeOfNode = s - 2 * (sizeof(Node));
    p_head->isFree = true;
    p_head->p_prev = 0;

    p_tail = (Node*)(p_memory + s - sizeof(Node));
    p_tail->sizeOfNode = 0;
    p_tail->isFree = false;
    p_tail->p_next = 0;
    p_tail->p_prev = p_head;
    p_head->p_next = p_tail;
}

void* MemoryManager::allocate(int s) {
    Node* curr = p_head;
    while (curr != 0 && (curr->sizeOfNode < s || !curr->isFree)){ curr = curr->p_next; }

    if (curr != 0) {
        if (curr->sizeOfNode >= s + sizeof(Node)) {
            Node* newNode = (Node*)((char*)curr + sizeof(Node) + s);
            newNode->p_prev = curr;
            newNode->p_next = curr->p_next;
            curr->p_next->p_prev = newNode;
            curr->p_next = newNode;
            newNode->isFree = true;
            newNode->sizeOfNode = curr->sizeOfNode - s - sizeof(Node);

            curr->isFree = false;
            curr->sizeOfNode = s;
            return (void*)((char*)curr + sizeof(Node));
        }
    }
    else
        return 0;
    return 0;
}

void MemoryManager::deallocate(void* p_delete) {
    Node* curr = (Node*)((char*)p_delete - sizeof(Node));

    Node* temp = p_head;
    bool isThere = false;

    while(temp != p_tail){
        if (temp == curr) isThere = true;
        temp = temp->p_next;
    }

    if(isThere){
        curr->isFree = true;

        if (curr->p_prev != 0 && curr->p_prev->isFree) {
            curr->p_prev->sizeOfNode += curr->sizeOfNode + sizeof(Node);
            curr->p_prev->p_next = curr->p_next;
            curr->p_next->p_prev = curr->p_prev;
            curr = curr->p_prev;
        }

        if (curr->p_next != 0 && curr->p_next->isFree) {
            curr->sizeOfNode += curr->p_next->sizeOfNode + sizeof(Node);
            curr->p_next = curr->p_next->p_next;
            curr->p_next->p_prev = curr;
        }
    }
}

void MemoryManager::dump() {
    Node* curr = p_head;
    int blockNumber = 1;
    while (curr != p_tail) {
        cout << "Block " << blockNumber << ": " << curr->sizeOfNode << " bytes of ";
        if (curr->isFree) {
            cout << "free space." << endl;
        }
        else {
            cout << "used space." << endl;
        }
        curr = curr->p_next;
        blockNumber++;
    }
}