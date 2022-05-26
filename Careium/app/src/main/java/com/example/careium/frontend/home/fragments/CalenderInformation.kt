package com.example.careium.frontend.home.fragments
import android.os.Build
import androidx.annotation.RequiresApi
import java.time.YearMonth
import java.util.*

class CalenderInformation
{
    var daynum: Int
    var year: Int
    var month: Int
    var day: String
    var c = Calendar.getInstance()
    var current = c.time
    var StartDay = 10
    var StartMonth = 3
    var StartYear = 2022
    var numOfFeilds = 0

    init {
        daynum = current.date
        year = current.year + 1900
        month = current.month + 1
        day = current.toString().split(" ".toRegex()).toTypedArray()[0]
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getNumberOfDaysInMonth(year: Int, month: Int): Int {
        val yearMonthObject = YearMonth.of(year, month)
        return yearMonthObject.lengthOfMonth()
    }

    @JvmName("getNumOfFeilds1")
    @RequiresApi(Build.VERSION_CODES.O)
     fun getNumOfFeilds(): Int {
        numOfFeilds = daynum
        for (i in month - 1 downTo StartMonth + 1) {
            numOfFeilds += getNumberOfDaysInMonth(year, i)
        }
        numOfFeilds += getNumberOfDaysInMonth(year, StartMonth) - StartDay + 1
        return numOfFeilds
    }
}