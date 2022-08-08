package com.task.trueapp.utils

sealed class WebContentParseType {
    object TextOnly : WebContentParseType() // Without HTML/JS Tags
    object HtmlContent : WebContentParseType() // With HTML/JS tags
}
