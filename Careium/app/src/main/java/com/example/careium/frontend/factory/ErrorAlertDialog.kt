package com.example.careium.frontend.factory

import android.view.View
import com.example.careium.databinding.LayoutErrorCustomViewBinding

class ErrorAlertDialog {
    companion object {
        fun alert(view: LayoutErrorCustomViewBinding, title: String, message: String) {
            view.layoutOverlay.visibility = View.VISIBLE
            view.errorContainer.visibility = View.VISIBLE
            view.errorInfoTitle.text = title
            view.errorInfoMessage.text = message
            view.errorInfoBtn.setOnClickListener {
                view.errorContainer.visibility = View.GONE
                view.layoutOverlay.visibility = View.GONE
            }
        }
    }
}