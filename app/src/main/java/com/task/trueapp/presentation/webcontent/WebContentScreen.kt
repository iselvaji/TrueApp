package com.task.trueapp.presentation.webcontent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.task.trueapp.R
import com.task.trueapp.utils.Constants
import com.task.trueapp.utils.NetworkUtil
import com.task.trueapp.utils.WebContentParseType

/**
 * Web content screen contains the UI to display web page parsed results
 *
 * @param navigator for navigation between screens
 * @param viewModel
 */

@Composable
@Destination(start = true)
fun WebContentScreen(
    navigator: DestinationsNavigator,
    viewModel: WebContentViewModel = hiltViewModel()) {

    val state = viewModel.taskState
    val isShowAlert = remember { mutableStateOf(false) }
    val contentType = remember { mutableStateOf<WebContentParseType>(WebContentParseType.TextOnly) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(stringResource(R.string.app_name))})
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {  isShowAlert.value = true },
                content = {
                    Icon(Icons.Filled.Settings, stringResource(R.string.settings))
                }
            )
        },
        content =  {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background)
                    .padding(16.dp)
            ) {
                Button(
                    onClick = {
                        viewModel.onEvent(WebContentEvent.OnGetDataButtonClick(contentType.value))
                    }) {
                    Text(text =  stringResource(R.string.fetch))
                }

                Spacer(modifier = Modifier.width(10.dp))

                if(state.isNotEmpty()) {

                    Column(modifier = Modifier.fillMaxSize()) {
                        state.forEach { task ->

                            if (task.isLoading) {
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalArrangement = Arrangement.Top,
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                ) {
                                    Text(text = stringResource(R.string.loading),
                                        Modifier.testTag(Constants.TEST_TAG_LOADING.plus(task.id)))
                                    Spacer(modifier = Modifier.height(10.dp))
                                    CircularProgressIndicator(Modifier.testTag(Constants.TEST_TAG_PROGRESS.plus(task.id)))
                                }
                            } else if(task.error != null) {
                                var errorMsg = stringResource(task.error as Int)
                                if(! NetworkUtil.isInternetAvailable(LocalContext.current)) {
                                    errorMsg = stringResource(R.string.err_connectivity)
                                }
                                Text(text = errorMsg, color = MaterialTheme.colors.error)
                            }
                            else {
                                var columnWeight = 0.3f
                                if(task.id == 0)
                                    columnWeight = 0.1f

                                Spacer(modifier = Modifier.height(10.dp))
                                Text(text = getTaskTitleToDisplay(task.id))
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(columnWeight)
                                        .verticalScroll(rememberScrollState())
                                        .padding(20.dp),

                                    ) {
                                    Text(text = task.data.toString())
                                }
                            }
                        }

                    }
                }

                if (isShowAlert.value) {
                    AlertSingleChoiceView(state = isShowAlert, contentType = contentType)
                }
            }
        }
    )
}

@Composable
fun getTaskTitleToDisplay(id: Int): String {
    return when(id) {
        0 ->
            stringResource(R.string.nth_Char, Constants.CHAR_POSITION_TO_FIND)
        1 ->
            stringResource(R.string.every_nthChar, Constants.CHAR_POSITION_TO_FIND)
        2 ->
            stringResource(R.string.words_count)
        else -> {
            ""
        }
    }
}

/**
 * AlertDialog with single choice [radio buttons]
 *
 * @param state
 * @param contentType
 */
@Composable
fun AlertSingleChoiceView(state: MutableState<Boolean>, contentType : MutableState<WebContentParseType>) {
    CommonDialog(title =  stringResource(R.string.dialog_title), state = state) {
        SingleChoiceAlert(contentType)
    }
}

@Composable
fun CommonDialog(
    title: String?,
    state: MutableState<Boolean>,
    content: @Composable (() -> Unit)? = null
) {
    AlertDialog(
        onDismissRequest = {
            state.value = false
        },
        title = title?.let {
            {
                Column(
                    Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = title)
                    Divider(modifier = Modifier.padding(bottom = 8.dp))
                }
            }
        },
        text = content,
        dismissButton = {
            Button(onClick = { state.value = false }) {
                Text(stringResource(R.string.cancel))
            }
        },
        confirmButton = {
            Button(onClick = {
                state.value = false
            }) {
                Text(stringResource(R.string.ok))
            }
        }, modifier = Modifier.padding(vertical = 8.dp)
    )
}

/**
 * Single choice [radio button] alert with radio options and ok/cancel buttons
 *
 * @param contentType
 */
@Composable
fun SingleChoiceAlert(contentType : MutableState<WebContentParseType>) {
    val radioOptions = listOf(stringResource(R.string.option_text_only), stringResource(R.string.option_html))
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }

    Column(
        Modifier.fillMaxWidth(),
    ) {
        radioOptions.forEach { text ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (text == selectedOption),
                        onClick = {
                            contentType.value = if (text == radioOptions[0]) {
                                WebContentParseType.TextOnly
                            } else WebContentParseType.HtmlContent
                            onOptionSelected(text)
                        }
                    )
                    .padding(vertical = 8.dp)
            ) {
                RadioButton(
                    selected = contentType.value == WebContentParseType.TextOnly && text == radioOptions[0]
                            || contentType.value == WebContentParseType.HtmlContent && text == radioOptions[1],
                    onClick = {
                        onOptionSelected(text)
                    }
                )
                Text(
                    text = text,
                    modifier = Modifier.padding(top = 10.dp, start = 10.dp)
                )
            }
        }
    }
}