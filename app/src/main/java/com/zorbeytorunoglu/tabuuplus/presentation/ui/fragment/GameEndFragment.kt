package com.zorbeytorunoglu.tabuuplus.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.zorbeytorunoglu.tabuuplus.databinding.FragmentGameEndBinding

class GameEndFragment : Fragment() {

    private lateinit var binding: FragmentGameEndBinding
    private val args: GameEndFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentGameEndBinding.inflate(inflater, container, false)

        val winnerTeam = args.winnerTeam
        val teamA = args.teamA
        val teamB = args.teamB

        binding.teamANameTV.text = teamA.name
        binding.teamBNameTV.text = teamB.name

        binding.teamAScore.text = "${teamA.correctScore - teamA.falseScore}"
        binding.teamBScore.text = "${teamB.correctScore - teamB.falseScore}"

        binding.winnerTeamTV.text = winnerTeam.name

        binding.continueButton.setOnClickListener {
            findNavController().navigate(
                GameEndFragmentDirections.actionGameEndFragmentToMainFragment()
            )
        }

        return binding.root

    }

}