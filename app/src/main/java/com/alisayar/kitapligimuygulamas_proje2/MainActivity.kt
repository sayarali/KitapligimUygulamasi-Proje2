package com.alisayar.kitapligimuygulamas_proje2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.navigateUp
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.alisayar.kitapligimuygulamas_proje2.databinding.ActivityMainBinding
import com.alisayar.kitapligimuygulamas_proje2.login.LoginActivity
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        auth = FirebaseAuth.getInstance()
        if(auth.currentUser == null){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        setupNavigation()

    }

    override fun onSupportNavigateUp() = navigateUp(findNavController(R.id.nav_host_fragment), binding.drawerLayout)

    private fun setupNavigation() {

        val navController = findNavController(R.id.nav_host_fragment)
        setSupportActionBar(binding.toolbar)

        setupActionBarWithNavController(navController, binding.drawerLayout)
        binding.navigationView.setupWithNavController(navController)



        navController.addOnDestinationChangedListener{_, destination: NavDestination, _ ->

        }



    }







}


