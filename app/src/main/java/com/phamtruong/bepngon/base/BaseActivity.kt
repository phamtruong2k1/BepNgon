package com.phamtruong.bepngon.base

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.facebook.drawee.backends.pipeline.Fresco
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class BaseActivity<VM : BaseViewModel, DB : ViewDataBinding>(private val mViewModelClass: Class<VM>) :
    DaggerAppCompatActivity() {

    companion object{
        val BASE_FOLDER = Environment.getExternalStorageDirectory().toString() + "/" + Environment.DIRECTORY_DOCUMENTS + "/Meme Sound"
    }


    @Inject
    internal lateinit var viewModelProviderFactory: ViewModelProvider.Factory

    @LayoutRes
    abstract fun getLayoutRes(): Int

    val binding by lazy {
        DataBindingUtil.setContentView(this, getLayoutRes()) as DB
    }

    val viewModel by lazy {
        ViewModelProvider(this, viewModelProviderFactory).get(mViewModelClass)
    }

    open fun onInject() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        //LanguageUtils.loadLocale(this)
        super.onCreate(savedInstanceState)
        Fresco.initialize(this)
        initViewModel(viewModel)
        onInject()
        setupBindingLifecycleOwner()
    }

    /**
     *
     *  You need override this method.
     *  And you need to set viewModel to binding: binding.viewModel = viewModel
     *
     */

    abstract fun initViewModel(viewModel: VM)

    private fun setupBindingLifecycleOwner() {
        binding.lifecycleOwner = this
    }
}