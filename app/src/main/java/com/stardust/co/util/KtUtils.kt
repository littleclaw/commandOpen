package com.pengxh.autodingding.utils

import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.stardust.co.CoApplication
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext



@DelicateCoroutinesApi
fun launchWithExpHandler(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
) = GlobalScope.launch(context + ExceptionHandler, start, block)


val ExceptionHandler by lazy {
    CoroutineExceptionHandler { _, throwable ->
        toast(throwable.message ?: "$throwable")
        throwable.printStackTrace()
    }
}

val mainHandler by lazy {
    Handler(Looper.getMainLooper())
}

fun runOnUi(block: () -> Unit) {
    if (Looper.getMainLooper() == Looper.myLooper()) {
        block()
    } else {
        mainHandler.post(block)
    }
}

fun postDelayed(delay: Long,block: () -> Unit){
    mainHandler.postDelayed(block, delay)
}


fun toast(m: String) =
    runOnUi {
        Toast.makeText(CoApplication.instance, m, Toast.LENGTH_SHORT).show()
    }
