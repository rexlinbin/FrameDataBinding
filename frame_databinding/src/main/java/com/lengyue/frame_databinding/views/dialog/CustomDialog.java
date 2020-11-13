package com.lengyue.frame_databinding.views.dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;

public class CustomDialog extends Dialog {

    private Context mContext;
    private View contentView;
    private int gravity = Gravity.CENTER;

    public CustomDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public void setView(View view, int gravity){
        this.gravity = gravity;
        initWindow();
        contentView = view;
        setContentView(contentView);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
    }

    public void setView(View view){
        initWindow();
        contentView = view;
        setContentView(contentView);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
    }

    /**
     * 初始化window参数
     */
    private void initWindow() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window dialogWindow = getWindow();
        assert dialogWindow != null;
        dialogWindow.getDecorView().setPadding(0, 0, 0, 0);
        dialogWindow.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialogWindow.setBackgroundDrawableResource(android.R.color.transparent);
        dialogWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = mContext.getResources().getDisplayMetrics();
        if (gravity == Gravity.BOTTOM) {
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        }else{
            lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        }
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = gravity;
        dialogWindow.setAttributes(lp);
    }

    @Override
    public void show() {
        super.show();
        if (gravity == Gravity.BOTTOM) {
            TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
                    0f, Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 0);
            animation.setInterpolator(new DecelerateInterpolator());
            animation.setDuration(350);
            contentView.setAnimation(animation);
        }
    }

    @Override
    public void dismiss() {
        if (gravity == Gravity.BOTTOM) {
            TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
                    0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 1f);
            animation.setInterpolator(new DecelerateInterpolator());
            animation.setDuration(350);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    CustomDialog.super.dismiss();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            contentView.startAnimation(animation);
        }else{
            super.dismiss();
        }
    }

    /**
     * dp 转 px
     */
    protected int dp2px(float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, mContext.getResources().getDisplayMetrics());
    }

}
