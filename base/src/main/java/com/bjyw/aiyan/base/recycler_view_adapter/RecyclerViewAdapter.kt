package com.bjyw.aiyan.base.recycler_view_adapter

import android.content.Context
import androidx.databinding.ViewDataBinding
import java.util.*

class RecyclerViewAdapter(context: Context,list: ArrayList<Any>) : BaseRecyclerViewAdapter(context,list){

    val  one = 1

    override fun getlayoutId(viewType: Int): Int { return 0 }
    override fun <V :ViewDataBinding> getDataBinding(viewType: Int): V { return  getViewDataBinding(viewType) }
    override fun <V : ViewDataBinding> isDataBinding(viewDataBinding: V) { setBinding(viewDataBinding,0,"111") }

}