package com.task.trueapp.data.remote

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

/**
 * Web api service implementation to retrieve the api response from api end points/url
 * @property client Httpclient
 */

class WebApiServiceImpl(private val client: HttpClient): WebApiService  {

    override suspend fun getWebContentResponse(): HttpResponse {
        return client.get(WebApiService.Endpoints.GetWebContent.url)
    }
}