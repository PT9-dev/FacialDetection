package com.example.facialdetection

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.facialdetection.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    //lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val navController = findNavController(R.id.navigate_main)
        //appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController)


    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
       menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.navigate_main)

        return navController.navigateUp() || super.onSupportNavigateUp()
    }




}


//https://medium.com/javarevisited/lets-develop-an-android-app-to-upload-files-and-images-on-cloud-f9670d812060