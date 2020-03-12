package com.imobile3.groovypayments.ui.misc;

import android.os.Bundle;
import android.widget.Button;

import com.imobile3.groovypayments.R;
import com.imobile3.groovypayments.data.GroovyDemoManager;
import com.imobile3.groovypayments.logging.LogHelper;
import com.imobile3.groovypayments.manager.CartManager;
import com.imobile3.groovypayments.ui.BaseActivity;
import com.imobile3.groovypayments.ui.dialog.ProgressDialog;

import java.util.logging.Level;

public class SecretFunctionsActivity extends BaseActivity {

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secret_functions_activity);
        setUpMainNavBar();
        setUpButtons();

        mProgressDialog = new ProgressDialog(this);
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
        showProgressDialog();

        GroovyDemoManager.getInstance().resetDatabase(() -> {
            dismissProgressDialog();

            showAlertDialog(
                    R.string.secret_functions_alert_title,
                    R.string.secret_functions_alert_reset_database,
                    R.string.secret_functions_alert_button);
        });
    }

    private void handleEraseCartClick() {
        showProgressDialog();

        CartManager.getInstance().eraseCurrentCart(() -> {
            dismissProgressDialog();

            showAlertDialog(
                    R.string.secret_functions_alert_title,
                    R.string.secret_functions_alert_erase_cart,
                    R.string.secret_functions_alert_button);
        });
    }

    private void showProgressDialog() {
        LogHelper.invoked(Level.CONFIG, TAG);
        mProgressDialog.show();
    }

    private void dismissProgressDialog() {
        LogHelper.invoked(Level.CONFIG, TAG);
        mProgressDialog.dismiss();
    }
}
