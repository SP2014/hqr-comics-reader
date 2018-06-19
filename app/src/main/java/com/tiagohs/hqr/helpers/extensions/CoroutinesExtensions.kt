package com.tiagohs.hqr.helpers.extensions

import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.CoroutineStart
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch


fun launchUI(block: suspend CoroutineScope.() -> Unit): Job =
        launch(UI, CoroutineStart.DEFAULT, null, null, block)

fun launchNow(block: suspend CoroutineScope.() -> Unit): Job =
        launch(UI, CoroutineStart.UNDISPATCHED, null, null, block)