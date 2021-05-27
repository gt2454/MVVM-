package com.bjyw.aiyan.common.ViewUtils

import android.content.Context
import android.graphics.Color
import android.text.SpannableString
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.TextView.BufferType
import com.bjyw.aiyan.common.R
import com.bjyw.aiyan.common.ViewUtils.DimensionUtil.dp2px
import com.zhy.autolayout.AutoLinearLayout
import com.zhy.autolayout.utils.AutoUtils
import com.zhy.autolayout.utils.DimenUtils

/**
 * 复合输入框，左边显示文字（标题），中间显示文字（内容），右边显示图片的输入框
 */
class ComplexTextView : AutoLinearLayout {
    /**
     * 标题（左边文字）
     */
    private var titleView: TextView? = null

    /**
     * 内容（中间文字）
     */
    private var contentView: TextView? = null

    /**
     * 右侧图片
     */
    private var rightView: ImageView? = null

    /**
     * 标题最小值
     */
    private var titleMinEms = -1

    /**
     * 标题文字大小
     */
    private var titleTextSize = 0

    /**
     * 内容文字大小
     */
    private var contentTextSize = 0

    /**
     * 标题文字颜色
     */
    private var titleTextColor = 0

    /**
     * 内容文字颜色
     */
    private var contentTextColor = 0

    /**
     * 右侧图片
     */
    private var rightDrawableId = 0
    private var contentText: String? = null
    private var contentHint: String? = null
    private var contentGravity = Gravity.CENTER_VERTICAL
    var titleText: String? = null
        private set

    constructor(context: Context) : super(context) {
        initChildren(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(
        context,
        attrs
    ) {
        initAttrs(context, attrs)
        initChildren(context, attrs)
        Log.e("TAG", "title size $titleTextSize")
    }

    private fun initAttrs(
        context: Context,
        attrs: AttributeSet
    ) {
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.ComplexTextView)
        titleMinEms = typedArray.getInt(R.styleable.ComplexTextView_android_minEms, titleMinEms)
        titleTextSize = typedArray.getDimension(
            R.styleable.ComplexTextView_titleTextSize,
            dp2px(getContext(), 15f).toFloat()
        ).toInt()
        if (DimenUtils.isPxVal(typedArray.peekValue(R.styleable.ComplexTextView_titleTextSize))) {
            titleTextSize = AutoUtils.getPercentWidthSizeBigger(titleTextSize)
        }
        titleTextColor = typedArray.getColor(
            R.styleable.ComplexTextView_titleTextColor,
            resources.getColor(R.color.textBlack)
        )
        titleText = typedArray.getString(R.styleable.ComplexTextView_titleText)
        contentTextSize = typedArray.getDimension(
            R.styleable.ComplexTextView_contentTextSize,
            dp2px(getContext(), 15f).toFloat()
        ).toInt()
        if (DimenUtils.isPxVal(typedArray.peekValue(R.styleable.ComplexTextView_contentTextSize))) {
            contentTextSize = AutoUtils.getPercentWidthSizeBigger(contentTextSize)
        }
        contentTextColor = typedArray.getColor(
            R.styleable.ComplexTextView_contentTextColor,
            resources.getColor(R.color.textBlack)
        )
        contentText = typedArray.getString(R.styleable.ComplexTextView_contentText)
        contentHint = typedArray.getString(R.styleable.ComplexTextView_contentHint)
        contentGravity =
            typedArray.getInt(R.styleable.ComplexTextView_contentGravity, Gravity.CENTER_VERTICAL)
        rightDrawableId = typedArray.getResourceId(R.styleable.ComplexTextView_rightDrawable, -1)
        typedArray.recycle()
    }

    private fun initChildren(
        context: Context,
        attrs: AttributeSet?
    ) {
        titleView = TextView(context)
        titleView!!.gravity = Gravity.CENTER_VERTICAL
        if (titleMinEms != -1) {
            titleView!!.minEms = titleMinEms
        }
        val titleLp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        titleView!!.setTextColor(titleTextColor)
        titleView!!.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleTextSize.toFloat())
        titleView!!.text = titleText
        contentView = TextView(context)
        contentView!!.gravity = contentGravity
        val contentLp =
            LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT)
        contentLp.rightMargin = AutoUtils.getPercentWidthSize(30)
        contentLp.weight = 1f
        contentView!!.setTextColor(contentTextColor)
        contentView!!.setTextSize(TypedValue.COMPLEX_UNIT_PX, contentTextSize.toFloat())
        contentView!!.text = contentText
        if (contentView!!.lineCount > 1) {
            contentView!!.setLineSpacing(dp2px(getContext(), 5f).toFloat(), 1f)
        }
        contentView!!.hint = contentHint
        rightView = ImageView(context)
        val rightLp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        if (rightDrawableId > 0) rightView!!.setImageResource(rightDrawableId)
        addView(titleView, titleLp)
        addView(contentView, contentLp)
        addView(rightView, rightLp)
    }

    fun setContentViewOnClickListener(l: OnClickListener?) {
        rightView!!.setOnClickListener(l)
        contentView!!.setOnClickListener(l)
    }

    fun setTitleViewGravity(gravity: Int) {
        titleView!!.gravity = gravity
    }

    fun setRightVisibility(visibility: Int) {
        rightView!!.visibility = visibility
    }

    fun setTitle(title: String?) {
        titleText = title
        titleView!!.text = title
    }

    fun getContentText(): String {
        return contentView!!.text.toString()
    }

    fun setContentText(content: CharSequence?) {
        contentView!!.text = content
    }

    /**
     * 设置字体
     *
     * @param style [android.graphics.Typeface.BOLD]
     * [android.graphics.Typeface.BOLD_ITALIC]
     * [android.graphics.Typeface.ITALIC]
     */
    fun setContentViewStyle(style: Int) {
        contentView!!.setTypeface(contentView!!.typeface, style)
    }

    fun setContentText(content: SpannableString?, type: BufferType?) {
        contentView!!.setText(content, type)
    }

    fun addContentTextChangedListener(watcher: TextWatcher?) {
        contentView!!.addTextChangedListener(watcher)
    }

    fun setContentTextColor(color: Int) {
        contentView!!.setTextColor(color)
    }
    // start by dzb 2019年10月30日11:27:50
    /**
     * 设置内容和字体颜色
     * @param content 内容
     * @param color 字体颜色（当内容为默认内容‘请选择’时，字体颜色强制设置为灰色）
     */
    fun setContentTextAndColor(content: CharSequence, color: Int) {
        contentView!!.text = content
        if (content == "请选择") {
            contentView!!.setTextColor(Color.GRAY)
        } else {
            contentView!!.setTextColor(color)
        }
    }

    // end by dzb 2019年10月30日11:27:58
    fun setRightViewVisibility(isshow: Int) {
        rightView!!.visibility = isshow
    }
}