package com.bjyw.aiyan.common.ViewUtils;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

import com.bjyw.aiyan.common.R;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * 说明: 可以折叠的TextView
 */
public class FoldTextView extends androidx.appcompat.widget.AppCompatTextView {

    private static final String DEFAULT_FOLD_TEXT = "收起";
    private static final String DEFAULT_OPEN_TEXT = "全文";

    private static final int LEFT = 0;
    private static final int HORIZONTAL = 1;
    private static final int RIGHT = 2;

    /**
     * 是否使用折叠效果
     */
    private boolean useFold = true;

    /**
     * 折叠的最大行数
     */
    private int mFoldMaxLines = 4;

    /**
     * open --> fold 的文字
     */
    private String mFoldText = DEFAULT_FOLD_TEXT;

    /**
     * fold --> open 的文字
     */
    private String mOpenText = DEFAULT_OPEN_TEXT;

    /**
     * 折叠的文字大小
     */
    private int mFoldTextSize;

    /**
     * 折叠的文字颜色
     */
    private int mFoldTextColor;

    private int mFoldMarginTop;
    private int mFoldMarginBottom;
    private int mFoldMarginLeft;
    private int mFoldMarginRight;

    /**
     * 折叠文字的位置
     */
    private int mFoldGravity = LEFT;

    private Paint mFoldPaint;

    /**
     * 当前是否打开
     */
    private boolean isOpen = false;

    /**
     * fold 遮罩的高度
     */
    private int mShadeHeight;

    private Rect mFoldRect;

    /**
     * 全文/收起点击的区域
     */
    private Rect mRect;

    /**
     * 折叠起来的高度
     */
    private int mFoldHeight;
    /**
     * 展开的高度
     */
    private int mOpenHeight;

    /**
     * 折叠动画的延时
     */
    private int mFoldDuration = 200;

    /**
     * 动画当前时刻的高度
     */
    private int mCurrentHeight;

    /**
     * onTouch 中用于判断折叠的点击事件
     */
    private boolean arrowFold;

    /**
     * 折叠的动画
     */
    private ValueAnimator mFoldAnimator;

    /**
     * 缓存item对象中的状态
     */
    private Map<Object, Boolean> mCache;

    /**
     * 当前缓存的对象
     */
    private Object mCurrentObj;

    public FoldTextView(Context context) {
        this(context, null);
    }

