package com.example.terapanimvo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.terapanimvo.ui.berita.BeritaFragment

class BeritaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.berita_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, BeritaFragment.newInstance())
                .commitNow()
        }
    }
}
