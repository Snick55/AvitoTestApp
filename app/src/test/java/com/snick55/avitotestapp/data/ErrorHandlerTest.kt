package com.snick55.avitotestapp.data

import com.snick55.avitotestapp.core.EmptyResponseException
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ErrorHandlerTest {


    @Test
    fun `testing handle when no internet connection`() {
        val errorHandler = ErrorHandler.BaseErrorHandler()
        val error = errorHandler.handle(UnknownHostException())
        val actual = error.message
        val expected = "No Internet connection"
        assertEquals(expected, actual)
    }
    @Test
    fun `testing handle when Server unavailable`() {
        val errorHandler = ErrorHandler.BaseErrorHandler()
        val error = errorHandler.handle(HttpException(Response.error<ResponseBody>(500 ,ResponseBody.create(
            "plain/text".toMediaTypeOrNull(),"some content"))))
        val actual = error.message
        val expected = "Server unavailable"
        assertEquals(expected, actual)
    }
    @Test
    fun `testing handle when connection timeout`() {
        val errorHandler = ErrorHandler.BaseErrorHandler()
        val error = errorHandler.handle(SocketTimeoutException())
        val actual = error.message
        val expected = "Timeout... try again later"
        assertEquals(expected, actual)
    }
    @Test
    fun `testing handle when empty response exception`() {
        val errorHandler = ErrorHandler.BaseErrorHandler()
        val error = errorHandler.handle(EmptyResponseException())
        val actual = error.message
        val expected = "No one movie for ths query"
        assertEquals(expected, actual)
    }
    @Test
    fun `testing handle error with other exception`() {
        val errorHandler = ErrorHandler.BaseErrorHandler()
        val error = errorHandler.handle(NullPointerException())
        val actual = error.message
        val expected = "Something went wrong"
        assertEquals(expected, actual)
    }
}