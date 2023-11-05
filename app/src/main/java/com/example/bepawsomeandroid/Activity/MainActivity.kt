package com.example.bepawsomeandroid.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.bepawsomeandroid.Fragment.*
import com.example.bepawsomeandroid.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var navegation : BottomNavigationView

    private val mOnNavMenu = BottomNavigationView.OnNavigationItemSelectedListener { item ->

        when (item.itemId){
            R.id.home -> {
                supportFragmentManager.commit {
                    replace<Home>(R.id.fragmentContainerView)
                    setReorderingAllowed(true)
                    addToBackStack("replacement")
                }
                return@OnNavigationItemSelectedListener true
            }

            R.id.favorite -> {
                supportFragmentManager.commit {
                    replace<Favorite>(R.id.fragmentContainerView)
                    setReorderingAllowed(true)
                    addToBackStack("replacement")
                }
                return@OnNavigationItemSelectedListener true
            }

            R.id.adoption -> {
                supportFragmentManager.commit {
                    replace<Adoption>(R.id.fragmentContainerView)
                    setReorderingAllowed(true)
                    addToBackStack("replacement")
                }
                return@OnNavigationItemSelectedListener true
            }

            R.id.publication -> {
                supportFragmentManager.commit {
                    replace<Publication>(R.id.fragmentContainerView)
                    setReorderingAllowed(true)
                    addToBackStack("replacement")
                }
                return@OnNavigationItemSelectedListener true
            }
        }

        false
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navegation = findViewById(R.id.navMenu)
        navegation.setOnNavigationItemSelectedListener(mOnNavMenu)

       supportFragmentManager.commit{
            replace<Home>(R.id.fragmentContainerView)
            setReorderingAllowed(true)
            addToBackStack("replacement")
        }

        val dCView: ImageView = findViewById(R.id.drawerClickId)

        val clickListener = View.OnClickListener {
            val intent = Intent(this, DrawerMenuActivity::class.java)
            startActivity(intent)
        }

        dCView.setOnClickListener(clickListener);


    }
}