/*
 * @author      Easton H Herbon
 * @version     3.30.23
 */

#pragma once
#include <iostream>

using namespace std;

template <class T>
class BSTree
{
public:
    BSTree();
    ~BSTree();
    T max();
    T min(); //TODO - done
    void printInOrder(); //TODO - done
    void printPreorder(); //TODO - done
    void printPostOrder(); //TODO - done
    bool search(const T& d); //TODO - done
    int heightOfTree(); //TODO - done
    void insert(const T& d);
    void remove(const T& d);
private:
    struct Node
    {
        T data;
        Node* left;
        Node* right;
        int heightOfLeftSubtree; //Implement - done
        int heightOfRightSubtree; //Implement - done
    };

    Node* root;
    int numberOfElements;
    int numOfNodesParsed; //new
    T maxHelper(Node* p);
    void insertHelper (Node*& p, const T& d); //Add Balance functionality
    void removeAll(Node*& p);
    void removeHelper(Node*& p, const T& d); //Add Balance Functionality
    bool isBalanced(Node*& p); //TODO - done
    void balanceLeft(Node*& p); //TODO - done
    void balanceRight(Node*& p); //TODO - done

    //added helper functions
    T minHelper(Node* p);
    void printInOrderHelper(Node* p);
    void printPreorderHelper(Node* p);
    void printPostOrderHelper(Node* p);
    bool searchHelper(const T& d, Node* p);
    int heightOfTreeHelper(Node* p);
};

template <class T>
BSTree<T>::BSTree()
{
    root = 0;
    numberOfElements = 0;
}

template <class T>
BSTree<T>::~BSTree()
{
    removeAll(root);
}

template <class T>
void BSTree<T>::removeAll(Node*& p)
{
    if (p != 0)
    {
        //Work our way down to the leaf nodes recursively and delete them
        removeAll(p->left);
        removeAll(p->right);
        cout << "\tDeleting " << p->data << endl;
        delete p;
    }
}

template <class T>
void BSTree<T>::insert(const T& elem)
{
    //Start at the root of the tree
    insertHelper(root, elem);
}

template <class T>
void BSTree <T>::insertHelper(Node*& p, const T& d)
{
    //if the passed in pointer is null (base case)
    if (p == 0)
    {
        //Create a new node and assign it to the pointer (which is passed in)
        p = new Node;

        // all new nodes are leaf nodes, so make left and right pointer NULL
        p->left = 0;
        p->right = 0;

        //set the data
        p->data = d;
        numberOfElements++;

        cout << "Inserting " << d << endl;
    }
    else if (d <= p->data) // p is not null AND insert item is less than or equal
    {
        insertHelper(p->left, d);

        //below we already are accounting for balancing to the right, but we also
        //have to account for the left side first (recursively)
        if(!isBalanced(p)){
            balanceLeft(p);
            if(!isBalanced(p)){
                balanceLeft(p->right);
                balanceRight(p);
            }
        }
    }
    else // p is not null AND insert item is greater
    {
        insertHelper(p->right, d); //Recursively insert to the right

        //On the way back up the call stack
        if (!isBalanced(p)) //Tree is not balanced after inserting
        {
            //attempt to balance to the right
            balanceRight(p);
            //if its still not balanced, we have a crooked shaped tree and have to try something else
            if (!isBalanced(p))
            {
                //balance to mae it the 'good' unbalanced tree
                balanceRight(p->left);
                //now re-balance the good case
                balanceLeft(p);
            }
        }
    }
}

template <class T>
void BSTree < T >::remove(const T& d)
{
    removeHelper(root, d);
}

template <class T>
void BSTree <T>::removeHelper(Node*& p, const T& d)
{
    if (p != 0)
    {
        if (p->data == d)
        {
            if (p->left == 0 && p->right == 0)
            {
                //leaf
                delete p;
                p = 0;
            }
            else if (p->left == 0)
            {
                //one child: right
                Node* p_next = p->right;
                delete p;
                p = p_next;
            }
            else if (p->right == 0)
            {
                //one child: left
                Node* p_next = p->left;
                delete p;
                p = p_next;
            }
            else
            {
                //two children
                p->data = maxHelper(p->left);
                removeHelper(p->left, p->data);
            }
            numberOfElements--;
        }
        else
        {
            removeHelper(p->left, d);
            removeHelper(p->right, d);
        }
    }
}

template <class T>
T BSTree <T>::max()
{
    if (root == 0)
    {
        throw "Tree Empty";
    }

    return maxHelper(root);
}

