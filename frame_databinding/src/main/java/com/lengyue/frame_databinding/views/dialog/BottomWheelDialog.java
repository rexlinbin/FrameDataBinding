package com.lengyue.frame_databinding.views.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.lengyue.frame_databinding.views.WheelView;

import java.util.List;


public class BottomWheelDialog<T> extends CustomDialog {

    private Context mContext;
    private OnDialogSureListener<T> onDialogSureListener;
    private WheelView<T> wheelView;
    private TextView sureTextView;
    private TextView cancelTextView;

    public BottomWheelDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
        initView();
    }

    private void initView(){
        LinearLayout contentView = new LinearLayout(mContext);
        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        contentView.setLayoutParams(linearLayoutParams);
        contentView.setBackgroundColor(Color.parseColor("#ffffff"));
        contentView.setOrientation(LinearLayout.VERTICAL);

        RelativeLayout relativeLayout = new RelativeLayout(mContext);
        RelativeLayout.LayoutParams relativeLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, dp2px(50));
        relativeLayout.setLayoutParams(relativeLayoutParams);

        sureTextView = new TextView(mContext);
        RelativeLayout.LayoutParams sureLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        sureLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        sureLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        sureLayoutParams.rightMargin = dp2px(15);
        sureTextView.setLayoutParams(sureLayoutParams);
        sureTextView.setText("确定");
        sureTextView.setTextSize(16);
        sureTextView.setTextColor(Color.parseColor("#358EF1"));
        sureTextView.setOnClickListener(v -> {
            if (onDialogSureListener != null){
                onDialogSureListener.onSure(wheelView.getSelectedItemData(), wheelView.getSelectedItemPosition());
            }
            dismiss();
        });
        relativeLayout.addView(sureTextView);

        cancelTextView = new TextView(mContext);
        RelativeLayout.LayoutParams cancelLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        cancelLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        cancelLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        cancelLayoutParams.leftMargin = dp2px(15);
        cancelTextView.setLayoutParams(cancelLayoutParams);
        cancelTextView.setText("取消");
        cancelTextView.setTextSize(16);
        cancelTextView.setTextColor(Color.parseColor("#358EF1"));
        cancelTextView.setOnClickListener(v -> dismiss());
        relativeLayout.addView(cancelTextView);

        contentView.addView(relativeLayout);

        View divider = new View(mContext);
        LinearLayout.LayoutParams dividerLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp2px(0.5f));
        divider.setLayoutParams(dividerLayoutParams);
        divider.setBackgroundColor(Color.parseColor("#eeeeee"));
        contentView.addView(divider);


        wheelView = new WheelView<>(mContext);
        LinearLayout.LayoutParams wheelViewLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dp2px(200));
        wheelView.setLayoutParams(wheelViewLayoutParams);
        wheelView.setTextSize(21, true);
        wheelView.setTextAlign(WheelView.TEXT_ALIGN_CENTER);
        wheelView.setDividerColor(Color.parseColor( "#358EF1"));
        wheelView.setDividerHeight(dp2px(0.5f));
        wheelView.setDividerType(WheelView.DIVIDER_TYPE_WRAP);
        wheelView.setSelectedItemPosition(0);
        wheelView.setSelectedItemTextColor(Color.parseColor("#358EF1"));
        wheelView.setShowDivider(true);
        wheelView.setVisibleItems(5);
        wheelView.setLineSpacing(10, true);
        contentView.addView(wheelView);
        setView(contentView, Gravity.BOTTOM);
    }

    public void setData(List<T> data){
        wheelView.setData(data);
    }

    public void setSureMsg(String msg){
        if (sureTextView != null){
            if (TextUtils.isEmpty(msg)){
                sureTextView.setText("确定");
            }else{
                sureTextView.setText(msg);
            }
        }
    }

    public void setCancelMsg(String msg){
        if (cancelTextView != null){
            if (TextUtils.isEmpty(msg)){
                cancelTextView.setText("取消");
            }else{
                cancelTextView.setText(msg);
            }
        }
    }

    public void setSelectIndex(int index){
        if (index > 0) {
            wheelView.setSelectedItemPosition(index);
        }

    }

    public void setOnDialogSureListener(OnDialogSureListener<T> onDialogSureListener){
        this.onDialogSureListener = onDialogSureListener;
    }

    public interface OnDialogSureListener<T>{
        void onSure(T data, int position);
    }
}
