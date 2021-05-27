package com.bjyw.aiyan.common.custom_Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerViewAdapter <D> (context: Context) : RecyclerView.Adapter<BaseRecyclerViewAdapter.BaseViewHolder>() {

    lateinit var  list : ArrayList<D>
    val  contex = context
    val  layoutInflater = LayoutInflater.from(context)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecyclerViewAdapter.BaseViewHolder {

        return  BaseViewHolder(getDataBinding(parent,viewType))
    }

    abstract fun  <V> getDataBinding(parent: ViewGroup, viewType: Int) : V

    protected fun <V : ViewDataBinding> getViewDataBinding(parent: ViewGroup, viewType: Int) : V{
        return DataBindingUtil.inflate<V>(LayoutInflater.from(contex), getlayoutId(viewType), parent, false)
    }

    private fun createDataBinding() {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: BaseRecyclerViewAdapter.BaseViewHolder, position: Int) {
        TODO("Not yet implemented")
    }


    open class BaseViewHolder: RecyclerView.ViewHolder {

        constructor(viewBinding : ViewDataBinding) : super(viewBinding.root){

        }

    }

    @LayoutRes
    abstract fun getlayoutId(viewType: Int) : Int



}