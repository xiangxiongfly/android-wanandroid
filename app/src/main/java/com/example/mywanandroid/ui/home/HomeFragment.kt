package com.example.mywanandroid.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.viewModels
import com.example.mywanandroid.base.BaseFragment
import com.example.mywanandroid.data.state.UiState
import com.example.mywanandroid.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val viewModel: HomeViewModel by viewModels()

    override fun initViews() {

    }

    override fun initData(savedInstanceState: Bundle?) {
        viewModel.getBanner()
        setupObserves()
    }

    private fun setupObserves() {
        launch {
            viewModel.bannerState.collect {
                when (it) {
                    is UiState.Loading -> {
                        Log.e("TAG", "loading")
                    }

                    is UiState.Success -> {
                        Log.e("TAG", "success ${it.data}")

                    }

                    is UiState.Error -> {
                        Log.e("TAG", "error")

                    }

                    else -> {
                        Log.e("TAG", "else")
                    }
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}