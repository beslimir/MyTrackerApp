package com.example.mytrackerapp.ui.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.mytrackerapp.Constants.KEY_FIRST_TIME_TOGGLE
import com.example.mytrackerapp.Constants.KEY_NAME
import com.example.mytrackerapp.Constants.KEY_WEIGHT
import com.example.mytrackerapp.R
import com.example.mytrackerapp.databinding.FragmentSetupBinding
import com.example.mytrackerapp.ui.MainActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SetupFragment : Fragment(R.layout.fragment_setup) {

    @Inject
    lateinit var sharedPrefs: SharedPreferences
    private lateinit var setupBinding: FragmentSetupBinding

    @set:Inject
    var isFirstAppOpen = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBinding = FragmentSetupBinding.bind(view)

        //remove setup fragment from backstack
        if (!isFirstAppOpen) {
            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.setupFragment, true)
                .build()
            findNavController().navigate(
                R.id.action_setupFragment_to_runFragment,
                savedInstanceState,
                navOptions
            )
        }

        setupBinding.tvContinue.setOnClickListener {
            val success = writePersonalDataToSharedPref()
            if (success) {
                findNavController().navigate(R.id.action_setupFragment_to_runFragment)
            } else {
                Snackbar.make(requireView(), "Please enter all fields", Snackbar.LENGTH_SHORT)
                    .show()
            }
        }

    }

    private fun writePersonalDataToSharedPref(): Boolean {
        val name = setupBinding.etName.text.toString()
        val weight = setupBinding.etWeight.text.toString()
        if (name.isEmpty() || weight.isEmpty()) {

            return false
        }

        sharedPrefs.edit()
            .putString(KEY_NAME, name)
            .putFloat(KEY_WEIGHT, weight.toFloat())
            .putBoolean(KEY_FIRST_TIME_TOGGLE, false)
            .apply()
        val toolbarText = "Let's go, $name!"
        (requireActivity() as MainActivity).mainBinding.tvToolbarTitle.text = toolbarText

        return true

    }

}