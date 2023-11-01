package com.zorbeytorunoglu.tabuuplus.presentation.ui.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import com.zorbeytorunoglu.tabuuplus.databinding.SetupGameDialogBinding


interface OnTeamNamesSelectedListener {
    fun onTeamNamesSelected(teamAName: String, teamBName: String)
}

class SetupGameDialog(context: Context) {

    private val dialog = Dialog(context)
    private val binding = SetupGameDialogBinding.inflate(LayoutInflater.from(context))
    private var listener: OnTeamNamesSelectedListener? = null

    init {
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(true)

        binding.startButton.setOnClickListener {
            val teamAVal = binding.teamANameET.text?.takeIf { it.isNotBlank() }?.toString() ?: "Team A"
            val teamBVal = binding.teamBNameET.text?.takeIf { it.isNotBlank() }?.toString() ?: "Team B"

            if (teamAVal.equals(teamBVal, true)) {
                binding.teamANameLayout.error = "Can't have the same name."
                binding.teamBNameLayout.error = "Can't have the same name."
            } else {
                listener?.onTeamNamesSelected(teamAVal.take(30), teamBVal.take(30))
                dialog.dismiss()
            }
        }

        dialog.setContentView(binding.root)
    }

    fun show() = dialog.show()

    fun dismiss() = dialog.dismiss()

    fun setListener(listener: OnTeamNamesSelectedListener) {
        this.listener = listener
    }

}