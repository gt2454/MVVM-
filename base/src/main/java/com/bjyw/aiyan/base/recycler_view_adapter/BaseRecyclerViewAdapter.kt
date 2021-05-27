package com.bjyw.aiyan.base.recycler_view_adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerViewAdapter(context: Context,baseList :ArrayList<Any>) :
    RecyclerView.Adapter<BaseRecyclerViewAdapter.BaseViewHolder>() {

     var list = baseList
    val contex = context
    val layoutInflater = LayoutInflater.from(context)

    private var  parentZ : ViewGroup? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseRecyclerViewAdapter.BaseViewHolder {
        parentZ= parent
        return BaseViewHolder(getDataBinding(viewType))
    }

    abstract fun <V :ViewDataBinding> getDataBinding( viewType: Int): V

    protected fun <V :ViewDataBinding> getViewDataBinding( viewType: Int): V {
        return DataBindingUtil.inflate(layoutInflater, getlayoutId(viewType), parentZ, false)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: BaseRecyclerViewAdapter.BaseViewHolder, position: Int) {
        isDataBinding(holder.viewDataBinding)
    }

    abstract fun <V : ViewDataBinding> isDataBinding(viewDataBinding: V)

    protected fun <V : ViewDataBinding> setBinding(viewDataBinding: V,br: Int, get: Any){
        viewDataBinding.setVariable(br,get)
        viewDataBinding.executePendingBindings()
    }

    open class BaseViewHolder(viewBinding: ViewDataBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        var viewDataBinding = viewBinding
    }

    @LayoutRes
    abstract fun getlayoutId(viewType: Int): Int


}