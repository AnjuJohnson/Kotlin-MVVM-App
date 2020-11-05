package com.example.mvvmsampleapp.data.network

import com.example.mvvmsampleapp.util.ApiException
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import java.lang.StringBuilder

//generic function to do the apirequest
abstract class SafeApiRequest {
    //parameter of apirequest fn is another suspend fn with return Response ie,we will  invoke the call and we will get the response
    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): T {
        val response = call.invoke()

        if (response.isSuccessful) {
            return response.body()!!
        } else {
            val error = response.errorBody()?.string()

            val message = StringBuilder()
            error?.let {
                try {
                    message.append(JSONObject(it).getString("message"))
                } catch (e: JSONException){}

                message.append("\n")
            }
            message.append("Error code:${response.code()}")
            throw ApiException(message.toString())

        }

    }
}