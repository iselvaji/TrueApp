package com.task.trueapp.util

import com.task.trueapp.data.WebContentParser
import com.task.trueapp.util.TestWebContentData.WEB_CONTENT
import com.task.trueapp.util.TestWebContentData.WEB_CONTENT_WITHOUT_SPACE
import com.task.trueapp.utils.Constants
import com.task.trueapp.utils.Constants.CHAR_COUNT_SPLIT
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.lang.StringBuilder

class WebContentParserTest {

    @Test
    fun `find Nth char from string success`() = runBlocking {

        val input = WEB_CONTENT
        val expected = 'j'
        val result = WebContentParser().findNthCharByPosition(input, Constants.CHAR_POSITION_TO_FIND)
        assert(result == expected)
    }

    @Test
    fun `find Nth char from string with invalid input`() = runBlocking {

        val input = null
        val result = WebContentParser().findNthCharByPosition(input, Constants.CHAR_POSITION_TO_FIND)
        assert(result == Char.MIN_VALUE)
    }

    @Test
    fun `find Nth char from string with out of range position`() = runBlocking {

        val input = WEB_CONTENT
        val result = WebContentParser().findNthCharByPosition(input, 100)
        assert(result == Char.MIN_VALUE)
    }


    @Test
    fun `find Nth chars from string success`() = runBlocking {

        val input = WEB_CONTENT
        val expected = "jT4"
        val result = WebContentParser().findEveryNthCharByPosition(input, Constants.CHAR_POSITION_TO_FIND)
        assert(result == expected)
    }

    @Test
    fun `find Nth chars from string with out of range position`() = runBlocking {

        val input = WEB_CONTENT
        val result = WebContentParser().findEveryNthCharByPosition(input, 100)
        val expected = ""
        assert(result == expected)
    }

    @Test
    fun `find words count chars from with invalid input`() = runBlocking {

        val input = null
        val result = WebContentParser().getWordCountSeparatedBySpace(input)
        val expected = ""
        assert(result == expected)
    }

    @Test
    fun `find words count chars from string without space`() = runBlocking {

        val input = WEB_CONTENT_WITHOUT_SPACE
        val result = WebContentParser().getWordCountSeparatedBySpace(input)
        val expected = StringBuilder()
        expected.append(WEB_CONTENT_WITHOUT_SPACE.lowercase()).append(CHAR_COUNT_SPLIT).append("1").append("\n")
        assert(result == expected.toString())
    }
}