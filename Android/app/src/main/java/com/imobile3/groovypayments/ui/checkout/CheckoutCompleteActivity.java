package com.imobile3.groovypayments.ui.checkout;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.imobile3.groovypayments.R;
import com.imobile3.groovypayments.manager.CartManager;
import com.imobile3.groovypayments.ui.BaseActivity;

import java.util.Locale;

public class CheckoutCompleteActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout_complete_activity);
        setUpMainNavBar();
        setUpViews();
    }

    @Override
    public void onBackPressed() {
        // Do nothing!
    }

    @Override
    protected void setUpMainNavBar() {
        super.setUpMainNavBar();
        mMainNavBar.showLogo();
        mMainNavBar.showSubtitle(getString(R.string.checkout_complete_subtitle));
    }

    @Override
    protected void initViewModel() {
        // No view model needed.
    }

    private void setUpViews() {
        TextView lblAmount = findViewById(R.id.label_amount);
        lblAmount.setText(CartManager.getInstance().getFormattedGrandTotal(Locale.getDefault()));

        Button btnGroovy = findViewById(R.id.btn_groovy);
        btnGroovy.setOnClickListener(v -> completeCheckout());
    }

    private void completeCheckout() {
        CartManager.getInstance().clearCurrentCart();
        // Remove this activity from the stack.
        finish();
    }
}