    public FoldTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FoldTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.FoldTextView);
        useFold = array.getBoolean(R.styleable.FoldTextView_useFold, false);
        mFoldMaxLines = array.getInt(R.styleable.FoldTextView_foldMaxLines, 4);
        mFoldText = array.getString(R.styleable.FoldTextView_foldText);
        if (TextUtils.isEmpty(mFoldText)) {
            mFoldText = DEFAULT_FOLD_TEXT;
        }
        mOpenText = array.getString(R.styleable.FoldTextView_openText);
        if (TextUtils.isEmpty(mOpenText)) {
            mOpenText = DEFAULT_OPEN_TEXT;
        }
        mFoldTextSize = array.getDimensionPixelSize(R.styleable.FoldTextView_foldTextSize, 10);
        mFoldTextColor = array.getColor(R.styleable.FoldTextView_foldTextColor, Color.BLUE);
        mFoldGravity = array.getInt(R.styleable.FoldTextView_foldGravity, LEFT);
        mFoldMarginTop = array.getDimensionPixelSize(R.styleable.FoldTextView_foldMarginTop, 10);
        mFoldMarginBottom = array.getDimensionPixelSize(R.styleable.FoldTextView_foldMarginBottom, 10);
        mFoldMarginLeft = array.getDimensionPixelSize(R.styleable.FoldTextView_foldMarginLeft, 0);
        mFoldMarginRight = array.getDimensionPixelSize(R.styleable.FoldTextView_foldMarginRight, 0);
        mFoldDuration = array.getInt(R.styleable.FoldTextView_foldDuration, 200);
        array.recycle();

        mFoldPaint = new Paint();
        mFoldPaint.setColor(mFoldTextColor);
        mFoldPaint.setTextSize(mFoldTextSize);
        mFoldPaint.setDither(true);
        mFoldPaint.setAntiAlias(true);
        mFoldPaint.setStyle(Paint.Style.FILL);

        mFoldRect = new Rect();
        mRect = new Rect();

        setClickable(true);

        mFoldAnimator = new ValueAnimator();
        mFoldAnimator.setDuration(mFoldDuration);
        mFoldAnimator.setInterpolator(new AccelerateDecelerateInterpolator());

        mCache = new HashMap<>();

    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (!useFold || getLineCount() <= mFoldMaxLines) {
            return;
        }

        mShadeHeight = getTextBounds(mFoldText, mFoldPaint).height() + mFoldMarginTop + mFoldMarginBottom;

        int height = getMeasuredHeight() + mShadeHeight;
        mOpenHeight = height;

        if (isOpen) {
            setMeasuredDimension(getMeasuredWidth(), mFoldAnimator.isRunning() ? mCurrentHeight : height);
        } else {
            int extraLines = getLineCount() - mFoldMaxLines;
            int extraHeight = (int) (extraLines * (getLineHeight() + getLineSpacingExtra()));
            mFoldHeight = height - extraHeight;
            setMeasuredDimension(getMeasuredWidth(), mFoldAnimator.isRunning() ? mCurrentHeight : (height - extraHeight));
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        if (!useFold || getLineCount() <= mFoldMaxLines || mFoldAnimator.isRunning()) {
            return;
        }

        mRect = getTextBounds(isOpen ? mFoldText : mOpenText, mFoldPaint);

        mRect.top = h - getPaddingBottom() - mShadeHeight;
        mRect.bottom = h - getPaddingBottom();

        int offsetX = 0;
        int length = mRect.width();

        if (mFoldGravity == LEFT) {
            offsetX = getPaddingRight() + mFoldMarginLeft;
        } else if (mFoldGravity == HORIZONTAL) {
            offsetX = getPaddingRight() + (w - getPaddingRight() - getPaddingLeft()) / 2 - mRect.width() / 2;
        } else if (mFoldGravity == RIGHT) {
            offsetX = w - getPaddingRight() - mRect.width() - mFoldMarginRight;
        }

        mRect.left = offsetX;
        mRect.right = offsetX + length;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                arrowFold = false;
                if (useFold && hasOnFold(event) && !mFoldAnimator.isRunning()) {
                    arrowFold = true;
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (arrowFold && hasOnFold(event)) {
                    isOpen = !isOpen;
                    updateState(isOpen);
                }
                break;
        }

        return super.onTouchEvent(event);
    }

    private void updateState(boolean isOpen) {
        updateState(isOpen, mFoldDuration);
    }


    /**
     * 更新折叠的高度
     *
     * @param isOpen
     * @param foldDuration
     */
    private void updateState(boolean isOpen, int foldDuration) {

        if (isOpen) {
            // 关闭 --> 打开
            mFoldAnimator.setIntValues(mFoldHeight, mOpenHeight);
        } else {
            // 打开 --> 关闭
            mFoldAnimator.setIntValues(mOpenHeight, mFoldHeight);
        }

        // 更新缓存的状态
        if (mCurrentObj != null) {
            mCache.put(mCurrentObj, isOpen);
        }

        mFoldAnimator.setDuration(foldDuration);
        mFoldAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurrentHeight = (int) animation.getAnimatedValue();
                requestLayout();
            }
        });

        mFoldAnimator.start();
    }

    /**
     * 是否在折叠按钮的范围内
     *
     * @param event
     * @return
     */
    private boolean hasOnFold(MotionEvent event) {
        return event.getX() >= mRect.left
                && event.getX() <= mRect.right
                && event.getY() >= mRect.top
                && event.getY() <= mRect.bottom;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawFold(canvas);
    }


    /**
     * 绘制折叠按钮
     *
     * @param canvas
     */
    private void drawFold(Canvas canvas) {

        if (!useFold || getLineCount() <= mFoldMaxLines) {
            return;
        }

        Drawable drawable = getBackground();
        int bgColor = Color.WHITE;
        if (drawable instanceof ColorDrawable) {
            bgColor = ((ColorDrawable) drawable).getColor();
        }

        mFoldPaint.setColor(bgColor);
        mFoldRect.left = 0;
        mFoldRect.top = getHeight() - mShadeHeight - getPaddingBottom();
        mFoldRect.right = getWidth();
        mFoldRect.bottom = getHeight() - getPaddingBottom();
        canvas.drawRect(mFoldRect, mFoldPaint);

        mFoldPaint.setColor(mFoldTextColor);

        int startX = 0;

        int baseLine = (int) (mFoldRect.top
                + mFoldMarginTop
                + getTextBounds(mOpenText, mFoldPaint).height() / 2
                + Math.abs(mFoldPaint.getFontMetrics().descent + mFoldPaint.getFontMetrics().ascent) / 2);


        if (mFoldGravity == LEFT) {
            mFoldPaint.setTextAlign(Paint.Align.LEFT);
            startX = getPaddingLeft() + mFoldMarginLeft;
        } else if (mFoldGravity == HORIZONTAL) {
            mFoldPaint.setTextAlign(Paint.Align.CENTER);
            startX = getPaddingLeft() + (getWidth() - getPaddingLeft() - getPaddingRight()) / 2;
        } else if (mFoldGravity == RIGHT) {
            mFoldPaint.setTextAlign(Paint.Align.RIGHT);
            startX = getWidth() - getPaddingRight() - mFoldMarginRight;
        }

        if (isOpen) {
            canvas.drawText(mFoldText, startX, baseLine, mFoldPaint);
        } else {
            canvas.drawText(mOpenText, startX, baseLine, mFoldPaint);
        }

    }

    private Rect getTextBounds(String text, Paint paint) {
        Rect rect = new Rect();
        if (!TextUtils.isEmpty(text)) {
            paint.getTextBounds(text, 0, text.length(), rect);
        }
        return rect;
    }


    @Override
    public void setText(CharSequence text, BufferType type) {
        if (TextUtils.isEmpty(text)) {
            text = "";
        }
        super.setText(text, type);
    }


    /**
     * 设置状态 打开/折叠
     *
     * @param isOpen
     */
    public void setState(boolean isOpen) {
        setState(isOpen, mFoldDuration);
    }

    public void setState(boolean isOpen, int foldDuration) {
        if (this.isOpen == isOpen || mFoldAnimator.isRunning()) {
            return;
        }
        this.isOpen = isOpen;
        updateState(isOpen, foldDuration);
    }

    /**
     * 获取当前的状态
     *
     * @return
     */
    public boolean getState() {
        return this.isOpen;
    }

    /**
     * 设置动画插值器
     *
     * @param interpolator
     */
    public void setInterpolator(Interpolator interpolator) {
        mFoldAnimator.setInterpolator(interpolator);
    }

    /**
     * 设置最大行数
     *
     * @param lines
     */
    public void setFoldMaxLines(int lines) {
        if (lines < 1) {
            lines = 1;
        }
        this.mFoldMaxLines = lines;
        requestLayout();
    }

    public int getFoldMaxLines() {
        return this.mFoldMaxLines;
    }

    /**
     * 设置 打开/折叠的文本
     *
     * @param foldText
     * @param openText
     */
    public void setFoldText(String foldText, String openText) {
        this.mFoldText = foldText;
        if (TextUtils.isEmpty(mFoldText)) {
            mFoldText = DEFAULT_FOLD_TEXT;
        }
        this.mOpenText = openText;
        if (TextUtils.isEmpty(mOpenText)) {
            mOpenText = DEFAULT_OPEN_TEXT;
        }
        requestLayout();
    }

    public String getFoldText() {
        return this.mFoldText;
    }

    public String getOpenText() {
        return this.mOpenText;
    }

    public void setFoldTextSize(int textSize) {
        this.mFoldTextSize = textSize;
        mFoldPaint.setTextSize(mFoldTextSize);
        requestLayout();
    }

    public int getFoldTextSize() {
        return this.mFoldTextSize;
    }

    public void setFoldTextColor(int textColor) {
        this.mFoldTextColor = textColor;
        invalidate();
    }

    public int getFoldTextColor() {
        return this.mFoldTextColor;
    }

    /**
     * 设置间距
     *
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    public void setFoldMargin(int left, int top, int right, int bottom) {
        this.mFoldMarginLeft = left < 0 ? mFoldMarginLeft : left;
        this.mFoldMarginTop = top < 0 ? mFoldMarginTop : top;
        this.mFoldMarginRight = right < 0 ? mFoldMarginRight : right;
        this.mFoldMarginBottom = bottom < 0 ? mFoldMarginBottom : bottom;
        requestLayout();
    }

    /**
     * 设置折叠文字的位置
     *
     * @param gravity
     */
    public void setFoldGravity(FoldGravity gravity) {
        if (gravity.getGravity() != mFoldGravity) {
            this.mFoldGravity = gravity.getGravity();
            requestLayout();
        }
    }

    /**
     * 是否使用折叠功能
     *
     * @param useFold
     */
    public void setUseFold(boolean useFold) {
        if (this.useFold != useFold) {
            this.useFold = useFold;
            requestLayout();
        }
    }

    public boolean isUseFold() {
        return this.useFold;
    }

    /**
     * 折叠动画的时间
     *
     * @param foldDuration
     */
    public void setFoldDuration(int foldDuration) {
        this.mFoldDuration = foldDuration;
    }

    public int getFoldDuration() {
        return this.mFoldDuration;
    }


    /**
     * 更新缓存的状态，防止列表中View复用的时候造成的错乱
     *
     * @param object
     */
    public void bindObjState(Object object) {
        if (!useFold) {
            return;
        }
        if (!mCache.containsKey(object)) {
            mCache.put(object, false);
        }
        mCurrentObj = object;

        isOpen = mCache.get(object);
        requestLayout();
    }

    // region ------------- enum -------------------

    public enum FoldGravity {

        LEFT(FoldTextView.LEFT),
        HORIZONTAL(FoldTextView.HORIZONTAL),
        RIGHT(FoldTextView.RIGHT);

        private int gravity;

        FoldGravity(int gravity) {
            this.gravity = gravity;
        }

        public int getGravity() {
            return this.gravity;
        }

    }

    // endregion -------------------------------------

}
