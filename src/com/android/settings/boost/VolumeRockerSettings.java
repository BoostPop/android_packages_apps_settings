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

public class VolumeRockerSettings extends SettingsPreferenceFragment implements Preference.OnPreferenceChangeListener{

    	private static final String TAG = "VolumeRocker";

	private static final String VOLUME_KEY_ADJUST_SOUND = "volume_key_adjust_sound";
	private static final String VOLUME_KEY_CURSOR_CONTROL = "volume_key_cursor_control";

	private ListPreference mVolumeKeyCursorControl;

	@Override
    	public void onCreate(Bundle savedInstanceState) {
        	super.onCreate(savedInstanceState);

        	addPreferencesFromResource(R.xml.boost_volume_rocker);

		// === Cursor controll ==
	        mVolumeKeyCursorControl = (ListPreference) findPreference(VOLUME_KEY_CURSOR_CONTROL);
	        if (mVolumeKeyCursorControl != null) {
	            mVolumeKeyCursorControl.setOnPreferenceChangeListener(this);
	            mVolumeKeyCursorControl.setValue(Integer.toString(Settings.System.getInt(getActivity()
	                    .getContentResolver(), Settings.System.VOLUME_KEY_CURSOR_CONTROL, 0)));
	            mVolumeKeyCursorControl.setSummary(mVolumeKeyCursorControl.getEntry());
		}	
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

	public boolean onPreferenceChange(Preference preference, Object objValue) {
                final String key = preference.getKey();
	        if (preference == mVolumeKeyCursorControl) {
			String volumeKeyCursorControl = (String) objValue;
			int volumeKeyCursorControlValue = Integer.parseInt(volumeKeyCursorControl);
			Settings.System.putInt(getActivity().getContentResolver(),
				Settings.System.VOLUME_KEY_CURSOR_CONTROL, volumeKeyCursorControlValue);
		        int volumeKeyCursorControlIndex = mVolumeKeyCursorControl.findIndexOfValue(volumeKeyCursorControl);
		        mVolumeKeyCursorControl.setSummary(mVolumeKeyCursorControl.getEntries()[volumeKeyCursorControlIndex]);
	        	return true;			
		}        
	return false;
    	}

}


