package com.nomnomkun.fluxenablr

import android.net.LocalSocket
import android.net.LocalSocketAddress
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.io.IOException
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private lateinit var socketListener: LocalSocket
    private fun sendLocalSocket(paramString: String): String {
        this.initSocket()
        if (!this.socketListener.isConnected) {
            try {
                Thread.sleep(200L);
            } catch (interruptedException: InterruptedException) {
            }
            this.initSocket()
        }
        if (!this.socketListener.isConnected)
            return String()
        try {
            val inputStream = this.socketListener.inputStream
            this.socketListener.outputStream.write((paramString + "\n").toByteArray())
            val arrayOfByte = ByteArray(2048)
            val i = inputStream.read(arrayOfByte)
            return if (i > 0) {
                val str = String(arrayOfByte, 0, i)
                try {
                    str
                } catch (exception: Exception) {
                    str
                }
            } else {
                String()
            }
        } catch (iOException: IOException) {
            Log.i("f.lux settings", iOException.toString());
            return String()
        } finally {
            try {
                this.socketListener.close()
            } catch (exception: Exception) {
            }
        }
    }

    private fun initSocket() {
        try {
            this.socketListener = LocalSocket()
            val str = "f.luxserver";
            this.socketListener.connect(LocalSocketAddress (str))
        } catch (iOException: IOException) {
            Log.i("oh no", iOException.toString())
            return
        }
    }

    fun sendToFlux() {
        val text = findViewById<TextView>(R.id.android_test)
        val input = findViewById<EditText>(R.id.input)
        text.text = this.sendLocalSocket(input.text.toString())
        input.setText("")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val text = findViewById<TextView>(R.id.android_test)
        text.text = this.sendLocalSocket("ping")
        val button = findViewById<Button>(R.id.ok)
        button.setOnClickListener {
            sendToFlux()
        }
    }

}
