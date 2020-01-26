package com.imobile3.groovypayments.ui;

import android.view.MenuItem;

import com.imobile3.groovypayments.logging.LogHelper;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.logging.Level;

public abstract class BaseActivity extends AppCompatActivity {

    public final String TAG = getClass().getSimpleName();

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
     * Shows the back or home button, if available.
     */
    protected void showBackButton() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
}
