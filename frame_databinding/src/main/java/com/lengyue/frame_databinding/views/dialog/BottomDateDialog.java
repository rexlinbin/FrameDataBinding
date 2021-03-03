package com.lengyue.frame_databinding.views.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
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
import androidx.annotation.Nullable;

import com.lengyue.frame_databinding.views.WheelView;
import com.lengyue.frame_databinding.views.datepicker.DatePickerView;
import com.lengyue.frame_databinding.views.datepicker.base.BaseDatePickerView;
import com.lengyue.frame_databinding.views.datepicker.ex.DayWheelView;
import com.lengyue.frame_databinding.views.datepicker.ex.MonthWheelView;
import com.lengyue.frame_databinding.views.datepicker.ex.YearWheelView;
import com.lengyue.frame_databinding.views.datepicker.listener.OnDateSelectedListener;

import java.util.Date;


public class BottomDateDialog extends CustomDialog {

    private Context mContext;
    private LinearLayout contentView;
    private OnDialogSureListener onDialogSureListener;
    private Date selectDate = new Date();
    private DatePickerView datePickerView;

    public BottomDateDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
        initView();
    }

    private void initView(){
        contentView = new LinearLayout(mContext);
        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        contentView.setLayoutParams(linearLayoutParams);
        contentView.setBackgroundColor(Color.parseColor("#ffffff"));
        contentView.setOrientation(LinearLayout.VERTICAL);

        RelativeLayout relativeLayout = new RelativeLayout(mContext);
        RelativeLayout.LayoutParams relativeLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, dp2px(50));
        relativeLayout.setLayoutParams(relativeLayoutParams);

        TextView sureTextView = new TextView(mContext);
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
                onDialogSureListener.onSure(selectDate);
            }
            dismiss();
        });
        relativeLayout.addView(sureTextView);

        TextView cancelTextView = new TextView(mContext);
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

        initDatePicker();

        setView(contentView, Gravity.BOTTOM);
    }

    private void initDatePicker(){
        datePickerView = new DatePickerView(mContext);
        LinearLayout.LayoutParams dateLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dp2px(200));
        datePickerView.setLayoutParams(dateLayoutParams);
        datePickerView.setTextSize(24, true);
        datePickerView.setShowLabel(false);
        datePickerView.setTextBoundaryMargin(16, true);
        datePickerView.setShowDivider(true);
        datePickerView.setDividerType(WheelView.DIVIDER_TYPE_FILL);
        datePickerView.setDividerColor(Color.parseColor("#9e9e9e"));
        datePickerView.setDividerPaddingForWrap(10, true);
        YearWheelView yearWv3 = datePickerView.getYearWv();
        MonthWheelView monthWv3 = datePickerView.getMonthWv();
        DayWheelView dayWv3 = datePickerView.getDayWv();
        yearWv3.setIntegerNeedFormat("%d年");
        monthWv3.setIntegerNeedFormat("%d月");
        dayWv3.setIntegerNeedFormat("%02d日");
        yearWv3.setCurvedArcDirection(WheelView.CURVED_ARC_DIRECTION_LEFT);
        yearWv3.setCurvedArcDirectionFactor(0.65f);
        dayWv3.setCurvedArcDirection(WheelView.CURVED_ARC_DIRECTION_RIGHT);
        dayWv3.setCurvedArcDirectionFactor(0.65f);

        datePickerView.setOnDateSelectedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(BaseDatePickerView datePickerView, int year,
                                       int month, int day, @Nullable Date date) {
                selectDate = date;
            }
        });

        contentView.addView(datePickerView);
    }

    public DatePickerView getDatePickerView() {
        return datePickerView;
    }

    public void setOnDialogSureListener(OnDialogSureListener onDialogSureListener){
        this.onDialogSureListener = onDialogSureListener;
    }

    public interface OnDialogSureListener{
        void onSure(Date date);
    }
}
