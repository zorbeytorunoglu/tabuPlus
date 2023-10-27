package com.zorbeytorunoglu.tabuuplus.presentation.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.zorbeytorunoglu.tabuuplus.databinding.FragmentMainBinding
import com.zorbeytorunoglu.tabuuplus.presentation.ui.dialog.OnTeamNamesSelectedListener
import com.zorbeytorunoglu.tabuuplus.presentation.ui.dialog.SetupGameDialog
import com.zorbeytorunoglu.tabuuplus.presentation.viewmodel.MainFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment: Fragment(), OnTeamNamesSelectedListener {

    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMainBinding.inflate(inflater, container, false)

        binding.newGameButton.setOnClickListener {

            val dialog = SetupGameDialog(requireContext())
            dialog.setListener(this)
            dialog.show()

        }

        binding.settingsButton.setOnClickListener {
            Navigation.findNavController(it).navigate(
                MainFragmentDirections.actionMainFragmentToSettingsFragment()
            )
        }

        viewModel.notificationMsgLiveData.observe(viewLifecycleOwner) {
            Snackbar.make(requireView(), it, Snackbar.LENGTH_INDEFINITE).show()
        }

        viewModel.updateCards(requireContext())

        return binding.root
    }

    override fun onTeamNamesSelected(teamAName: String, teamBName: String) {
        Log.e("Tabu", "tetik $teamAName , $teamBName")
        Navigation.findNavController(requireView()).navigate(
            MainFragmentDirections.actionMainFragmentToGameFragment(teamAName, teamBName)
        )
    }

}