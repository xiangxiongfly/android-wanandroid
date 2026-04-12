package com.example.mywanandroid.ui.project

import com.example.mywanandroid.base.BaseViewModel
import com.example.mywanandroid.data.model.ProjectTree
import com.example.mywanandroid.data.respository.ProjectRepository
import com.example.mywanandroid.data.state.Resource
import com.example.mywanandroid.data.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProjectViewModel : BaseViewModel() {
    private val repo by lazy { ProjectRepository() }
    private val _projectTreeState = MutableStateFlow<UiState<List<ProjectTree>>>(UiState.Idle)
    val projectTreeState = _projectTreeState.asStateFlow()

    init {
        getProjectTree()
    }

    fun getProjectTree() {
        launchIO {
            _projectTreeState.value = UiState.Loading
            repo.getProjectTree().collect {
                when (it) {
                    is Resource.Success -> {
                        _projectTreeState.value = UiState.Success(it.data)
                    }

                    is Resource.Error -> {
                        _projectTreeState.value = UiState.Error(it.errMsg, it.errCode)
                    }
                }
            }
        }
    }

}