package com.example.terapanimvo.ui.magang

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.terapanimvo.R

class MagangFragment : Fragment() {

    companion object {
        fun newInstance() = MagangFragment()
    }

    private lateinit var viewModel: MagangViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.magang_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MagangViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
