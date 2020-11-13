package com.lengyue.frame_databinding.views;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;

import com.lengyue.frame_databinding.R;

/**
 * 圆形进度条控件
 * Author: JueYes jueyes_1024@163.com
 * Time: 2019-08-07 15:38
 */

public class CircularProgressView extends View {

    private Paint mBackPaint, mProgPaint;   // 绘制画笔
    private RectF mProgRectF;       // 绘制区域
    private RectF mBackRectF;       // 绘制区域
    private int[] mBackColorArray;  // 圆环渐变色
    private int[] mProgColorArray;  // 圆环渐变色
    private int mProgress;      // 圆环进度(0-100)

    public CircularProgressView(Context context) {
        this(context, null);
    }

    public CircularProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircularProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        @SuppressLint("Recycle")
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircularProgressView);

        // 初始化背景圆环画笔
        mBackPaint = new Paint();
        mBackPaint.setStyle(Paint.Style.STROKE);    // 只描边，不填充
//        mBackPaint.setStrokeCap(Paint.Cap.ROUND);   // 设置圆角
        mBackPaint.setAntiAlias(true);              // 设置抗锯齿
        mBackPaint.setDither(true);                 // 设置抖动
        mBackPaint.setStrokeWidth(typedArray.getDimension(R.styleable.CircularProgressView_backWidth, 5));
        mBackPaint.setColor(typedArray.getColor(R.styleable.CircularProgressView_backColor, Color.LTGRAY));

        // 初始化进度圆环画笔
        mProgPaint = new Paint();
        mProgPaint.setStyle(Paint.Style.STROKE);    // 只描边，不填充
