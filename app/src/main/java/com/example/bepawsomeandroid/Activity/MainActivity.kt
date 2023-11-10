package com.example.bepawsomeandroid.Activity
import android.content.Context
import android.content.SharedPreferences
import com.example.bepawsomeandroid.Fragment.Publication
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.bepawsomeandroid.Fragment.Adoption
import com.example.bepawsomeandroid.Fragment.Favorite
import com.example.bepawsomeandroid.Fragment.Home
import com.example.bepawsomeandroid.Fragment.Profile
import com.example.bepawsomeandroid.Models.User
import com.example.bepawsomeandroid.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.Gson
import org.json.JSONException
import org.json.JSONObject

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

            R.id.profile -> {
                supportFragmentManager.commit {
                    replace<Profile>(R.id.frame_container)
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



        var sharedPreferences: SharedPreferences = getSharedPreferences("Credenciales", Context.MODE_PRIVATE)
        //var nameUser: String? = sharedPreferences.getString("nameUser", "")
        //var mailUser: String? = sharedPreferences.getString("mailUser", "")
        //var imageUrlUser: String? = sharedPreferences.getString("imageUrlUser","")
        //var passwordUser: String? = sharedPreferences.getString("passwordUser","")


        val jsonObjectString = sharedPreferences.getString("UserLogueado", null)

        var jsonObject: JSONObject? = null

        if (jsonObjectString != null) {
            try {
                jsonObject = JSONObject(jsonObjectString)
                // Ahora tienes tu objeto JSON
            } catch (e: JSONException) {
                e.printStackTrace()
                }
        }

        var gson = Gson()

        var userObject = gson.fromJson(jsonObject.toString(), User::class.java)

       var modoOscuro = sharedPreferences.getBoolean("modoOscuro", false)
        println(modoOscuro)
/*

        try {
            if (modoOscuro) {
                // Aplicar tema oscuro
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                // Aplicar tema claro
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
*/

        //println(userObject.name)

        val searchView = findViewById<SearchView>(R.id.searchView)


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
               // val searchResults = searchInYourData(query)
               // updateFrameLayoutContent(searchResults)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(query: String): Boolean {
                return false;
            }
        })

       FirebaseDatabase.getInstance().setPersistenceEnabled(true)
       databaseReference = FirebaseDatabase.getInstance().reference

        navegation = findViewById(R.id.navMenu)
        navegation.setOnNavigationItemSelectedListener(mOnNavMenu)

        supportFragmentManager.commit {
            replace<Home>(R.id.frame_container)
            setReorderingAllowed(true)
            addToBackStack("replacement")
        }
    }


}

