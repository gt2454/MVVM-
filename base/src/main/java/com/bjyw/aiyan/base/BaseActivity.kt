package com.bjyw.aiyan.base

import android.content.Intent
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bjyw.aiyan.custom_target.InjectUtils
import com.bjyw.aiyan.mvvm.IBaseView
import com.bjyw.aiyan.util.StatusBarTool
import java.lang.reflect.ParameterizedType

abstract  class BaseActivity <V : ViewDataBinding,VM : BaseViewModel> : AppCompatActivity(),IBaseView {

    lateinit var  binding : V

    lateinit var viewModel : VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initParam()

        InjectUtils.injectAutowired(this)

        initDataBinding()
        initViewModel()

        initStartActivity()

        initView()

        initData()
    }






    @LayoutRes
    abstract fun getLayoutId() : Int

    open fun getBR(): Int = 0

    open var isApplicationViewModel: Boolean? = false

    private fun initDataBinding() {

        binding =  DataBindingUtil.setContentView<V>(this,getLayoutId())
        binding.lifecycleOwner = this

    }

    private fun initViewModel() {
        if(isApplicationViewModel == null){

        }else{
            val aClass = (javaClass.genericSuperclass as ParameterizedType?)!!.actualTypeArguments[1] as Class<VM>

            if (isApplicationViewModel!!) {
                viewModel = ViewModelProvider(this, getFactory()).get(aClass)
            } else {
                viewModel= ViewModelProviders.of(this).get(aClass)
            }


            lifecycle.addObserver(viewModel)
            binding.setVariable(getBR(), viewModel)
        }
    }

    open fun getFactory(): ViewModelProvider.Factory {
//        val factory = BaseViewModelFactory(application, DataRepository())
        val factory=ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        return factory
    }





    /*
    * 沉浸式状态栏
    * */
    protected open fun translucentBar() {
        StatusBarTool.translucent(this, 1073741824)
    }
    /*
    * 白色字体状态栏
    * */
    protected open fun statusBarLightMode() {
        StatusBarTool.setStatusBarLightMode(this)
    }
    /*
    * 黑色字体状态栏
    * */
    protected open fun statusBarDarkMode() {
        StatusBarTool.setStatusBarDarkMode(this)
    }

    private fun initStartActivity() {

        viewModel.getUC().startActivityEvent.observe(this,
                Observer<MutableMap<String, Any>> { params ->
                    val clz = params?.get(BaseViewModel.ParameterField.CLASS) as Class<*>
                    if(params[BaseViewModel.ParameterField.BUNDLE] != null){
                        val bundle = params[BaseViewModel.ParameterField.BUNDLE] as Bundle
                        startActivity(clz, bundle)
                    }
                    startActivity(clz, null)
                })
    }

    open fun startActivity(clz: Class<*>?, bundle: Bundle?) {
        val intent = Intent(this, clz)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivity(intent)
    }


}