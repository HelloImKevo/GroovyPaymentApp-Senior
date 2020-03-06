package com.imobile3.groovypayments.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.imobile3.groovypayments.R;

public class MainNavBar extends RelativeLayout {

    private ProgressBar mProgressBar, mProgressBarRight;
    private TextView mTitle, mSubtitle;
    private ImageView mBtnBack, mBtnLock, mImgLogo, mBtnOverflowMenu;
    private ImageView mBtnCheckout;

    public MainNavBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup(context);
    }

    private void setup(Context context) {
        LayoutInflater inflater =
                (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.main_nav_bar, this);

        mProgressBar = view.findViewById(R.id.loading_progress);
        mBtnLock = view.findViewById(R.id.btn_lock);
        mBtnOverflowMenu = view.findViewById(R.id.btn_nav_menu);

        mBtnBack = view.findViewById(R.id.btn_back);
        mImgLogo = view.findViewById(R.id.logo);
        mBtnCheckout = view.findViewById(R.id.btn_checkout);

        mTitle = view.findViewById(R.id.title);
        mSubtitle = view.findViewById(R.id.subtitle);
        mProgressBarRight = findViewById(R.id.loading_progress_right);
    }

    public void showTitle(String message) {
        mTitle.setVisibility(View.VISIBLE);
        mTitle.setText(message);
    }

    public void showSubtitle(String message) {
        mSubtitle.setVisibility(View.VISIBLE);
        mSubtitle.setText(message);
    }

    public ImageView getBackButton() {
        return mBtnBack;
    }

    public void showBackButton() {
        mBtnBack.setVisibility(View.VISIBLE);
    }

    public void showLogo() {
        mImgLogo.setVisibility(View.VISIBLE);
    }

    public ImageView getCheckoutButton() {
        return mBtnCheckout;
    }

    public void showCheckoutButton() {
        mBtnCheckout.setVisibility(View.VISIBLE);
    }
}
