package com.bjyw.aiyan.common.ViewUtils

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.widget.Button
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.bjyw.aiyan.common.R


class CountdownButton(mContext: Context, attrSet: AttributeSet) : androidx.appcompat.widget.AppCompatTextView(mContext, attrSet) {
    private val mHandler: Handler = Handler()
    private var mCountTime = 60


    init {
        this.text = resources.getText(R.string.get_verification_code)
    }

    /*
           倒计时，并处理点击事件
        */
    fun sendVerifyCode() {
        mHandler.postDelayed(countDown, 0)
    }

    /*
        倒计时
     */
    private val countDown = object : Runnable {
        @SuppressLint("SetTextI18n")
        override fun run() {
            this@CountdownButton.text = mCountTime.toString() + "s "
            this@CountdownButton.setBackgroundResource( R.drawable.count_bown)
            this@CountdownButton.setTextColor(ResourcesCompat.getColor(resources, R.color.hint_text, null))
            this@CountdownButton.isEnabled = false

            if (mCountTime > 0) {
                mHandler.postDelayed(this, 1000)
            } else {
                resetCounter()
            }
            mCountTime--
        }
    }

    fun removeRunable() {
        mHandler.removeCallbacks(countDown)
    }

    //重置按钮状态
    fun resetCounter(vararg text: String) {
        this.isEnabled = true
        if (text.isNotEmpty() && "" != text[0]) {
            this.text = text[0]
        } else {
            this.text = resources.getText(R.string.get_verification_code)
        }
        this.setBackgroundResource(R.drawable.shape_hint_login_40dp)
        this@CountdownButton.setTextColor(ResourcesCompat.getColor(resources, R.color.textColorPrimary, null))
        mCountTime = 60
    }
}