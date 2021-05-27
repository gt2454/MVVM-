package com.bjyw.aiyan.common.ViewUtils;

import android.widget.TextView;

/**
 *
 * 说明: 在Drawable上的点击事件
 */

public interface OnDrawableClickListener {

    int INVALID_TOUCH = -1;

    /**
     * 触摸在drawableLeft
     */
    int TOUCH_DRAWABLE_LEFT = 0;
    /**
     * 触摸在 drawableRight
     */
    int TOUCH_DRAWABLE_RIGHT = 1;

    void onDrawableLeftClickListener(TextView view);

    void onDrawableRightClickListener(TextView view);

}

