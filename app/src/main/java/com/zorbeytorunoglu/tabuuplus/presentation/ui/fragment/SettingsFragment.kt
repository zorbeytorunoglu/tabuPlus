package com.zorbeytorunoglu.tabuuplus.presentation.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.zorbeytorunoglu.tabuuplus.R
import com.zorbeytorunoglu.tabuuplus.databinding.FragmentSettingsBinding
import com.zorbeytorunoglu.tabuuplus.presentation.viewmodel.SettingsFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment: Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    private val viewModel: SettingsFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSettingsBinding.inflate(inflater, container, false)

        binding.backButton.setOnClickListener {
            Navigation.findNavController(it).navigate(
                SettingsFragmentDirections.actionSettingsFragmentToMainFragment()
            )
        }

        binding.

        return binding.root

    }

}