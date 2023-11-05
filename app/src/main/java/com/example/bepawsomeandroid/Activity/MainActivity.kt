<<<<<<< HEAD:app/src/main/java/com/example/bepawsomeandroid/Activity/MainActivity.kt
package com.example.bepawsomeandroid.Activity
=======
package com.example.bepawsomeandroid.Fragment
>>>>>>> develop:app/src/main/java/com/example/bepawsomeandroid/MainActivity.kt

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.fragment.app.replace
<<<<<<< HEAD:app/src/main/java/com/example/bepawsomeandroid/Activity/MainActivity.kt
import com.example.bepawsomeandroid.Fragment.*
=======
import com.example.bepawsomeandroid.Fragment.Adoption
import com.example.bepawsomeandroid.Fragment.Favorite
import com.example.bepawsomeandroid.Fragment.Home
>>>>>>> develop:app/src/main/java/com/example/bepawsomeandroid/MainActivity.kt
import com.example.bepawsomeandroid.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    lateinit var navegation: BottomNavigationView
    private lateinit var databaseReference: DatabaseReference

    private val mOnNavMenu = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.home -> {
                supportFragmentManager.commit {
                    replace<Home>(R.id.frame_container)
                    setReorderingAllowed(true)
                    addToBackStack("replacement")
                }
                return@OnNavigationItemSelectedListener true
            }

            R.id.favorite -> {
                supportFragmentManager.commit {
                    replace<Favorite>(R.id.frame_container)
                    setReorderingAllowed(true)
                    addToBackStack("replacement")
                }
                return@OnNavigationItemSelectedListener true
            }

            R.id.adoption -> {
                supportFragmentManager.commit {
                    replace<Adoption>(R.id.frame_container)
                    setReorderingAllowed(true)
                    addToBackStack("replacement")
                }
                return@OnNavigationItemSelectedListener true
            }

            R.id.publication -> {
                supportFragmentManager.commit {
                    replace<Publication>(R.id.frame_container)
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

        // Inicializar Firebase
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        databaseReference = FirebaseDatabase.getInstance().reference

        // Configurar la barra de navegación
        navegation = findViewById(R.id.navMenu)
        navegation.setOnNavigationItemSelectedListener(mOnNavMenu)

        // Reemplazar el fragmento inicial
        supportFragmentManager.commit {
            replace<Home>(R.id.frame_container)
            setReorderingAllowed(true)
            addToBackStack("replacement")
        }
    }
}
