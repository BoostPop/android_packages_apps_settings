package com.android.settings.boost;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;

public class SecurityTweaks extends SettingsPreferenceFragment implements
        OnPreferenceChangeListener {

    // General
    private static String STATUS_BAR_GENERAL_CATEGORY = "status_bar_general_category";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.boost_security_tweaks);

    }

    public boolean onPreferenceChange(Preference preference, Object objValue) {
        return false;
    }

    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
 		return super.onPreferenceTreeClick(preferenceScreen, preference);
    }
}
