package com.example.mywanandroid.ui.project

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mywanandroid.base.BaseFragment
import com.example.mywanandroid.common.utils.showToast
import com.example.mywanandroid.data.state.UiState
import com.example.mywanandroid.databinding.FragmentProjectBinding
import com.example.mywanandroid.ui.project.page.PageFragment
import com.google.android.material.tabs.TabLayoutMediator

class ProjectFragment : BaseFragment<FragmentProjectBinding>(FragmentProjectBinding::inflate) {

    private val viewModel: ProjectViewModel by viewModels()

    companion object {
        @JvmStatic
        fun newInstance() = ProjectFragment()
    }

    override fun initViews() {
        binding.viewPager.offscreenPageLimit = 1
    }

    override fun initData(savedInstanceState: Bundle?) {
        setupObserves()
    }

    private fun setupObserves() {
        launch {
            viewModel.projectTreeState.collect {
                when (it) {
                    is UiState.Loading -> showLoading()

                    is UiState.Success -> {
                        hideLoading()
                        val titles = it.data.map { it.name }
                        val ids = it.data.map { it.id }

                        val fragments = ids.mapIndexed { index, i ->
                            PageFragment.newInstance(
                                i,
                                titles[index]
                            )
                        }
                        binding.viewPager.adapter = PageAdapter(this@ProjectFragment, fragments)
                        TabLayoutMediator(
                            binding.tabLayout,
                            binding.viewPager
                        ) { tab, position ->
                            tab.text = titles[position]
                        }.attach()
                    }

                    is UiState.Error -> {
                        hideLoading()
                        showToast(it.errMsg)
                    }

                    else -> {}
                }
            }
        }
    }


    class PageAdapter(fragment: Fragment, private val fragments: List<PageFragment>) :
        FragmentStateAdapter(fragment) {
        override fun createFragment(position: Int) = fragments[position]
        override fun getItemCount() = fragments.size
    }
}