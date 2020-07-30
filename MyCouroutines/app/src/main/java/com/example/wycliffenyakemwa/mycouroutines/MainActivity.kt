package com.example.wycliffenyakemwa.mycouroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
     var TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG, "Oncreate Thread : ${Thread.currentThread().name}")

        //start a co-routine ( simplest way )
        GlobalScope.launch {
            //instructions we want it to execute

            delay(4000L) // pauses current co-routine
            Log.d(TAG, "Thread launched by co-routine : ${Thread.currentThread().name}")
        }
    }
}
