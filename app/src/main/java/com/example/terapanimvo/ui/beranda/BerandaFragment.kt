package com.example.terapanimvo.ui.beranda

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.terapanimvo.R

class BerandaFragment : Fragment() {

    companion object {
        fun newInstance() = BerandaFragment()
    }

    private lateinit var viewModel: BerandaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.beranda_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(BerandaViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
