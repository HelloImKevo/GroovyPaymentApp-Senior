package com.imobile3.groovypayments.ui.checkout;

import android.os.Bundle;
import android.view.View;

import com.imobile3.groovypayments.R;
import com.imobile3.groovypayments.data.model.PaymentType;
import com.imobile3.groovypayments.ui.BaseActivity;
import com.imobile3.groovypayments.ui.adapter.PaymentTypeListAdapter;
import com.imobile3.groovypayments.utils.AnimUtil;
import com.imobile3.groovypayments.utils.SoftKeyboardHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CheckoutActivity extends BaseActivity {

    private CheckoutViewModel mViewModel;
    private PaymentTypeListAdapter mPaymentTypeListAdapter;
    private RecyclerView mPaymentTypeListRecyclerView;
    private View mPayWithCashView;
    private View mPayWithCreditView;

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

        mPayWithCashView = findViewById(R.id.pay_with_cash_view);
        mPayWithCreditView = findViewById(R.id.pay_with_credit_view);

        loadPaymentTypes();
    }

    @Override
    public void onBackPressed() {
        View currentlyVisibleDashboard = getCurrentlyVisibleDashboard();
        if (currentlyVisibleDashboard == mPayWithCashView
                || currentlyVisibleDashboard == mPayWithCreditView) {
            showPaymentTypeSelection();
            return;
        }

        finish();
    }

    @Override
    protected void setUpMainNavBar() {
        super.setUpMainNavBar();
        mMainNavBar.showBackButton();
        mMainNavBar.showLogo();
        mMainNavBar.showSubtitle(getString(R.string.checkout_subtitle));
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
        switch (paymentType.getType()) {
            case Cash:
                showPayWithCashView();
                break;

            case Credit:
                showPayWithCreditView();
                break;
        }
    }

    //region (Animated) View Transitions

    @Nullable
    private View getCurrentlyVisibleDashboard() {
        final int visible = View.VISIBLE;
        if (mPaymentTypeListRecyclerView.getVisibility() == visible) {
            return mPaymentTypeListRecyclerView;
        } else if (mPayWithCashView.getVisibility() == visible) {
            return mPayWithCashView;
        } else if (mPayWithCreditView.getVisibility() == visible) {
            return mPayWithCreditView;
        }
        return null;
    }

    private void showPaymentTypeSelection() {
        View currentlyVisibleDashboard = getCurrentlyVisibleDashboard();
        if (currentlyVisibleDashboard == mPaymentTypeListRecyclerView) {
            return;
        }

        if (currentlyVisibleDashboard == null) {
            AnimUtil.fadeIn(this, mPaymentTypeListRecyclerView);
        } else {
            if (currentlyVisibleDashboard == mPayWithCashView
                    || currentlyVisibleDashboard == mPayWithCreditView) {
                mPayWithCashView.clearFocus();
                mPayWithCreditView.clearFocus();
                SoftKeyboardHelper.hide(this);

                // If the middle or right views are showing
                AnimUtil.slideInFromLeftToReplace(this,
                        mPaymentTypeListRecyclerView, currentlyVisibleDashboard);
            }
        }
    }

    private void showPayWithCashView() {
        View currentlyVisibleDashboard = getCurrentlyVisibleDashboard();
        if (currentlyVisibleDashboard == mPayWithCashView) {
            return;
        }

        if (currentlyVisibleDashboard == null) {
            AnimUtil.fadeIn(this, mPayWithCashView);
        } else {
            if (currentlyVisibleDashboard == mPaymentTypeListRecyclerView
                    || currentlyVisibleDashboard == mPayWithCreditView) {
                mPayWithCreditView.clearFocus();
                SoftKeyboardHelper.hide(this);

                AnimUtil.slideInFromRightToReplace(this,
                        mPayWithCashView, currentlyVisibleDashboard);
            }
        }
    }

    private void showPayWithCreditView() {
        View currentlyVisibleDashboard = getCurrentlyVisibleDashboard();
        if (currentlyVisibleDashboard == mPayWithCreditView) {
            return;
        }

        if (currentlyVisibleDashboard == null) {
            AnimUtil.fadeIn(this, mPayWithCreditView);
        } else {
            if (currentlyVisibleDashboard == mPaymentTypeListRecyclerView
                    || currentlyVisibleDashboard == mPayWithCashView) {
                mPayWithCashView.clearFocus();
                SoftKeyboardHelper.hide(this);

                AnimUtil.slideInFromRightToReplace(this,
                        mPayWithCreditView, currentlyVisibleDashboard);
            }
        }
    }

    //endregion
}
