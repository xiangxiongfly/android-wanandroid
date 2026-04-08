package com.example.mywanandroid.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseActivity<VB : ViewBinding>(private val inflate: (LayoutInflater) -> VB) :
    AppCompatActivity() {
    private var _binding: VB? = null
    val binding: VB
        get() = _binding ?: throw IllegalStateException("ViewBinding没有初始化")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

}