package com.imobile3.groovypayments.ui.adapter;

import com.imobile3.groovypayments.R;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

public enum MainDashboardButton {

    OrderEntry(
            R.string.common_order_entry,
            R.string.order_entry_description,
            R.drawable.ic_scroll_unfurled,
            R.color.green_emerald,
            R.drawable.dashboard_icon_bg_green),

    Management(
            R.string.common_management,
            R.string.management_description,
            R.drawable.ic_bag_coins,
            R.color.red_thunderbird,
            R.drawable.dashboard_icon_bg_red),

    TimeTracking(
            R.string.common_time_tracking,
            R.string.time_tracking_description,
            R.drawable.ic_alarm_clock,
            R.color.blue_dodger_blue,
            R.drawable.dashboard_icon_bg_light_blue),

    Placeholder1(
            R.string.common_under_construction,
            R.string.placeholder_description,
            R.drawable.ic_strongbox,
            R.color.purple_magic,
            R.drawable.dashboard_icon_bg_purple),

    Placeholder2(
            R.string.common_under_construction,
            R.string.placeholder_description,
            R.drawable.ic_money_stack,
            R.color.purple_magic,
            R.drawable.dashboard_icon_bg_purple);

    @StringRes
    public final int labelResource;
    @StringRes
    public final int descriptionResource;
    @DrawableRes
    public final int iconResource;
    @ColorRes
    public final int colorResource;
    @DrawableRes
    public final int backgroundResource;

    MainDashboardButton(
            @StringRes int label,
            @StringRes int description,
            @DrawableRes int icon,
            @ColorRes int color,
            @DrawableRes int background) {
        labelResource = label;
        descriptionResource = description;
        iconResource = icon;
        colorResource = color;
        backgroundResource = background;
    }
}
