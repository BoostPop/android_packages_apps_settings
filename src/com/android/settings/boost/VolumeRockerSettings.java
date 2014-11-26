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

public class VolumeRockerSettings extends SettingsPreferenceFragment {

    	private static final String TAG = "VolumeRocker";

	@Override
    	public void onCreate(Bundle savedInstanceState) {
        	super.onCreate(savedInstanceState);

        	addPreferencesFromResource(R.xml.boost_volume_rocker);

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

	

}


