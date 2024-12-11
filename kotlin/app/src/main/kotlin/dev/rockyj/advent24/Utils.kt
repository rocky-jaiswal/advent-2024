package dev.rockyj.advent24

import kotlinx.coroutines.*
import java.io.File
import java.time.Instant
import java.util.concurrent.Executors

val Dispatchers.Virtual: CoroutineDispatcher
    get() = Executors.newVirtualThreadPerTaskExecutor().asCoroutineDispatcher()

fun fileToArr(filePath: String): List<String> {
    val res = ClassLoader.getSystemClassLoader().getResource(filePath)
    return File(res.file).readLines()
}

suspend fun <T, R> Iterable<T>.mapParallel(transform: (T) -> R): List<R> = coroutineScope {
    map { async(Dispatchers.Virtual) { transform(it) } }.map { it.await() }
}

fun timed(function: () -> Unit) {
    val start = Instant.now().toEpochMilli()
    function()
    println("This took - ${Instant.now().toEpochMilli() - start} millisecs")
}