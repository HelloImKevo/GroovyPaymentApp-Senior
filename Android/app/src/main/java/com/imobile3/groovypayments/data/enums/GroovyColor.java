/*
 *  Copyright (c) 2020 iMobile3, LLC. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, is permitted provided that adherence to the following
 *  conditions is maintained. If you do not agree with these terms,
 *  please do not use, install, modify or redistribute this software.
 *
 *  1. Redistributions of source code must retain the above copyright notice, this
 *  list of conditions and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice,
 *  this list of conditions and the following disclaimer in the documentation
 *  and/or other materials provided with the distribution.
 */

package com.imobile3.groovypayments.data.enums;

import com.imobile3.groovypayments.R;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;

/**
 * @author Kevin Schanz
 */
public enum GroovyColor {

    Red(1, R.color.red_thunderbird),
    Green(2, R.color.green_shamrock),
    Blue(3, R.color.blue_atlantis),
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
