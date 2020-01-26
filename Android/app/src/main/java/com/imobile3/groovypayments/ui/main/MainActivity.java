package com.imobile3.groovypayments.ui.main;

import android.os.Bundle;

import com.imobile3.groovypayments.R;
import com.imobile3.groovypayments.ui.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        showBackButton();

        if (savedInstanceState == null) {
            showFragmentNow(R.id.container, MainFragment.newInstance(), MainFragment.TAG);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
