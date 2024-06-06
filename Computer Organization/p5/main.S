/**
 * @author Easton Herbon
 * @date 5/3/2024
 * 
 * @brief 
 * Implemention of RGB to Y conversion using NEON intrinsics as well as
 * CPU *only* conversions for images.
*/

#include <arm_neon.h>
#include "./ppm.hpp"

/*  Implement the RGB to Y conversion using NEON intrinsics here.
*/
extern "C" void ConvertToYNeon(PPM & src) {
    // Constants for RGB to Y conversion
    const float32x4_t coef_r = vdupq_n_f32(0.299f);
    const float32x4_t coef_g = vdupq_n_f32(0.587f);
    const float32x4_t coef_b = vdupq_n_f32(0.114f);
    
    // Iterate through the image data in chunks of 4 floats
    for (int i = 0; i < src.width * src.height * 4; i += 4) {
        // Load RGB data
        float32x4_t r = vld1q_f32(&src.data[i]);
        float32x4_t g = vld1q_f32(&src.data[i + 4]);
        float32x4_t b = vld1q_f32(&src.data[i + 8]);
        
        // Calculate Y component using the RGB to Y formula
        float32x4_t y = vmulq_f32(coef_r, r);
        y = vmlaq_f32(y, coef_g, g);
        y = vmlaq_f32(y, coef_b, b);
        
        // Store Y components back to gray_data
        vst1q_f32(reinterpret_cast<float*>(&src.gray_data[i / 4]), y);
    }
}

/*  Implement the RGB to Y conversion using only the CPU here.
*/
extern "C" void ConvertToYCPU(PPM & src) {
    // Constants for RGB to Y conversion
    const float coef_r = 0.299f;
    const float coef_g = 0.587f;
    const float coef_b = 0.114f;
    
    // Iterate through each pixel
    for (int i = 0; i < src.width * src.height; i++) {
        float r = src.data[i * 4]; // Red component
        float g = src.data[i * 4 + 1]; // Green component
        float b = src.data[i * 4 + 2]; // Blue component
        
        // Calculate Y component using the RGB to Y formula
        int y = static_cast<int>(coef_r * r + coef_g * g + coef_b * b);
        
        // Store Y component in gray_data
        src.gray_data[i] = y;
    }
}

