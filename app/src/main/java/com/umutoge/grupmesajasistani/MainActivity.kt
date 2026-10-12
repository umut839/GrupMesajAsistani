package com.umutoge.grupmesajasistani

import android.app.Activity
import android.os.Bundle
import android.widget.TextView

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val textView = TextView(this)
        textView.text = "GrupMesaj Asistanı - İlk Sürüm Hazır"
        textView.textSize = 24f
        textView.gravity = android.view.Gravity.CENTER
        
        setContentView(textView)
    }
}
