package com.example.mytrackerapp.ui.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.mytrackerapp.Constants.KEY_NAME
import com.example.mytrackerapp.Constants.KEY_WEIGHT
import com.example.mytrackerapp.R
import com.example.mytrackerapp.databinding.FragmentSettingsBinding
import com.example.mytrackerapp.ui.MainActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment: Fragment(R.layout.fragment_settings) {

    @Inject
    lateinit var sharedPrefs: SharedPreferences
    private lateinit var settingsBinding: FragmentSettingsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settingsBinding = FragmentSettingsBinding.bind(view)

        loadFieldsFromSharedPrefs()

        settingsBinding.bApplyChanges.setOnClickListener{
            val success = applyChangesToSharedPrefs()
            if (success) {
                Snackbar.make(view, "Saved changes!", Snackbar.LENGTH_SHORT).show()
            } else {
                Snackbar.make(view, "Please, fill out all fields!", Snackbar.LENGTH_SHORT).show()
            }
        }

    }

    private fun applyChangesToSharedPrefs(): Boolean {
        val nameText = settingsBinding.etName.text.toString()
        val weightText = settingsBinding.etWeight.text.toString()
        if (nameText.isEmpty() || weightText.isEmpty()) {

            return false
        }

        sharedPrefs.edit()
            .putString(KEY_NAME, nameText)
            .putFloat(KEY_WEIGHT, weightText.toFloat())
            .apply()
        val toolbarText = "Let's go, $nameText!"
        (requireActivity() as MainActivity).mainBinding.tvToolbarTitle.text = toolbarText

        return true
    }

    private fun loadFieldsFromSharedPrefs() {
        val name = sharedPrefs.getString(KEY_NAME, "") ?: ""
        val weight = sharedPrefs.getFloat(KEY_NAME, 79f)

        settingsBinding.etName.setText(name)
        settingsBinding.etWeight.setText(weight.toString())
    }

}