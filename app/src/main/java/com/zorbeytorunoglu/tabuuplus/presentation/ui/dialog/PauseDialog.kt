package com.zorbeytorunoglu.tabuuplus.presentation.ui.dialog

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import com.zorbeytorunoglu.tabuuplus.databinding.PauseDialogBinding

class PauseDialog(context: Context): Dialog(context) {

    init {
        val binding = PauseDialogBinding.inflate(LayoutInflater.from(context))

        binding.continueButton.setOnClickListener {
            dismiss()
        }

        setCanceledOnTouchOutside(false)

        setContentView(binding.root)
    }

}