package com.hanmirae.emoney.presentasion.base

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.cloneapp.R
import com.example.cloneapp.common.extension.transact
import com.example.cloneapp.presentasion.base.BaseViewModel
import com.example.cloneapp.presentasion.common.SchedulerProvider
import com.hanmirae.emoney.presentasion.widget.AppSnackbar
import io.reactivex.disposables.CompositeDisposable

abstract class BaseFragment<VM : BaseViewModel> : Fragment() {
    private val subscriptions = CompositeDisposable()

    private lateinit var internalViewModel: VM

    val viewModel
        get() = internalViewModel

    protected open val snackBarRootView: View?
        get() = view

    abstract fun layoutRes(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        internalViewModel = onCreateViewModel()
        subscriptions.add(
            viewModel.getSnackBarConfig()
                .subscribeOn(SchedulerProvider.computation())
                .observeOn(SchedulerProvider.ui())
                .subscribe(this::showSnackBar)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(layoutRes(), container, false)

    override fun onDestroy() {
        super.onDestroy()
        subscriptions.clear()
    }

    abstract fun onCreateViewModel(): VM

    private fun showSnackBar(config: AppSnackbar.Config) {
        snackBarRootView?.let {
            AppSnackbar.make(it, config).show()
        }
    }

    protected fun showSnackBar(
        title: String,
        content: String,
        duration: Int,
        titleColor: Int = ContextCompat.getColor(
            context!!,
            R.color.halation_red
        )
    ) {
        snackBarRootView?.let {
            val config = AppSnackbar.Config()
                .title(title)
                .text(content)
                .duration(duration)
                .titleColor(titleColor)
            AppSnackbar.make(it, config).show()
        }
    }

    fun replaceFragmentInsideFragment(
        containerViewId: Int,
        fragment: Fragment,
        addToStack: Boolean = false,
        animation: Boolean = false
    ) {
        childFragmentManager.transact {
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
    fun addFragmentInsideFragment(
        containerViewId: Int,
        fragment: Fragment,
        addToStack: Boolean = false,
        animation: Boolean = false
    ) {
        childFragmentManager.transact {
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

    protected fun setUpUI(view: View?) {
        view?.run {
            if (this !is EditText) {
                this.setOnTouchListener { _, _ ->
                    hideKeyboard()
                    false
                }
            }
            if (this is ViewGroup) {
                for (i in 0 until this.childCount) {
                    setUpUI(this.getChildAt(i))
                }
            }
        }
    }

     fun hideKeyboard() {
        val inputMethodManager =
            context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        if (inputMethodManager.isActive) {
            activity?.currentFocus?.let {
                inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
            }
        }
    }

    fun onBackPressed() {
        val fm = fragmentManager
        if (fm!!.backStackEntryCount > 0) {
            fm.popBackStack()
        }
    }
}