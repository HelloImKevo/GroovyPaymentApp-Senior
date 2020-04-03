package com.imobile3.groovypayments.ui.chart;

import android.os.Bundle;

import com.imobile3.groovypayments.R;
import com.imobile3.groovypayments.ui.BaseActivity;

public class PieChartActivity extends BaseActivity {
    // TODO: Stubbed class, implement pie chart for product popularity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_common);
        setUpMainNavBar();
    }

    @Override
    protected void initViewModel() {
    }

    @Override
    protected void setUpMainNavBar() {
        super.setUpMainNavBar();
        mMainNavBar.showBackButton();
        mMainNavBar.showLogo();
        mMainNavBar.showSubtitle(getString(R.string.daily_report_subtitle));
    }
}
