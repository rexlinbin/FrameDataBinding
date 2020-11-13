package com.lengyue.frame_databinding.views.datepicker.listener;


import androidx.annotation.Nullable;

import com.lengyue.frame_databinding.views.datepicker.base.BaseDatePickerView;

import java.util.Date;


/**
 * 日期选中监听器
 */
public interface OnDateSelectedListener {

    /**
     * @param datePickerView BaseDatePickerView
     * @param year           选中的年份
     * @param month          选中的月份
     * @param day            选中的天
     * @param date           选中的日期
     */
    void onDateSelected(BaseDatePickerView datePickerView, int year, int month,
                        int day, @Nullable Date date);
}