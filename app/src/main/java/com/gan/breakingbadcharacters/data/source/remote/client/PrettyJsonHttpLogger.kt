package com.gan.breakingbadcharacters.data.source.remote.client

import android.util.Log.INFO
import android.util.Log.WARN
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import okhttp3.internal.platform.Platform
import okhttp3.logging.HttpLoggingInterceptor

class PrettyJsonHttpLogger : HttpLoggingInterceptor.Logger {
    private val gson = GsonBuilder().setPrettyPrinting().create()
    private val jsonParser = JsonParser()

    override fun log(message: String) {
        val trimMessage = message.trim { it <= ' ' }
        if (trimMessage.startsWith("{") && trimMessage.endsWith("}") || trimMessage.startsWith("[") && trimMessage.endsWith(
                "]"
            )
        ) {
            try {
                val prettyJson = gson.toJson(jsonParser.parse(message))
                Platform.get().log(INFO, prettyJson, null)
            } catch (e: Exception) {
                Platform.get().log(WARN, message, e)
            }

        } else {
            Platform.get().log(INFO, message, null)
        }
    }
}