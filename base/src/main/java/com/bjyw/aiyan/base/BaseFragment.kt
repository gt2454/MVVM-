package com.bjyw.aiyan.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bjyw.aiyan.mvvm.IBaseView
import java.lang.reflect.ParameterizedType

abstract class BaseFragment<V : ViewDataBinding,VM : BaseViewModel> : Fragment(),IBaseView{

    lateinit var binding : V
    lateinit var  viewModel : VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initParam()
        initViewModel()
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


        }
    }


    open var getBR: Int = 0

    open var isApplicationViewModel: Boolean? = false


    open fun getFactory(): ViewModelProvider.Factory {

        return ViewModelProvider.AndroidViewModelFactory(activity!!.application)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<V>(inflater,getlayoutId(),container,false)

        binding.setVariable(getBR, viewModel)

        initView()
        initData()

        return binding.root
    }


    @LayoutRes
    abstract fun getlayoutId(): Int

}