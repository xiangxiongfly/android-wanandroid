package com.example.mywanandroid.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.activity.viewModels
import com.example.mywanandroid.base.BaseActivity
import com.example.mywanandroid.common.utils.showToast
import com.example.mywanandroid.data.state.UiState
import com.example.mywanandroid.databinding.ActivityLoginBinding
import com.hjq.bar.OnTitleBarListener
import com.hjq.bar.TitleBar

class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate) {
    private val viewModel: LoginViewModel by viewModels()

    companion object {
        const val REQ_LOGIN = 1
        fun actionStart(activity: Activity) {
            activity.startActivityForResult(Intent(activity, LoginActivity::class.java), REQ_LOGIN)
        }
    }

    override fun initViews() {
        binding.titleBar.setOnTitleBarListener(object : OnTitleBarListener {
            override fun onLeftClick(titleBar: TitleBar?) {
                super.onLeftClick(titleBar)
                finish()
            }
        })
        binding.tvRegister.setOnClickListener {
            showToast("注册账号")
        }
        binding.btnLogin.setOnClickListener {
            doLogin()
        }
    }

    private fun doLogin() {
        val username = binding.etUsername.text.toString()
        if (TextUtils.isEmpty(username)) {
            binding.tilUsername.error = "用户名不能为空"
            return
        }
        binding.tilUsername.error = null
        val password = binding.etPassword.text.toString()
        if (TextUtils.isEmpty(password)) {
            binding.tilPassword.error = "密码不能为空"
            return
        }
        if (password.length < 6) {
            binding.tilPassword.error = "密码不能小于6位"
            return
        }
        binding.tilPassword.error = null
        viewModel.login(username, password)
    }

    override fun initData(savedInstanceState: Bundle?) {
        setupObserves()
    }

    private fun setupObserves() {
        launch {
            viewModel.state.collect {
                when (it) {
                    is UiState.Loading -> showLoading()

                    is UiState.Success -> {
                        hideLoading()
                        showToast("登录成功")
                        setResult(
                            RESULT_OK,
                            Intent().apply { putExtra("username", it.data.username) }
                        )
                        finish()
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
}