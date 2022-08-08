package com.task.trueapp.presentation.webcontent

import com.task.trueapp.utils.WebContentParseType

/**
 * Web content Event sealed class contains the events belongs to WebContentScreen
 *
 */
sealed class WebContentEvent {
    class OnGetDataButtonClick(val contentParserType: WebContentParseType) : WebContentEvent()
}