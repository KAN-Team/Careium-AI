package com.example.careium.frontend.home.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.careium.R
import com.example.careium.core.adapters.RecommendationAdapter
import com.example.careium.core.database.authentication.InternetConnection
import com.example.careium.core.database.realtime.UserData
import com.example.careium.core.models.FoodCalories
import com.example.careium.core.models.User
import com.example.careium.databinding.FragmentRecommendationBinding
import com.example.careium.frontend.factory.Gender
import com.example.careium.frontend.factory.UserDataViewModel
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader


class RecommendationFragment : Fragment(R.layout.fragment_recommendation) {
    private lateinit var binding: FragmentRecommendationBinding
    private lateinit var userDataViewModel: UserDataViewModel
    private lateinit var foodAdapter: RecommendationAdapter
    private lateinit var foodArrayList: ArrayList<FoodCalories>

    companion object {
        fun newInstance() = RecommendationFragment().apply {}
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRecommendationBinding.bind(view)
        binding.waitContainer.visibility = View.VISIBLE

        foodArrayList = ArrayList()
        userDataViewModel = ViewModelProviders.of(this).get(UserDataViewModel::class.java)
        observeUserDataCallBackChange()

        if (InternetConnection.isConnected(this.requireContext())) {
            val userData = UserData()
            userData.getUserData(userDataViewModel)
        } else
            Toast.makeText(activity, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT)
                .show()
    }


    private fun observeUserDataCallBackChange() {
        userDataViewModel.mutableUserData.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                val bmr = calBMR(user)


                val foodCaloriesArray = readFoodCaloriesFile()
                for (obj in foodCaloriesArray) {
                    if ((obj.getFoodCalories() < (bmr)
                                && obj.getFoodCalories() >= (bmr) - 200)
                        || (obj.getFoodCalories() > (bmr)
                                && obj.getFoodCalories() <= (bmr) + 200)
                    ) {
                        foodArrayList.add(FoodCalories(obj.getFoodName(), obj.getFoodCalories()))
                    }
                }

                binding.recommendRecyclerView.layoutManager = LinearLayoutManager(this.context)
                foodAdapter = RecommendationAdapter(foodArrayList, this.requireContext())
                binding.recommendRecyclerView.adapter = foodAdapter
                binding.waitContainer.visibility = View.GONE

            }
        }
    }

    private fun calBMR(user: User): Int {
        var bmr = (10 * user.weight) + (6.25 * user.height) - (5 * user.age)
        if (user.gender == Gender.Male) bmr += 5
        else bmr -= 161
        bmr /= 3   // divided by 3, because we have 3 meals per day

        return bmr.toInt()
    }

    private fun readFoodCaloriesFile(): ArrayList<FoodCalories> {
        val foodCalories: ArrayList<FoodCalories> = ArrayList()
        val textFile: InputStream = (this.resources.openRawResource(R.raw.food_recommendation))
        val reader = BufferedReader(InputStreamReader(textFile))
        var record: String
        while (reader.readLine() != null) {
            try {
                if (reader.readLine().also { record = it } == null) break
                val recordList = record.split(",")
                foodCalories.add(FoodCalories(recordList[0], recordList[1].toInt()))
            } catch (e: IOException) {
                Log.d("DLModels", "Error in Reading C1 Classes File")
            }
        }

        textFile.close()
        return foodCalories
    }


}