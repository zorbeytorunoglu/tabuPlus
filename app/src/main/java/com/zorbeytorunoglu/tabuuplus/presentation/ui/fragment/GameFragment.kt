package com.zorbeytorunoglu.tabuuplus.presentation.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.zorbeytorunoglu.tabuuplus.databinding.FragmentGameBinding
import com.zorbeytorunoglu.tabuuplus.presentation.viewmodel.GameFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GameFragment : Fragment() {

    private lateinit var binding: FragmentGameBinding
    private val viewModel: GameFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentGameBinding.inflate(inflater, container, false)

        val bundle: GameFragmentArgs by navArgs()

        viewModel.newGame(bundle.teamAName, bundle.teamBName)

        viewModel.countdownManager.setOnFinish {
            // on finish
        }

        binding.pauseCardView.setOnClickListener {
            if (viewModel.countdownManager.isPaused())
                viewModel.countdownManager.resume()
            else
                viewModel.countdownManager.pause()
        }

        lifecycleScope.launch {
            viewModel.countdownManager.remainingTimeFlow.collectLatest {
                binding.countdownTextView.text = it.toString()
            }
        }

        binding.correctButton.setOnClickListener {
            
        }

        viewModel.countdownManager.start()

        return binding.root

    }

}