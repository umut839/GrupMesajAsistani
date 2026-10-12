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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val messageEditText = findViewById<EditText>(R.id.messageEditText)
        val selectImageButton = findViewById<Button>(R.id.selectImageButton)
        val sendWhatsAppButton = findViewById<Button>(R.id.sendWhatsAppButton)
        previewImageView = findViewById(R.id.previewImageView)

        selectImageButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, 100)
        }

        sendWhatsAppButton.setOnClickListener {
            val message = messageEditText.text.toString()
            
            if (message.isEmpty() && selectedImageUri == null) {
                Toast.makeText(this, "Lütfen mesaj yazın veya görsel seçin.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val sendIntent = Intent(Intent.ACTION_SEND)
            
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

            // Android paylaşım ekranını atlayıp doğrudan WhatsApp'ı açar
            sendIntent.setPackage("com.whatsapp")

            try {
                startActivity(sendIntent)
            } catch (e: Exception) {
                Toast.makeText(this, "WhatsApp yüklü değil veya bulunamadı.", Toast.LENGTH_SHORT).show()
            }
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
