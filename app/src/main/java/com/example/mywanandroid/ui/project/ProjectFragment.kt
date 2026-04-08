package com.example.mywanandroid.ui.project

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mywanandroid.R
import com.example.mywanandroid.base.BaseFragment
import com.example.mywanandroid.databinding.FragmentProjectBinding

class ProjectFragment : BaseFragment<FragmentProjectBinding>(FragmentProjectBinding::inflate) {

    override fun initViews() {

    }

    override fun initData(savedInstanceState: Bundle?) {

    }

    companion object {
        @JvmStatic
        fun newInstance() = ProjectFragment()
    }
}