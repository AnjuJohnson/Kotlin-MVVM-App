package com.example.mvvmsampleapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mvvmsampleapp.data.db.AppDatabase
import com.example.mvvmsampleapp.data.db.entities.Quote
import com.example.mvvmsampleapp.data.network.MyApi
import com.example.mvvmsampleapp.data.network.SafeApiRequest
import com.example.mvvmsampleapp.data.preferences.PrefernceProvider
import com.example.mvvmsampleapp.util.Coroutines
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
private val MINIMUM_INTERVEL=6
class QuoteRepository(
    private val api:MyApi,
    private val db:AppDatabase,
    private val prefs:PrefernceProvider
):SafeApiRequest() {

    private val quotes=MutableLiveData<List<Quote>>()


    //will call automatically when the class instantiated
    init {
        //we  are not in activty or fragamnet so we dont need to worry about lifecycle changes so use observeforever
        quotes.observeForever {
            //whevever changes in quotes we willl push to db.
            saveQuotes(it)
        }
    }
    //to fetch from backend
    private suspend fun fetchQuotes(){
        val lastSavedAt=prefs.getLastSavedAt()


        if(lastSavedAt==null||isFetchNeeded(LocalDateTime.parse(lastSavedAt))){
            try {
                val response = apiRequest { api.getQuotes() }
                quotes.postValue(response.quotes)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    private fun isFetchNeeded(savedAt: LocalDateTime):Boolean{
        return ChronoUnit.HOURS.between(savedAt,LocalDateTime.now())> MINIMUM_INTERVEL
    }
    //to get data from db
    suspend fun getQuotes():LiveData<List<Quote>>{
        //need coroutinescope so use withContext
        return withContext(Dispatchers.IO){
            fetchQuotes()
            db.getQuoteDao().getQuotes()
        }
    }


    private fun saveQuotes(quotes:List<Quote>){
        //call saveAllQuote fun from QuoteDao but it should be in dfferent thread.so use coroutine

        Coroutines.io {
            //save timestamp in preference


            prefs.savelastSavedAt(LocalDateTime.now().toString())


            db.getQuoteDao().saveAllQuote(quotes)
        }
    }
}