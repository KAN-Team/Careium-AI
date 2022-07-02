package com.example.careium.core.models

import android.content.Context
import com.example.careium.ml.NutritionModel
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import kotlin.math.abs

class DishNutritionRegression() : DeepLearning() {
    var context: Context? = null
    private val nutritionList: ArrayList<Float> = ArrayList()
    private var calories = 0f
    private var mass = 0f
    private var fats = 0f
    private var carbs = 0f
    private var proteins = 0f

    constructor(context: Context) : this() {
        this.context = context
    }


    fun predictNutritionModel(inputFeature: TensorBuffer): ArrayList<Float> {
        val model = NutritionModel.newInstance(context!!)

        // Runs model inference and gets result.
        val outputs = model.process(inputFeature)
        model.close()

        this.calories = outputs.outputFeature0AsTensorBuffer.floatArray[0]
        this.mass = outputs.outputFeature1AsTensorBuffer.floatArray[0]
        this.fats = outputs.outputFeature2AsTensorBuffer.floatArray[0]
        this.carbs = outputs.outputFeature3AsTensorBuffer.floatArray[0]
        this.proteins = outputs.outputFeature4AsTensorBuffer.floatArray[0]

        this.nutritionList.add(calories)
        this.nutritionList.add(mass)
        this.nutritionList.add(fats)
        this.nutritionList.add(carbs)
        this.nutritionList.add(proteins)

        unNormalizeNutrition()

        return this.nutritionList
    }

    private fun unNormalizeNutrition() {
        this.nutritionList[0] = abs(this.nutritionList[0] * 9485.8154296875f)
        this.nutritionList[1] = abs(this.nutritionList[1] * 7975f)
        this.nutritionList[2] = abs(this.nutritionList[2] * 875.541015625f)
        this.nutritionList[3] = abs(this.nutritionList[3] * 844.568603515625f)
        this.nutritionList[4] = abs(this.nutritionList[4] * 147.491821f)
    }

}