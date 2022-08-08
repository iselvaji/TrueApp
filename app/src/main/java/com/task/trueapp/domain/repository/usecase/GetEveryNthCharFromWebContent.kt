package com.task.trueapp.domain.repository.usecase

import com.task.trueapp.domain.repository.WebContentRepository
import com.task.trueapp.utils.Resource
import com.task.trueapp.utils.WebContentParseType

/**
 * Get every nth char position (given as input) from web content from repository
 * @property repository
 */
class GetEveryNthCharFromWebContent(private val repository: WebContentRepository){
    suspend operator fun invoke(contentParseType: WebContentParseType) : Resource<Any?> {
        return repository.getEveryNthCharFromWebContent(contentParseType)
    }
}
