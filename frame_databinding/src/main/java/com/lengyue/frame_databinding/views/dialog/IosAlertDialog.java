package com.lengyue.frame_databinding.views.dialog;


import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.lengyue.frame_databinding.R;


public class IosAlertDialog extends CustomDialog {
    private Context context;
    private TextView txt_msg;
    private TextView btn_cancle;
    private TextView btn_confirm;

    public IosAlertDialog(Context context) {
        super(context);
        this.context = context;
    }

    public IosAlertDialog builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(
                R.layout.view_alertdialog, null);
        txt_msg = view.findViewById(R.id.txt_msg);
        btn_cancle = view.findViewById(R.id.btn_cancle);
        btn_confirm = view.findViewById(R.id.btn_confirm);
        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IosAlertDialog.super.dismiss();
            }
        });

        // 定义Dialog布局和参数
        setView(view);

        return this;
    }

    public IosAlertDialog oneButtonBuilder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(
                R.layout.view_alertdialog_one_button, null);
        txt_msg = view.findViewById(R.id.txt_msg);
        btn_confirm = view.findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IosAlertDialog.super.dismiss();
            }
        });

        setView(view);

        return this;
    }


    public IosAlertDialog setMsg(String msg) {
        if (TextUtils.isEmpty(msg)) {
            txt_msg.setText("内容");
        } else {
            txt_msg.setText(msg);
        }
        return this;
    }


    public IosAlertDialog setConfirmMsg(String msg) {
        if (TextUtils.isEmpty(msg)) {
            btn_confirm.setText("确定");
        } else {
            btn_confirm.setText(msg);
        }
        return this;
    }

    public IosAlertDialog setConcleMsg(String msg) {
        if (TextUtils.isEmpty(msg)) {
            btn_cancle.setText("取消");
        } else {
            btn_cancle.setText(msg);
        }
        return this;
    }


    public IosAlertDialog setConfirmButton(final OnDialogAlertListener listener) {
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IosAlertDialog.super.dismiss();
                if (listener != null) {
                    listener.yes();
                }
            }
        });
        return this;
    }

    //需要no的点击监听，那么再加个接口。实现。
    public interface OnDialogAlertListener {
        void yes() ;
    }

}
