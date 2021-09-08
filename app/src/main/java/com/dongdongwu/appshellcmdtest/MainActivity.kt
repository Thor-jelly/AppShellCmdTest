package com.dongdongwu.appshellcmdtest

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("123===", "ping--start")
        val isSuccess = ping("www.baidu.com")
        Log.d("123===", "isSuccess=$isSuccess")
        Log.d("123===", "ping---end")

        shellExec()
    }

    fun shellExec() {
        val mRuntime = Runtime.getRuntime()
        try {
            //Process中封装了返回的结果和执行错误的结果
            val mProcess = mRuntime.exec("logcat -d -v time -f /mnt/sdcard/logcat.txt")
            val mReader = BufferedReader(InputStreamReader(mProcess.inputStream))
            val mRespBuff = StringBuffer()
            val buff = CharArray(1024)
            var ch = 0
            while (mReader.read(buff).also { ch = it } != -1) {
                mRespBuff.append(buff, 0, ch)
            }
            mReader.close()
            Log.d("123===", "mRespBuff.toString()")
            Log.d("123===", "$mRespBuff---")
            Log.d("123===", "$mRespBuff---")
            Log.d("123===", "$mRespBuff---")
            Log.d("123===", "mRespBuff.toString()")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("123===", e.message)
        }
    }

    fun ping(address: String): Boolean {
        return try {
            val process = Runtime.getRuntime().exec("ping $address")
            val inputStream = process.inputStream
            val br = BufferedReader(InputStreamReader(inputStream))
            var isOk = true
            var line: String?
            while (true) {
                line = br.readLine()
                Log.d("123===", "line = $line")
                if (line.isNullOrEmpty()) break
            }
            // 网络不可用
            if ((line ?: "").contains("Destination Net Unreachable")) {
                isOk = false
            }
            inputStream.close()
            br.close()
            process.destroy()
            isOk
        } catch (e: Exception) {
            false
        }
    }
}