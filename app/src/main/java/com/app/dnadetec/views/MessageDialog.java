package com.app.dnadetec.views;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.app.dnadetec.R;

/**
 * Created by user on 3/20/2017.
 */

public class MessageDialog extends Dialog {

    private TextView textMsg;
    private TextView textClose;
    private TextView textCancel;
    private FrameLayout flClose;
    private FrameLayout flCancel;

    private MessageDialog.CallBack callBack;
    private MessageDialog.CallBack cancelCallBack;

    public MessageDialog(Context context) {
        super(context);
        init();
    }

    public MessageDialog(Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    protected MessageDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init();
    }

    private void init() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        setContentView(R.layout.dialog_msg);
        setCancelable(false);
        assignViews();
    }

    private void assignViews() {
        textCancel = (TextView) findViewById(R.id.textCancel);
        textMsg = (TextView) findViewById(R.id.textMsg);
        textClose = (TextView) findViewById(R.id.textClose);
        flClose = (FrameLayout) findViewById(R.id.flClose);
        flCancel = (FrameLayout) findViewById(R.id.flCancel);

        flClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeDismiss();
            }
        });

        flCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelDismiss();
            }
        });
    }

    public void showCancelButton() { this.flCancel.setVisibility(View.VISIBLE); }
    public void setCancelCallback(CallBack callBack) { this.cancelCallBack = callBack; }

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    public void setCancelButtonText(String msg) {
        textCancel.setText(msg);
    }

    public void setTextMsg(String name){
        textMsg.setText(name);
    }
    public void setTextBtnClose(String name){
        textClose.setText(name);
    }

    private void cancelDismiss() {
        dismiss();

        if (cancelCallBack != null) {
            cancelCallBack.onDismiss();
        }
    }

    private void closeDismiss() {
        dismiss();

        if (callBack != null) {
            callBack.onDismiss();
        }
    }

    public interface CallBack{
        public void onDismiss();
    }
}
