package com.example.test_utils_android

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

fun <T> LiveData<T>.getOrAwaitValue(
    time: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS,
    afterObserve: ((result: T?) -> Unit)? = null
): T {
    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(o: T) {
            data = o
            latch.countDown()
            this@getOrAwaitValue.removeObserver(this)
        }
    }
    this.observeForever(observer)

    afterObserve?.invoke(data)

    // Don't wait indefinitely if the LiveData is not set.
    if (!latch.await(time, timeUnit)) {
        this.removeObserver(observer)
        throw TimeoutException("LiveData value was never set.")
    }

    @Suppress("UNCHECKED_CAST")
    return data as T
}

fun <T> LiveData<T>.observeNoValue(
    time: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS
): T? {
    var data: T? = null
    val latch = CountDownLatch(1)

    var isCalled = false

    val observer = object : Observer<T> {
        override fun onChanged(o: T) {
            isCalled = true
            data = o
            latch.countDown()
            this@observeNoValue.removeObserver(this)
        }
    }

    this.observeForever(observer)

    if (latch.await(time, timeUnit)) {
        if (isCalled) {
            this.removeObserver(observer)
            throw AssertionError("LiveData value was set, but it was not expected.")
        }
    }

    this.removeObserver(observer)
    return data
}

