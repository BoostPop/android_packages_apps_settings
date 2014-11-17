package com.android.settings.boost;

import android.os.Bundle;
import android.preference.Preference;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

public class LedSettings extends SettingsPreferenceFragment implements
        Preference.OnPreferenceChangeListener {

    	private static final String TAG = "LedSettings";

	@Override
    	public void onCreate(Bundle savedInstanceState) {
        	super.onCreate(savedInstanceState);

        	addPreferencesFromResource(R.xml.boost_led_settings);
	}

	@Override
	    public boolean onPreferenceChange(Preference preference, Object newValue) {
	    // TODO Auto-generated method stub
        return false;
    }
}
