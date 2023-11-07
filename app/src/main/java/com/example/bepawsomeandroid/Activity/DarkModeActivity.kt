package com.example.bepawsomeandroid.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.switchmaterial.SwitchMaterial
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import com.example.bepawsomeandroid.R

class DarkModeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dark_mode)

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
