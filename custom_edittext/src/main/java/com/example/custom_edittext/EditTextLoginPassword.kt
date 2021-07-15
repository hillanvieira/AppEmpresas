package com.example.custom_edittext

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.EditorInfo

class EditTextLoginPassword @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.editTextStyle //Otherwise, some attributes may not be used
) : EditTextLoginEmail(context, attrs, defStyleAttr) {

    private var editInputIcon: Drawable? = null // edit icon
    private var editCompleteIcon: Drawable? = null // Complete icon
    private var layoutBackground: Drawable? = null // Layout background
    private var isEditable = false // Whether to edit
    var errorShow = false

    init {
        //Initialize the properties of EditableTextView here
        this.transformationMethod = PasswordTransformationMethod.getInstance()
        this.setDefaultView()
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        val DRAWABLE_LEFT = 0
        val DRAWABLE_TOP = 1
        val DRAWABLE_RIGHT = 2
        val DRAWABLE_BOTTOM = 3

        Log.i("EDITTEXT", "CLICKED")
        if (errorShow) {
            Log.i("EDITTEXT", errorShow.toString())
            errorShow = false
            this.setDefaultView()
        }
        if (event.action == MotionEvent.ACTION_UP) {

            if (event.rawX >= this.right - compoundDrawables[DRAWABLE_RIGHT].bounds.width()) {

                if (this.transformationMethod == HideReturnsTransformationMethod.getInstance()) {
                    this.transformationMethod = PasswordTransformationMethod.getInstance()
                } else {
                    this.transformationMethod = HideReturnsTransformationMethod.getInstance()
                }
                return true
            }
        }
        performClick()
        return super.onTouchEvent(event)
    }

    override fun setErrorView() {
        this.setCompoundDrawablesWithIntrinsicBounds(
            resources.getDrawable(R.drawable.ic_lock),
            null,
            resources.getDrawable(R.drawable.ic_info),
            null
        )
        this.errorShow = true
    }

    override fun setDefaultView() {
        this.setCompoundDrawablesWithIntrinsicBounds(
            resources.getDrawable(R.drawable.ic_lock),
            null,
            resources.getDrawable(R.drawable.ic_eye),
            null
        )

        if (this.inputType == EditorInfo.TYPE_NULL) this.inputType = TYPE_TEXT_VARIATION_PASSWORD
        if (this.hint == null) this.hint = "Password"
    }

}