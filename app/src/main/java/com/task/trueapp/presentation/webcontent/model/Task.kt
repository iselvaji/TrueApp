package com.task.trueapp.presentation.webcontent.model

import com.task.trueapp.utils.Resource
import kotlinx.coroutines.Deferred

data class Task(
    val id: Int = -1,
    val titleId: Int = -1,
    val data: Any? = null,
    val isLoading: Boolean = false,
    val error:Any? = null,
    val asyncJob: Deferred<Resource<Any?>>
)