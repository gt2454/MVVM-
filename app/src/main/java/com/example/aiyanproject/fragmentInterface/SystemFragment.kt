package com.example.aiyanproject.fragmentInterface

import androidx.fragment.app.Fragment
import com.example.aiyanproject.R
import com.example.aiyanproject.adpater.VpAdapter_find
import com.example.aiyanproject.fragmentInterface.site.*
import kotlinx.android.synthetic.main.fragment_system.*
import luyao.util.ktx.base.BaseVMFragment


class SystemFragment : BaseVMFragment<SystemViewModel>() {
    private var mFragments: ArrayList<Fragment>? = null
    private var mTitles: ArrayList<String>? = null
    var viewPager: VpAdapter_find? = null
    override fun providerVMClass(): Class<SystemViewModel>? = SystemViewModel::class.java







    override fun initView() {
    }


    override fun initData() {
       /* initViews()
        initFragments()
*/



    }

  /*  fun initFragments() {
        mFragments = ArrayList()
        mFragments!!.add(AFragment())
        mFragments!!.add(BFragment())
        mTitles = ArrayList()
        mTitles!!.add("找活")
        mTitles!!.add("找人")
    }

    fun initViews() {
        //getSupportFragmentManager()
        //viewpager
        val vpAdapter = mTitles?.let {
            childFragmentManager!!.let { it1 ->
                VpAdapter_find(
                    it1,
                    mFragments!!, it
                )
            }
        }



        vps!!.adapter = viewPager
        //TabLayout
        tablayout_find!!.setupWithViewPager(vps)
    }
*/


    override fun getLayoutResId(): Int {
        return  R.layout.fragment_system
    }


}