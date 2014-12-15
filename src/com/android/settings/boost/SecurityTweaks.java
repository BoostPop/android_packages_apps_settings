package com.android.settings.boost;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;

import com.android.internal.widget.LockPatternUtils;

public class SecurityTweaks extends SettingsPreferenceFragment implements
        OnPreferenceChangeListener {

    private static final String PREF_BLOCK_ON_SECURE_KEYGUARD = "block_on_secure_keyguard";
    private static final String LOCKSCREEN_QUICK_UNLOCK_CONTROL = "quick_unlock_control";
    private static final String LOCKSCREEN_ENABLE_POWER_MENU = "lockscreen_enable_power_menu";

    private SwitchPreference mBlockOnSecureKeyguard;
    private SwitchPreference mQuickUnlockScreen;
    private SwitchPreference mBlockOnSecurePowermenu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.boost_security_tweaks);

	PreferenceScreen prefs = getPreferenceScreen();

	final LockPatternUtils lockPatternUtils = new LockPatternUtils(getActivity());	
        mBlockOnSecureKeyguard = (SwitchPreference) findPreference(PREF_BLOCK_ON_SECURE_KEYGUARD);	
        mQuickUnlockScreen = (SwitchPreference) findPreference(LOCKSCREEN_QUICK_UNLOCK_CONTROL);
	mBlockOnSecurePowermenu = (SwitchPreference) findPreference(LOCKSCREEN_ENABLE_POWER_MENU);

        if (lockPatternUtils.isSecure()) {	

            // === Block QS on secure keyguard ===
            mBlockOnSecureKeyguard.setChecked(Settings.Secure.getInt(getContentResolver(),	
                    Settings.Secure.STATUS_BAR_LOCKED_ON_SECURE_KEYGUARD, 1) == 1);
            mBlockOnSecureKeyguard.setOnPreferenceChangeListener(this);	

            // === Quick Unlock Screen Control ===
            if (mQuickUnlockScreen != null) {
            	mQuickUnlockScreen.setChecked(Settings.Secure.getInt(getContentResolver(),
                	Settings.Secure.LOCKSCREEN_QUICK_UNLOCK_CONTROL, 1) == 1);
            	mQuickUnlockScreen.setOnPreferenceChangeListener(this);
	    }

            // === Block Powermenu on secure keyguard ===
	    mBlockOnSecurePowermenu.setChecked(Settings.System.getInt(getContentResolver(),
                    Settings.System.LOCKSCREEN_ENABLE_POWER_MENU, 1) == 1);	
	    mBlockOnSecurePowermenu.setOnPreferenceChangeListener(this); 

        } else {	
            prefs.removePreference(mBlockOnSecureKeyguard);	
            prefs.removePreference(mQuickUnlockScreen);
            prefs.removePreference(mBlockOnSecurePowermenu);
        }

    }

    public boolean onPreferenceChange(Preference preference, Object objValue) {
	if (preference == mBlockOnSecureKeyguard) {	
            Settings.Secure.putInt(getContentResolver(),	
                    Settings.Secure.STATUS_BAR_LOCKED_ON_SECURE_KEYGUARD,	
                    (Boolean) objValue ? 1 : 0);	
            return true;	
        } else if (preference == mQuickUnlockScreen) {
            Settings.Secure.putInt(getActivity().getApplicationContext().getContentResolver(),
                    Settings.Secure.LOCKSCREEN_QUICK_UNLOCK_CONTROL,
                    (Boolean) objValue ? 1 : 0);
	    return true;
	} else if (preference == mBlockOnSecurePowermenu) {
            Settings.System.putInt(getActivity().getApplicationContext().getContentResolver(),
                    Settings.System.LOCKSCREEN_ENABLE_POWER_MENU,
                    (Boolean) objValue ? 1 : 0);
            return true;
	}	
        return false;
    }

    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
 		return super.onPreferenceTreeClick(preferenceScreen, preference);
    }
}
