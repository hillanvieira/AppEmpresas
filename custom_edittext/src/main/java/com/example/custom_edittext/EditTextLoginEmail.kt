package com.example.custom_edittext

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
import android.util.AttributeSet

import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.AppCompatEditText


open class EditTextLoginEmail @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.editTextStyle //Otherwise, some attributes may not be used
) : AppCompatEditText(context, attrs, defStyleAttr) {

    private var editInputIcon: Drawable? = null // edit icon
    private var editCompleteIcon: Drawable? = null // Complete icon
    private var layoutBackground: Drawable? = null // Layout background
    private var isEditable = false // Whether to edit

    override fun performClick(): Boolean {
        return super.performClick()
    }

    init {
        //Initialize the properties of EditableTextView here
        this.setDefaultView()
    }

    open fun setErrorView() {
        this.setCompoundDrawablesWithIntrinsicBounds(
            resources.getDrawable(R.drawable.ic_letter),
            null,
            resources.getDrawable(R.drawable.ic_info),
            null
        )
    }

    open fun setDefaultView() {
        this.setCompoundDrawablesWithIntrinsicBounds(
            resources.getDrawable(R.drawable.ic_letter),
            null,
            null,
            null
        )

        if (this.inputType == EditorInfo.TYPE_NULL) this.inputType =
            TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        if (this.hint == null) this.hint = "Email"
    }

}

