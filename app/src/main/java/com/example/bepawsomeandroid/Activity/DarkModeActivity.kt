package com.example.bepawsomeandroid.Activity

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.switchmaterial.SwitchMaterial
import androidx.appcompat.app.AppCompatDelegate
import com.example.bepawsomeandroid.R

class DarkModeActivity : AppCompatActivity() {

    private lateinit var swDarkMode: SwitchMaterial

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dark_mode)

        swDarkMode = findViewById(R.id.swDarkMode)
        swDarkMode.isChecked = isDarkModeEnabled()

        swDarkMode.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                enableDarkMode()
            } else {
                disableDarkMode()
            }
        }
    }

    private fun enableDarkMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        applyDayNightTheme()
    }

    private fun disableDarkMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        applyDayNightTheme()
    }

    private fun isDarkModeEnabled(): Boolean {
        return AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
    }

    private fun applyDayNightTheme() {
        val mode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        delegate.localNightMode = when (mode) {
            Configuration.UI_MODE_NIGHT_YES -> AppCompatDelegate.MODE_NIGHT_YES
            else -> AppCompatDelegate.MODE_NIGHT_NO
        }
    }
}




/*
        val swDarkMode = findViewById<SwitchMaterial>(R.id.swDarkMode)

        swDarkMode.isChecked = isDarkModeEnabled()
        swDarkMode.setOnCheckedChangeListener { _, isSelected ->
            if (isSelected) {
                enableDarkMode()
            } else {
                disableDarkMode()
            }
        }
    }

    private fun enableDarkMode() {
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
        delegate.applyDayNight()
    }

    private fun disableDarkMode() {
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
        delegate.applyDayNight()
    }


    private fun isDarkModeEnabled(): Boolean {
        return AppCompatDelegate.getDefaultNightMode() == MODE_NIGHT_YES
    }

}
*/
