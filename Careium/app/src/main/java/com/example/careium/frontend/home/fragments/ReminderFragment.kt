package com.example.careium.frontend.home.fragments

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.careium.R
import com.example.careium.databinding.FragmentReminderBinding
import com.example.careium.frontend.factory.*
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*


//TODO: Save Data after application closed
class ReminderFragment : Fragment(R.layout.fragment_reminder) {
    private lateinit var binding: FragmentReminderBinding
    private lateinit var picker: MaterialTimePicker
    private var calender: Calendar = Calendar.getInstance()
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent
    lateinit var sharedPreferences: SharedPreferences

    companion object {
        @JvmStatic
        fun newInstance() =
            ReminderFragment().apply {
            }
    }

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentReminderBinding.bind(view)

        createNotificationChannel()

        setClickListener(
            binding.breakfastTime,
            "Time For Your Breakfast!!",
            "Breakfast Time",
            binding.breakfastSetButton,
            binding.breakfastCancelButton
        )
        setClickListener(
            binding.lunchTime,
            "Time For Your Lunch!!",
            "Lunch Time",
            binding.lunchSetButton,
            binding.lunchCancelButton
        )
        setClickListener(
            binding.dinnerTime,
            "Time For Your Dinner!!",
            "Dinner Time",
            binding.dinnerSetButton,
            binding.dinnerCancelButton
        )
        setClickListener(
            binding.snackTime,
            "Time For Your Snack!!",
            "Snack Time",
            binding.snackSetButton,
            binding.snackCancelButton
        )

    }

    private fun setClickListener(
        mealTime: TextView,
        message: String,
        meal: String,
        SetButton: Button,
        CancelButton: Button
    ) {

        SetButton.setOnClickListener {
            setNotification(
                message,
                meal,
                mealTime,
                CancelButton, SetButton
            )
        }
        CancelButton.setOnClickListener {
            cancelNotification(mealTime)
            SetButton.visibility = View.VISIBLE
            CancelButton.visibility = View.GONE
        }
    }

    private fun cancelNotification(text: TextView) {
        Toast.makeText(context, "you cancel breakfast time, set a new time ", Toast.LENGTH_SHORT)
            .show()
        alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, ReminderNotification::class.java)
        pendingIntent =
            PendingIntent.getBroadcast(
                context,
                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )

        text.text = getString(R.string.time)
        alarmManager.cancel(pendingIntent)
    }

    @SuppressLint("ResourceAsColor")
    private fun setNotification(
        message: String,
        meal: String,
        mealText: TextView,
        cancel: Button,
        set: Button
    ) {
        val intent = Intent(context, ReminderNotification::class.java)
        intent.putExtra(
            titleExtra,
            "Careium Notification Manager"
        )
        intent.putExtra(
            messageExtra,
            message
        )
        pendingIntent = PendingIntent.getBroadcast(
            context,
            notificationID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setHour(12)
            .setMinute(0)
            .setTitleText(meal)
            .build()
        picker.show(parentFragmentManager, "Careium")
        picker.addOnPositiveButtonClickListener {
            if (picker.hour > 12) {
                (String.format("%02d", picker.hour - 12) + ":" + String.format(
                    "%02d",
                    picker.minute
                ) + "PM").also { mealText.text = it }
            } else {
                (String.format("%02d", picker.hour) + ":" + String.format(
                    "%02d",
                    picker.minute
                ) + "AM").also { mealText.text = it }

            }
            calender = Calendar.getInstance()
            calender[Calendar.HOUR_OF_DAY] = picker.hour
            calender[Calendar.MINUTE] = picker.minute
            calender[Calendar.SECOND] = 0
            calender[Calendar.MILLISECOND] = 0

            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP, calender.timeInMillis, pendingIntent
            )
            Toast.makeText(context, "Time Set successfully", Toast.LENGTH_SHORT).show()
            cancel.visibility = View.VISIBLE
            set.visibility = View.GONE
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = "CareiumReminderChannel"
            val description = "Channel for Reminder Manager"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channelID, name, importance)

            channel.description = description

            val notificationManager =
                context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }


    }
}