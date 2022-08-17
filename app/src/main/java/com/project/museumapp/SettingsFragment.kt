package com.project.museumapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.appcompat.app.AppCompatDelegate


class SettingsFragment : Fragment() {

    private lateinit var sp: SharedPref

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_settings, container, false)
        val switch : Switch = view.findViewById(R.id.switchTheme)
        sp = SharedPref(view.context)
        if (sp.loadNightModeState() == true) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            switch.text = getString(R.string.themeButtonOn)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            switch.text = getString(R.string.themeButtonOff)
        }

        if (sp.loadNightModeState() == true) {
            switch.isChecked = true
        }
        switch.setOnCheckedChangeListener { _, isChecked ->
            if (switch.isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switch.text = getString(R.string.themeButtonOn)
                sp.setNightModeState(true)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switch.text = getString(R.string.themeButtonOff)
                sp.setNightModeState(false)
            }
        }
        return view
    }
}