package com.umutoge.grupmesajasistani

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast

class MainActivity : Activity() {
    
    private var selectedImageUri: Uri? = null
    private lateinit var previewImageView: ImageView
    private lateinit var messageEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        messageEditText = findViewById(R.id.messageEditText)
        val selectImageButton = findViewById<Button>(R.id.selectImageButton)
        previewImageView = findViewById(R.id.previewImageView)

        // 4 Butonu tanımlıyoruz
        val btnSend1 = findViewById<Button>(R.id.btnSend1)
        val btnSend2 = findViewById<Button>(R.id.btnSend2)
        val btnSend3 = findViewById<Button>(R.id.btnSend3)
        val btnSend4 = findViewById<Button>(R.id.btnSend4)

        selectImageButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, 100)
        }

        // Tıklama işlemini tüm butonlar için ortak yapıyoruz
        val sendClickListener = android.view.View.OnClickListener {
            sendToWhatsApp()
        }

        btnSend1.setOnClickListener(sendClickListener)
        btnSend2.setOnClickListener(sendClickListener)
        btnSend3.setOnClickListener(sendClickListener)
        btnSend4.setOnClickListener(sendClickListener)
    }

    // Ortak WhatsApp Gönderme Fonksiyonu
    private fun sendToWhatsApp() {
        val message = messageEditText.text.toString()
        
        if (message.isEmpty() && selectedImageUri == null) {
            Toast.makeText(this, "Lütfen mesaj yazın veya görsel seçin.", Toast.LENGTH_SHORT).show()
            return
        }

        val sendIntent = Intent(Intent.ACTION_SEND)
        sendIntent.setPackage("com.whatsapp")
        
        if (selectedImageUri != null) {
            sendIntent.type = "image/*"
            sendIntent.putExtra(Intent.EXTRA_STREAM, selectedImageUri)
            sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        } else {
            sendIntent.type = "text/plain"
        }

        if (message.isNotEmpty()) {
            sendIntent.putExtra(Intent.EXTRA_TEXT, message)
        }

        try {
            startActivity(sendIntent)
        } catch (e: Exception) {
            Toast.makeText(this, "WhatsApp bulunamadı.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK) {
            selectedImageUri = data?.data
            previewImageView.setImageURI(selectedImageUri)
        }
    }
}
