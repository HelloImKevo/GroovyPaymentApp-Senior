package com.imobile3.groovypayments.ui.checkout;

import android.os.Bundle;

import com.imobile3.groovypayments.R;
import com.imobile3.groovypayments.data.model.PaymentType;
import com.imobile3.groovypayments.ui.BaseActivity;
import com.imobile3.groovypayments.ui.adapter.PaymentTypeListAdapter;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CheckoutActivity extends BaseActivity {

    private CheckoutViewModel mViewModel;
    private PaymentTypeListAdapter mPaymentTypeListAdapter;
    private RecyclerView mPaymentTypeListRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout_activity);
        setUpMainNavBar();

        mPaymentTypeListAdapter = new PaymentTypeListAdapter(this,
                new ArrayList<>(),
                new PaymentTypeListAdapter.AdapterCallback() {
                    @Override
                    public void onPaymentTypeClick(PaymentType paymentType) {
                        handlePaymentTypeClick(paymentType);
                    }
                });
        mPaymentTypeListRecyclerView = findViewById(R.id.list_payment_types);
        mPaymentTypeListRecyclerView.setAdapter(mPaymentTypeListAdapter);
        mPaymentTypeListRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadPaymentTypes();
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
        mMainNavBar.showSubtitle("How will you pay?");
    }

    @Override
    protected void initViewModel() {
        mViewModel = ViewModelProviders.of(this, new CheckoutViewModelFactory())
                .get(CheckoutViewModel.class);
    }

    @NonNull
    private CheckoutViewModel getViewModel() {
        return mViewModel;
    }

    private void loadPaymentTypes() {
        getViewModel().getPaymentTypes(getApplicationContext())
                .observe(this, data -> mPaymentTypeListAdapter.setItems(data));
    }

    private void handlePaymentTypeClick(@NonNull PaymentType paymentType) {
        // TODO: Handle product click. Example: Add product to the cart.
    }
}
