package com.bjyw.aiyan.custom_View

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.bjyw.aiyan.base.R
import com.bjyw.aiyan.base.location.BaseApplication

class ToastApp : Toast(BaseApplication.baseApplication.getContext()) {

   private val infalte=BaseApplication.baseApplication.getContext()!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

   private var layout : TextView? = null

   private object  init{
      val  toastApp = ToastApp()
   }

   companion object{
      val  toast = init.toastApp
   }


   fun showText(text : CharSequence){
      if(layout != null){
         layout!!.text = text
         show()
         return
      }
//      layout = DataBindingUtil.inflate(
////         infalte,
////         R.layout.toast_app_layout,
////         null,
////         false
////      )
      view= infalte.inflate(R.layout.toast_app_layout,null) as View
      layout= view.findViewById(R.id.toast_text)
      duration = LENGTH_SHORT
      show()

   }








}