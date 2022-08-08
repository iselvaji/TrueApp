package com.task.trueapp.data

import com.task.trueapp.utils.Constants.CHAR_BLANK
import com.task.trueapp.utils.Constants.CHAR_SPACE
import com.task.trueapp.utils.ResponseUtil.getFormattedWordCount
import com.task.trueapp.utils.ResponseUtil.getStringWithOutSpace
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Web content parser contains the functions to parse and
 * Get the nth Character, every nth Charters, words count data from provided data
 */

@Singleton
class WebContentParser @Inject constructor() {

    /**
     * Find nth char by given position from given string [excluding space from input]
     *
     * @param input
     * @param position
     * @return result char
     */
    suspend fun findNthCharByPosition(input: String?, position: Int): Char {
        var result = Character.MIN_VALUE
        try {
            withContext(Dispatchers.IO) {
                getStringWithOutSpace(input)?.let {
                    val pos = position -1 // index from 0
                    if(pos <= it.length) {
                        result = it[pos]
                    }
                }
            }
        } catch (ex : Exception) {
            ex.printStackTrace()
        }
        return result
    }

    /**
     * Find every nth char by given position from given string [excluding space from input]
     *
     * @param input
     * @param position
     * @return result string with all the characters on given position separated by blank space
     */
   suspend fun findEveryNthCharByPosition(input: String?, position: Int): String {
        val result = StringBuilder()
        try{
            withContext(Dispatchers.IO) {
                getStringWithOutSpace(input)?.let { it->
                    val pos = position -1 // index from 0
                    if(it.length >= pos) {
                        var i = 0
                        while (i <= it.length) {
                            if (i > 0) {
                                result.append(it[i - 1]).append(CHAR_BLANK)
                            }
                            i += position
                        }
                    }
                }
            }
        } catch (ex : Exception) {
            ex.printStackTrace()
        }
        return result.toString()
    }

    /**
     * Get word count separated by space from given string
     *
     * @param input
     * @return result string with all the characters separated by blank space and their count
     * result format is 'word : count'
     */
    suspend fun getWordCountSeparatedBySpace(input: String?) : String {
       var wordCountMap = mapOf<String, Int>()
       val result = StringBuilder()
        try {
            withContext(Dispatchers.IO) {
                input?.let { input->
                    val words = input.split(CHAR_SPACE.toRegex()).toTypedArray()
                    wordCountMap = words.groupingBy { it.lowercase() }.eachCount().filter { it.value >= 1 }
                }

                wordCountMap.onEach {
                    result.append(getFormattedWordCount(it.key, it.value))
                }
            }
        } catch (ex : Exception) {
            ex.printStackTrace()
        }
        return result.toString()
    }
}
