package com.task.trueapp.domain.repository

import com.task.trueapp.utils.Resource
import com.task.trueapp.utils.WebContentParseType

interface WebContentRepository {
    suspend fun getNthCharFromWebContent(contentParserType: WebContentParseType): Resource<Any?>
    suspend fun getEveryNthCharFromWebContent(contentParseType: WebContentParseType):Resource<Any?>
    suspend fun getWordsCountFromWebContent(contentParseType: WebContentParseType): Resource<Any?>
}