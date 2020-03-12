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

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

/**
 * @author Kevin Schanz
 */
public enum GroovyIcon {

    AlarmClock(1, R.drawable.ic_alarm_clock),
    BagCoins(2, R.drawable.ic_bag_coins),
    BatteryPack(3, R.drawable.ic_battery_pack),
    CeremonialMask(4, R.drawable.ic_ceremonial_mask),
    CoffeeMug(5, R.drawable.ic_coffee_mug),
    HammerNails(6, R.drawable.ic_hammer_nails),
    Lock(7, R.drawable.ic_lock),
    MoneyStack(8, R.drawable.ic_money_stack),
    OpenedFoodCan(9, R.drawable.ic_opened_food_can),
    Pencil(10, R.drawable.ic_pencil),
    Person(11, R.drawable.ic_person),
    PiggyBank(12, R.drawable.ic_piggy_bank),
    PineTree(13, R.drawable.ic_pine_tree),
    RetroController(14, R.drawable.ic_retro_controller),
    Sandwich(15, R.drawable.ic_sandwich),
    ScrollUnfurled(16, R.drawable.ic_scroll_unfurled),
    Stopwatch(17, R.drawable.ic_stopwatch),
    Strongbox(18, R.drawable.ic_strongbox),
    TShirt(19, R.drawable.ic_t_shirt),
    Teapot(20, R.drawable.ic_teapot),
    WoodenChair(21, R.drawable.ic_wooden_chair),
    WrappedSweet(22, R.drawable.ic_wrapped_sweet),
    Bookmarklet(23, R.drawable.ic_bookmarklet),
    StealthHood(24, R.drawable.ic_stealth_hood),
    ;

    public final int id;
    @DrawableRes
    public final int drawableRes;

    GroovyIcon(int id, @DrawableRes int drawableRes) {
        this.id = id;
        this.drawableRes = drawableRes;
    }

    @NonNull
    public static GroovyIcon fromId(int id) {
        for (GroovyIcon value : values()) {
            if (value.id == id) {
                return value;
            }
        }
        return Person;
    }
}
