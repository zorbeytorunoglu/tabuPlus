package com.zorbeytorunoglu.tabuuplus.presentation.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.zorbeytorunoglu.tabuuplus.databinding.FragmentSettingsBinding
import com.zorbeytorunoglu.tabuuplus.presentation.ui.dialog.LoadingDialog
import com.zorbeytorunoglu.tabuuplus.presentation.viewmodel.SettingsFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingsFragment: Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    private val viewModel: SettingsFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = FragmentSettingsBinding.inflate(inflater, container, false)

        binding.backButton.setOnClickListener {
            Navigation.findNavController(it).navigate(
                SettingsFragmentDirections.actionSettingsFragmentToMainFragment()
            )
        }

        binding.downloadButton.setOnClickListener {
            viewModel.updateCards(requireContext(), true, LoadingDialog(requireContext(), layoutInflater), true)
        }

        binding.saveButton.setOnClickListener {

            val op = viewModel.validateNSaveForm(
                binding.timeLimitEditText,
                binding.timeLimitTextInputLayout,
                binding.maxPassEditText,
                binding.maxPassTextInputLayout,
                binding.falsePenaltyEditText,
                binding.falsePenaltyLayout,
                binding.winningPointEditText,
                binding.winningPointLayout
            )

            if (op)
                Navigation.findNavController(it).navigate(
                    SettingsFragmentDirections.actionSettingsFragmentToMainFragment()
                )

        }

        viewModel.notificationMsgLiveData.observe(viewLifecycleOwner) { message ->
            Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
        }

        return binding.root

    }

}