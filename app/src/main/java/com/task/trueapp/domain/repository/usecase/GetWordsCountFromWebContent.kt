package com.task.trueapp.domain.repository.usecase

import com.task.trueapp.domain.repository.WebContentRepository
import com.task.trueapp.utils.Resource
import com.task.trueapp.utils.WebContentParseType

/**
 * Get words and count from web content from repository
 * @property repository
 */
class GetWordsCountFromWebContent(private val repository: WebContentRepository){

    suspend operator fun invoke(contentParseType: WebContentParseType) : Resource<Any?> {
        return repository.getWordsCountFromWebContent(contentParseType)
    }
}
