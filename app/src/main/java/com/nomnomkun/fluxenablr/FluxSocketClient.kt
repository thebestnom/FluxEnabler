package com.nomnomkun.fluxenablr

import android.net.LocalSocket
import android.net.LocalSocketAddress
import android.util.Log
import java.io.IOException
import java.lang.Exception

class FluxSocketClient {
    companion object {
        private lateinit var socketListener: LocalSocket
        fun sendLocalSocket(paramString: String): String {
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
                this.socketListener.connect(LocalSocketAddress(str))
            } catch (iOException: IOException) {
                Log.i("oh no", iOException.toString())
                return
            }
        }
    }
}