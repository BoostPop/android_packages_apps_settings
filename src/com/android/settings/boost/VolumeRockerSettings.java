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
        private static final int DLG_SAFE_HEADSET_VOLUME = 0;

        private static final String KEY_SAFE_HEADSET_VOLUME = "safe_headset_volume";
	private static final String VOLUME_WAKE_SCREEN = "volume_wake_screen";

	private SwitchPreference mSafeHeadsetVolume;
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

		// === Safe headset ===
	        mSafeHeadsetVolume = (SwitchPreference) findPreference(KEY_SAFE_HEADSET_VOLUME);
                mSafeHeadsetVolume.setOnPreferenceChangeListener(this);
        	mSafeHeadsetVolume.setChecked(Settings.System.getInt(getContentResolver(),
               		Settings.System.SAFE_HEADSET_VOLUME, 1) != 0);

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
		if ((Boolean) objValue) {
                	Settings.System.putInt(getContentResolver(),
                        Settings.System.SAFE_HEADSET_VOLUME, 1);
            	} else {
                	showDialogInner(DLG_SAFE_HEADSET_VOLUME);
            	}
	        if (preference == mVolumeWake) {
                    boolean value = (Boolean) objValue;
                        Settings.System.putInt(getContentResolver(), VOLUME_WAKE_SCREEN,
                        value ? 1 : 0);
                return true;
                }
        	return false;
    	}
	
	private void showDialogInner(int id) {
        	DialogFragment newFragment = MyAlertDialogFragment.newInstance(id);
        	newFragment.setTargetFragment(this, 0);
        	newFragment.show(getFragmentManager(), "dialog " + id);
    	}

    	public static class MyAlertDialogFragment extends DialogFragment {

        	public static MyAlertDialogFragment newInstance(int id) {
            		MyAlertDialogFragment frag = new MyAlertDialogFragment();
            		Bundle args = new Bundle();
            		args.putInt("id", id);
            		frag.setArguments(args);
            		return frag;
        	}

        	VolumeRockerSettings getOwner() {
            		return (VolumeRockerSettings) getTargetFragment();
        	}

        	@Override
        		public Dialog onCreateDialog(Bundle savedInstanceState) {
            		int id = getArguments().getInt("id");
            		switch (id) {
                		case DLG_SAFE_HEADSET_VOLUME:
                    			return new AlertDialog.Builder(getActivity())
                    			.setTitle(R.string.attention)
                    			.setMessage(R.string.safe_headset_volume_warning_dialog_text)
                   			.setPositiveButton(R.string.dlg_ok,
                        			new DialogInterface.OnClickListener() {
                        				public void onClick(DialogInterface dialog, int which) {
                            				Settings.System.putInt(getOwner().getContentResolver(),
                                    			Settings.System.SAFE_HEADSET_VOLUME, 0);

                        			}
                   			 })
                    			.setNegativeButton(R.string.dlg_cancel,
                        		new DialogInterface.OnClickListener() {
                        			public void onClick(DialogInterface dialog, int which) {
                            			dialog.cancel();
                        		}
                    		})
                    		.create();
            		}
            		throw new IllegalArgumentException("unknown id " + id);
        	}

        	@Override
        	public void onCancel(DialogInterface dialog) {
            		int id = getArguments().getInt("id");
            		switch (id) {
                		case DLG_SAFE_HEADSET_VOLUME:
                    		getOwner().mSafeHeadsetVolume.setChecked(true);
                    		break;
            		}
        	}
    }
}


