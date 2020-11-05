package com.example.mvvmsampleapp.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//object is like static in java
//Coroutines are Asynchronous or non-blocking programming
object Coroutines {
    //fn main will take anther fn as parameter(lamda fn),here it is coroutine fn ie suspend fn
    fun main(work:suspend( ()->Unit) )=
        //define the thread in scope here it is main thread
    CoroutineScope(Dispatchers.Main).launch {
        work()
    }

    //to use different thread write another coroutine fn

    fun io(work:suspend( ()->Unit) )=

        CoroutineScope(Dispatchers.IO).launch {
            work()
        }
}