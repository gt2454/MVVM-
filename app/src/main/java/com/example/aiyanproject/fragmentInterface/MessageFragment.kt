package com.example.aiyanproject.fragmentInterface

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.aiyanproject.R
import com.example.aiyanproject.databinding.FragmentMessageBinding
import luyao.util.ktx.base.BaseVMFragment


class MessageFragment : BaseVMFragment<MessageViewModel>(){
    override fun providerVMClass(): Class<MessageViewModel>? = MessageViewModel::class.java






    override fun initView() {
    }

    override fun initData() {
    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_message
    }


}