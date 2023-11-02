package com.zorbeytorunoglu.tabuuplus.presentation.ui.dialog

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.zorbeytorunoglu.tabuuplus.R

class LoadingDialog(context: Context) {

    private val dialog: AlertDialog = MaterialAlertDialogBuilder(context)
        .setView(LayoutInflater.from(context).inflate(R.layout.loading_dialog, null))
        .setCancelable(false)
        .setBackground(ColorDrawable(android.graphics.Color.TRANSPARENT))
        .create()

    fun show() = dialog.show()
    fun dismiss() = dialog.dismiss()

}