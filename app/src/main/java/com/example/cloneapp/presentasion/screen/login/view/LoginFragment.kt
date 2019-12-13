package com.example.cloneapp.presentasion.screen.login.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.example.cloneapp.R
import com.example.cloneapp.common.extension.initViewModel
import com.example.cloneapp.data.request.LoginRequest
import com.example.cloneapp.presentasion.screen.login.viewmodel.LoginViewModel
import com.hanmirae.emoney.presentasion.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment: BaseFragment<LoginViewModel>() {

    override fun layoutRes(): Int =  R.layout.fragment_login

    override fun onCreateViewModel(): LoginViewModel = initViewModel()

    companion object {
        fun newInstance(): LoginFragment = LoginFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.run {
            data.observe(this@LoginFragment, Observer {
                if (it) {
                    //handle data
                }
            })
        }

        actionLogin.setOnClickListener {
            viewModel.login(LoginRequest(edAccount.text.toString(), edPass.text.toString()))
        }
    }
}