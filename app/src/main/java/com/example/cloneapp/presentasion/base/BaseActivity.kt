package com.hanmirae.emoney.presentasion.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.cloneapp.R
import com.example.cloneapp.common.extension.transact
import com.example.cloneapp.presentasion.base.BaseViewModel
import com.example.cloneapp.presentasion.common.SchedulerProvider
import com.hanmirae.emoney.presentasion.widget.AppSnackbar
import io.reactivex.disposables.CompositeDisposable

abstract class BaseActivity<VM : BaseViewModel> : AppCompatActivity() {
    private lateinit var internalViewModel: VM
    protected open val snackBarRootView: View? = null

    val viewModel: VM
        get() = internalViewModel

    abstract fun onCreateViewModel(): VM
    abstract fun layoutRes(): Int
    abstract fun initView()
    private val snackBarSubscriptions = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes())
        internalViewModel = onCreateViewModel()
        snackBarSubscriptions.add(
            viewModel.getSnackBarConfig()
                .subscribeOn(SchedulerProvider.computation())
                .observeOn(SchedulerProvider.ui())
                .subscribe(this::showErrorDialog)
        )
        initView()
    }

    private fun showErrorDialog(config: AppSnackbar.Config) {
        snackBarRootView?.let {
            AppSnackbar.make(it, config).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        snackBarSubscriptions.clear()
    }

    fun replaceFragment(
        containerViewId: Int,
        fragment: Fragment,
        addToStack: Boolean = false,
        animation: Boolean = false
    ) {
        supportFragmentManager.transact {
            if (animation) setCustomAnimations(
                R.anim.slide_right_in,
                R.anim.slide_left_out_30percent,
                R.anim.slide_left_in_30percent,
                R.anim.slide_right_out
            )
            replace(containerViewId, fragment)
            if (addToStack) {
                addToBackStack(fragment::class.java.name)
            }
        }
    }

    /**
     * You have to override [containerViewId] in order to make this function work
     */
    fun addFragment(
        containerViewId: Int,
        fragment: Fragment,
        addToStack: Boolean = false,
        animation: Boolean
    ) {
        supportFragmentManager.transact {
            if (animation) setCustomAnimations(
                R.anim.slide_right_in,
                R.anim.slide_left_out_30percent,
                R.anim.slide_left_in_30percent,
                R.anim.slide_right_out
            )
            add(containerViewId, fragment)
            if (addToStack) {
                addToBackStack(fragment::class.java.name)
            }
        }
    }
}