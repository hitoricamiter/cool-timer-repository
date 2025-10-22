package ru.zaikin.cooltimer

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.CheckBoxPreference
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat

class SettingsFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.timer_preferences)

        val sharedPreferences = preferenceScreen.sharedPreferences
        val preferenceScreen = preferenceScreen
        val count = preferenceScreen.preferenceCount

        for (i in 0 until count) {
            val preference = preferenceScreen.getPreference(i)

            if (preference !is CheckBoxPreference) {
                val value = sharedPreferences.getString(preference.key, "") ?: ""
                setPreferenceLabel(preference, value)
            }
        }
    }

    private fun setPreferenceLabel(preference: Preference, value: String) {
        if (preference is ListPreference) {
            val index = preference.findIndexOfValue(value)
            if (index >= 0) {
                preference.summary = preference.entries[index]
            }
        }
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key == null) return

        val preference = findPreference<Preference>(key)
        if (preference != null && preference !is CheckBoxPreference) {
            val value = sharedPreferences?.getString(key, "") ?: ""
            setPreferenceLabel(preference, value)
        }
    }

    override fun onStart() {
        super.onStart()
        preferenceScreen.sharedPreferences?.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onStop() {
        super.onStop()
        preferenceScreen.sharedPreferences?.unregisterOnSharedPreferenceChangeListener(this)
    }
}