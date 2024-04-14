package com.snick55.avitotestapp.data


import com.snick55.avitotestapp.core.AppException
import com.snick55.avitotestapp.core.EmptyResponseException
import com.snick55.avitotestapp.core.GenericException
import com.snick55.avitotestapp.core.NoInternetException
import com.snick55.avitotestapp.core.ServerUnavailableException
import com.snick55.avitotestapp.core.TimeOutException
import retrofit2.HttpException
import java.net.SocketTimeoutException

import java.net.UnknownHostException
import javax.inject.Inject

interface ErrorHandler{

    fun handle(e: Exception): AppException

    class BaseErrorHandler @Inject constructor(): ErrorHandler {
        override fun handle(e: Exception): AppException {
            return when(e){
                is UnknownHostException -> NoInternetException()
                is HttpException -> ServerUnavailableException()
                is SocketTimeoutException -> TimeOutException()
                is EmptyResponseException -> EmptyResponseException()
                else -> GenericException()
            }
        }
    }

}