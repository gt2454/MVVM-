package com.example.aiyanproject.fragmentInterface

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import com.example.aiyanproject.MainActivity
import com.example.aiyanproject.R
import kotlinx.android.synthetic.main.fragment_task.*
import luyao.util.ktx.base.BaseVMFragment

class TaskFragment : BaseVMFragment<TaskViewModel>() {

    override fun providerVMClass(): Class<TaskViewModel>? = TaskViewModel::class.java







    override fun initView() {
    }

    override fun initData() {
        tv_all.setOnClickListener {
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)

        }
    }

    override fun getLayoutResId(): Int {
        return  R.layout.fragment_task
    }


}