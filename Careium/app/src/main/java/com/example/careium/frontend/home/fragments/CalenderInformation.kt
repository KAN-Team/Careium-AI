package com.example.careium.frontend.home.fragments

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.YearMonth
import java.util.*


class CalenderInformation {
    var dayNum: Int
    var month: Int
    var startDay = 10
    var startMonth = 3
    private var year: Int
    private var day: String
    private var c: Calendar = Calendar.getInstance()
    private var current: Date = c.time
    private var startYear = 2022
    private var numOfFields = 0

    init {
        dayNum = c.get(Calendar.DAY_OF_MONTH)
        year = c.get(Calendar.YEAR) + 1900
        month = c.get(Calendar.MONTH) + 1
        day = current.toString().split(" ".toRegex()).toTypedArray()[0]
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getNumberOfDaysInMonth(year: Int, month: Int): Int {
        val yearMonthObject = YearMonth.of(year, month)
        return yearMonthObject.lengthOfMonth()
    }

    @JvmName("getNumOfFields1")
    @RequiresApi(Build.VERSION_CODES.O)
    fun getNumOfFields(): Int {
        numOfFields = dayNum
        for (i in month - 1 downTo startMonth + 1) {
            numOfFields += getNumberOfDaysInMonth(year, i)
        }
        numOfFields += getNumberOfDaysInMonth(year, startMonth) - startDay + 1
        return numOfFields
    }
}