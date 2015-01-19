package com.android.settings.boost;

import android.content.ContentResolver;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.provider.Settings;
import android.text.format.DateFormat;
import android.view.View;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.preference.PreferenceScreen;
import android.provider.Settings.SettingNotFoundException;
import com.android.settings.Utils;

public class StatusBarSettings extends SettingsPreferenceFragment
        implements OnPreferenceChangeListener {

    private static final String STATUS_BAR_SHOW_BATTERY_PERCENT = "status_bar_show_battery_percent";
    private static final String STATUS_BAR_POWER_MENU = "status_bar_power_menu";
    private static final String STATUS_BAR_CLOCK_STYLE = "status_bar_clock";
    private static final String STATUS_BAR_AM_PM = "status_bar_am_pm";

    private ListPreference mStatusBarBatteryShowPercent;
    private ListPreference mStatusBarPowerMenu;
    private ListPreference mStatusBarClock;
    private ListPreference mStatusBarAmPm;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        addPreferencesFromResource(R.xml.boost_status_bar_settings);
        ContentResolver resolver = getActivity().getContentResolver();

        mStatusBarClock = (ListPreference) findPreference(STATUS_BAR_CLOCK_STYLE);
        mStatusBarAmPm = (ListPreference) findPreference(STATUS_BAR_AM_PM);
        mStatusBarBatteryShowPercent = (ListPreference) findPreference(STATUS_BAR_SHOW_BATTERY_PERCENT);

        int batteryShowPercent = Settings.System.getInt(resolver, Settings.System.STATUS_BAR_SHOW_BATTERY_PERCENT, 0);
        mStatusBarBatteryShowPercent.setValue(String.valueOf(batteryShowPercent));
        mStatusBarBatteryShowPercent.setSummary(mStatusBarBatteryShowPercent.getEntry());
        mStatusBarBatteryShowPercent.setOnPreferenceChangeListener(this);

        // status bar power menu
        mStatusBarPowerMenu = (ListPreference) findPreference(STATUS_BAR_POWER_MENU);
        mStatusBarPowerMenu.setOnPreferenceChangeListener(this);
        int statusBarPowerMenu = Settings.System.getInt(getContentResolver(),
                STATUS_BAR_POWER_MENU, 0);
        mStatusBarPowerMenu.setValue(String.valueOf(statusBarPowerMenu));
        mStatusBarPowerMenu.setSummary(mStatusBarPowerMenu.getEntry());

        int clockStyle = Settings.System.getInt(resolver,
                Settings.System.STATUS_BAR_CLOCK, 1);
        mStatusBarClock.setValue(String.valueOf(clockStyle));
        mStatusBarClock.setSummary(mStatusBarClock.getEntry());
        mStatusBarClock.setOnPreferenceChangeListener(this);

        if (DateFormat.is24HourFormat(getActivity())) {
            mStatusBarAmPm.setEnabled(false);
            mStatusBarAmPm.setSummary(R.string.status_bar_am_pm_info);
        } else {
            int statusBarAmPm = Settings.System.getInt(resolver,
                    Settings.System.STATUS_BAR_AM_PM, 2);
            mStatusBarAmPm.setValue(String.valueOf(statusBarAmPm));
            mStatusBarAmPm.setSummary(mStatusBarAmPm.getEntry());
            mStatusBarAmPm.setOnPreferenceChangeListener(this);
        }


    }

    @Override
    public void onResume() {
        super.onResume();
        // Adjust clock position for RTL if necessary
        Configuration config = getResources().getConfiguration();
        if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
                mStatusBarClock.setEntries(getActivity().getResources().getStringArray(
                        R.array.status_bar_clock_style_entries_rtl));
                mStatusBarClock.setSummary(mStatusBarClock.getEntry());
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        ContentResolver resolver = getActivity().getContentResolver();
        if (preference == mStatusBarClock) {
            int clockStyle = Integer.parseInt((String) newValue);
            int index = mStatusBarClock.findIndexOfValue((String) newValue);
            Settings.System.putInt(
                    resolver, STATUS_BAR_CLOCK_STYLE, clockStyle);
            mStatusBarClock.setSummary(mStatusBarClock.getEntries()[index]);
            return true;
        } else if (preference == mStatusBarAmPm) {
            int statusBarAmPm = Integer.valueOf((String) newValue);
            int index = mStatusBarAmPm.findIndexOfValue((String) newValue);
            Settings.System.putInt(
                    resolver, STATUS_BAR_AM_PM, statusBarAmPm);
            mStatusBarAmPm.setSummary(mStatusBarAmPm.getEntries()[index]);
            return true;
        } else if (preference == mStatusBarBatteryShowPercent) {
            int batteryShowPercent = Integer.valueOf((String) newValue);
            int index = mStatusBarBatteryShowPercent.findIndexOfValue((String) newValue);
            Settings.System.putInt(
                    resolver, Settings.System.STATUS_BAR_SHOW_BATTERY_PERCENT, batteryShowPercent);
            mStatusBarBatteryShowPercent.setSummary(mStatusBarBatteryShowPercent.getEntries()[index]);
            return true;
        } else if (preference == mStatusBarPowerMenu) {
            String statusBarPowerMenu = (String) newValue;
            int statusBarPowerMenuValue = Integer.parseInt(statusBarPowerMenu);
            Settings.System.putInt(getActivity().getContentResolver(),
                    Settings.System.STATUS_BAR_POWER_MENU, statusBarPowerMenuValue);
            int statusBarPowerMenuIndex = mStatusBarPowerMenu
                    .findIndexOfValue(statusBarPowerMenu);
            mStatusBarPowerMenu
                    .setSummary(mStatusBarPowerMenu.getEntries()[statusBarPowerMenuIndex]);
            return true;
        }	

        return false;
    }
}
