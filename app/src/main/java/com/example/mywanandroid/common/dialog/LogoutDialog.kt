package com.example.mywanandroid.common.dialog

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialog
import com.example.mywanandroid.R
import com.example.mywanandroid.databinding.DialogLogoutBinding

class LogoutDialog(context: Context) : AppCompatDialog(context, R.style.DialogStyle_Normal) {
    private var binding: DialogLogoutBinding = DialogLogoutBinding.inflate(layoutInflater)
    private var onConfrimListener: (() -> Unit)? = null

    init {
        setContentView(binding.root)
        setCancelable(false)
        setCanceledOnTouchOutside(false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        window?.setLayout(
//            (context.resources.displayMetrics.widthPixels * 0.8).toInt(),
//            WindowManager.LayoutParams.WRAP_CONTENT
//        )
        binding.tvCancel.setOnClickListener { dismiss() }
        binding.tvConfirm.setOnClickListener {
            onConfrimListener?.invoke()
            dismiss()
        }
    }

    fun setOnConfirmListener(listener: () -> Unit): LogoutDialog {
        onConfrimListener = listener
        return this
    }
}