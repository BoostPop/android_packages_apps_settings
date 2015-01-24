package com.android.settings.boost;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.UserHandle;
import android.preference.ListPreference;
import android.preference.Preference;

import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;
import android.provider.Settings;
import android.util.Log;
import android.preference.PreferenceCategory;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

import java.util.ArrayList;

import com.android.settings.cyanogenmod.DisplayRotation;


public class TweaksSettings extends SettingsPreferenceFragment implements
        Preference.OnPreferenceChangeListener {

        private static final String TAG = "Tweaks";

	private static final String KEY_DEVICE = "device_category";
	private static final String SCREENSHOT_SOUND = "screenshot_sound";
	private static final String KILL_APP_LONGPRESS_BACK = "kill_app_longpress_back";
	private static final String KEY_BATTERY_LIGHT = "battery_light";
	private static final String KEY_DISPLAY_ROTATION = "display_rotation";

        private SwitchPreference mScreenshotSound;
        private SwitchPreference mKillAppLongpressBack;
	private PreferenceScreen mBatteryPulse;
	private PreferenceCategory mDevice;
        private PreferenceScreen mDisplayRotationPreference;

        @Override
        public void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);

                addPreferencesFromResource(R.xml.boost_tweaks_settings);
		PreferenceScreen prefSet = getPreferenceScreen();

		final PreferenceCategory mDevice = (PreferenceCategory) prefSet.findPreference(KEY_DEVICE);

		// === Screenshot sound ===
	        mScreenshotSound = (SwitchPreference) findPreference(SCREENSHOT_SOUND);
                mScreenshotSound.setOnPreferenceChangeListener(this);
                mScreenshotSound.setChecked(Settings.System.getInt(getContentResolver(),
                        Settings.System.SCREENSHOT_SOUND, 1) != 0);

		mDisplayRotationPreference = (PreferenceScreen) findPreference(KEY_DISPLAY_ROTATION);
		updateDisplayRotationPreferenceDescription();

		mBatteryPulse = (PreferenceScreen) findPreference(KEY_BATTERY_LIGHT);
                mBatteryPulse.setOnPreferenceChangeListener(this);
		if (mBatteryPulse != null) {
	            if (getResources().getBoolean(
	                    com.android.internal.R.bool.config_intrusiveBatteryLed) == false) {
		                mDevice.removePreference(mBatteryPulse);
	            } else {
			updatemBatterySummary();
	            }
	        }
		
		// === Kill app ===
                mKillAppLongpressBack = (SwitchPreference) findPreference(KILL_APP_LONGPRESS_BACK);
                mKillAppLongpressBack.setOnPreferenceChangeListener(this);
                mKillAppLongpressBack.setChecked(Settings.Secure.getInt(getContentResolver(),
                        Settings.Secure.KILL_APP_LONGPRESS_BACK, 0) != 0);

	}

	private void updatemBatterySummary() {
		if (Settings.System.getInt(getActivity().getContentResolver(),
                        Settings.System.BATTERY_LIGHT_ENABLED, 1) == 1) {
                        mBatteryPulse.setSummary(getString(R.string.enabled));
                } else {
                        mBatteryPulse.setSummary(getString(R.string.disabled));
                }
        }

    private void updateDisplayRotationPreferenceDescription() {
        if (mDisplayRotationPreference == null) {
            // The preference was removed, do nothing
            return;
        }

        // We have a preference, lets update the summary
        boolean rotationEnabled = Settings.System.getInt(getContentResolver(),
                Settings.System.ACCELEROMETER_ROTATION, 0) != 0;

        if (!rotationEnabled) {
            mDisplayRotationPreference.setSummary(R.string.display_rotation_disabled);
            return;
        }

        StringBuilder summary = new StringBuilder();
        int mode = Settings.System.getInt(getContentResolver(),
                Settings.System.ACCELEROMETER_ROTATION_ANGLES,
                DisplayRotation.ROTATION_0_MODE
                | DisplayRotation.ROTATION_90_MODE
                | DisplayRotation.ROTATION_270_MODE);
        ArrayList<String> rotationList = new ArrayList<String>();
        String delim = "";

        if ((mode & DisplayRotation.ROTATION_0_MODE) != 0) {
            rotationList.add("0");
        }
        if ((mode & DisplayRotation.ROTATION_90_MODE) != 0) {
            rotationList.add("90");
        }
        if ((mode & DisplayRotation.ROTATION_180_MODE) != 0) {
            rotationList.add("180");
        }
        if ((mode & DisplayRotation.ROTATION_270_MODE) != 0) {
            rotationList.add("270");
        }
        for (int i = 0; i < rotationList.size(); i++) {
            summary.append(delim).append(rotationList.get(i));
            if ((rotationList.size() - i) > 2) {
                delim = ", ";
            } else {
                delim = " & ";
            }
        }
        summary.append(" " + getString(R.string.display_rotation_unit));
        mDisplayRotationPreference.setSummary(summary);
    }

        @Override
        public void onResume() {
                super.onResume();
		updatemBatterySummary();
		updateDisplayRotationPreferenceDescription();
        }

        @Override
        public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
                // If we didn't handle it, let preferences handle it.
                return super.onPreferenceTreeClick(preferenceScreen, preference);
        }

        public boolean onPreferenceChange(Preference preference, Object objValue) {
	        if (preference == mScreenshotSound) {
        	    boolean value = (Boolean) objValue;
            		Settings.System.putInt(getContentResolver(), SCREENSHOT_SOUND,
                    	value ? 1 : 0);
            	    return true;
                } else if (preference == mKillAppLongpressBack) {
                    boolean value = (Boolean) objValue;
                        Settings.Secure.putInt(getContentResolver(), KILL_APP_LONGPRESS_BACK,
                        value ? 1 : 0);
                    return true;
		}
                return false;
        }

}	 
