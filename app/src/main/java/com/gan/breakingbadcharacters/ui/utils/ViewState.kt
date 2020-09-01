package com.gan.breakingbadcharacters.ui.utils

import com.gan.breakingbadcharacters.ui.utils.ViewStateType.*
import java.lang.Exception

data class ViewState(
    val type: ViewStateType,
    val data: Any? = null,
    val error: Throwable? = null
) {

    fun isNetworkError() = error is NoNetworkException

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ViewState

        if (type != other.type) return false
        if (data != other.data) return false
        if (error?.javaClass != other.error?.javaClass
            || error?.message != other.error?.message) return false

        return true
    }

    companion object {
        fun Loading() = ViewState(LOADING)

        fun Completed() = ViewState(COMPLETED)

        fun <T> Loaded(data: T? = null) = ViewState(COMPLETED, data = data)

        fun NetworkError() = ViewState(ERROR, error = NoNetworkException())

        fun Error(msg: String? = null, error: Throwable? = null) = when {
            error != null -> ViewState(type = ERROR, error = error)
            msg != null -> ViewState(type = ERROR, error = Exception(msg))
            else -> ViewState(type = ERROR, error = Exception("Something went wrong."))
        }
    }
}

enum class ViewStateType {
    LOADING,
    COMPLETED,
    ERROR
}

class NoNetworkException : Exception()