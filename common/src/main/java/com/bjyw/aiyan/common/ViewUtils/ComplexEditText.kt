package com.bjyw.aiyan.common.ViewUtils

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.text.InputFilter
import android.text.InputType
import android.text.TextWatcher
import android.text.method.NumberKeyListener
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bjyw.aiyan.common.R
import com.bjyw.aiyan.common.ViewUtils.DimensionUtil.dp2px
import com.zhy.autolayout.AutoLinearLayout
import com.zhy.autolayout.utils.AutoUtils
import com.zhy.autolayout.utils.DimenUtils

/**
 * 复合输入框，可以再输入框左边显示一些文字，右边显示图片的输入框
 */
class ComplexEditText : AutoLinearLayout {
    private var titleView: TextView? = null
    var editTextView: EditText? = null
        private set

    var imgView: ImageView? = null
        private set

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        init(context, attrs)
    }

    private fun init(
        context: Context,
        attrs: AttributeSet?
    ) {
        orientation = LinearLayout.HORIZONTAL
        addChildrenView(context)
        var minTitleWidth = dp2px(getContext(), 25f)
        var titleTextSize = dp2px(getContext(), 15f)
        var editTextSize = dp2px(getContext(), 15f)
        var editTextColor = resources.getColor(R.color.textBlack)
        var titleTextColor = resources.getColor(R.color.textBlack)
        var editHintTextColor = resources.getColor(R.color.textGray)
        val titleText: String
        val editText: String
        val editHint: String
        if (attrs != null) {
            val typedArray =
                context.obtainStyledAttributes(attrs, R.styleable.ComplexEditText)
            if (DimenUtils.isPxVal(typedArray.peekValue(R.styleable.ComplexEditText_titleMinWidth))) {
                minTitleWidth = typedArray.getDimensionPixelOffset(
                    R.styleable.ComplexEditText_titleMinWidth,
                    minTitleWidth
                )
                minTitleWidth = AutoUtils.getPercentWidthSizeBigger(minTitleWidth)
            } else {
                minTitleWidth = typedArray.getDimension(
                    R.styleable.ComplexEditText_titleMinWidth,
                    minTitleWidth.toFloat()
                ).toInt()
            }
            //            titleView.setMinWidth(minTitleWidth);
            if (DimenUtils.isPxVal(typedArray.peekValue(R.styleable.ComplexEditText_editTextSize))) {
                editTextSize = typedArray.getDimensionPixelOffset(
                    R.styleable.ComplexEditText_editTextSize,
                    editTextSize
                )
                editTextSize = AutoUtils.getPercentWidthSizeBigger(editTextSize)
            } else {
                editTextSize = typedArray.getDimension(
                    R.styleable.ComplexEditText_editTextSize,
                    editTextSize.toFloat()
                ).toInt()
            }
            editTextView!!.setTextSize(TypedValue.COMPLEX_UNIT_PX, editTextSize.toFloat())
            titleTextSize =
                if (DimenUtils.isPxVal(typedArray.peekValue(R.styleable.ComplexEditText_titleTextSize))) {
                    AutoUtils.getPercentWidthSizeBigger(
                        typedArray.getDimensionPixelSize(
                            R.styleable.ComplexEditText_titleTextSize,
                            dp2px(getContext(), 15f)
                        )
                    )
                } else {
                    typedArray.getDimension(
                        R.styleable.ComplexEditText_titleTextSize,
                        dp2px(getContext(), 15f).toFloat()
                    ).toInt()
                }
            titleView!!.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleTextSize.toFloat())
            editTextColor = typedArray.getColor(
                R.styleable.ComplexEditText_editTextColor,
                editTextColor
            )
            editTextView!!.setTextColor(editTextColor)
            editHintTextColor = typedArray.getColor(
                R.styleable.ComplexEditText_editHintColor,
                editHintTextColor
            )
            editTextView!!.setHintTextColor(editHintTextColor)
            titleTextColor = typedArray.getColor(
                R.styleable.ComplexEditText_titleTextColor,
                titleTextColor
            )
            titleView!!.setTextColor(titleTextColor)
            titleText = typedArray.getString(R.styleable.ComplexEditText_titleText).toString()
            titleView!!.text = titleText
            editHint = typedArray.getString(R.styleable.ComplexEditText_editHint).toString()
            editTextView!!.hint = editHint
            editText = typedArray.getString(R.styleable.ComplexEditText_editText).toString()
            editTextView!!.setText(editText)
            val anInt = typedArray.getInt(
                R.styleable.ComplexEditText_android_inputType,
                editTextView!!.inputType
            )
            editTextView!!.inputType = anInt
            val digits =
                typedArray.getText(R.styleable.ComplexEditText_android_digits)
            if (digits != null) {
                val ch = CharArray(digits.length)
                digits.toString().toCharArray(ch, 0, 0, digits.length)
                editTextView!!.filters = arrayOf<InputFilter>(
                    object : NumberKeyListener() {
                        override fun getAcceptedChars(): CharArray {
                            return ch
                        }

                        override fun getInputType(): Int {
                            return InputType.TYPE_CLASS_TEXT
                        }
                    }
                )

                //以下代码三星输入法无效
                //                inputView.setKeyListener(DigitsKeyListener.getInstance(digits.toString()));
            }
            val minEms = typedArray.getInt(R.styleable.ComplexEditText_android_minEms, 4)
            titleView!!.minEms = minEms
            val drawable =
                typedArray.getDrawable(R.styleable.ComplexEditText_rightDrawable)
            imgView!!.setImageDrawable(drawable)
            val rightVisibility = typedArray.getInt(
                R.styleable.ComplexEditText_rightDrawableVisibility,
                View.VISIBLE
            )
            imgView!!.visibility = rightVisibility
            typedArray.recycle()
        }
    }

    fun setEditViewOnFocusChangeListener(l: OnFocusChangeListener?) {
        editTextView!!.onFocusChangeListener = l
    }

    private fun addChildrenView(context: Context) {
        titleView = TextView(context)
        titleView!!.gravity = Gravity.CENTER_VERTICAL
        val titleLp = LinearLayout.LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.MATCH_PARENT
        )
        editTextView = EditText(context)
        editTextView!!.setSingleLine()
        editTextView!!.setPadding(0, 0, 0, 0)

        //        inputView.setInputType(EditorInfo.TYPE_CLASS_TEXT);
        editTextView!!.isFocusable = true
        editTextView!!.isFocusableInTouchMode = true
        //
        editTextView!!.setBackgroundResource(android.R.color.transparent)
        //        inputView.setBackgroundDrawable(new BitmapDrawable());
        editTextView!!.gravity = Gravity.CENTER_VERTICAL
        val inputLp =
            LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT)
        inputLp.weight = 1f
        imgView = ImageView(context)
        imgView!!.scaleType = ImageView.ScaleType.CENTER
        val imgLp = LinearLayout.LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.MATCH_PARENT
        )
        addView(titleView, titleLp)
        addView(editTextView, inputLp)
        addView(imgView, imgLp)
    }

    fun setTitleText(titleText: CharSequence?) {
        titleView!!.text = titleText
    }

    fun setEditHint(editHint: CharSequence?) {
        editTextView!!.hint = editHint
    }

    var isEditViewEnable: Boolean
        get() = editTextView!!.isEnabled
        set(enable) {
            editTextView!!.isEnabled = enable
        }

    var editText: CharSequence?
        get() = editTextView!!.text
        set(editText) {
            editTextView!!.setText(editText)
            editTextView!!.setSelection(editTextView!!.text.length)
        }

    fun setRightOnClickListener(l: OnClickListener?) {
        imgView!!.setOnClickListener(l)
    }

    fun addEditTextChangedListener(tw: TextWatcher?) {
        editTextView!!.removeTextChangedListener(tw)
        editTextView!!.addTextChangedListener(tw)
    }

    fun removeEditTextChangedListener(tw: TextWatcher?) {
        editTextView!!.removeTextChangedListener(tw)
    }

    fun setRightDrawble(bitmapDrawable: BitmapDrawable?) {
        imgView!!.setImageDrawable(bitmapDrawable)
    }

    fun setEditInputType(type: Int) {
        editTextView!!.inputType = type
    }

    fun setRightVisibility(visibility: Int) {
        imgView!!.visibility = visibility
    }
}