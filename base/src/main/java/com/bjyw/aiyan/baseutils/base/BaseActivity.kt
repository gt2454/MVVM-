package com.bjyw.aiyan.baseutils.base

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

/**
 * 封装了双击返回键退出程序功能
 *
 * 要使用 ARouter 来接收参数，请在 onCreate 方法中加上：ARouter.getInstance().inject(this)。
 * 然后在需要为每一个参数声明一个字段，并使用 @Autowired 标注，这样 ARouter 会自动对字段进行赋值，无需主动获取
 */
abstract class BaseActivity(var layout : Int) : AppCompatActivity() {
    companion object {
        /**
         * 双击返回键退出程序的时间间隔
         */
        private const val INTERVAL_DOUBLE_PRESS_BACK_TO_EXIT = 2000L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout)
//        hideBottomUIMenu()

        initView()
        initData()
        initEvent()

    }

    /**
     * 初始化组件
     */
    open fun initView() {
    }

    /**
     * 初始化数据
     */
    open fun initData() {


    }


    /**
     * 初始化事件
     */
    open fun initEvent() {

    }

    /**
     * 是否第一次按下返回键，用于双击退出
     */
    private var isFirstPressBack: Boolean = true
    private val doublePressBackToExitHandler = Handler(Handler.Callback {
        isFirstPressBack = true
        true
    })

    override fun onBackPressed() {
        // 双击返回键退出程序
        if (isSupportDoublePressBackToExit() && isFirstPressBack) {
            Toast.makeText(this, "再按一次返回键退出程序", Toast.LENGTH_SHORT).show()
            isFirstPressBack = false
            doublePressBackToExitHandler.sendEmptyMessageDelayed(0,
                INTERVAL_DOUBLE_PRESS_BACK_TO_EXIT
            )
            return
        }
        super.onBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()
        doublePressBackToExitHandler.removeCallbacksAndMessages(null)
    }

    /**
     * 如果需要双击退出界面，请重写此方法
     *
     * @return
     */
    protected open fun isSupportDoublePressBackToExit(): Boolean {
        return false
    }



    /**
     * 隐藏虚拟按键，并且全屏
     */
    private fun hideBottomUIMenu() {
        val decorView = window.decorView
        val uiOptions = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar

                //                    | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                or View.SYSTEM_UI_FLAG_IMMERSIVE)
        decorView.systemUiVisibility = uiOptions
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)

    }
}