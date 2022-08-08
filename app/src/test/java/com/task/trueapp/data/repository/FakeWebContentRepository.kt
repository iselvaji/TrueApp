package com.task.trueapp.data.repository

import com.task.trueapp.data.WebContentParser
import com.task.trueapp.domain.repository.WebContentRepository
import com.task.trueapp.util.TestWebContentData.getWebContentByParseType
import com.task.trueapp.utils.Constants
import com.task.trueapp.utils.Resource
import com.task.trueapp.utils.WebContentParseType

class FakeWebContentRepository : WebContentRepository {

    override suspend fun getNthCharFromWebContent(contentParseType: WebContentParseType): Resource<Any?> {
        return Resource.Success(data = WebContentParser().findNthCharByPosition(getWebContentByParseType(contentParseType), Constants.CHAR_POSITION_TO_FIND).toString())
    }

    override suspend fun getEveryNthCharFromWebContent(contentParseType: WebContentParseType): Resource<Any?> {
        return Resource.Success(data = WebContentParser().findEveryNthCharByPosition(getWebContentByParseType(contentParseType), Constants.CHAR_POSITION_TO_FIND))
    }

    override suspend fun getWordsCountFromWebContent(contentParseType: WebContentParseType): Resource<Any?> {
        return Resource.Success(data = WebContentParser().getWordCountSeparatedBySpace(getWebContentByParseType(contentParseType)))
    }

}