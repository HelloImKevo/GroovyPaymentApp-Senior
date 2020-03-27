package com.imobile3.groovypayments.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.imobile3.groovypayments.R;
import com.imobile3.groovypayments.ui.login.LoginActivity;

import androidx.annotation.Nullable;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        ImageView logo = findViewById(R.id.brand_logo);

        // Animate the brand logo to slide up from bottom.
        Animation animationUtils = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        logo.setAnimation(animationUtils);
        animationUtils.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startLoginActivity();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    private void startLoginActivity() {
        new Handler().postDelayed(
                () -> {
                    SplashActivity.this.startActivity(
                            new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }, 500L);
    }

    @Override
    protected void initViewModel() {
        // Not used
    }
}
