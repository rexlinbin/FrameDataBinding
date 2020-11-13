package com.lengyue.frame_databinding.views.dialog;

import android.app.Activity;
import android.content.Context;

import java.util.List;


/**
 * Created by leo on 2016/12/27.
 */
public final class DialogUtil {

    private DialogUtil() {
    }

    public static <T> void showBottomWheelDialog(Context context, BottomWheelDialog.OnDialogSureListener<T> onDialogSureListener, List<T> list, int index) {
        BottomWheelDialog<T> bottomDialog = new BottomWheelDialog<>(context);
        bottomDialog.setOnDialogSureListener(onDialogSureListener);
        bottomDialog.setData(list);
        bottomDialog.setSelectIndex(index);
        bottomDialog.show();
    }

    public static void showBottomDataDialog(Context context, BottomDateDialog.OnDialogSureListener listener){
        BottomDateDialog dateBottomDialog = new BottomDateDialog(context);
        dateBottomDialog.setOnDialogSureListener(listener);
        dateBottomDialog.show();
    }

    /**
    * 仿ios dialog
    */
    public static void alertIosDialog(Activity act, String message, String confirmMessage, String cancelMessage, final IosAlertDialog.OnDialogAlertListener listener) {
        IosAlertDialog dialog = new IosAlertDialog(act).builder();
        dialog.setMsg(message);
        dialog.setConfirmMsg(confirmMessage);
        dialog.setConcleMsg(cancelMessage);
        dialog.setConfirmButton(listener);
        if (!act.isFinishing()) {
            dialog.show();
        }
    }

    /**
     * 仿ios dialog
     */
    public static void alertIosDialog(Activity act, String message, String confirmMessage,final IosAlertDialog.OnDialogAlertListener listener) {
        IosAlertDialog dialog = new IosAlertDialog(act).oneButtonBuilder();
        dialog.setMsg(message);
        dialog.setConfirmMsg(confirmMessage);
        dialog.setConfirmButton(listener);
        if (!act.isFinishing()) {
            dialog.show();
        }
    }

}
