package com.example.courotine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.*
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
            CoroutineScope(Dispatchers.IO).launch {
                Log.d("nk1", Thread.currentThread().name)
            }
        GlobalScope.launch(Dispatchers.Main){
            Log.d("nk2", Thread.currentThread().name)
        }
        MainScope().launch (Dispatchers.Default){
            Log.d("nk3", Thread.currentThread().name)
        }
    }
}