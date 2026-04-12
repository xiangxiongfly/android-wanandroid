package com.example.mywanandroid.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.example.mywanandroid.common.dialog.LoadingDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseActivity<VB : ViewBinding>(private val inflate: (LayoutInflater) -> VB) :
    AppCompatActivity() {
    protected lateinit var context: Context
    private var _binding: VB? = null
    val binding: VB
        get() = _binding ?: throw IllegalStateException("ViewBinding没有初始化")

    private val loadingDialog by lazy { LoadingDialog(context) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        _binding = inflate(layoutInflater)
        setContentView(binding.root)
        intent?.let {
            initExtras(it)
        }
        initViews()
        initData(savedInstanceState)
    }

    open fun initExtras(intent: Intent) {}

    abstract fun initViews()

    abstract fun initData(savedInstanceState: Bundle?)

    fun launch(block: suspend CoroutineScope.() -> Unit) =
        lifecycleScope.launch {
            block()
        }

    fun launchIO(block: suspend CoroutineScope.() -> Unit) =
        lifecycleScope.launch(Dispatchers.IO) {
            block()
        }

    fun launchCPU(block: suspend CoroutineScope.() -> Unit) =
        lifecycleScope.launch(Dispatchers.Default) {
            block()
        }

    fun launchMain(block: suspend CoroutineScope.() -> Unit) =
        lifecycleScope.launch(Dispatchers.Main) {
            block()
        }

    fun showLoading() {
        loadingDialog.show()
    }

    fun hideLoading() {
        loadingDialog.dismiss()
    }
}