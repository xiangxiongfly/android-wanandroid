package com.example.mywanandroid.ui.home

import android.os.Bundle
import com.example.mywanandroid.base.BaseFragment
import com.example.mywanandroid.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    override fun initViews() {

    }

    override fun initData(savedInstanceState: Bundle?) {

    }


    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}