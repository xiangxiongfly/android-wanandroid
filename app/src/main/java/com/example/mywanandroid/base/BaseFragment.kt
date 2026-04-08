package com.example.mywanandroid.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseFragment<VB : ViewBinding>(private val inflate: (LayoutInflater, ViewGroup?, Boolean) -> VB) :
    Fragment() {

    private var _binding: VB? = null
    val binding: VB
        get() = _binding ?: throw IllegalStateException("ViewBinding没有初始化")

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.let {
            initArguments(it)
        }
    }

    open fun initArguments(arguments: android.os.Bundle) {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initData(savedInstanceState)
    }

    abstract fun initViews()

    abstract fun initData(savedInstanceState: Bundle?)

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun launch(block: suspend CoroutineScope.() -> Unit) =
        viewLifecycleOwner.lifecycleScope.launch {
            block()
        }

    fun launchIO(block: suspend CoroutineScope.() -> Unit) =
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            block()
        }

    fun launchCPU(block: suspend CoroutineScope.() -> Unit) =
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Default) {
            block()
        }

    fun launchMain(block: suspend CoroutineScope.() -> Unit) =
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            block()
        }
}