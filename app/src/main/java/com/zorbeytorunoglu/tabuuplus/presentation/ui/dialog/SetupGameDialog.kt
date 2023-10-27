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

    private val dialog: Dialog = Dialog(context)
    private val binding: SetupGameDialogBinding

    private var listener: OnTeamNamesSelectedListener? = null

    init {

        binding = SetupGameDialogBinding.inflate(LayoutInflater.from(context))

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(true)

        binding.startButton.setOnClickListener {

            val teamAVal = kotlin.runCatching {
                binding.teamANameET.text.toString()
            }.getOrNull() ?: "Team A"

            val teamBVal = kotlin.runCatching {
                binding.teamBNameET.text.toString()
            }.getOrNull() ?: "Team B"

            if (teamAVal.length > 30) {
                teamAVal.substring(0, 30)
            }

            if (teamBVal.length > 30) {
                teamBVal.substring(0, 30)
            }

            listener?.onTeamNamesSelected(teamAVal, teamBVal)
            dialog.dismiss()

        }

        dialog.setContentView(binding.root)

    }

    fun show() = dialog.show()

    fun dismiss() = dialog.dismiss()

    fun setListener(listener: OnTeamNamesSelectedListener) {
        this.listener = listener
    }

}