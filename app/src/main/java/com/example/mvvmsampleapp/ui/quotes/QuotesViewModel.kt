package com.example.mvvmsampleapp.ui.quotes

import androidx.lifecycle.ViewModel
import com.example.mvvmsampleapp.data.repository.QuoteRepository
import com.example.mvvmsampleapp.util.lazyDeferred

class QuotesViewModel(
    repository: QuoteRepository
) : ViewModel() {

    //we dont want to initialise "quotes" whenever the  Quoteviewmodel is instantiated,
    // we need quotes only when it is needed,so use lazy initialisation

    val quotes by lazyDeferred {
        //it will call only when it is needed.
        repository.getQuotes()}
}
//we can call a suspend fun only from a suspend fn or from a coroutine fn so create lazydeferred,it will create a coroutine
// so it return a deferred value
//we cant call suspend fn directly so use custom lazy block that use coroutine scope to make call
