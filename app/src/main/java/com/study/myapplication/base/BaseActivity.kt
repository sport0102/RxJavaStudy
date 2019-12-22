package com.study.myapplication.base

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.study.myapplication.BR
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


abstract class BaseActivity<B : ViewDataBinding, VM : ViewModel>(
    @LayoutRes private val layoutResId: Int
) : AppCompatActivity() {

    protected lateinit var binding: B
        private set

    abstract val viewModel: VM

    protected val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(layoutInflater, layoutResId, null, false)
        binding {
            binding.lifecycleOwner = this@BaseActivity
            binding.setVariable(BR.vm, viewModel)
        }
        setContentView(binding.root)

    }

    protected fun binding(action: B.() -> Unit) {
        binding.run(action)
    }

    fun toastM(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    fun removeDisposable(disposable: CompositeDisposable) {
        compositeDisposable.remove(disposable)
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}