package com.example.wycliffenyakemwa.mycouroutines

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG, "Oncreate Thread : ${Thread.currentThread().name}")

        //start a co-routine
        GlobalScope.launch {

            delay(4000L) // pauses current co-routine. Only run inside co-routine or another suspend function.
            Log.d(TAG, "Thread launched by co-routine : ${Thread.currentThread().name}")

            //Suspend functions (sequential execution)
            val networkCall1 = doNetworkCall()
            Log.d(TAG, networkCall1)
            val networkCall2 = doNetworkCall2()
            Log.d(TAG, networkCall2)

        }

        //doNetworkCall() - cant call from here
    }

    private suspend fun doNetworkCall(): String {
        delay(3000L) // simulated network call
        return "This is the network response"
    }

    private suspend fun doNetworkCall2(): String {
        delay(3000L) // simulated network call
        return "This is the network response2"
    }
}
