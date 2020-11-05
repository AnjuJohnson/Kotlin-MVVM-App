package com.example.mvvmsampleapp.util

import kotlinx.coroutines.*
///Deferred value is a non-blocking ,cancellable future — it is a Job(a cancelable thing) with a result.
// Deferred has the same state machine as the Job with additional convenience methods to retrieve the successful or failed
// result of the computation that was carried out. The result of the deferred is available when
// it is completed and can be retrieved by "await" method,
// which throws an exception if the deferred had failed. Note that a cancelled deferred is also considered as completed.
/*Usually, a deferred value is created in active state (it is created and started).
However, the async coroutine builder has an optional start parameter that creates a deferred value in
new state when this parameter is set to CoroutineStart.LAZY.
Such a deferred can be be made active by invoking start, join, or await.*/
//deferred is job with result
//generic fn
fun<T> lazyDeferred(block:suspend CoroutineScope.()->T):Lazy<Deferred<T>>{
    return lazy {
        //everything shuold be lazy
        GlobalScope.async(start = CoroutineStart.LAZY) {
            block.invoke(this)
        }
    }
}