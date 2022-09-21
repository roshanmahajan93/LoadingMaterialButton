package com.developermill.loadingmaterialbutton

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import com.google.android.material.button.MaterialButton

class LoadingMaterialButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var button: MaterialButton
    private var progressBar: ProgressBar
    private var text: String? = null
    private var buttonColor: Int = 0
    private var textColor: Int = 0
    private lateinit var colorStateList: ColorStateList

    companion object {

        @JvmStatic
        @BindingAdapter("app:onButtonClick")
        fun setOnClickListener(view: LoadingMaterialButton, onClick: View.OnClickListener?) {
            view.button.setOnClickListener(onClick)
        }

        @JvmStatic
        @BindingAdapter("app:buttonColor")
        fun setButtonColor(view: LoadingMaterialButton, color: Int) {
            view.buttonColor = color
            view.drawButton()
        }

        @JvmStatic
        @BindingAdapter("app:textColor")
        fun setTextColor(view: LoadingMaterialButton, color: Int) {
            view.textColor = color
            view.drawButton()
        }

        @JvmStatic
        @BindingAdapter("app:buttonText")
        fun setButtonText(view: LoadingMaterialButton, text: String) {
            view.text = text
            view.button.text = text
        }
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_loading_material_button, this, true)
        button = findViewById(R.id.btn)
        progressBar = findViewById(R.id.pb)
        val a = context.theme.obtainStyledAttributes(attrs, R.styleable.LoadingMaterialButton, 0, 0)
        text = a.getString(R.styleable.LoadingMaterialButton_buttonText)
        buttonColor = a.getColor(R.styleable.LoadingMaterialButton_buttonColor, 0)
        textColor = a.getColor(R.styleable.LoadingMaterialButton_textColor, 0)
        drawButton()
    }

    private fun drawButton() {


        if(button.isEnabled){
            button.setBackgroundColor(buttonColor)
        }else{
            button.setBackgroundColor(ContextCompat.getColor(context,R.color.disabledButtonBackground))
        }

        button.text = text

        if (button.isEnabled) {
            if (textColor != 0) {
                button.setTextColor(textColor)
                progressBar.indeterminateTintList = ColorStateList.valueOf(textColor)
            } else {
                button.setTextColor(if (isColorDark(buttonColor)) Color.WHITE else Color.BLACK)
                progressBar.indeterminateTintList = ColorStateList.valueOf(if (isColorDark(buttonColor)) Color.WHITE else Color.BLACK)
            }
        } else {
            button.setTextColor(Color.GRAY)
        }
    }

    fun setButtonOnClickListener(onClick: View.OnClickListener?) {
        button.setOnClickListener(onClick)
    }

    fun setButtonColor(color: Int) {
        buttonColor = color
        drawButton()
    }

    fun setFont(typeface: Typeface){
        button.setTypeface(typeface)
        drawButton()
    }

    fun setTextColor(color: Int) {
        textColor = color
        drawButton()
    }

    fun setButtonText(text: String) {
        this.text = text
        button.text = text
    }

    fun setButtonEnabled(isEnabled: Boolean) {
        button.isEnabled = isEnabled
        drawButton()
    }

    fun onStartLoading() {
        button.text = "Wait.."
        button.isClickable = false
        progressBar.visibility = View.VISIBLE
        button.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)
    }

    public fun onStopLoading() {
        button.isClickable = true
        button.text = text
        progressBar.visibility = View.GONE
        button.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_round_arrow_right_alt_24,0)
    }

    public fun isInProgress(): Boolean {
        return progressBar.visibility == View.VISIBLE
    }

    public fun setButtonOnClick(onClick: OnClickListener?) {
        button.setOnClickListener(onClick)
    }

    private fun fetchAccentColor(): Int {
        val typedValue = TypedValue()
        val a = context.obtainStyledAttributes(typedValue.data, intArrayOf(R.attr.buttonColor))
        val color = a.getColor(0, 0)
        a.recycle()
        return color
    }

}