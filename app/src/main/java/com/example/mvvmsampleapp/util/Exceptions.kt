package com.example.mvvmsampleapp.util

import java.io.IOException

//message is the constructor
class ApiException(message:String):IOException(message)
class  NoInternetException(message: String):IOException(message)