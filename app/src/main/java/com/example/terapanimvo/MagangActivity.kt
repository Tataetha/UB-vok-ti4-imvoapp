package com.example.terapanimvo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.terapanimvo.ui.magang.MagangFragment

class MagangActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.magang_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MagangFragment.newInstance())
                .commitNow()
        }
    }
}
