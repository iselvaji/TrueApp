package com.task.trueapp.presentation.webcontent

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.trueapp.R
import com.task.trueapp.domain.repository.usecase.WebContentUseCase
import com.task.trueapp.presentation.webcontent.model.Task
import com.task.trueapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

/**
 * Web content view model fetch the data from usecase based on events triggered from UI layer
 *
 * @property webContentUseCase
 *
 */
@HiltViewModel
class WebContentViewModel
    @Inject constructor(private val webContentUseCase: WebContentUseCase): ViewModel() {

    private val _taskState = mutableStateListOf<Task>()
    val taskState : List<Task> = _taskState

    fun onEvent(event: WebContentEvent) {
        when (event) {

            // fetch data Simultaneously when user clicks on button
            is WebContentEvent.OnGetDataButtonClick -> {

                viewModelScope.launch {
                    coroutineScope {
                        _taskState.clear() // clear previous task if any

                        val parseType = event.contentParserType

                        val nthCharRequest = async(start = CoroutineStart.LAZY) { webContentUseCase.getNthCharFromWebContent(parseType) }
                        val everyNthCharRequest = async { webContentUseCase.getEveryNthCharFromWebContent(parseType) }
                        val wordCountRequest = async { webContentUseCase.getWordsCountFromWebContent(parseType) }

                        val asyncRequests = mutableListOf<Deferred<Resource<Any?>>>()

                        asyncRequests.apply {
                            add(nthCharRequest)
                            add(everyNthCharRequest)
                            add(wordCountRequest)
                        }

                        executeAsyncTaskAndUpdateState(asyncJobs = asyncRequests)
                    }
                }
            }
        }
    }

    private suspend fun executeAsyncTaskAndUpdateState(asyncJobs : List<Deferred<Resource<Any?>>>) {

        var id = 0

        // create a task for each async request
        asyncJobs.forEach{
            val task = Task(id, isLoading = true, asyncJob = it)
            _taskState.add(task)
            id++
        }

        val taskResponsePairs = mutableListOf<Pair<Task, Resource<Any?>>>()

        // get the async response of each request
        _taskState.forEach { task->
            awaitAll(task.asyncJob).forEach {
                taskResponsePairs.add(Pair(task, it))
            }
        }

        // update the ui state with task and it response
        taskResponsePairs.forEach {
            updateTaskState(it.first, it.second)
        }

    }

    private suspend fun updateTaskState(task: Task, response: Resource<Any?>, ) {

        withContext(Dispatchers.IO) {
            when (response) {
                is Resource.Success -> {
                    task.apply {
                        _taskState[id] = taskState[id].copy(titleId = titleId, isLoading = false, data = response.data, error = null)
                    }
                }
                is Resource.Error -> {
                    task.apply {
                        _taskState[id] = taskState[id].copy(titleId = titleId, isLoading = false, error = R.string.err_unable_to_load)
                    }
                }
                is Resource.Loading -> {
                    task.apply {
                        _taskState[id] = taskState[id].copy(titleId = titleId, isLoading = true, error = null)
                    }

                }
            }
        }
    }
}