package com.example.careium.core.models

import android.content.Context
import android.util.Log
import com.example.careium.R
import com.example.careium.ml.ClassificationModel
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

class DishClassification() : DeepLearning() {
    var context: Context? = null

    constructor(context: Context) : this() {
        this.context = context
    }


    fun classifyDish(inputFeature: TensorBuffer): String {
        val model = ClassificationModel.newInstance(context!!)
        var index = 0
        var max = 0f

        // Runs model inference and gets result.
        val outputs = model.process(inputFeature)
        val outputList = outputs.outputFeature0AsTensorBuffer.floatArray
        model.close()


        for (i in 0..100) {
            if (outputList[i] > max) {
                max = outputList[i]
                index = i
            }
        }

        //Get Label Class
        val classesList = readClassesFile()
        return getDishLabel(classesList, index)
    }


    private fun readClassesFile(): ArrayList<String> {
        val classes: ArrayList<String> = ArrayList()
        val textFile: InputStream = (context!!.resources!!.openRawResource(R.raw.c1_classes))
        val reader = BufferedReader(InputStreamReader(textFile))
        var classLabel: String?

        while (true) {
            try {
                if (reader.readLine().also { classLabel = it } == null) break
                classes.add(classLabel!!)

            } catch (e: IOException) {
                Log.d("DLModels", "Error in Reading C1 Classes File")
            }
        }
        textFile.close()

        return classes
    }

    private fun getDishLabel(classList: ArrayList<String>, index: Int): String {
        return classList[index]
    }

}