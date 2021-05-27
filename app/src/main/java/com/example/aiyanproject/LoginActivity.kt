package com.example.aiyanproject

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer

import com.example.aiyanproject.LoginViewModel
import com.example.aiyanproject.base.BaseVMActivity

import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : BaseVMActivity<LoginViewModel>()  {


    override fun providerVMClass(): Class<LoginViewModel>? = LoginViewModel::class.java
    override fun getLayoutResId(): Int {
     return R.layout.activity_login
    }

    override fun initData() {
        btn_login_click.setOnClickListener {
            if (et_user.text.toString().isNotBlank() && et_phone.text.toString().isNotBlank()) {
                Toast.makeText(getApplication(), "登录成功", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(getApplication(), "账号信息不完整", Toast.LENGTH_SHORT).show()

            }
        }
        tv_forget_message.setOnClickListener {

            val intent = Intent(this@LoginActivity, ForgetActivity::class.java)
            startActivity(intent)
        }
    }

    override fun initView() {

    }

}