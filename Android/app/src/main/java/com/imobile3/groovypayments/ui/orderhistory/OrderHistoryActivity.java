package com.imobile3.groovypayments.ui.orderhistory;

import android.os.Bundle;

import com.imobile3.groovypayments.R;
import com.imobile3.groovypayments.data.model.Cart;
import com.imobile3.groovypayments.ui.BaseActivity;
import com.imobile3.groovypayments.ui.adapter.CartListAdapter;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OrderHistoryActivity extends BaseActivity {

    private OrderHistoryViewModel mViewModel;
    private CartListAdapter mCartListAdapter;
    private RecyclerView mCartListRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_history_activity);
        setUpMainNavBar();

        mCartListAdapter = new CartListAdapter(this,
                new ArrayList<>(),
                new CartListAdapter.AdapterCallback() {
                    @Override
                    public void onCartClick(Cart cart) {
                        handleCartClick(cart);
                    }
                });
        mCartListRecyclerView = findViewById(R.id.list_carts);
        mCartListRecyclerView.setAdapter(mCartListAdapter);
        mCartListRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // TODO: Load order history (Cart data models) from the database
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
        mMainNavBar.showSubtitle(getString(R.string.order_history_subtitle));
    }

    @Override
    protected void initViewModel() {
        mViewModel = ViewModelProviders.of(this, new OrderHistoryViewModelFactory())
                .get(OrderHistoryViewModel.class);
    }

    @NonNull
    private OrderHistoryViewModel getViewModel() {
        return mViewModel;
    }

    private void handleCartClick(@NonNull Cart cart) {
        // TODO: Do something with the cart
        getViewModel().addCartClick();

        if (getViewModel().getCartClicks() > 4) {
            showAlertDialog(
                    R.string.order_history_alert_warning_title,
                    R.string.order_history_alert_warning_message,
                    R.string.order_history_alert_warning_button);
        } else {
            showAlertDialog(
                    R.string.order_history_alert_title,
                    R.string.order_history_alert_message,
                    R.string.order_history_alert_button);
        }
    }
}
