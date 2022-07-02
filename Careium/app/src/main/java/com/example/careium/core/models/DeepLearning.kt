package com.example.careium.core.models

import android.graphics.Bitmap
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.nio.ByteOrder

open class DeepLearning {

    fun loadImageBuffer(
        dish_image: Bitmap,
        dishImageWidth: Int,
        dishImageHeight: Int
    ): TensorBuffer {
        val inputFeature =
            TensorBuffer.createFixedSize(
                intArrayOf(1, dishImageWidth, dishImageHeight, 3), DataType.FLOAT32
            )

        val byteBuffer: ByteBuffer = ByteBuffer.allocate(4 * dishImageWidth * dishImageHeight * 3)
        byteBuffer.order(ByteOrder.nativeOrder())


        // make an dish image in 1D array
        val intValues = IntArray(dishImageWidth * dishImageHeight)
        dish_image.getPixels(
            intValues, 0, dish_image.width,
            0, 0, dish_image.width, dish_image.height
        )


        // iterate over pixels and extract R, G, and B values. Add to bytebuffer.
        var pixel = 0
        for (i in 0 until dishImageWidth) {
            for (j in 0 until dishImageHeight) {
                val value = intValues[pixel++] // RGB
                byteBuffer.putFloat((value shr 16 and 0xFF) / 255f)
                byteBuffer.putFloat((value shr 8 and 0xFF) / 255f)
                byteBuffer.putFloat((value and 0xFF) / 255f)
            }
        }
        inputFeature.loadBuffer(byteBuffer)
        return inputFeature

    }

}