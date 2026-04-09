package com.example.mywanandroid.ui.nav

import android.os.Bundle
import android.util.Log
import com.example.mywanandroid.base.BaseFragment
import com.example.mywanandroid.databinding.FragmentNavBinding

class NavFragment : BaseFragment<FragmentNavBinding>(FragmentNavBinding::inflate) {

    override fun initViews() {
        Log.e("TAG","NavFragment onCreateView")

    }

    override fun initData(savedInstanceState: Bundle?) {

    }

    companion object {
        @JvmStatic
        fun newInstance() = NavFragment()
    }
}