//        mProgPaint.setStrokeCap(Paint.Cap.ROUND);   // 设置圆角
        mProgPaint.setAntiAlias(true);              // 设置抗锯齿
        mProgPaint.setDither(true);                 // 设置抖动
        mProgPaint.setStrokeWidth(typedArray.getDimension(R.styleable.CircularProgressView_progWidth, 10));
        mProgPaint.setColor(typedArray.getColor(R.styleable.CircularProgressView_progColor, Color.BLUE));

        // 初始化进度圆环渐变色
        int progStartColor = typedArray.getColor(R.styleable.CircularProgressView_progStartColor, -1);
        int progEndColor = typedArray.getColor(R.styleable.CircularProgressView_progEndColor, -1);
        if (progStartColor != -1 && progEndColor != -1) mProgColorArray = new int[]{progStartColor, progEndColor};
        else mProgColorArray = null;

        int backStartColor = typedArray.getColor(R.styleable.CircularProgressView_backStartColor, -1);
        int backEndColor = typedArray.getColor(R.styleable.CircularProgressView_backEndColor, -1);
        if (backStartColor != -1 && backEndColor != -1) mBackColorArray = new int[]{backStartColor, backEndColor};
        else mBackColorArray = null;

        // 初始化进度
        mProgress = typedArray.getInteger(R.styleable.CircularProgressView_progress, 0);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int viewWide = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        int viewHigh = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
        int mRectLength = (int) ((viewWide > viewHigh ? viewHigh : viewWide) - (mBackPaint.getStrokeWidth() > mProgPaint.getStrokeWidth() ? mBackPaint.getStrokeWidth() : mProgPaint.getStrokeWidth()));
        int mRectL = getPaddingLeft() + (viewWide - mRectLength) / 2;
        int mRectT = getPaddingTop() + (viewHigh - mRectLength) / 2;
        mProgRectF = new RectF(mRectL, mRectT, mRectL + mRectLength, mRectT + mRectLength);
        float strokediff = (mBackPaint.getStrokeWidth() - mProgPaint.getStrokeWidth()) / 2;
        mBackRectF = new RectF(mRectL + strokediff, mRectT + strokediff, mRectL + mRectLength - strokediff, mRectT + mRectLength - strokediff);

        // 设置进度圆环渐变色
        if (mProgColorArray != null && mProgColorArray.length > 1)
            mProgPaint.setShader(new LinearGradient(0, 0, 0, getMeasuredWidth(), mProgColorArray, null, Shader.TileMode.MIRROR));

        if (mBackColorArray != null && mBackColorArray.length > 1)
            mBackPaint.setShader(new LinearGradient(0, 0, 0, getMeasuredWidth(), mBackColorArray, null, Shader.TileMode.MIRROR));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(mProgRectF, 270 + 360f * mProgress / 100, 360 - 360f * mProgress / 100, false, mBackPaint);
        canvas.drawArc(mBackRectF, 270, 360f * mProgress / 100, false, mProgPaint);
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * 获取当前进度
     *
     * @return 当前进度（0-100）
     */
    public int getProgress() {
        return mProgress;
    }

    /**
     * 设置当前进度
     *
     * @param progress 当前进度（0-100）
     */
    public void setProgress(int progress) {
        this.mProgress = progress;
        invalidate();
    }

    /**
     * 设置当前进度，并展示进度动画。如果动画时间小于等于0，则不展示动画
     *
     * @param progress 当前进度（0-100）
     * @param animTime 动画时间（毫秒）
     */
    public void setProgress(int progress, long animTime) {
        if (animTime <= 0) setProgress(progress);
        else {
            ValueAnimator animator = ValueAnimator.ofInt(mProgress, progress);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mProgress = (int) animation.getAnimatedValue();
                    invalidate();
                }
            });
            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            animator.setDuration(animTime);
            animator.start();
        }
    }

    @BindingAdapter(value = "progress")
    public static void setProgress(CircularProgressView view, int progress){
        ValueAnimator animator = ValueAnimator.ofInt(100, progress);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int mProgress = (int) animation.getAnimatedValue();
                view.setProgress(mProgress);
            }
        });
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(10 *(100 - progress));
        animator.start();
    }

    /**
     * 设置背景圆环宽度
     *
     * @param width 背景圆环宽度
     */
    public void setBackWidth(int width) {
        mBackPaint.setStrokeWidth(width);
        invalidate();
    }

    /**
     * 设置背景圆环颜色
     *
     * @param color 背景圆环颜色
     */
    public void setBackColor(@ColorRes int color) {
        mBackPaint.setColor(ContextCompat.getColor(getContext(), color));
        invalidate();
    }

    /**
     * 设置进度圆环宽度
     *
     * @param width 进度圆环宽度
     */
    public void setProgWidth(int width) {
        mProgPaint.setStrokeWidth(width);
        invalidate();
    }

    /**
     * 设置进度圆环颜色
     *
     * @param color 景圆环颜色
     */
    public void setProgColor(@ColorRes int color) {
        mProgPaint.setColor(ContextCompat.getColor(getContext(), color));
        mProgPaint.setShader(null);
        invalidate();
    }

    /**
     * 设置进度圆环颜色(支持渐变色)
     *
     * @param startColor 进度圆环开始颜色
     * @param firstColor 进度圆环结束颜色
     */
    public void setProgColor(@ColorRes int startColor, @ColorRes int firstColor) {
        mProgColorArray = new int[]{ContextCompat.getColor(getContext(), startColor), ContextCompat.getColor(getContext(), firstColor)};
        mProgPaint.setShader(new LinearGradient(0, 0, 0, getMeasuredWidth(), mProgColorArray, null, Shader.TileMode.MIRROR));
        invalidate();
    }

    /**
     * 设置进度圆环颜色(支持渐变色)
     *
     * @param colorArray 渐变色集合
     */
    public void setProgColor(@ColorRes int[] colorArray) {
        if (colorArray == null || colorArray.length < 2) return;
        mProgColorArray = new int[colorArray.length];
        for (int index = 0; index < colorArray.length; index++)
            mProgColorArray[index] = ContextCompat.getColor(getContext(), colorArray[index]);
        mProgPaint.setShader(new LinearGradient(0, 0, 0, getMeasuredWidth(), mProgColorArray, null, Shader.TileMode.MIRROR));
        invalidate();
    }
}
