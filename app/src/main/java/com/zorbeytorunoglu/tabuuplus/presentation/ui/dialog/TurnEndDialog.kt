package com.zorbeytorunoglu.tabuuplus.presentation.ui.dialog

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import com.zorbeytorunoglu.tabuuplus.databinding.TurnEndDialogBinding
import com.zorbeytorunoglu.tabuuplus.domain.model.TeamData
import com.zorbeytorunoglu.tabuuplus.domain.model.TurnData

class TurnEndDialog(
    context: Context,
    teamAData: TeamData,
    teamBData: TeamData,
    turnData: TurnData
): Dialog(context) {

    private val binding: TurnEndDialogBinding

    init {

        binding = TurnEndDialogBinding.inflate(LayoutInflater.from(context))

        binding.teamANameTV.text = teamAData.name
        binding.teamBNameTV.text = teamBData.name

        binding.teamAScore.text = "${teamAData.correctScore - teamAData.falseScore}"
        binding.teamBScore.text = "${teamBData.correctScore - teamBData.falseScore}"

        binding.turnCorrectScoreTV.text = turnData.correctPoint.toString()
        binding.turnFalseScoreTV.text = turnData.falsePoint.toString()

        binding.turnTotalPointsTV.text = "${turnData.correctPoint - turnData.falsePoint}"

        binding.continueButton.setOnClickListener {
            dismiss()
        }

        setContentView(binding.root)

    }

}