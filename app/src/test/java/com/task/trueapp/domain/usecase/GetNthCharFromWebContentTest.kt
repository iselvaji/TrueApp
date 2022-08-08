package com.task.trueapp.domain.usecase

import com.task.trueapp.data.repository.FakeWebContentRepository
import com.task.trueapp.domain.repository.usecase.GetNthCharFromWebContent
import com.task.trueapp.utils.Resource
import com.task.trueapp.utils.WebContentParseType
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetNthCharFromWebContentTest {

    private lateinit var repository: FakeWebContentRepository

    @Before
    fun setUp() {
        repository = FakeWebContentRepository()
    }

    @Test
    fun `get Nth char from web content (Html content) excluding space and check success`() = runBlocking {

        val response = GetNthCharFromWebContent(repository = repository)(WebContentParseType.HtmlContent)
        assert(response is Resource.Success)
        val expected = "l"
        assert(response.data == expected)
    }

    @Test
    fun `get Nth char from web content (Text only) excluding space and check success`() = runBlocking {

        val response = GetNthCharFromWebContent(repository = repository)(WebContentParseType.TextOnly)
        assert(response is Resource.Success)
        val expected = "r"
        print(response.data)
        assert(response.data == expected)
    }

}