package com.example.careium.frontend.home.fragments

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.GridView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.careium.R
import com.example.careium.core.database.authentication.InternetConnection
import com.example.careium.core.database.realtime.DishImage
import com.example.careium.databinding.FragmentAlbumBinding
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

class AlbumFragment : Fragment(R.layout.fragment_album) {
    private lateinit var binding: FragmentAlbumBinding
    private lateinit var gridView: GridView
    private var today: Int = 0
    private var month: Int = 0
    private var daysFrom: Int = 0
    @SuppressLint("SimpleDateFormat")
    private val dayFormat = SimpleDateFormat("dd")
    @SuppressLint("SimpleDateFormat")
    private val monthFormat = SimpleDateFormat("M")

    companion object {
        @JvmStatic
        fun newInstance() =
            AlbumFragment().apply {}
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAlbumBinding.bind(view)
        gridView = binding.gridview
        binding.waitContainer.visibility = View.VISIBLE
        today = LocalDateTime.now().dayOfMonth
        month = LocalDateTime.now().monthValue
        hookDateSection()
        connectToDataBase()
    }

    private fun getDate(from: Int): String {
        val cal: Calendar = Calendar.getInstance()
        cal.add(Calendar.DATE, -from)
        val sdf = SimpleDateFormat("EEEE MMM dd, yyyy", Locale.UK) // e.g. (Friday Mar 04, 2022)
        today = dayFormat.format(cal.time).toInt()
        month = monthFormat.format(cal.time).toInt()
        return sdf.format(cal.time)
    }

    private fun hookDateSection() {
        binding.textCalendarDate.text = getDate(0) // display the date of the current day

        binding.imageButtonPrevDate.setOnClickListener {
            daysFrom++
            binding.textCalendarDate.text = getDate(daysFrom)

            if (binding.imageButtonNextDate.visibility == View.INVISIBLE)
                binding.imageButtonNextDate.visibility = View.VISIBLE
            gridView.adapter = null
           binding.waitContainer.visibility = View.VISIBLE
            connectToDataBase()
        }

        binding.imageButtonNextDate.setOnClickListener {
            if (daysFrom > 0)
                daysFrom--
            binding.textCalendarDate.text = getDate(daysFrom)

            if (daysFrom == 0 && binding.imageButtonNextDate.visibility == View.VISIBLE)
                binding.imageButtonNextDate.visibility = View.INVISIBLE
            gridView.adapter = null
            binding.waitContainer.visibility = View.VISIBLE
            connectToDataBase()
        }
    }

    private fun connectToDataBase() {
        if (InternetConnection.isConnected(this.requireContext())) {
            context?.let {
                DishImage.fetchImages(
                    it,
                    gridView,
                    binding.waitContainer,
                    "$today $month")
            }
        } else {
            Toast.makeText(activity, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT)
                .show()
        }

    }

}


