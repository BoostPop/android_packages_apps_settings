/*
 * Copyright (C) 2015 The CyanogenMod Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.settings.cyanogenmod.qs;

import android.content.Context;

import com.android.settings.R;

/**
 * This class holds the icon, the name - or the string the user sees,
 * and the value which will be stored
 */
public class QSTileHolder {
    public final int drawableId;
    public final String value;
    public final String name;

    public QSTileHolder(int drawableId, String value, String name) {
        this.drawableId = drawableId;
        this.value = value;
        this.name = name;
    }

    public static QSTileHolder from(Context context, String tileType) {
        int resId = -1;
        int stringId = -1;

        if (tileType.equals("wifi")) {
            resId = R.drawable.ic_settings_wireless;
            stringId = R.string.wifi_quick_toggle_title;
        } else if (tileType.equals("bt")) {
            resId = R.drawable.ic_settings_bluetooth2;
            stringId = R.string.bluetooth_settings_title;
        } else if (tileType.equals("inversion")) {
            resId = R.drawable.ic_settings_accessibility;
            stringId = R.string.accessibility_display_inversion_preference_title;
        } else if (tileType.equals("cell")) {
            resId = R.drawable.ic_qs_signal;
            stringId = R.string.cellular_data_title;
        } else if (tileType.equals("airplane")) {
            resId = R.drawable.ic_qs_airplane;
            stringId = R.string.airplane_mode;
        } else if (tileType.equals("rotation")) {
            resId = R.drawable.ic_qs_rotation;
            stringId = R.string.display_rotation_title;
        } else if (tileType.equals("flashlight")) {
            resId = R.drawable.ic_qs_flashlight;
            stringId = R.string.power_flashlight;
        } else if (tileType.equals("location")) {
            resId = R.drawable.ic_settings_location;
            stringId = R.string.location_title;
        } else if (tileType.equals("cast")) {
            resId = R.drawable.ic_qs_cast;
            stringId = R.string.cast_screen;
        } else if (tileType.equals("hotspot")) {
            resId = R.drawable.ic_qs_hotspot;
            stringId = R.string.hotspot;
        } else if (tileType.equals("screen_off")){
            resId = R.drawable.ic_qs_screen_off;
            stringId = R.string.screen_off;
        } else if (tileType.equals("nfc")){
            resId = R.drawable.ic_qs_nfc;
            stringId = R.string.nfc;
        } else if (tileType.equals("sync")){
            resId = R.drawable.ic_qs_sync;
            stringId = R.string.sync;
        } else if (tileType.equals("adb_network")){
            resId = R.drawable.ic_qs_adb_network;
            stringId = R.string.adb_network;
        } else if (tileType.equals("ScreenshotTile")){
            resId = R.drawable.ic_qs_screenshot;
            stringId = R.string.screenshot;
        } else if (tileType.equals("notifications")){
            resId = R.drawable.ic_qs_notifications;
            stringId = R.string.ic_qs_notifications;
        } else if (tileType.equals("immersive")){
            resId = R.drawable.ic_qs_immersive;
            stringId = R.string.ic_qs_immersive_mode;
        } else if (tileType.equals("usb_tether")){
            resId = R.drawable.ic_qs_usb_tether;
            stringId = R.string.ic_qs_usb_tether;
        }

        String name = stringId != -1 ? context.getString(stringId) : null;
        return new QSTileHolder(resId, tileType, name);
    }
}
