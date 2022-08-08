package com.task.trueapp.domain.repository.usecase

/**
 * Web content use case data class contains all the use cases
 *
 * @property getNthCharFromWebContent
 * @property getEveryNthCharFromWebContent
 * @property getWordsCountFromWebContent
 */
data class WebContentUseCase(
    val getNthCharFromWebContent: GetNthCharFromWebContent,
    val getEveryNthCharFromWebContent: GetEveryNthCharFromWebContent,
    val getWordsCountFromWebContent: GetWordsCountFromWebContent
)
