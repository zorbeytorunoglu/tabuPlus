package com.zorbeytorunoglu.tabuuplus.presentation.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.zorbeytorunoglu.tabuuplus.R
import com.zorbeytorunoglu.tabuuplus.databinding.FragmentSettingsBinding
import com.zorbeytorunoglu.tabuuplus.presentation.ui.dialog.LoadingDialog
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
        super.onCreateView(inflater, container, savedInstanceState)

        binding = FragmentSettingsBinding.inflate(inflater, container, false)

        setupClickListeners()
        setupLangAutoComplete()
        subscribeNotificationObserver()

        return binding.root
    }

    private fun setupLangAutoComplete() {
        val cardLanguageOptionArray = resources.getStringArray(R.array.card_languages)
        binding.langAutoComplete.setAdapter(ArrayAdapter(requireContext(), R.layout.lang_selection_dropdown_item, cardLanguageOptionArray))
    }

    private fun setupClickListeners() {
        binding.backButton.setOnClickListener { navigateToMainFragment() }
        binding.downloadButton.setOnClickListener { viewModel.updateCards(requireContext(), true, LoadingDialog(requireContext()), true) }
        binding.saveButton.setOnClickListener { saveSettingsAndNavigate() }
    }

    private fun subscribeNotificationObserver() {
        viewModel.notificationMsgLiveData.observe(viewLifecycleOwner) { message ->
            Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun navigateToMainFragment() {
        findNavController().navigate(SettingsFragmentDirections.actionSettingsFragmentToMainFragment())
    }

    private fun saveSettingsAndNavigate() {
        val validForm = viewModel.validateNSaveForm(
            binding.timeLimitEditText,
            binding.timeLimitTextInputLayout,
            binding.maxPassEditText,
            binding.maxPassTextInputLayout,
            binding.falsePenaltyEditText,
            binding.falsePenaltyLayout,
            binding.winningPointEditText,
            binding.winningPointLayout,
            binding.langAutoComplete,
            binding.langAutoCompleteLayout
        )

        if (validForm) {
            navigateToMainFragment()
        }
    }

}