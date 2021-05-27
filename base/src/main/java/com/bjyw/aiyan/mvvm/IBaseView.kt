package com.bjyw.aiyan.mvvm

interface IBaseView {

    /**
     * 初始化界面传递参数
     */
    fun initParam()

    /*
    *
    *   初始化UI控件
    * */
    fun initView()
    /**
     * 初始化数据
     */
    fun initData()

    /**
     * 初始化界面观察者的监听
     */
    fun initViewObservable()
}