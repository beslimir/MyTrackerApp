package com.example.mytrackerapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mytrackerapp.R
import com.example.mytrackerapp.databinding.FragmentSetupBinding

class SetupFragment: Fragment(R.layout.fragment_setup) {

    private lateinit var setupBinding: FragmentSetupBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBinding = FragmentSetupBinding.bind(view)

        setupBinding.tvContinue.setOnClickListener {
            findNavController().navigate(R.id.action_setupFragment_to_runFragment)
        }

    }

}