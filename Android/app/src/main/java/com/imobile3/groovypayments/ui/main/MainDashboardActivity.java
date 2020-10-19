package com.imobile3.groovypayments.ui.main;

import android.content.Intent;
import android.os.Bundle;

import com.imobile3.groovypayments.R;
import com.imobile3.groovypayments.logging.LogHelper;
import com.imobile3.groovypayments.ui.BaseActivity;
import com.imobile3.groovypayments.ui.adapter.MainDashboardButton;
import com.imobile3.groovypayments.ui.adapter.MainDashboardButtonAdapter;
import com.imobile3.groovypayments.ui.chart.PieChartActivity;
import com.imobile3.groovypayments.ui.misc.SecretFunctionsActivity;
import com.imobile3.groovypayments.ui.orderentry.OrderEntryActivity;
import com.imobile3.groovypayments.ui.orderhistory.OrderHistoryActivity;
import com.imobile3.groovypayments.ui.user.UserProfileActivity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class MainDashboardActivity extends BaseActivity {

    private MainDashboardButtonAdapter mMainDashboardButtonAdapter;
    private RecyclerView mLaunchButtonsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_dashboard_activity);
        setUpMainNavBar();

        final List<MainDashboardButton> dashboardButtons = getDashboardButtons();
        mMainDashboardButtonAdapter = new MainDashboardButtonAdapter(this,
                dashboardButtons,
                new MainDashboardButtonAdapter.AdapterCallback() {
                    @Override
                    public void onButtonClick(MainDashboardButton button) {
                        handleDashboardButtonClick(button);
                    }
                });
        mLaunchButtonsRecyclerView = findViewById(R.id.grid_launch_buttons);
        mLaunchButtonsRecyclerView.setAdapter(mMainDashboardButtonAdapter);
        mLaunchButtonsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // TODO: Relocate this to another activity
        if (savedInstanceState == null) {
            // showFragmentNow(R.id.container, MainFragment.newInstance(), MainFragment.TAG);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void setUpMainNavBar() {
        super.setUpMainNavBar();
        mMainNavBar.showBackButton();
        mMainNavBar.showLogo();
        mMainNavBar.showSubtitle(getString(R.string.main_dashboard_subtitle));
    }

    @Override
    protected void initViewModel() {
        // Nothing to do here, yet.
    }

    private void handleDashboardButtonClick(@NonNull MainDashboardButton button) {
        switch (button) {
            case OrderEntry:
                startActivity(new Intent(this, OrderEntryActivity.class));
                break;

            case OrderHistory:
                startActivity(new Intent(this, OrderHistoryActivity.class));
                break;

            case SecretFunctions:
                startActivity(new Intent(this, SecretFunctionsActivity.class));
                break;

            case UserProfile:
                startActivity(new Intent(this, UserProfileActivity.class));
                break;

            case Management:
                LogHelper.writeWithTrace(Level.INFO, TAG, "Management area not implemented");
                break;

            case TimeTracking:
                LogHelper.writeWithTrace(Level.INFO, TAG, "Time Tracking area not implemented");
                break;

            case DailyReport:
                startActivity(new Intent(this, PieChartActivity.class));
                break;

            case Placeholder2:
                showAlertDialog(
                        R.string.common_under_construction,
                        R.string.under_construction_alert_message,
                        R.string.common_acknowledged);
        }
    }

    private List<MainDashboardButton> getDashboardButtons() {
        List<MainDashboardButton> dashboardButtons = new ArrayList<>();
        dashboardButtons.add(MainDashboardButton.OrderEntry);
        dashboardButtons.add(MainDashboardButton.OrderHistory);
        dashboardButtons.add(MainDashboardButton.UserProfile);
        dashboardButtons.add(MainDashboardButton.SecretFunctions);
        dashboardButtons.add(MainDashboardButton.Management);
        dashboardButtons.add(MainDashboardButton.TimeTracking);
        dashboardButtons.add(MainDashboardButton.DailyReport);
        dashboardButtons.add(MainDashboardButton.Placeholder2);
        return dashboardButtons;
    }
}
