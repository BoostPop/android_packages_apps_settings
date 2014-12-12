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
	private static final String KEY_VOL_MEDIA = "volume_keys_control_media_stream";
	private static final String VOLUME_KEY_ADJUST_SOUND = "volume_key_adjust_sound";
	private static final String VOLUME_KEY_CURSOR_CONTROL = "volume_key_cursor_control";
	private static final String KEY_VOLBTN_MUSIC_CTRL = "volbtn_music_controls";

	private SwitchPreference mSafeHeadsetVolume;
	private SwitchPreference mVolumeKeysControlMedia;
	private SwitchPreference mVolumeKeyAdjustSound;
	private ListPreference mVolumeKeyCursorControl;
	private SwitchPreference mVolBtnMusicCtrl;

	@Override
    	public void onCreate(Bundle savedInstanceState) {
        	super.onCreate(savedInstanceState);

        	addPreferencesFromResource(R.xml.boost_volume_rocker);

		// === Safe headset ===
	        mSafeHeadsetVolume = (SwitchPreference) findPreference(KEY_SAFE_HEADSET_VOLUME);
                mSafeHeadsetVolume.setOnPreferenceChangeListener(this);
        	mSafeHeadsetVolume.setChecked(Settings.System.getInt(getContentResolver(),
               		Settings.System.SAFE_HEADSET_VOLUME, 1) != 0);

		// === Control Media ===	
	        mVolumeKeysControlMedia = (SwitchPreference) findPreference(KEY_VOL_MEDIA);	
	        mVolumeKeysControlMedia.setChecked(Settings.System.getInt(getContentResolver(),	
	                Settings.System.VOLUME_KEYS_CONTROL_MEDIA_STREAM, 0) != 0);
	        mVolumeKeysControlMedia.setOnPreferenceChangeListener(this);

		// === Adjust sound ===
	        mVolumeKeyAdjustSound = (SwitchPreference) findPreference(VOLUME_KEY_ADJUST_SOUND);
	        mVolumeKeyAdjustSound.setOnPreferenceChangeListener(this);
	        mVolumeKeyAdjustSound.setChecked(Settings.System.getInt(getContentResolver(),
	                VOLUME_KEY_ADJUST_SOUND, 1) != 0);		

		// === Cursor controll ===
	        mVolumeKeyCursorControl = (ListPreference) findPreference(VOLUME_KEY_CURSOR_CONTROL);
	        if (mVolumeKeyCursorControl != null) {
	            mVolumeKeyCursorControl.setOnPreferenceChangeListener(this);
	            mVolumeKeyCursorControl.setValue(Integer.toString(Settings.System.getInt(getActivity()
	                    .getContentResolver(), Settings.System.VOLUME_KEY_CURSOR_CONTROL, 0)));
	            mVolumeKeyCursorControl.setSummary(mVolumeKeyCursorControl.getEntry());
		}

		// === Music control ===
        	mVolBtnMusicCtrl = (SwitchPreference) findPreference(KEY_VOLBTN_MUSIC_CTRL);
                mVolBtnMusicCtrl.setOnPreferenceChangeListener(this);
        	mVolBtnMusicCtrl.setChecked(Settings.System.getInt(getContentResolver(),
        	        Settings.System.VOLUME_MUSIC_CONTROLS, 1) != 0);
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
                if (KEY_SAFE_HEADSET_VOLUME.equals(key)) {
                        if ((Boolean) objValue) {
                                Settings.System.putInt(getContentResolver(),
                                Settings.System.SAFE_HEADSET_VOLUME, 1);
                        } else {
                        showDialogInner(DLG_SAFE_HEADSET_VOLUME);
                        }
                    return true;
                }
                if (preference == mVolumeKeysControlMedia) {
			boolean value = (Boolean) objValue;
                        Settings.System.putInt(getContentResolver(), KEY_VOL_MEDIA,
        	                value ? 1 : 0);
                	return true;
	        } else if (preference == mVolumeKeyAdjustSound) {
	            boolean value = (Boolean) objValue;
	            Settings.System.putInt(getContentResolver(), VOLUME_KEY_ADJUST_SOUND,
	                    value ? 1: 0);
	            return true;
		} else if (preference == mVolBtnMusicCtrl ) {
                    boolean value = (Boolean) objValue;
		    Settings.System.putInt(getContentResolver(), KEY_VOLBTN_MUSIC_CTRL,
                    	    value ? 1 : 0);
		    return true;
	        } else if (preference == mVolumeKeyCursorControl) {
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


