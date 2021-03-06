package com.example.aiyanproject

import android.widget.FrameLayout
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.blankj.utilcode.util.ActivityUtils
import com.example.aiyanproject.adpater.VpAdapter
import com.example.aiyanproject.base.BaseVMActivity
import com.example.aiyanproject.fragmentInterface.*
import kotlinx.android.synthetic.main.activity_main.*
import luyao.util.ktx.base.BaseActivity
import java.util.ArrayList


class MainActivity : BaseActivity() {

    private val fl: FrameLayout? = null
    private val rb1: RadioButton? = null
    private val rb2: RadioButton? = null
    private val rb3: RadioButton? = null
    private val rb4: RadioButton? = null
    private val rb5: RadioButton? = null

    //    private var taskFragment: TaskFragment? = null
//    private var systemFragment: SystemFragment? = null
//    private var checkFragment: CheckFragment? = null
//    private var messageFragment: MessageFragment? = null
//    private var weFragment: WeFragment? = null
    private var fragments: ArrayList<Fragment> = arrayListOf()
    override fun getLayoutResId(): Int {
        return R.layout.activity_main
    }

    override fun initData() {

    }

    override fun initView() {
        fragments.add(TaskFragment())
        fragments.add(SystemFragment())
        fragments.add(CheckFragment())
        fragments.add(MessageFragment())
        fragments.add(WeFragment())
        // add to items for change ViewPager item
        /* items.put(R.id.navigation_scan, 0)
         items.put(R.id.navigation_my, 1)*/
        // set adapter
        vp.adapter = VpAdapter(supportFragmentManager, fragments)
        init()

    }

    private fun init() {
        vp!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(i: Int, v: Float, i1: Int) {

            }

            override fun onPageSelected(i: Int) {
                when (i) {
                    0 -> {
                        rg!!.check(R.id.rb1)
                    }
                    1 -> {
                        rg!!.check(R.id.rb2)
                    }
                    2 -> {
                        rg!!.check(R.id.rb3)
                    }
                    3 -> {
                        rg!!.check(R.id.rb4)
                    }
                    else -> {
                        rg!!.check(R.id.rb5)
                    }
                }
            }

            override fun onPageScrollStateChanged(i: Int) {

            }
        })
        rg!!.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb1 -> vp!!.currentItem = 0
                R.id.rb2 -> vp!!.currentItem = 1
                R.id.rb3 -> vp!!.currentItem = 2
                R.id.rb4 -> vp!!.currentItem = 3
                R.id.rb4 -> vp!!.currentItem = 4
                else -> {

                }
            }
        }
    }
    /* private fun Fragments() {
         taskFragment = TaskFragment()
         systemFragment = SystemFragment()
         checkFragment = CheckFragment()
         messageFragment = MessageFragment()
         weFragment = WeFragment()




         // ?????????   fragment
         val manager = supportFragmentManager //  ?????????
         val transaction =
             manager.beginTransaction() //  ??????
         // ?????????  fragment    add  ??????    replace  ??????  (  remove   add )
         transaction.add(R.id.fl, taskFragment!!) //  ??????   FrameLayout
         transaction.add(R.id.fl, systemFragment!!)
         transaction.add(R.id.fl, checkFragment!!)
         transaction.add(R.id.fl, messageFragment!!)
         transaction.add(R.id.fl, weFragment!!)
         transaction.hide(systemFragment!!).hide(checkFragment!!).hide(messageFragment!!)
             .hide(weFragment!!)
         // ??????
         transaction.commit()


         // replace   ?????? ????????????  ??? ?????? ???  ??????finishn (??????)       ???????????????   ???????????? ( ??????  )
         //  add      ??????   ??????   ??????    ????????????
         //    ???????????? ??????   show   hide

         rb1!!.setOnClickListener {
             val manager =
                 supportFragmentManager //  ?????????
             val transaction =
                 manager.beginTransaction() //  ??????
             // ?????????  fragment    add  ??????    replace  ??????  (  remove   add )
             //transaction.add(R.id.fl,blankFragment2); //  ??????   FrameLayout
             transaction.show(taskFragment!!).hide(systemFragment!!).hide(checkFragment!!)
                 .hide(messageFragment!!)
                 .hide(weFragment!!)
             // ??????
             transaction.commit()

         }
         rb2!!.setOnClickListener { // ?????????   fragment
             val manager =
                 supportFragmentManager //  ?????????
             val transaction =
                 manager.beginTransaction() //  ??????
             // ?????????  fragment    add  ??????    replace  ??????  (  remove   add )
             //transaction.add(R.id.fl,blankFragment2); //  ??????   FrameLayout
             transaction.show(systemFragment!!).hide(taskFragment!!).hide(checkFragment!!)
                 .hide(messageFragment!!)
                 .hide(weFragment!!)
             // ??????
             transaction.commit()
         }
         rb3!!.setOnClickListener { // ?????????   fragment
             val manager: FragmentManager =
                 supportFragmentManager //  ?????????
             val transaction =
                 manager.beginTransaction() //  ??????
             // ?????????  fragment    add  ??????    replace  ??????  (  remove   add )
             //transaction.add(R.id.fl,blankFragment2); //  ??????   FrameLayout
             transaction.show(checkFragment!!).hide(taskFragment!!).hide(systemFragment!!)
                 .hide(messageFragment!!)
                 .hide(weFragment!!)

             // ??????
             transaction.commit()
         }
         rb4!!.setOnClickListener {
             //????????? fragment
             val manager =
                 supportFragmentManager //  ?????????
             val transaction =
                 manager.beginTransaction() //  ??????
             // ?????????  fragment    add  ??????    replace  ??????  (  remove   add )
             //transaction.add(R.id.fl,blankFragment2); //  ??????   FrameLayout
             transaction.show(messageFragment!!).hide(taskFragment!!).hide(systemFragment!!)
                 .hide(checkFragment!!)
                 .hide(weFragment!!)
             // ??????
             transaction.commit()
         }
         rb5!!.setOnClickListener {
             val manager =
                 supportFragmentManager //  ?????????
             val transaction =
                 manager.beginTransaction() //  ??????
             // ?????????  fragment    add  ??????    replace  ??????  (  remove   add )
             //transaction.add(R.id.fl,blankFragment2); //  ??????   FrameLayout
             transaction.show(weFragment!!).hide(taskFragment!!).hide(systemFragment!!)
                 .hide(checkFragment!!)
                 .hide(messageFragment!!)
             // ??????
             transaction.commit()
         }
     }*/
}