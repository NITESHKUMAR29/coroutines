package com.example.courotine

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.courotine.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding= DataBindingUtil.setContentView(this,R.layout.activity_main)
//            CoroutineScope(Dispatchers.IO).launch {
//                Log.d("nk1", Thread.currentThread().name)
//            }
//        GlobalScope.launch(Dispatchers.Main){
//            Log.d("nk2", Thread.currentThread().name)
//        }
//        MainScope().launch (Dispatchers.Default){
//            Log.d("nk3", Thread.currentThread().name)
//        }
//        CoroutineScope(Dispatchers.Main).launch {
//            task1()
//        }
//        CoroutineScope(Dispatchers.Main).launch {
//            task2()
//        }

        CoroutineScope(Dispatchers.IO).launch {
            // printAmount()
            // printAmounts()
            //printAmount2()
//            printAmount2()
//            printAmount3()
//            canccelJob()
            lifecycleScopes()
        }
        binding.delays.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }


    }

    //launch function
    private suspend fun printAmount() {
        var amount = 0
        val job = CoroutineScope(Dispatchers.IO).launch {//this is launch operation that return job
            amount =
                getAmount()                          //this should be used where we only need to launch
            //coroutine  jaha result se hume koi matlb nahi hai
        }
        job.join()//jon ka kam hai jabtak Coroutine execute nahi hoga tab tak dusra call nahi hoga
        Log.d("nkAmount", amount.toString())
    }

    //normal data
    @SuppressLint("SetTextI18n")
    private suspend fun printAmount2() {

        CoroutineScope(Dispatchers.Main).launch {
            val amount = getAmount()
            val amount2 = getAmountLarge()
            Log.d(
                "nkAmount1",
                "$amount  $amount2"
            )//isme total 6 second lagenge kyoki 3 3 second ka wait ho raha hai
                binding.delays.text="$amount  $amount2"

        }

    }


    //async function
    private suspend fun printAmounts() {

        val amount = CoroutineScope(Dispatchers.IO).async {//this is async operation that return job
            getAmount()                          //this should be used where we  need some result code clean karta hai kam launch ke
            //jaisa hi hai
        }

        Log.d(
            "nkAmount",
            amount.await().toString()
        )//yaha await ka kam hai rukega jabtak coroutine execute nahi hoaga
    }


    //async data
    @SuppressLint("SetTextI18n")
    private suspend fun printAmount3() {

        CoroutineScope(Dispatchers.Main).launch {
            val amount = async { getAmount() }
            val amount2 = async { getAmountLarge() }
            Log.d(
                "nkAmount",
                "${amount.await()}  ${amount2.await()}" //isme total 3 second lagenge kyoki dono parallel executw hoga
            )
            binding.notDelay.text="${amount.await()}  ${amount2.await()}"
        }
    }

    //cancel job
    private suspend fun canccelJob(){
        Log.d("nk/check","job started")
        val job= CoroutineScope(Dispatchers.IO).launch {
            for (i in 1..100000){
                if (isActive){
                    longRunTask()
                    Log.d("nk/check",i.toString())
                }

            }
        }
        delay(10)

        job.cancel()
        Log.d("nk/check","job Canceled")
        job.join()
        Log.d("nk/check","parent Completed")
    }

    //suspend function
    private suspend fun task1() {
        Log.d("nk/Task", "task1 started")
        delay(5000)//this is long running task so here it is suspension point
        Log.d("nk/Task", "task1 end")
    }

    private suspend fun task2() {
        Log.d("nk/Task", "task2 started")
        delay(5000)//this is long running task so here it is suspension point
        Log.d("nk/Task", "task2 end")
    }


    private suspend fun getAmount(): Int {
        delay(3000)
        return 10000
    }

    private suspend fun getAmountLarge(): Int {
        delay(3000)
        return 10000
    }

    private fun longRunTask() {
        for (i in 1..1000000) {

        }
    }


    private fun lifecycleScopes() {
        lifecycleScope.launchWhenStarted {
            Log.d("nk/lifesycle", "started")
        }
    }

    override fun onStop() {
        super.onStop()
        Log.d("nk/lifesycle", "onstop")
    }

    override fun onStart() {
        super.onStart()
        Log.d("nk/lifesycle", "onstart")
    }

    override fun onPause() {
        super.onPause()
        Log.d("nk/lifesycle","onpause")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("nk/lifesycle","onDestroy")
    }
}