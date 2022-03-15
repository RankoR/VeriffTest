package com.example.core_tests

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.firstOrNull
import org.junit.Assert.fail

/**
 * This looks like a hack, in a bigger project we should better use some library
 * TODO: Add more docs
 */
suspend inline fun <T> Flow<T>.awaitSingle(block: (value: T) -> Unit) {
    firstOrNull()?.let(block) ?: run {
        fail("Got no values")
    }
}

/**
 * This looks like a hack, in a bigger project we should better use some library
 * TODO: Add more docs
 */
suspend inline fun <reified E : Throwable> Flow<Any>.assertThrows() {
    var isExceptionThrown = false

    catch { t ->
        isExceptionThrown = true

        if (t !is E) {
            fail("Expected exception ${E::class.java.simpleName}, but got ${t::class.java.simpleName}")
        }
    }.collect()

    if (!isExceptionThrown) {
        fail("Expected exception ${E::class.java.simpleName} was not thrown")
    }
}
