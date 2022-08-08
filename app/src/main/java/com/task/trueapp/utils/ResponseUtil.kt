package com.task.trueapp.utils

import com.task.trueapp.utils.Constants.CHAR_COUNT_SPLIT
import com.task.trueapp.utils.Constants.CHAR_NEW_LINE
import com.task.trueapp.utils.Constants.CHAR_SPACE
import org.jsoup.Jsoup

object ResponseUtil {

    /**
     * Convert provided string which contains HTML tags to string without html tags
     * @param response input string with HTML tags
     * @return string without html tags [only text]
     */
    fun htmlToString(response:String?): String {
        response?.let {
            val doc = Jsoup.parse(it)
            return doc.text()
        }
        return ""
    }

    /**
     * Remove space,new line char, tab from String
     * @param input string
     * @return result string without space,new line char,tab chars
     */
    fun getStringWithOutSpace(input: String?): String? {
        var result: String? = null
        input?.let {
            result = it.replace(CHAR_SPACE.toRegex(), "")
        }
        return result
    }

    /**
     * Get formatted word count
     *
     * @param word
     * @param count
     * @return result in formatted [word : count with newline char at the end] format
     */
    fun getFormattedWordCount(word: String, count: Int) : String {
        return word.plus(CHAR_COUNT_SPLIT).plus(count).plus(CHAR_NEW_LINE)
    }
}