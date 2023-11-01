package com.zorbeytorunoglu.tabuuplus.presentation.ui.dialog

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import com.zorbeytorunoglu.tabuuplus.databinding.SecondHelpDialogBinding

class SecondHelpDialog(context: Context): Dialog(context) {

    init {
        val binding = SecondHelpDialogBinding.inflate(LayoutInflater.from(context))
        binding.dialogLayout.setOnClickListener {
            dismiss()
        }
        setCanceledOnTouchOutside(true)
        setContentView(binding.root)
    }

}