package com.example.mywanandroid.ui.tree

import com.example.mywanandroid.base.BaseViewModel
import com.example.mywanandroid.data.model.Tree
import com.example.mywanandroid.data.respository.TreeRepository
import com.example.mywanandroid.data.state.LOAD_TYPE_INITIAL
import com.example.mywanandroid.data.state.ListUiState
import com.example.mywanandroid.data.state.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TreeViewModel : BaseViewModel() {
    private val repo by lazy { TreeRepository() }
    private val _treeListState = MutableStateFlow<ListUiState<Tree>>(ListUiState.Idle)
    val treeListState = _treeListState.asStateFlow()

    init {
        loadTreeList()
    }

    fun loadTreeList() {
        launchIO {
            _treeListState.value = ListUiState.Loading
            repo.getTreeList().collect {
                when (it) {
                    is Resource.Success -> {
                        _treeListState.value = ListUiState.Success(LOAD_TYPE_INITIAL, it.data)
                    }

                    is Resource.Error -> {
                        _treeListState.value =
                            ListUiState.Error(LOAD_TYPE_INITIAL, it.errMsg, it.errCode)
                    }
                }
            }
        }
    }
}