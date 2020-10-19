package com.imobile3.groovypayments.ui.checkout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.imobile3.groovypayments.MainApplication;
import com.imobile3.groovypayments.R;
import com.imobile3.groovypayments.data.model.PaymentType;
import com.imobile3.groovypayments.logging.LogHelper;
import com.imobile3.groovypayments.manager.CartManager;
import com.imobile3.groovypayments.network.WebServiceManager;
import com.imobile3.groovypayments.network.domainobjects.PaymentResponseHelper;
import com.imobile3.groovypayments.ui.BaseActivity;
import com.imobile3.groovypayments.ui.adapter.PaymentTypeListAdapter;
import com.imobile3.groovypayments.ui.dialog.ProgressDialog;
import com.imobile3.groovypayments.utils.AnimUtil;
import com.imobile3.groovypayments.utils.JsonHelper;
import com.imobile3.groovypayments.utils.SoftKeyboardHelper;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.PaymentIntentResult;
import com.stripe.android.Stripe;
import com.stripe.android.model.ConfirmPaymentIntentParams;
import com.stripe.android.model.PaymentIntent;
import com.stripe.android.model.PaymentMethodCreateParams;
import com.stripe.android.view.CardInputWidget;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;
import java.util.logging.Level;

public class CheckoutActivity extends BaseActivity {

    private CheckoutViewModel mViewModel;
    private PaymentTypeListAdapter mPaymentTypeListAdapter;
    private RecyclerView mPaymentTypeListRecyclerView;

    // Cash
    private View mPayWithCashView;
    private TextView mLblCashAmount;
    private Button mBtnPayWithCash;

    // Credit
    private View mPayWithCreditView;
    private TextView mLblCreditAmount;
    private Button mBtnPayWithCredit;

    // Android SDK Docs: https://stripe.com/docs/payments/accept-a-payment#android
    // Test Card Numbers: https://stripe.com/docs/testing
    private CardInputWidget mCreditCardInputWidget;

    private ProgressDialog mProgressDialog;

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

        // Cash
        mPayWithCashView = findViewById(R.id.pay_with_cash_view);
        mLblCashAmount = findViewById(R.id.label_cash_amount);
        mBtnPayWithCash = findViewById(R.id.btn_pay_with_cash);
        mBtnPayWithCash.setOnClickListener(v -> handlePayWithCashClick());

        // Credit
        mPayWithCreditView = findViewById(R.id.pay_with_credit_view);
        mLblCreditAmount = findViewById(R.id.label_credit_amount);
        mBtnPayWithCredit = findViewById(R.id.btn_pay_with_credit);
        mBtnPayWithCredit.setOnClickListener(v -> handlePayWithCreditClick());
        mCreditCardInputWidget = findViewById(R.id.credit_card_input_widget);

        mProgressDialog = new ProgressDialog(this);

