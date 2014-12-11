package com.android.settings.boost;

import android.content.ContentResolver;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;

public class StatusBarSettings extends SettingsPreferenceFragment implements
        OnPreferenceChangeListener {

    private static final String STATUS_BAR_SHOW_BATTERY_PERCENT = "status_bar_show_battery_percent";

    private SwitchPreference mStatusBarShowBatteryPercent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.boost_status_bar_settings);
	PreferenceScreen prefSet = getPreferenceScreen();

        // === status bar show battery percent ===
        mStatusBarShowBatteryPercent = (SwitchPreference) findPreference(STATUS_BAR_SHOW_BATTERY_PERCENT);
        mStatusBarShowBatteryPercent.setOnPreferenceChangeListener(this);
        int statusBarShowBatteryPercent = Settings.System.getInt(getContentResolver(),
                STATUS_BAR_SHOW_BATTERY_PERCENT, 0);
        mStatusBarShowBatteryPercent.setChecked(statusBarShowBatteryPercent != 0);

    }

    public boolean onPreferenceChange(Preference preference, Object objValue) {
	ContentResolver cr = getActivity().getContentResolver();
	boolean value = (Boolean) objValue;
        if (preference == mStatusBarShowBatteryPercent) {
            Settings.System.putInt(cr,
                    Settings.System.STATUS_BAR_SHOW_BATTERY_PERCENT, value ? 1 : 0);
            return true;
        }

        return false;
    }

    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
 	return super.onPreferenceTreeClick(preferenceScreen, preference);
    }
}

