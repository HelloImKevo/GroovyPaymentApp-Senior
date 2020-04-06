package com.imobile3.groovypayments.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.TextView;

import com.imobile3.groovypayments.R;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class ProgressDialog extends Dialog {

    private TextView mLblMessage;
    private String mProgressMessage;

    private final Timer mTimer = new Timer();
    private final Handler mHandler = new Handler();

    public ProgressDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.progress_dialog);

        setCancelable(false);
        setCanceledOnTouchOutside(false);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        mLblMessage = findViewById(R.id.label_message);

        mTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mLblMessage.setText(getMessage());
                    }
                });
            }
        }, 0, 1500L);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mTimer != null) {
            mTimer.cancel();
        }
    }

    private String getRandomMessage() {
        String[] messages = getContext().getResources()
                .getStringArray(R.array.progress_dialog_messages);
        return messages[new Random().nextInt(messages.length)];
    }

    private String getMessage() {
        return mProgressMessage != null ? mProgressMessage : getRandomMessage();
    }

    public void setMessage(String message) {
        mProgressMessage = message;
    }
}