        // Web Services must be initialized for payment processing.
        WebServiceManager.getInstance().init(
                MainApplication.getInstance().getWebServiceConfig());

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Handle the result of stripe.confirmPayment
        final Context context = getApplicationContext();
        Stripe stripe = new Stripe(context,
                PaymentConfiguration.getInstance(context).getPublishableKey());
        stripe.onPaymentResult(requestCode, data, new PaymentResultCallback(this));
    }

    /*
    Example:
    {
        "amount": 2532,
        "amount_capturable": 0,
        "amount_received": 0,
        "capture_method": "automatic",
        "charges": {
            "data": [],
            "hasMore": false,
            "object": "list",
            "url": "/v1/charges?payment_intent\u003dpi_1GLroFK4qYdXjx43ClPBuv2o"
        },
        "client_secret": "pi_1GLroFK4qYdXjx43ClPBuv2o_secret_7aekJHI4zy1k8i2Uo3XSdMLnV",
        "confirmation_method": "automatic",
        "created": 1584022491,
        "currency": "usd",
        "id": "pi_1GLroFK4qYdXjx43ClPBuv2o",
        "livemode": false,
        "metadata": {},
        "object": "payment_intent",
        "payment_method_options": {
            "card": {
                "request_three_d_secure": "automatic"
            }
        },
        "payment_method_types": [
            "card"
        ],
        "status": "requires_payment_method"
    }
     */
    private static final class PaymentResultCallback
            implements ApiResultCallback<PaymentIntentResult> {
        @NonNull
        private final WeakReference<CheckoutActivity> activityRef;

        PaymentResultCallback(@NonNull CheckoutActivity activity) {
            activityRef = new WeakReference<>(activity);
        }

        @Override
        public void onSuccess(@NonNull PaymentIntentResult result) {
            final CheckoutActivity activity = activityRef.get();
            if (activity == null) {
                return;
            }

            activity.dismissProgressDialog();

            PaymentIntent paymentIntent = result.getIntent();
            PaymentIntent.Status status = paymentIntent.getStatus();
            if (status == PaymentIntent.Status.Succeeded) {
                // Payment completed successfully

                CartManager.getInstance().addCreditPayment(
                        PaymentResponseHelper.transform(paymentIntent));

                activity.showAlertDialog(
                        "Payment completed",
                        JsonHelper.toPrettyJson(paymentIntent),
                        "OK",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                activity.handleCheckoutComplete();
                            }
                        });
            } else if (status == PaymentIntent.Status.RequiresPaymentMethod) {
                // Payment failed
                activity.showAlertDialog(
                        "Payment failed",
                        Objects.requireNonNull(paymentIntent.getLastPaymentError()).getMessage(),
                        "Uh-Oh!",
                        null);
            }
        }

        @Override
        public void onError(@NonNull Exception e) {
            final CheckoutActivity activity = activityRef.get();
            if (activity == null) {
                return;
            }

            // Payment request failed – allow retrying using the same payment method
            activity.showAlertDialog("Error", e.toString(), "Woopsie!", null);
        }
    }

    private void handlePayWithCreditClick() {
        PaymentMethodCreateParams params = mCreditCardInputWidget.getPaymentMethodCreateParams();
        if (params != null) {
            showProgressDialog();

            // TODO: Migrate this stuff to the ViewModel
            WebServiceManager.getInstance().generateClientSecret(
                    getApplicationContext(),
                    CartManager.getInstance().getCart().getGrandTotal(),
                    new WebServiceManager.ClientSecretCallback() {
                        @Override
                        public void onClientSecretError(@NonNull String message) {
                            dismissProgressDialog();

                            showAlertDialog(
                                    "Client Secret Error",
                                    "Error: " + message,
                                    "OK",
                                    null);
                        }

                        @Override
                        public void onClientSecretGenerated(@NonNull String clientSecret) {
                            ConfirmPaymentIntentParams confirmParams = ConfirmPaymentIntentParams
                                    .createWithPaymentMethodCreateParams(params, clientSecret);

                            final Context context = getApplicationContext();
                            Stripe stripe = new Stripe(context,
                                    PaymentConfiguration.getInstance(context).getPublishableKey());
                            stripe.confirmPayment(CheckoutActivity.this, confirmParams);
                        }
                    });
        }
    }

    @Override
    protected void setUpMainNavBar() {
        super.setUpMainNavBar();
        mMainNavBar.showBackButton();
        mMainNavBar.showTitle(CartManager.getInstance()
                .getFormattedGrandTotal(Locale.getDefault()));
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

        updateAmounts();
    }

    private void updateAmounts() {
        String formattedGrandTotal = CartManager.getInstance()
                .getFormattedGrandTotal(Locale.getDefault());
        mLblCashAmount.setText(formattedGrandTotal);
        mLblCreditAmount.setText(formattedGrandTotal);
    }

    private void handlePayWithCashClick() {
        showAlertDialog(
                R.string.common_under_construction,
                R.string.under_construction_alert_message,
                R.string.common_acknowledged);
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

    private void showProgressDialog() {
        LogHelper.invoked(Level.CONFIG, TAG);
        mProgressDialog.show();
    }

    private void dismissProgressDialog() {
        LogHelper.invoked(Level.CONFIG, TAG);
        mProgressDialog.dismiss();
    }

    private void handleCheckoutComplete() {
        LogHelper.writeWithTrace(Level.FINE, TAG,
                "Proceeding to the Checkout Complete screen");

        startActivity(new Intent(this, CheckoutCompleteActivity.class));
        // Remove this activity from the stack.
        finish();
    }
}
