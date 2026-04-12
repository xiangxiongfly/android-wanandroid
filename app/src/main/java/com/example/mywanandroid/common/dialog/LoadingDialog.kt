package com.example.mywanandroid.common.dialog

import android.content.Context
import androidx.appcompat.app.AppCompatDialog
import com.example.mywanandroid.R

class LoadingDialog(context: Context) : AppCompatDialog(context, R.style.BaseDialogStyle) {

    init {
        setContentView(R.layout.dialog_loading)
        setCancelable(false)
        setCanceledOnTouchOutside(false)
    }
}