template <class T>
T BSTree <T>::maxHelper(Node* p)
{
    if (p->right == 0)
    {
        return p->data;
    }

    //maybe add return if doesnt work
    maxHelper(p->right);
}

//-------

//start min function
template <class T>
T BSTree<T>::min(){
    if (root == NULL) {
        throw "Tree Empty";
    }

    minHelper(root);
}//end min

//start minHelper
template <class T>
T BSTree<T>::minHelper(Node *p) {
    if (p->left == NULL){
        throw "Tree Empty";
    }

    return minHelper(p->left);
}//end minHelper

//start printInOrder
template <class T>
void BSTree<T>::printInOrder() {
    printInOrderHelper(root);
}//end printInOrder

//start printInOrderHelper
template<class T>
void BSTree<T>::printInOrderHelper(Node* p) {
    if (p != NULL) {
        printInOrderHelper(p->left);
        cout << p->data << endl;
        printInOrderHelper(p->right);
    }
}//end printInOrderHelper

//start printPreOrder
template <class T>
void BSTree<T>::printPreorder() {
    printPreorderHelper(root);
}//end printPreOrder

//start printPreOrderHelper
template <class T>
void BSTree<T>::printPreorderHelper(Node *p) {
    if(p != NULL) {
        cout << p->data << endl;
        printPreorderHelper(p->left);
        printPreorderHelper(p->right);
    }
}//end printPreOrderHelper

//start printPostOrder
template <class T>
void BSTree<T>::printPostOrder() {
    printPostOrderHelper(root);
}//end printPostOrder

//start printPostOrderHelper
template <class T>
void BSTree <T>::printPostOrderHelper(Node* p) {
    if (p != NULL) {
        printPostOrderHelper(p->left);
        printPostOrderHelper(p->right);
        cout << p->data << "\n";
    }
}//end printPostOrderHelper

//start search
template <class T>
bool BSTree<T>::search(const T &d) {
    return searchHelper(d, root);
}//end search

//start searchHelper
template <class T>
bool BSTree<T>::searchHelper(const T &d, Node *p) {
    numOfNodesParsed++;
    while(p != 0) {
        if (p->data == d) {
            cout << "Node" << d << " was found." << endl;
            cout << numOfNodesParsed << " were parsed." << endl;
            return true;
        } else if (p->data > d)
            return searchHelper(d, p->left);
        else if (p->data < d)
            return searchHelper(d, p->reight);
    }
    cout << "Node not found." << endl;
}//end searchHelper

//start heightOfTree
template <class T>
int BSTree<T>::heightOfTree() {
    return heightOfTreeHelper(root);
}//end heightOfTree

//start heightOfTreeHelper
template <class T>
int BSTree<T>::heightOfTreeHelper(Node *p) {
    int lHeight; //left
    int rHeight; //right

    if(p != 0){
        lHeight = heightOfTreeHelper(p->left);
        rHeight = heightOfTreeHelper(p->right);

        p->heightOfLeftSubtree = lHeight;
        p->heightOfRightSubtree = rHeight;
    } else
        return 0;

    if(lHeight > rHeight){
        cout << "Height of: " << lHeight << endl;
        return lHeight + 1;
    }else{
        cout << "Height of: " << rHeight << endl;
        return rHeight + 1;
    }
}//end heightOfTreeHelper

//start isBalanced
template <class T>
bool BSTree <T>::isBalanced(Node*& p) {
    heightOfTreeHelper(p);
    int leftHeight = p->heightOfLeftSubtree;
    int rightHeight = p->heightOfRightSubtree;
    if (leftHeight == rightHeight - 1 || leftHeight == rightHeight || leftHeight == rightHeight + 1)
        return true;
    else
        return false;
}//end isBalanced

//start balancedLeft
template <class T>
void BSTree <T>::balanceLeft(Node*& p) {
    if (p->left != 0) {
        Node* currentNode = p->left;
        Node* previousNode = currentNode->right;
        currentNode->right = p;
        p->left = previousNode;
        p = currentNode;
        cout << "is balancing..." << endl;
    }
}//end balanceLeft

// start balanceRight
template <class T>
void BSTree <T>::balanceRight(Node*& p) {
    if (p->right != 0) {
        Node* currentNode = p->right;
        Node* previousNode = currentNode->left;
        currentNode->left = p;
        p->right = previousNode;
        p = currentNode;
        cout << "is balancing..." << endl;
    }
}//end balanceRight