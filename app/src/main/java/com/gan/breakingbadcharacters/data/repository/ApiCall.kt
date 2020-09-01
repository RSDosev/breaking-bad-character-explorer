package com.gan.breakingbadcharacters.data.repository

import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException

suspend fun <T> apiCall(apiCall: suspend () -> T): Result<T> {
    return try {
        Result.Success(apiCall.invoke())
    } catch (error: Throwable) {
        when (error) {
            is IOException, is ConnectException -> Result.Error.NetworkError
            is HttpException -> Result.Error.ApiCallError(
                code = error.code(),
                errorMsg = error.response()?.errorBody()?.string() ?: error.message()
            )
            else -> {
                Result.Error.ApiCallError(null, null)
            }
        }
    }
}