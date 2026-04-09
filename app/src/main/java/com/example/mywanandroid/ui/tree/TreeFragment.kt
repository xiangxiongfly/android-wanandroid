package com.example.mywanandroid.ui.tree

import android.os.Bundle
import android.util.Log
import com.example.mywanandroid.base.BaseFragment
import com.example.mywanandroid.databinding.FragmentTreeBinding

class TreeFragment : BaseFragment<FragmentTreeBinding>(FragmentTreeBinding::inflate) {

    override fun initViews() {
        Log.e("TAG","TreeFragment onCreateView")

    }

    override fun initData(savedInstanceState: Bundle?) {

    }

    companion object {
        @JvmStatic
        fun newInstance() = TreeFragment()
    }
}