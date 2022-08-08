package com.task.trueapp.domain.usecase

import com.task.trueapp.data.repository.FakeWebContentRepository
import com.task.trueapp.domain.repository.usecase.GetEveryNthCharFromWebContent
import com.task.trueapp.utils.Resource
import com.task.trueapp.utils.WebContentParseType
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetEveryNthCharFromWebContentTest {

    private lateinit var repository: FakeWebContentRepository

    @Before
    fun setUp() {
        repository = FakeWebContentRepository()
    }

    @Test
    fun `get EveryNth chars from web content (Html content) excluding space and check success`() = runBlocking {

        val response = GetEveryNthCharFromWebContent(repository = repository)(WebContentParseType.HtmlContent)
        assert(response is Resource.Success)
        val expected = "l o n n u p "
        print(expected)
        assert(response.data == expected)
    }

    @Test
    fun `get EveryNth chars from web content (Text only) excluding space and check success`() = runBlocking {

        val response = GetEveryNthCharFromWebContent(repository = repository)(WebContentParseType.TextOnly)
        assert(response is Resource.Success)
        val expected = "r d m h a "
        print(response.data)
        assert(response.data == expected)
    }


}