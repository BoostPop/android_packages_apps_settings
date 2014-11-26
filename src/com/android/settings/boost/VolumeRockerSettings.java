package com.android.settings.boost;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;
import android.provider.Settings;
import android.util.Log;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

public class VolumeRockerSettings extends SettingsPreferenceFragment implements
        Preference.OnPreferenceChangeListener {

    	private static final String TAG = "VolumeRocker";

	private static final String VOLUME_WAKE_SCREEN = "volume_wake_screen";

	private SwitchPreference mVolumeWake;

	@Override
    	public void onCreate(Bundle savedInstanceState) {
        	super.onCreate(savedInstanceState);

        	addPreferencesFromResource(R.xml.boost_volume_rocker);

	        // === Volume to wake up ===
	        mVolumeWake = (SwitchPreference) findPreference(VOLUME_WAKE_SCREEN);
                mVolumeWake.setOnPreferenceChangeListener(this);
	        mVolumeWake.setChecked(Settings.System.getInt(getContentResolver(),
        	        Settings.System.VOLUME_WAKE_SCREEN, 1) != 0);
	
	}

	@Override
	public void onResume() {
		super.onResume();
	}

    	@Override
    	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        	// If we didn't handle it, let preferences handle it.
        	return super.onPreferenceTreeClick(preferenceScreen, preference);
    	}

	// === Volume to wake up ===
	public boolean onPreferenceChange(Preference preference, Object objValue) {
	        if (preference == mVolumeWake) {
        	    boolean value = (Boolean) objValue;
            		Settings.System.putInt(getContentResolver(), VOLUME_WAKE_SCREEN,
                    	value ? 1 : 0);
            	return true;
        	}
        return false;
    	}

}


