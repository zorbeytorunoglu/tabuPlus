package com.zorbeytorunoglu.tabuuplus.presentation.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.zorbeytorunoglu.tabuuplus.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment: Fragment() {

    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMainBinding.inflate(inflater, container, false)

        binding.newGameButton.setOnClickListener {
            Navigation.findNavController(it).navigate(
                MainFragmentDirections.actionMainFragmentToGameFragment()
            )
        }

        binding.settingsButton.setOnClickListener {
            Navigation.findNavController(it).navigate(
                MainFragmentDirections.actionMainFragmentToSettingsFragment()
            )
        }

        return binding.root
    }

}