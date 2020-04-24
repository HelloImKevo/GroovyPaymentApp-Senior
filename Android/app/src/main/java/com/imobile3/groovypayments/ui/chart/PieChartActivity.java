package com.imobile3.groovypayments.ui.chart;

import android.os.Bundle;

import com.imobile3.groovypayments.R;
import com.imobile3.groovypayments.ui.BaseActivity;
import com.imobile3.groovypayments.ui.dialog.ProgressDialog;

import androidx.lifecycle.ViewModelProviders;

import java.util.List;

public class PieChartActivity extends BaseActivity {

    private ProgressDialog mProgressDialog;
    private PieChartViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_common);
        setUpMainNavBar();
        mProgressDialog = new ProgressDialog(this);

        mViewModel.getProductList().observe(this, PieChartActivity.this::setPieChart);
    }

    // TODO: Update list parameterized type to chart library data wrapper
    private void setPieChart(List<String> data) {
        // TODO: Populate the chart view
    }

    @Override
    protected void initViewModel() {
        mViewModel = ViewModelProviders.of(this, new PieChartViewModelFactory())
                .get(PieChartViewModel.class);
    }

    @Override
    protected void setUpMainNavBar() {
        super.setUpMainNavBar();
        mMainNavBar.showBackButton();
        mMainNavBar.showLogo();
        mMainNavBar.showSubtitle(getString(R.string.daily_report_subtitle));
    }
}
