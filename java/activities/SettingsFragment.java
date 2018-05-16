package com.cwprogramming.pacman.activities;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v4.app.Fragment;

import com.cwprogramming.pacman.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {


    public SettingsFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        SharedPreferences sharedPref = getPreferenceScreen().getSharedPreferences();
        onSharedPreferenceChanged(sharedPref,"init_ghosts");
        onSharedPreferenceChanged(sharedPref,"additional_ghosts");
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference preference = findPreference(key);

        switch (key){
            case "init_ghosts":
                int i = sharedPreferences.getInt(key,2);
                preference.setSummary(""+i);
                break;
            case "additional_ghosts":
                preference.setSummary("" + sharedPreferences.getInt(key,2));
                break;
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause(){
        super.onPause();
    }
}
