package com.example.mywanandroid.ui.chapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mywanandroid.R
import com.example.mywanandroid.base.BaseFragment
import com.example.mywanandroid.databinding.FragmentChapterBinding


class ChapterFragment : BaseFragment<FragmentChapterBinding>(FragmentChapterBinding::inflate) {
    override fun initViews() {

    }

    override fun initData(savedInstanceState: Bundle?) {

    }

    companion object {
        @JvmStatic
        fun newInstance() = ChapterFragment()
    }
}