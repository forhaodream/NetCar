package com.land.netcar.min.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

import com.land.netcar.R;

/**
 * Created by VULCAN on 2018/3/1.
 */

public class LodingDialog extends Dialog {

    private String text;
    public TextView txtView;

    private String type = "";

    private DialogCallBackListener dialogCallBackListener;

    /**
     * dialog显示的时候，监听返回键
     */
    DialogInterface.OnKeyListener keylistener = new DialogInterface.OnKeyListener() {
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

                dismiss();
                dialogCallBackListener.ReceiveData(type);

                return true;
            } else {
                return false;
            }
        }
    };

    public LodingDialog(Context context, DialogCallBackListener listener, String str) {
        super(context, R.style.LodingDialog);

        dialogCallBackListener = listener;
        type = str;

        setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        setOnKeyListener(keylistener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_loding_dialog);
        txtView = (TextView) findViewById(R.id.text_dialog);
        txtView.setText(text);
    }

    public void setRoundName(String text) {
        this.text = text;
        if (txtView != null) {
            txtView.setText(text);
        }
    }

}