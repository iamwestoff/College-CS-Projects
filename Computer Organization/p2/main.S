// @author Easton Herbon
// @brief Project 2 - Singly Sorted Linked List

/*
    I couldnt really figure out a lot past here. I kept getting segmentation faults
    and I think I can pin point where they are in my insert and delete portions of the
    code but I do not really know what direction to go to make it work.

    I attempted to do the insert by loading the character, then converting ASCII to an 
    int, multiplying for decimal conversion, then setting the payload to 0. Then kinda
    continuing to do the same thing for the rest of it. Then allocating the memory in
    done_conversion and loading and storing addresses. Then with the delete node I tried
    to just find the numbers and if it worked then it would delete the node in delete_found.

    I tried using gdb p2 for debugging using breakpoints but I am a little confused on how
    to just read it like that. I would really appreciate input on what went wrong here because
    I had input all the way up to the insert and delete portions.

    On the bright side valgrind didnt show any errors..
 */

    .p2align     2
    .data
    
head_address:   .asciz  "head points to: %x\n"
node_info:      .asciz  "node at 0x%8x contains payload: %lu next: 0x%8x\n"
bad_malloc:     .asciz  "malloc() failed\n"
buffer:         .space  8
head:           .xword 0                                                        // to fix null_input error

.section Node
    .struct 0
Node.next:  .struct Node.next + 8
Node.payload:   .struct Node.payload + 4

	.text
	.global	main

main:                       
    mov x20, 0
    ldr x21, =buffer

    cmp x0, 2
    blt null_input

    mov x22, x0
    mov x25, x0

read:
    cmp x25, 2
    blt print_head_address
    sub x25, x25, #1

    mov x23, #8
    add x2, x1, x23

    ldrb w24, [x2]
    cmp w24, #112
    ble delete_node
    b insert_node

print_head_address:
    mov x0, x20
    ldr x0, =head_address
    bl printf
    b print_node_info

print_node_info:
    mov x0, x20
    ldr x0, =node_info
    bl printf
    cmp x22, 2
    blt print_node_info
    ret

print_bad_malloc:
    ldr x0, =bad_malloc 
    bl printf
    ret

// node insertion and delete func's
insert_node:
    ldr x24, [x2]
    sub x24, x24, #'0'
    mov x25, 10
    ldr x26, =0

    convert_loop:
        ldrb w23, [x2], #1
        cbz w23, done_conversion
        sub w23, w23, #'0'
        mul x26, x26, x25
        add x26, x26, x23
        b convert_loop

    done_conversion:
        ldr x30, =Node
        ldr x1, [x30, #Node.next]
        bl malloc
        mov x22, x0

        str x26, [x22, #Node.payload]
        str x1, [x22, #Node.next]

        mov x1, x22
        ret

delete_node:
    ldr x24, [x2]
    sub x24, x24, #'0'
    mov x25, 10
    ldr x26, =0

    convert_loop_del:
        ldrb w23, [x2], #1
        cbz w23, done_conversion_del
        sub w23, w23, #'0'
        mul x26, x26, x25
        add x26, x26, x23
        b convert_loop_del

    done_conversion_del:
        ldr x1, =head

    traverse_loop:
        ldr x2, [x1, #Node.payload]
        cmp x2, x26
        b.eq delete_found
        ldr x1, [x1, #Node.next]
        cbnz x1, traverse_loop

    delete_found:
        ldr x3, [x1, #Node.next]
        str x3, [x1, #Node.next]
        ret

null_input:
    ret

    .end
