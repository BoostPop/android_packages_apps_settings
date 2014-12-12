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

public class TweaksSettings extends SettingsPreferenceFragment implements
        Preference.OnPreferenceChangeListener {

        private static final String TAG = "Tweaks";

	private static final String SCREENSHOT_SOUND = "screenshot_sound";
	private static final String KILL_APP_LONGPRESS_BACK = "kill_app_longpress_back";

        private SwitchPreference mScreenshotSound;
        private SwitchPreference mKillAppLongpressBack;

        @Override
        public void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);

                addPreferencesFromResource(R.xml.boost_tweaks_settings);

		// === Screenshot sound ===
	        mScreenshotSound = (SwitchPreference) findPreference(SCREENSHOT_SOUND);
                mScreenshotSound.setOnPreferenceChangeListener(this);
                mScreenshotSound.setChecked(Settings.System.getInt(getContentResolver(),
                        Settings.System.SCREENSHOT_SOUND, 1) != 0);

		// === Kill app ===
                mKillAppLongpressBack = (SwitchPreference) findPreference(KILL_APP_LONGPRESS_BACK);
                mKillAppLongpressBack.setOnPreferenceChangeListener(this);
                mKillAppLongpressBack.setChecked(Settings.Secure.getInt(getContentResolver(),
                        Settings.Secure.KILL_APP_LONGPRESS_BACK, 0) != 0);

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
