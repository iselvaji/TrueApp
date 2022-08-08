package com.task.trueapp.util

import com.task.trueapp.utils.ResponseUtil
import org.junit.Test

class ResponseUtilTest {

    @Test
    fun `html to string conversion success`() {

        val htmlContent = TestWebContentData.HTML_CONTENT
        val stringContent = ResponseUtil.htmlToString(htmlContent)
        val expected = "Life as an Android Engineer - Truecaller Blog"
        assert(expected == stringContent)
    }

    @Test
    fun `html to string conversion with invalid input`() {

        val htmlContent = null
        val stringContent = ResponseUtil.htmlToString(htmlContent)
        val expected = ""
        assert(expected == stringContent)
    }

    @Test
    fun `get string without space success`() {

        val input = TestWebContentData.WEB_CONTENT
        val expected = TestWebContentData.WEB_CONTENT_WITHOUT_SPACE
        val result = ResponseUtil.getStringWithOutSpace(input)
        print(result)
        assert(result == expected)
    }

    @Test
    fun `get string without new line char success`() {

        val input = "A\nB\n\n\r\nC"
        val expected = "ABC"
        val result = ResponseUtil.getStringWithOutSpace(input)
        print(result)
        assert(result == expected)
    }
}