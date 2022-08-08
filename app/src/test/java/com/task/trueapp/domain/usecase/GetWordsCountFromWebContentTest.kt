package com.task.trueapp.domain.usecase

import com.task.trueapp.data.repository.FakeWebContentRepository
import com.task.trueapp.domain.repository.usecase.GetWordsCountFromWebContent
import com.task.trueapp.utils.Resource
import com.task.trueapp.utils.ResponseUtil.getFormattedWordCount
import com.task.trueapp.utils.WebContentParseType
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetWordsCountFromWebContentTest {

    private lateinit var repository: FakeWebContentRepository

    @Before
    fun setUp() {
        repository = FakeWebContentRepository()
    }

    @Test
    fun `get words count from web content (Html content) split by space and check success`() = runBlocking {

        val response = GetWordsCountFromWebContent(repository = repository)(WebContentParseType.HtmlContent)
        assert(response is Resource.Success)

        val expected = StringBuilder()
        expected.apply {
            append(getFormattedWordCount("<p>", 1))
            append(getFormattedWordCount("truecaller", 2))
            append(getFormattedWordCount("hello", 2))
            append(getFormattedWordCount("world", 1))
            append(getFormattedWordCount("we", 1))
            append(getFormattedWordCount("are", 1))
            append(getFormattedWordCount("on", 1))
            append(getFormattedWordCount("something", 1))
            append(getFormattedWordCount("new", 1))
            append(getFormattedWordCount("</p>", 1))
        }
        assert(response.data == expected.toString())
    }

    @Test
    fun `get words count from web content (Text only) split by space and check success`() = runBlocking {

        val response = GetWordsCountFromWebContent(repository = repository)(WebContentParseType.TextOnly)
        assert(response is Resource.Success)

        val expected = StringBuilder()

        expected.apply {
            append(getFormattedWordCount("truecaller", 2))
            append(getFormattedWordCount("hello", 2))
            append(getFormattedWordCount("world", 1))
            append(getFormattedWordCount("we", 1))
            append(getFormattedWordCount("are", 1))
            append(getFormattedWordCount("on", 1))
            append(getFormattedWordCount("something", 1))
            append(getFormattedWordCount("new", 1))
        }

        assert(response.data == expected.toString())
    }

}