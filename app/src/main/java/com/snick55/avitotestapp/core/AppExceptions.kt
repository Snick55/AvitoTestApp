package com.snick55.avitotestapp.core

open class AppException(errorMessage: String): java.lang.Exception(errorMessage)

class NoInternetException: AppException("No Internet connection")
class ServerUnavailableException: AppException("Server unavailable")
class TimeOutException: AppException("Timeout... try again later")
class GenericException: AppException("Something went wrong")