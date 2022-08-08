package com.task.trueapp.data.repository

import android.util.Log
import com.task.trueapp.data.WebContentParser
import com.task.trueapp.data.remote.WebApiService
import com.task.trueapp.domain.repository.WebContentRepository
import com.task.trueapp.utils.Constants
import com.task.trueapp.utils.Resource
import com.task.trueapp.utils.ResponseUtil
import com.task.trueapp.utils.WebContentParseType
import io.ktor.client.statement.*
import javax.inject.Inject


/**
 * Web content repository implementation contains implementation repository functions to return the required data
 *
 * @property api WebApiService
 * @property parser WebContentParser
 */
class WebContentRepositoryImpl
    @Inject constructor(private val api: WebApiService,
                        private val parser: WebContentParser) : WebContentRepository{

    /**
     * Get nth char from web content
     *
     * @param contentParseType WebContentParseType
     * @return Resource.Success with CHAR_POSITION_TO_FIND character in case of successful without space character
     *         Resource.Error in case of error
     */
    override suspend fun getNthCharFromWebContent(contentParseType: WebContentParseType) : Resource<Any?> {
        Log.d("getNthCharFromWeb","begins")
        return try {
            Resource.Success(data = parser.findNthCharByPosition(getWebContent(contentParseType), Constants.CHAR_POSITION_TO_FIND).toString())
        } catch(ex :  Exception) {
            Resource.Error(message = ex.message.toString())
        }
    }

    /**
     * Get every nth char from web content
     *
     * @param contentParseType WebContentParseType
     * @return Resource.Success with String which contain every {CHAR_POSITION_TO_FIND} character without space character
     * in case of successful
     * Resource.Error in case of error
     */
    override suspend fun getEveryNthCharFromWebContent(contentParseType: WebContentParseType): Resource<Any?> {
        Log.d("getEveryNthCharFromWeb","begins")
        return try {
            Resource.Success(data = parser.findEveryNthCharByPosition(getWebContent(contentParseType), Constants.CHAR_POSITION_TO_FIND))
        } catch(ex :  Exception) {
            Resource.Error(message = ex.message.toString())
        }
    }

    /**
     * Get words count from web content
     *
     * @param contentParseType WebContentParseType
     * @return Resource.Success with String which contain each word and their count separated by space
     *         Resource.Error in case of error
     */
    override suspend fun getWordsCountFromWebContent(contentParseType: WebContentParseType): Resource<Any?> {
        Log.d("getWordsCountFromWeb ","begins")
        return try {
            Resource.Success(data = parser.getWordCountSeparatedBySpace(getWebContent(contentParseType)))
        } catch(ex :  Exception) {
            Resource.Error(message = ex.message.toString())
        }
    }

    /**
     * Get web content
     * This function will get the api response and return the string which contain text or html content of response
     * based on WebContentParseType provided
     * @param contentParseType TextOnly or HtmlContent
     * @return
     */
    private suspend fun getWebContent(contentParseType:WebContentParseType = WebContentParseType.TextOnly) : String {
        val response = api.getWebContentResponse().readText()
        val responseString = when(contentParseType) {
                is WebContentParseType.TextOnly ->
                    ResponseUtil.htmlToString(response)
                is WebContentParseType.HtmlContent ->
                    response
        }
        Log.d("contentParseType ", contentParseType.toString())
        Log.d("responseString ", responseString)
        return responseString
    }

}