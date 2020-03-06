package com.imobile3.groovypayments.data.enums;

import com.imobile3.groovypayments.R;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;

public enum GroovyColor {

    Red(1, R.color.red_thunderbird),
    Green(2, R.color.green_shamrock),
    Blue(3, R.color.blue_voyager_sky),
    Yellow(4, R.color.yellow_ripe_lemon),
    Orange(5, R.color.orange_california),
    Purple(6, R.color.purple_wisteria),
    Gray(7, R.color.gray_down_pour);

    public final int id;
    @ColorRes
    public final int colorRes;

    GroovyColor(int id, @ColorRes int colorRes) {
        this.id = id;
        this.colorRes = colorRes;
    }

    @NonNull
    public static GroovyColor fromId(int id) {
        for (GroovyColor value : values()) {
            if (value.id == id) {
                return value;
            }
        }
        return Gray;
    }
}
