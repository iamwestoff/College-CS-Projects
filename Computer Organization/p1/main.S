#include <errno.h>

// @author Easton Herbon

        .p2align    2
        .text
        .global     main

/* Registers:
   x30: link register
   x29: frame pointer
   x20: address of buffer in RAM into which read will deposit characters.
   x21: counts the number of characters to read

   0    stdin   cin
   1    stdout  cout
   2    stderr  cerr
*/

main:
    stp         x29, x30, [sp, -16]!
    mov         x29, sp

loop:  
    mov         w0, 0
    ldr         x1, =buffer
    mov         w2, 1
    bl          read

    cmp         w0, 0
    ble         exit

    mov         w0, 1
    ldr         x1, =buffer
    mov         w2, 1
    bl          write

    cmp         w0, 0
    ble         error_check

    b           loop

error_check:
    mov         w0, 1                   // if error detected, set write to #1 
    b           exit

exit:
    ldp         x29, x30, [sp], 16
    mov         w0, wzr                 // if no error, set write to #0
    ret

    .data

buffer: .space  1
        .end