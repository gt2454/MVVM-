package com.bjyw.aiyan.common.view_adapter.recyalerview

import android.widget.TextView
import androidx.databinding.BindingAdapter

class TextViewAdapter  {

    @BindingAdapter("TextViewtext")
    fun setText(textView: TextView,textViewbulder : TextView.BufferType){
        textView.setText(textView.text,textViewbulder)
    }
}