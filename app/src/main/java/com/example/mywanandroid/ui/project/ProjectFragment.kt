package com.example.mywanandroid.ui.project

import android.os.Bundle
import android.util.Log
import com.example.mywanandroid.base.BaseFragment
import com.example.mywanandroid.databinding.FragmentProjectBinding

class ProjectFragment : BaseFragment<FragmentProjectBinding>(FragmentProjectBinding::inflate) {

    override fun initViews() {
        Log.e("TAG","ProjectFragment onCreateView")

    }

    override fun initData(savedInstanceState: Bundle?) {

    }

    companion object {
        @JvmStatic
        fun newInstance() = ProjectFragment()
    }
}