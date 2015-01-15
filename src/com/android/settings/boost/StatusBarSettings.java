package com.android.settings.boost;

import android.content.ContentResolver;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.provider.Settings;

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

    private ListPreference mStatusBarBatteryShowPercent;
    private ListPreference mStatusBarPowerMenu;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        addPreferencesFromResource(R.xml.boost_status_bar_settings);
        ContentResolver resolver = getActivity().getContentResolver();

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

    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        ContentResolver resolver = getActivity().getContentResolver();
        if (preference == mStatusBarBatteryShowPercent) {
            int batteryShowPercent = Integer.valueOf((String) newValue);
            int index = mStatusBarBatteryShowPercent.findIndexOfValue((String) newValue);
            Settings.System.putInt(
                    resolver, Settings.System.STATUS_BAR_SHOW_BATTERY_PERCENT, batteryShowPercent);
            mStatusBarBatteryShowPercent.setSummary(mStatusBarBatteryShowPercent.getEntries()[index]);
            return true;
        }
        else if (preference == mStatusBarPowerMenu) {
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
