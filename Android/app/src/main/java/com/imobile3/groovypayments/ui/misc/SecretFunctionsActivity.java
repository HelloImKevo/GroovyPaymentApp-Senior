package com.imobile3.groovypayments.ui.misc;

import android.os.Bundle;
import android.widget.Button;

import com.imobile3.groovypayments.R;
import com.imobile3.groovypayments.data.GroovyDemoManager;
import com.imobile3.groovypayments.manager.CartManager;
import com.imobile3.groovypayments.ui.BaseActivity;

public class SecretFunctionsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secret_functions_activity);
        setUpMainNavBar();
        setUpButtons();
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
        mMainNavBar.showSubtitle(getString(R.string.secret_functions_subtitle));
    }

    @Override
    protected void initViewModel() {
        // No view model needed.
    }

    private void setUpButtons() {
        Button btnResetDatabase = findViewById(R.id.btn_reset_database);
        btnResetDatabase.setOnClickListener(v -> handleResetDatabaseClick());

        Button btnEraseCart = findViewById(R.id.btn_erase_cart);
        btnEraseCart.setOnClickListener(v -> handleEraseCartClick());
    }

    private void handleResetDatabaseClick() {
        GroovyDemoManager.getInstance().resetDatabase(() ->
                showAlertDialog(
                        R.string.secret_functions_alert_title,
                        R.string.secret_functions_alert_reset_database,
                        R.string.secret_functions_alert_button));
    }

    private void handleEraseCartClick() {
        CartManager.getInstance().eraseCurrentCart(() ->
                showAlertDialog(
                        R.string.secret_functions_alert_title,
                        R.string.secret_functions_alert_erase_cart,
                        R.string.secret_functions_alert_button));
    }
}
