package com.imobile3.groovypayments.ui.main;

import android.os.Bundle;
import android.widget.Toast;

import com.imobile3.groovypayments.R;
import com.imobile3.groovypayments.ui.BaseActivity;
import com.imobile3.groovypayments.ui.adapter.MainDashboardButton;
import com.imobile3.groovypayments.ui.adapter.MainDashboardButtonAdapter;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

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
                        Toast.makeText(MainDashboardActivity.this,
                                "Under construction!", Toast.LENGTH_SHORT).show();
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
        mMainNavBar.showSubtitle("Where would you like to go?");
    }

    private List<MainDashboardButton> getDashboardButtons() {
        List<MainDashboardButton> dashboardButtons = new ArrayList<>();
        dashboardButtons.add(MainDashboardButton.OrderEntry);
        dashboardButtons.add(MainDashboardButton.Management);
        dashboardButtons.add(MainDashboardButton.TimeTracking);
        dashboardButtons.add(MainDashboardButton.Placeholder1);
        dashboardButtons.add(MainDashboardButton.Placeholder2);
        return dashboardButtons;
    }
}
