package com.example.mvvmsampleapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mvvmsampleapp.data.db.AppDatabase
import com.example.mvvmsampleapp.data.db.entities.User
import com.example.mvvmsampleapp.data.network.MyApi
import com.example.mvvmsampleapp.data.network.SafeApiRequest
import com.example.mvvmsampleapp.data.network.responses.AuthResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


//loginactivity interact with authviewmodel and authviewmodel interact with userrepository

//to avoid the creating instance of myapi,we use constructor injection.so my api wont be dependent on userrepository

class UserRepository(
    private val api: MyApi,
    private val db: AppDatabase

) : SafeApiRequest() {


    suspend fun userLogin(email: String, password: String): AuthResponse {
        return apiRequest {api.userLogin(email, password) }
        /* //code before generic function
          return MyApi().userLogin(email,password)*/
    }
//upsert fn return id of inserted user and it is of type long
    suspend fun saveUser(user: User) = db.getUserDao().upsert(user)
    fun getUser()=db.getUserDao().getuser()



suspend fun userSignUp(name:String,email: String, password: String):AuthResponse{
    return apiRequest {api.userSignUp(name,email, password)}
}


    /*

    //code before adding coroutine
    fun userLogin(email: String, password: String): LiveData<String> {


        //cannot create instance of livedata because it is a abstract class.so use mutablelelivedata
        val loginResponse = MutableLiveData<String>()
//its a bad practise use this bcoz userrepository dependednt on MyApi class,so must use injection dependancy
        //but now creating instance of myapi
        //enque is asynchronouns call it can be make nicely using coroutines
        MyApi().userLogin(email, password)
            .enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    loginResponse.value = t.message;
                }

                override fun onResponse(
                    call: Call<ResponseBody>, response: Response<ResponseBody>
                ) {

                    if (response.isSuccessful) {
                        loginResponse.value = response.body()?.string()
                    } else {
                        loginResponse.value = response.errorBody()?.string()
                    }


                }
            })

        return loginResponse
    }*/
}