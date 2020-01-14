package com.nomnomkun.fluxenablr

import android.content.ContextWrapper
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private fun sendToFlux() {
        val text = findViewById<TextView>(R.id.android_test)
        val input = findViewById<EditText>(R.id.input)
        text.text = FluxSocketClient.sendLocalSocket(input.text.toString())
        input.setText("")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val text = findViewById<TextView>(R.id.android_test)
        text.text = FluxSocketClient.sendLocalSocket("ping")
        val button = findViewById<Button>(R.id.ok)
        button.setOnClickListener {
            sendToFlux()
        }
        startService(Intent(this, FluxScreenOffDisabler::class.java))
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}
