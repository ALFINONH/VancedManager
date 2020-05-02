package com.vanced.manager.ui.fragments

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.fragment.app.FragmentManager
import androidx.preference.*
import com.vanced.manager.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        //Fuck Android 6 android 5 users! Because theme is not working
        //we can't display preference for them.
        //They should've upgraded to something newer
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
            val prefScreen: PreferenceScreen? = findPreference("prefScreen")
            val prefCategory: PreferenceCategory? = findPreference("interface_category")
            prefScreen?.removePreference(prefCategory)
        }

        activity?.title = getString(R.string.title_settings)
        setHasOptionsMenu(true)

        val updateCheck: Preference? = findPreference("update_check")
        updateCheck?.setOnPreferenceClickListener {
            val fm = childFragmentManager.beginTransaction()
            val updateDialog = UpdateCheckFragment()
            updateDialog.show(fm, "Update Center")
            true
        }
        
       val themeSwitch: ListPreference? = findPreference("theme_mode")
        themeSwitch?.setOnPreferenceChangeListener { _, _ ->

            when (themeSwitch.value){
                "LIGHT" -> {
                    activity?.setTheme(R.style.LightTheme)
                    activity?.recreate()
                }
                "DARK" -> {
                    activity?.setTheme(R.style.DarkTheme)
                    activity?.recreate()
                }
                "FOLLOW" -> {
                    when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                        Configuration.UI_MODE_NIGHT_YES ->{
                            activity?.setTheme(R.style.DarkTheme)
                            activity?.recreate()
                        }
                        Configuration.UI_MODE_NIGHT_NO -> {
                            activity?.setTheme(R.style.LightTheme)
                            activity?.recreate()
                        }
                    }
                }
                else -> {
                    activity?.setTheme(R.style.LightTheme)
                    activity?.recreate()
                }
            }
            true
        }

    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.secret_settings_menu, menu)
        super .onCreateOptionsMenu(menu, inflater)
    }

}