package com.imobile3.groovypayments.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.ViewGroup;

import com.imobile3.groovypayments.logging.LogHelper;

import androidx.annotation.NonNull;

import java.util.logging.Level;

public class BaseDialogFragment extends DialogFragment {

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int style = DialogFragment.STYLE_NO_TITLE;
        int theme = android.R.style.Theme_Holo_Light_Dialog;
        setStyle(style, theme);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@NonNull Bundle savedInstanceState) {
        final Dialog dialog = super.onCreateDialog(savedInstanceState);

        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        LogHelper.write(Level.FINE, getClass().getSimpleName(), "onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();
        LogHelper.write(Level.FINE, getClass().getSimpleName(), "onPause()");
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        /*
         * Resolves a known memory leak common to all {@link DialogFragment}s,
         * that only occurs on specific Android versions;
         * Do not remove without thorough regression testing of dialog memory management.
         */
        if (getView() instanceof ViewGroup) {
            ((ViewGroup)getView()).removeAllViews();
        }
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
