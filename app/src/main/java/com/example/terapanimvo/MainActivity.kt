package com.example.terapanimvo

import android.graphics.Color
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.terapanimvo.helper.BASE_URL
import com.google.android.material.navigation.NavigationView

val ip = BASE_URL

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbarMain)
        setSupportActionBar(toolbar)

        val drawerlayout : DrawerLayout = findViewById(R.id.drawer_layout)
        val navView : NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_beranda, R.id.navigation_magang, R.id.navigation_berita
            ), drawerlayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val bottomNavView: BottomNavigationView = findViewById(R.id.navigation)
        bottomNavView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

//        toolbar.setNavigationOnClickListener {
//            if (drawerlayout.isDrawerOpen(Gravity.RIGHT))
//            {
//                drawerlayout.closeDrawer(Gravity.RIGHT)
//            }
//            else
//            {
//                drawerlayout.openDrawer(Gravity.RIGHT)
//            }
//        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_beranda -> {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.navigation_beranda)
                }
                R.id.navigation_magang -> {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.navigation_magang)
                }
                R.id.navigation_berita -> {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.navigation_berita)
                }
            }
            false
        }
}
