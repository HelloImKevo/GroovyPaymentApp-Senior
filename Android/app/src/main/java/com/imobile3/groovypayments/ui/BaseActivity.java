package com.imobile3.groovypayments.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.imobile3.groovypayments.R;
import com.imobile3.groovypayments.logging.LogHelper;
import com.imobile3.groovypayments.ui.dialog.CommonAlertDialog;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.logging.Level;

public abstract class BaseActivity extends AppCompatActivity {

    public final String TAG = getClass().getSimpleName();

    protected MainNavBar mMainNavBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewModel();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

            default:
                LogHelper.writeWithTrace(Level.CONFIG, TAG, "Not supported: " + item);
                break;
        }
        return true;
    }

    protected abstract void initViewModel();

    /**
     * Invoke after setting the content view for activity.
     */
    protected void setUpMainNavBar() {
        mMainNavBar = findViewById(R.id.main_nav_bar);
        if (mMainNavBar != null) {
            hideNativeActionBar();

            // Back Arrow
            mMainNavBar.getBackButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle in subclass
                    onBackPressed();
                }
            });
        }
    }

    /**
     * Shows the back or home button, if available.
     */
    protected void showBackButton() {
        if (mMainNavBar != null) {
            mMainNavBar.showBackButton();
        } else {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }
    }

    /**
     * Shows the {@link Fragment} using {@link #getSupportFragmentManager()}.
     *
     * @param containerId The container show the fragment in.
     * @param fragment The fragment to replace any content in the container.
     * @param tag The fragment's tag.
     */
    protected void showFragmentNow(@IdRes int containerId,
            @NonNull Fragment fragment, @NonNull String tag) {
        if (isFinishing()) return;
        getSupportFragmentManager().beginTransaction()
                .replace(containerId, fragment, tag)
                .commitNow();
    }

    /**
     * Removes the {@link Fragment} using {@link #getSupportFragmentManager()}.
     *
     * @param fragment The {@link Fragment} to remove.
     */
    protected void removeFragmentNow(@Nullable Fragment fragment) {
        if (fragment == null || isFinishing()) return;
        getSupportFragmentManager().beginTransaction()
                .remove(fragment)
                .commitNow();
    }

    private void hideNativeActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    //region Alert Dialogs

    protected void showAlertDialog(
            @StringRes int title,
            @StringRes int message,
            @StringRes int neutralText) {
        showAlertDialog(getString(title), getString(message), getString(neutralText), null);
    }

    protected void showAlertDialog(
            @StringRes int title,
            @NonNull String message,
            @StringRes int neutralText) {
        showAlertDialog(getString(title), message, getString(neutralText), null);
    }

    protected void showAlertDialog(
            String title,
            String message,
            String neutralText,
            @Nullable View.OnClickListener neutralListener) {
        CommonAlertDialog dialog = new CommonAlertDialog(this);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setNeutralButton(neutralText, neutralListener);
        dialog.show();
    }

    protected void showAlertDialog(
            @StringRes int title,
            @StringRes int message,
            @StringRes int negativeText,
            View.OnClickListener negativeListener,
            @StringRes int positiveText,
            View.OnClickListener positiveListener) {
        showAlertDialog(getString(title), getString(message),
                getString(negativeText), negativeListener,
                getString(positiveText), positiveListener);
    }

    protected void showAlertDialog(
            String title, String message,
            String negativeText,
            View.OnClickListener negativeListener,
            String positiveText,
            View.OnClickListener positiveListener) {
        showAlertDialog(title, message,
                negativeText, negativeListener,
                positiveText, positiveListener,
                CommonAlertDialog.Style.STANDARD);
    }

    protected void showAlertDialog(
            String title,
            String message,
            String negativeText,
            View.OnClickListener negativeListener,
            String positiveText,
            View.OnClickListener positiveListener,
            @NonNull CommonAlertDialog.Style style) {
        CommonAlertDialog dialog = new CommonAlertDialog(this);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setStyle(style);
        dialog.setNegativeButton(negativeText, negativeListener);
        dialog.setPositiveButton(positiveText, positiveListener);
        dialog.show();
    }

    protected void showAlertDialog(
            String title,
            String message,
            View.OnClickListener positiveListener) {
        CommonAlertDialog dialog = new CommonAlertDialog(this);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setNegativeButton(getString(R.string.common_cancel), null);
        dialog.setPositiveButton(getString(R.string.common_ok), positiveListener);
        dialog.show();
    }

    //endregion
}
