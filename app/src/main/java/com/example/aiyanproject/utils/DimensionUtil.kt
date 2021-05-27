package com.example.aiyanproject.utils

import android.content.Context
import android.util.TypedValue

object DimensionUtil {
    fun dp2px(context: Context, dp: Float): Int {
        return (TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, dp,
            context.resources.displayMetrics
        ) + 0.5f).toInt()
    }

}