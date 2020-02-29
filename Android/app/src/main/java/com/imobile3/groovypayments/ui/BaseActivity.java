package com.imobile3.groovypayments.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.imobile3.groovypayments.R;
import com.imobile3.groovypayments.logging.LogHelper;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.logging.Level;

public abstract class BaseActivity extends AppCompatActivity {

    public final String TAG = getClass().getSimpleName();

    protected MainNavBar mMainNavBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
}
