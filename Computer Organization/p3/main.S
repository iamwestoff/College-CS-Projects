/*
@authors
    Easton Herbon
    Josh Serpico
@brief
    Project 3 - Tail program to print the last n lines of code of a file.
*/

// ERROR MESSAGES
usage:      .asciz    "File name must be given.\n"
badopen:    .asciz    "Open file failed\n"
noline:     .asciz    "Allocating line buffer failed.\n"
badtail:    .asciz    "Allocating tail pointer buffer failed.\n"
badalloc:   .asciz    "Allocating a tail line failed.\n"

// CONSTANTS
    .EQU    MAX_LINE_LENGTH, 4096
    .EQU    TAILLEN, 10

// DATA
    .data
filename:           .skip   256                 // Buffer to store filename
lines:              .skip   TAILLEN * 8         // Buffer to store pointers to lines
line_buf:           .skip   MAX_LINE_LENGTH     // Buffer to store line read from file
circular_buffer:    .skip   TAILLEN * 8 * 2     // Double the size for additional metadata

// TESTING MSG'S
test_openfile:          .asciz  "open_file\n"
test_readfilelines:     .asciz  "read_file_lines\n"
test_readloop:          .asciz  "read_loop\n"
test_endreadloop:       .asciz  "end_read_loop\n"
test_printtail:         .asciz  "print_tail\n"
test_printloop:         .asciz  "print_loop\n"
test_getlinecustom:     .asciz  "get_line_custom\n"
test:                   .asciz "test\n"

// TEXT
    .p2align    2
    .text
    .global main
    .func main


/*
C version of the function's signature

Main function that executes the tail program, printing the last n lines of a file.

Parameters:
    x0      Number of command line arguments.
            - 2: Expected, indicating the program name and the filename.
    sp      Stack pointer.
    
Returns:
    None.   (Implicit)

Registers preserved:
    x0-x1, x8-x11, x16-x18, x28
*/

