package com.johnugwuadi.keyandtempo.ui.search

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent

class SearchEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : androidx.appcompat.widget.AppCompatEditText(context, attrs) {

    interface OnBackPressedListener {
        fun onBackPressed()
    }

    var listener: OnBackPressedListener? = null

    fun setOnBackListener(onBackListener: OnBackPressedListener) {
        listener = onBackListener
    }

    override fun onKeyPreIme(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            listener?.onBackPressed()
            return false
        }
        return super.onKeyPreIme(keyCode, event)
    }
}