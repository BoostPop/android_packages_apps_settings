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
import android.preference.PreferenceCategory;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

public class NotificationsSettings extends SettingsPreferenceFragment implements
        Preference.OnPreferenceChangeListener {

    	private static final String TAG = "NotificationsSettings";

	private static final String KEY_NOTIFICATION_LED = "notification_light";

	private PreferenceScreen mNotificationLed;
	
	@Override
    	public void onCreate(Bundle savedInstanceState) {
        	super.onCreate(savedInstanceState);

        	addPreferencesFromResource(R.xml.boost_notifications_settings);

		mNotificationLed = (PreferenceScreen) findPreference(KEY_NOTIFICATION_LED);
		if (mNotificationLed != null) {
			updateLedSummary();
		}
	}

	private void updateLedSummary() {
		if (Settings.System.getInt(getActivity().getContentResolver(),
			Settings.System.NOTIFICATION_LIGHT_PULSE, 0) == 1) {
			mNotificationLed.setSummary(getString(R.string.enabled));
		} else {
			mNotificationLed.setSummary(getString(R.string.disabled));
		}
	
	}

        @Override
        public void onResume() {
                 super.onResume();
		 updateLedSummary();
        }
	
	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
	    // TODO Auto-generated method stub
        return false;
    }
}
