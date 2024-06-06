/*  @author     Easton Herbon
    @version    10/26/2022
    @file       test.S
    @brief      This is an assembly program to calculate the cosine of an angle

    @questions
    I do not really know where I went wrong, I tested a few things and I got to a point
    where some of it was working and then after a few hours of debugging and coding, I ended
    up getting more errors than I started with so I am not sure where I went wrong exactly.
    I did not do the utmost of testing after I made a lot of adjustments to the code, but
    this is around what I ended up trying to accomplish.
 */    
    
    .data
pi:             .float 3.14159265358979323846
msg_usage:      .asciz "Two numerical arguments must be given.\n"
msg_range:      .asciz "Number of terms is out of range.\n"
float_180:      .float 180.0
float_minus1:   .float -1.0

    .bss
    .lcomm          cosine, 8

    .p2align        2
    .text
    .global         main

// D2R function: Convert degrees to radians
//
// Convert degrees to radians using the formula:
//
//   radians = degrees * pi / 180
//
// The function takes a double as input and returns a double.
D2R:
    ldr d2, float_180
    fdiv d0, d0, d2
    ret

// Factorial function: Calculate factorial of n
//
// Calculate n! using the following formula:
//
//   n! = 1 * 2 * 3 * ... * n
//
// The function takes an integer as input and returns a double.
Factorial:
    mov x1, #1
    cmp x0, #1
    ble .L2
.L1:
    mul x1, x1, x0
    sub x0, x0, #1
    cmp x0, #1
    bgt .L1
.L2:
    scvtf d0, x1
    ret

// IntegerPower function: Calculate b^e
//
// Calculate b^e using the following formula:
//
//   b^e = 1 * b * b * ... * b
//
// The function takes two integer arguments b and e and returns a double.
IntegerPower:
    fmov d0, #1.0
    cmp w1, #0
    ble .L4
.L3:
    fmul d0, d0, d1
    sub w1, w1, #1
    cmp w1, #0
    bgt .L3
.L4:
    ret

main:
    // Setup stack frame
    stp x29, x30, [sp, -16]
    mov x29, sp

    // Check command line arguments
    ldr x1, [x29, #16]
    cmp x1, #3
    bne usage_error

    // Load angle in degrees and number of terms
    ldr x0, [x29, #24]
    bl atof
    str d0, [x29, #-8]

    ldr w0, [x29, #32]
    bl atoi
    mov w1, w0

    // Validate number of terms
    cmp w1, #1
    blt range_error
    cmp w1, #10
    bgt range_error

    // Call D2R function
    ldr d0, [x29, #-8]
    bl D2R
    fmov d1, d0

    // Initialize variables
    fmov d2, #1.0
    ldr d3, =float_minus1
    ldr d3, float_minus1

    // Calculate cosine using Taylor series expansion
loop:
    mov x2, #2
    mov x0, x2
    bl Factorial
    scvtf d4, x0

    // Calculate the terms
    ldr d4, [x29, #-8]
    fdiv d5, d4, d0

    // Toggle addition/subtraction
    fmul d5, d5, d3
    fadd d2, d2, d5
    fmul d3, d3, d3

    // Increment base and terms
    add x2, x2, #2
    add w1, w1, #1

    // Check if the desired number of terms has been reached
    cmp w1, w1
    b.le loop

    // Print the result
    ldr x0, [x29, #-8]
    bl printf
    bl cos

    // Cleanup and exit
    mov sp, x29
    ldp x29, x30, [sp], #16
    ret

// Error handling functions
usage_error:
    ldr x0, =msg_usage
    bl puts
    mov w0, #1
    b exit

range_error:
    ldr x0, =msg_range
    bl puts
    mov w0, #1
    b exit

// Exiting the program
exit:
    mov sp, x29
    ldp x29, x30, [sp], #16
    ret
