package com.example.aiyanproject.fragmentInterface

import com.example.aiyanproject.R
import com.example.aiyanproject.base.BaseVMFragment



class CheckFragment :  BaseVMFragment<CheckViewModel>() {
    override fun providerVMClass(): Class<CheckViewModel>? = CheckViewModel::class.java







    override fun initView() {
    }

    override fun initData() {
    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_check
    }


}