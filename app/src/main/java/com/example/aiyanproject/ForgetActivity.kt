package com.example.aiyanproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.aiyanproject.databinding.LayoutForgetPasswordBinding
import kotlinx.android.synthetic.main.layout_forget_password.*
import luyao.util.ktx.base.BaseVMActivity

class ForgetActivity : BaseVMActivity< ForgetViewModel>() {



    override fun providerVMClass(): Class<ForgetViewModel>? = ForgetViewModel::class.java


    override fun initView() {
    }

    override fun getLayoutResId(): Int {
        return R.layout.layout_forget_password
    }

    override fun initData() {
        btn_ok.setOnClickListener {
            if (et_forget_password.text.toString().isNotBlank() && et_forget_phone.text.toString()
                    .isNotBlank() && et_forget_yan.text.toString().isNotBlank()
            ) {
                val intent = Intent(this@ForgetActivity, LoginActivity::class.java)
                startActivity(intent)

            }
            if (et_forget_password.text.toString().isBlank()) {
                Toast.makeText(getApplication(), "密码不完整", Toast.LENGTH_SHORT).show()
            }
            if (et_forget_phone.text.toString().isBlank()) {
                Toast.makeText(getApplication(), "手机号码不完整", Toast.LENGTH_SHORT).show()
            }
            if (et_forget_yan.text.toString().isBlank()) {
                Toast.makeText(getApplication(), "验证码不完整", Toast.LENGTH_SHORT).show()
            }

        }
        btn_back_click.setOnClickListener {
            val intent = Intent(this@ForgetActivity, LoginActivity::class.java)
            startActivity(intent)
        }
        btn_verification_code.setOnClickListener {
            //获取验证码
            val c = it as CountdownButton
            c.sendVerifyCode()
        }


    }


}