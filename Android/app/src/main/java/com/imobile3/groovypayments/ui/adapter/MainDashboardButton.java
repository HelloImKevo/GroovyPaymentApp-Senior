package com.imobile3.groovypayments.ui.adapter;

import com.imobile3.groovypayments.R;

public enum MainDashboardButton {

    OrderEntry(
            R.string.common_order_entry,
            R.drawable.ic_scroll_unfurled,
            R.color.green_emerald,
            R.drawable.dashboard_icon_bg_green),

    Management(
            R.string.common_management,
            R.drawable.ic_bag_coins,
            R.color.red_thunderbird,
            R.drawable.dashboard_icon_bg_red),

    TimeTracking(
            R.string.common_time_tracking,
            R.drawable.ic_alarm_clock,
            R.color.blue_dodger_blue,
            R.drawable.dashboard_icon_bg_light_blue),

    Placeholder1(
            R.string.common_under_construction,
            R.drawable.ic_strongbox,
            R.color.purple_magic,
            R.drawable.dashboard_icon_bg_purple),

    Placeholder2(
            R.string.common_under_construction,
            R.drawable.ic_money_stack,
            R.color.purple_magic,
            R.drawable.dashboard_icon_bg_purple);

    public final int labelResource;
    public final int iconResource;
    public final int colorResource;
    public final int backgroundResource;

    MainDashboardButton(int label, int icon, int color, int background) {
        labelResource = label;
        iconResource = icon;
        colorResource = color;
        backgroundResource = background;
    }
}