main:
    // CHECK COMMAND LINE ARGUMENTS
    cmp     x0, #2
    ldr     x0, [sp, #8]
    b.ne    usage_error

    // GET FILENAME
    ldr     x0, =filename
    add     x1, sp, #16
    str     x1, [x0]

    // OPEN FILE
    mov     x8, #2
    svc     #0
    cbz     x0, badopen_error

    // SAVE FILE DESCRIPTOR
    mov     x20, x0

    // READ FILE LINES
    ldr     x21, =lines
    bl      read_file_lines
    cbz     x0, noline_error

    // PRINT TAIL
    ldr     x22, =MAX_LINE_LENGTH
    ldr     x23, =TAILLEN
    bl      print_tail

    // FREE CIRCULAR BUFFER
    ldr     x22, =circular_buffer
    ldr     x23, [x22]
    ldr     x24, [x22, #8]
.free_loop:
    cmp     x23, x24
    b.eq    .end_free_loop
    ldr     x25, [x22, x23, lsl #3]
    cbz     x25, .next_slot
    bl      free
.next_slot:
    add     x23, x23, #1
    and     x23, x23, #255
    b       .free_loop
.end_free_loop:
    ret

/*
C version of the function's signature

Read function to read the lines from the given parameters.

Parameters:
    x0      Text file descriptor.
    x1      Pointer to the buffer to store the read line.
    x2      Size of the buffer.
    
Returns:
    None.   (Implicit)

Registers preserved:
    x0-x2, x8-x11, x16-x18, x28
*/

read_file_lines:
    // READ FILE LINES
    mov     x2, #MAX_LINE_LENGTH
    ldr     x21, =lines
.read_loop:
    ldr     x1, =line_buf

    // GET LINE FUNC
    // ALLOCATE LINE BUFFER WITH PROPER ALIGNMENT
    mov     x19, #8
    mov     x0, #MAX_LINE_LENGTH + 7
    add     x0, x0, x19
    bl      malloc
    cbz     x0, noline_error

    // ALIGN ALLCOATED MEMORY
    and     x0, x0, -8
    cbz     x0, noline_error

    ldrb    w9, [x0]

    // SET ARGUMENTS FOR READ
    mov     x0, x20
    mov     x1, #0
    mov     x2, 1

    // READ LINE FROM FILE
    mov     x8, 63
    svc     #0
    cbz     w0, read_failed

    // ALLOCATE MEMORY FOR LINE BUFFER
    mov     x0, #MAX_LINE_LENGTH + 1
    bl      malloc
    cbz     x0, noline_error

    strb    w9, [x0]

    add     x0, x0, #1

    cbz     x0, .end_read_loop

    // ALLOCATE MEMORY FOR LINE
    mov     x8, x0
    mov     x0, #MAX_LINE_LENGTH + 1
    bl      malloc
    cbz     x0, read_failed
    str     x0, [x21]
    add     x21, x21, #8

    // UPDATE CIRCULAR BUFFER
    ldr     x22, =circular_buffer
    ldr     x23, [x22]
    str     x8, [x22, x23, lsl #3]
    add     x23, x23, #1
    and     x23, x23, #255
    str     x23, [x22]

    ldr x0, =test_readloop
    bl printf

    b       .read_loop                  // ?????????????? loops multiple times before giving a segmentation fault not sure why, possibly just a logic issue

.end_read_loop:
    ldr x0, =test_endreadloop
    bl printf

    ret


/*
C version of the function's signature

Function to print the tail of the lines read from the file.

Parameters:
    None.
    
Returns:
    None.   (Implicit)

Registers preserved:
    x0-x3, x8-x9, x16-x17, x28
*/

print_tail:
    ldr x0, =test_printtail
    bl printf

    // PRINT TAIL
    mov     x25, x21
    mov     x26, #0
    ldr     x27, [x22]
    ldr     x28, [x23]
.print_loop:
    ldr x0, =test_printloop
    bl printf

    ldr     x0, [x25, x26, lsl #3]
    bl      printf
    add     x26, x26, #1
    cmp     x26, x28
    bne    .print_loop
    ret

read_failed:
    ldr     x0, =badalloc
    bl      perror
    b       exit

usage_error:
    ldr     x0, =usage
    bl      perror
    b       exit

badopen_error:
    ldr     x0, =badopen
    bl      perror
    b       exit

noline_error:
    ldr     x0, =noline
    bl      perror
    b       exit



// SYSTEM EXIT
exit:
    mov     x8, #93
    svc     #0



/*
    Everything was tested in one way or another but for some reason the read loop
    is forcing to loop many many times before a segmentation fault happens. A little
    confused and neither Josh nor I could really figure out what was going on.

    Valgrind returned no errors or leaks.

    Comments are done.
 */




/*

TODO
-COMMENTING IS GRADED - Functions must be commented in
-REQUIRED ERROR MESSAGES:                                            -- DONE --
    usage:        .asciz    "File name must be given."                  ./
    badopen:    .asciz    "Open file failed"                            ./
    noline:        .asciz    "Allocating line buffer failed."           ./
    badtail:    .asciz    "Allocating tail pointer buffer failed."      ./
    badalloc:    .asciz    "Allocating a tail line failed."             ./

ARE ABLE TO USE THESE FILE HANDLING CALLS:
    open
    close
    read

LIKELY TO USE
    malloc
    free
    memset
    strlen -- func call is dangerous... Why?
    puts
    printf

MUST USE PERROR() TO PRINT REASON WHY NOT PRINTING
    "Notice that my error string labeled badopen has 
    no trailing punctuation like all the other error messages. 
    This is because it must be used as the prefix to the 
    message printed by perror. perror will supply a : between 
    the prefix and the error message it prints."

WHAT TO TURN IN
    I will turn in the code. You will have to submit a .txt file
    containing my name I think. And that should be it.
*/ 
