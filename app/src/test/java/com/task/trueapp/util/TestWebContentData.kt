package com.task.trueapp.util

import com.task.trueapp.utils.ResponseUtil
import com.task.trueapp.utils.WebContentParseType

/**
 * Class provides test data for Unit testing
 *
 */
object TestWebContentData {

    const val HTML_CONTENT = "<title>Life as an Android Engineer - Truecaller Blog</title>"
    const val WEB_CONTENT = "Abcdefghij klmnop qrsT uv WXYZ 1234"
    const val WEB_CONTENT_WITHOUT_SPACE = "AbcdefghijklmnopqrsTuvWXYZ1234"
    private const val WEB_RESPONSE = "<p> Truecaller Hello World We are on something new hello truecaller </p>"

    fun getWebContentByParseType(parseType: WebContentParseType): String {

        return when(parseType) {
            is WebContentParseType.TextOnly -> {
                ResponseUtil.htmlToString(WEB_RESPONSE)
            }
            is WebContentParseType.HtmlContent -> {
                WEB_RESPONSE
            }
        }
    }


}