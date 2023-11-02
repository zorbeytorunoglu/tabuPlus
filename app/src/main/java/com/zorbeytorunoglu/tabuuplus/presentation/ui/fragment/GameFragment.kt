package com.zorbeytorunoglu.tabuuplus.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.zorbeytorunoglu.tabuuplus.R
import com.zorbeytorunoglu.tabuuplus.databinding.FragmentGameBinding
import com.zorbeytorunoglu.tabuuplus.domain.model.TeamData
import com.zorbeytorunoglu.tabuuplus.presentation.ui.dialog.PauseDialog
import com.zorbeytorunoglu.tabuuplus.presentation.ui.dialog.TurnEndDialog
import com.zorbeytorunoglu.tabuuplus.presentation.viewmodel.GameFragmentViewModel
import com.zorbeytorunoglu.tabuuplus.presentation.viewmodel.OnGameEndListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GameFragment : Fragment(), OnGameEndListener {

    private lateinit var binding: FragmentGameBinding
    private val viewModel: GameFragmentViewModel by viewModels()
    private val args: GameFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentGameBinding.inflate(inflater, container, false)

        viewModel.setTeams(args.teamAName, args.teamBName)

        binding.pauseCardView.setOnClickListener {
            val pauseDialog = PauseDialog(requireContext())
            pauseDialog.setOnDismissListener {
                viewModel.countdownManager.resume()
            }
            viewModel.countdownManager.pause()
            pauseDialog.show()
        }

        binding.correctButton.setOnClickListener {
            viewModel.onCorrect()
        }

        binding.falseButton.setOnClickListener {
            viewModel.onFalse()
        }

        binding.passButton.setOnClickListener {
            viewModel.onPass()
        }

        viewModel.countdownManager.setOnFinish {
            viewModel.countdownManager.stop()
            val turnEndDialog = TurnEndDialog(requireContext(), viewModel.teamA, viewModel.teamB, viewModel.turnData)
            turnEndDialog.setOnDismissListener {
                viewModel.onTurnEnd()
            }
            turnEndDialog.show()
        }

        lifecycleScope.launch {
            viewModel.countdownManager.remainingTimeFlow.collectLatest {
                binding.countdownTextView.text = it.toString()
            }
        }

        viewModel.passCountLiveData.observe(viewLifecycleOwner) {
            binding.passCountTextView.text = getString(R.string.pass_count, it)
        }

        viewModel.cardLiveData.observe(viewLifecycleOwner) { card ->
            binding.wordTextView.text = card.mainWord
            binding.relatedWord1TextView.text = card.relatedWords.getOrNull(0) ?: ""
            binding.relatedWord2TextView.text = card.relatedWords.getOrNull(1) ?: ""
            binding.relatedWord3TextView.text = card.relatedWords.getOrNull(2) ?: ""
            binding.relatedWord4TextView.text = card.relatedWords.getOrNull(3) ?: ""
            binding.relatedWord5TextView.text = card.relatedWords.getOrNull(4) ?: ""
        }

        viewModel.currentTeamLiveData.observe(viewLifecycleOwner) {
            binding.teamTextView.text = it.name
            binding.scoreTextView.text = getString(R.string.label_score, it.correctScore - it.falseScore)
        }

        viewModel.setOnGameEndListener(this)

        viewModel.startGame()

        return binding.root

    }

    override fun onGameEnd(winnerTeam: TeamData, teamA: TeamData, teamB: TeamData) {
        Navigation.findNavController(requireView()).navigate(
            GameFragmentDirections.actionGameFragmentToGameEndFragment(winnerTeam,teamA,teamB)
        )
    }

}