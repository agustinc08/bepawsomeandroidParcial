package com.example.bepawsomeandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils.replace
import com.example.bepawsomeandroid.Fragment.Home

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
       /* supportFragmentManager.commit{
            replace<Home>(R.id.frame_container)
            setReorderingAllowed(true)
            addToBackStack("replacement")
        }*/

        
    }
}