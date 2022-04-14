package com.example.careium.core.models

import android.content.Context
import com.example.careium.ml.NutritionModel
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer

class DishNutritionRegression() : DeepLearning() {
    var context: Context? = null

    constructor(context: Context) : this() {
        this.context = context
    }


    fun predictNutritionModel(inputFeature: TensorBuffer): ArrayList<Float> {
        val nutritionList: ArrayList<Float> = ArrayList()
        val model = NutritionModel.newInstance(context!!)

        // Runs model inference and gets result.
        val outputs = model.process(inputFeature)
        model.close()

        val calories = outputs.outputFeature0AsTensorBuffer.floatArray[0]
        val mass = outputs.outputFeature1AsTensorBuffer.floatArray[0]
        val fats = outputs.outputFeature2AsTensorBuffer.floatArray[0]
        val carbs = outputs.outputFeature3AsTensorBuffer.floatArray[0]
        val proteins = outputs.outputFeature4AsTensorBuffer.floatArray[0]

        nutritionList.add(calories)
        nutritionList.add(mass)
        nutritionList.add(fats)
        nutritionList.add(carbs)
        nutritionList.add(proteins)

        return nutritionList
    }

}