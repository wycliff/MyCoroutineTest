package com.example.wycliffenyakemwa.mycouroutines

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"

    @ObsoleteCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

        /**
         * Jobs
         * Waiting
         * Cancellation
         * Usecase : cancel long running network calls
         */
//        val job = GlobalScope.launch(Dispatchers.Default) {
//            repeat(5) {
//                Log.d(TAG, "Coroutine is still working... ")
//                delay(1000L)
//            }
//        }

//         runBlocking {
//            job.join() //Suspends the coroutine until this job is complete.
//            Log.d(TAG , "Main thread is continuing... ")
//        }

        val job = GlobalScope.launch(Dispatchers.Default) {
            Log.d(TAG, "Starting Long Running Job...")

            // Automatic job cancellation if it times out
            withTimeout(3000L) {
                //Automatic cancellation
                for (i in 30..45) {
                    if (isActive) { // check if our coroutine is still active or if it has been canceled
                        Log.d(TAG, "Output for i = $i: ${fib(i)} ")
                    }
                }
            }
            Log.d(TAG, "Ending Long Running Job...")
        }

        //manually cancelling a job
//        runBlocking {
//            delay(2000L)
//            job.cancel() // sometimes coroutine too busy to receive cancellation
//            Log.d(TAG, "Cancel Job!")
//        }

        /**
         * Async and Await
         */
        GlobalScope.launch(Dispatchers.IO) {
            val time = measureTimeMillis {
                val answer1 = async { doNetworkCall() }
                val answer2 = async { doNetworkCall2() }

                Log.d(TAG, "Answer 1: ${answer1.await()}")
                Log.d(TAG, "Answer 2: ${answer2.await()}")
            }
            Log.d(TAG, "Time taken : $time ")
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

    fun fib(n: Int): Long {
        return if (n == 0) 0
        else if (n == 1) 1
        else fib(n - 1) + fib(n - 2)
    }
}
