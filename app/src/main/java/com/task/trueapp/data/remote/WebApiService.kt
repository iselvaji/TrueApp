package com.task.trueapp.data.remote

import io.ktor.client.statement.*

/**
 * Web api service - contain the function to get the api response
 */
interface WebApiService {

    suspend fun getWebContentResponse() : HttpResponse

    companion object {
        const val BASE_URL = "https://truecaller.blog/"
    }

    /**
     * API Endpoints contains the URL end points
     * @property url Base url
     */
    sealed class Endpoints(val url: String) {
        object GetWebContent : Endpoints("$BASE_URL/2018/01/22/life-as-an-android-engineer/")
    }
}