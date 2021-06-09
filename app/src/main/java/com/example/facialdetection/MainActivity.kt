package com.example.facialdetection

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.facialdetection.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    //lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var viewModel: MainActivityViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = MainActivityViewModel()
        supportActionBar?.title = "uiuuuuuuuuuuu"
        val navController = findNavController(R.id.navigate_main)
        //appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController)
        viewModel.title.observe(this, Observer {
            supportActionBar?.title = it
        })



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