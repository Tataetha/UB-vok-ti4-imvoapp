package com.example.terapanimvo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.terapanimvo.ui.beranda.BerandaFragment

class BerandaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.beranda_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, BerandaFragment.newInstance())
                .commitNow()
        }
    }
}
