package com.imobile3.groovypayments.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;

import com.imobile3.groovypayments.R;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

public class StateListHelper {

    private StateListHelper() {
    }

    public static StateListDrawable createStateListDrawable(
            @NonNull Integer[] states,
            @NonNull Integer[] colors) {

        if (states.length != colors.length) {
            throw new IndexOutOfBoundsException("State list and Color list must be the same size");
        }

        StateListDrawable list = new StateListDrawable();

        for (int i = 0; i < states.length; i++) {
            if (i == (states.length - 1)) {
                list.addState(new int[] {}, new ColorDrawable(colors[i]));
                break;
            }

            list.addState(new int[] {states[i]}, new ColorDrawable(colors[i]));
        }

        return list;
    }

    public static StateListDrawable getBgColorSelector(Context context, int intColor) {
        int color = ContextCompat.getColor(context, intColor);

        return createStateListDrawable(
                new Integer[] {android.R.attr.state_selected, android.R.attr.state_pressed, null},
                new Integer[] {color, color, Color.WHITE}
        );
    }

    public static ColorStateList getTextColorSelector(Context context, int intColor) {
        int color = ContextCompat.getColor(context, intColor);
        int pressed = ContextCompat.getColor(context, R.color.button_text_pressed);

        return new ColorStateList(
                new int[][] {
                        new int[] {android.R.attr.state_selected},
                        new int[] {android.R.attr.state_pressed},
                        new int[] {}},

                new int[] {Color.WHITE,
                        pressed,
                        color}
        );
    }
}
