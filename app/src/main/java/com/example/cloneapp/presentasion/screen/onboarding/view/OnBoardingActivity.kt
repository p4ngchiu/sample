package com.example.cloneapp.presentasion.screen.onboarding.view

import com.example.cloneapp.R
import com.example.cloneapp.common.extension.initViewModel
import com.example.cloneapp.presentasion.screen.login.view.LoginFragment
import com.example.cloneapp.presentasion.screen.onboarding.viewmodel.OnBoadingViewModel
import com.hanmirae.emoney.presentasion.base.BaseActivity

class OnBoardingActivity : BaseActivity<OnBoadingViewModel>() {

    override fun onCreateViewModel(): OnBoadingViewModel = initViewModel()

    override fun layoutRes(): Int = R.layout.activity_onboarding

    override fun initView() {
        replaceFragment(R.id.onBoardingContainer, LoginFragment.newInstance())
    }

}
