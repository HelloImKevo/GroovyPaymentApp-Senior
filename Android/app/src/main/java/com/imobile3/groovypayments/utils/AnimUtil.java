package com.imobile3.groovypayments.utils;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.imobile3.groovypayments.R;

import androidx.annotation.NonNull;

public final class AnimUtil {

    private AnimUtil() {
    }

    public static void fadeIn(
            @NonNull final Context context,
            @NonNull final View view) {
        Animation fadeIn = AnimationUtils
                .loadAnimation(context, R.anim.fade_in);
        view.startAnimation(fadeIn);
        view.setVisibility(View.VISIBLE);
    }

    public static void fadeOut(
            @NonNull final Context context,
            @NonNull final View view) {
        Animation fadeOut = AnimationUtils
                .loadAnimation(context, R.anim.fade_out);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
            }
        });
        view.startAnimation(fadeOut);
    }

    public static void slideInFromLeftToReplace(
            @NonNull final Context context,
            @NonNull final View newViewToSlideIn,
            @NonNull final View existingViewToReplace) {
        // Slide In From Left (Show)
        Animation slideInFromLeft = AnimationUtils
                .loadAnimation(context, R.anim.slide_in_from_left);
        newViewToSlideIn.startAnimation(slideInFromLeft);
        newViewToSlideIn.setVisibility(View.VISIBLE);

        // Slide Out Right (Hide)
        Animation slideOutToRight = AnimationUtils
                .loadAnimation(context, R.anim.slide_out_to_right);
        existingViewToReplace.startAnimation(slideOutToRight);
        existingViewToReplace.setVisibility(View.GONE);
    }

    public static void slideInFromRightToReplace(
            @NonNull final Context context,
            @NonNull final View newViewToSlideIn,
            @NonNull final View existingViewToReplace) {
        // Slide In From Right (Show)
        Animation slideInFromRight = AnimationUtils
                .loadAnimation(context, R.anim.slide_in_from_right);
        newViewToSlideIn.startAnimation(slideInFromRight);
        newViewToSlideIn.setVisibility(View.VISIBLE);

        // Slide Out Left (Hide)
        Animation slideOutToLeft = AnimationUtils
                .loadAnimation(context, R.anim.slide_out_to_left);
        existingViewToReplace.startAnimation(slideOutToLeft);
        existingViewToReplace.setVisibility(View.GONE);
    }
}
