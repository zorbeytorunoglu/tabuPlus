package com.zorbeytorunoglu.tabuuplus.presentation.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.zorbeytorunoglu.tabuuplus.R
import com.zorbeytorunoglu.tabuuplus.databinding.FragmentGameEndBinding

class GameEndFragment : Fragment() {

    private lateinit var binding: FragmentGameEndBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentGameEndBinding.inflate(inflater, container, false)

        val bundle: GameEndFragmentArgs by navArgs()

        val winnerTeam = bundle.winnerTeam
        val teamA = bundle.teamA
        val teamB = bundle.teamB

        binding.teamANameTV.text = teamA.name
        binding.teamBNameTV.text = teamB.name

        binding.teamAScore.text = "${teamA.correctScore - teamA.falseScore}"
        binding.teamBScore.text = "${teamB.correctScore - teamB.falseScore}"

        binding.winnerTeamTV.text = winnerTeam.name

        binding.continueButton.setOnClickListener {
            Navigation.findNavController(it).navigate(
                GameEndFragmentDirections.actionGameEndFragmentToMainFragment()
            )
        }

        return binding.root

    }

}