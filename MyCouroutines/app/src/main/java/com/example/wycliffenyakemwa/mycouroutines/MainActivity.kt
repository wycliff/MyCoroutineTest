package com.example.wycliffenyakemwa.mycouroutines

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"

    @ObsoleteCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

     //   Log.d(TAG, "Oncreate Thread : ${Thread.currentThread().name}")

        //start a co-routine
        GlobalScope.launch {

            delay(4000L) // pauses current co-routine. Only run inside co-routine or another suspend function.
          //  Log.d(TAG, "Thread launched by co-routine : ${Thread.currentThread().name}")

            //Suspend functions (sequential execution)
            val networkCall1 = doNetworkCall()
            Log.d(TAG, networkCall1)
            val networkCall2 = doNetworkCall2()
            Log.d(TAG, networkCall2)

        }

        //Co-routine contexts / threads
        /**
         * Dispatchers.Main : co-routine in main thread : UI stuff from within the co-routine
         * IO :  Network, DB writes, RW-files
         * Default : Complex and long lasting executions that can block main thread (sort 10k elements)
         * Unconfined : not confined
         * newSingleThreadContext(name) : Your own
         */

        GlobalScope.launch(newSingleThreadContext("MyThread")) { }

        GlobalScope.launch(Dispatchers.IO) {
         //   Log.d(TAG, "Thread launched by co-routine net call : ${Thread.currentThread().name}")
            val response = doNetworkCall()

            // switch context
            withContext(Dispatchers.Main) {
              //  Log.d(TAG, "Thread launched for response: ${Thread.currentThread().name}")
                tvTest.text = response
            }
        }


        /**
         * runBlocking : not asynchronous / in-sync with normal main thread flow
         * stops the main thread
         * usecase1 : if you need to call suspend functions
         * usecase2 : junit testing, to use suspend functions from inside  a test function
         */
        runBlocking {

            //can also run an async coroutine scope
            launch(Dispatchers.IO) {
                delay(3000L)
                Log.d(TAG, "After IO 1")
            }

            launch(Dispatchers.IO) {
                delay(3000L)
                Log.d(TAG, "After IO 2")
            }

            Log.d(TAG, "Before Delay")
            delay(5000L)
            Log.d(TAG, "After Delay")



        }
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
