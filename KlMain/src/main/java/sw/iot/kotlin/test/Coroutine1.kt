package sw.iot.kotlin.test

import kotlin.coroutines.experimental.*

/**
 * Created by GoldWatch on 7/11/17.
 */
fun log(msg: String) {
    println(msg)
}

fun main(args: Array<String>) {
    log("before coroutine")
    asyncCalcMd5("test.zip") {
        log("in coroutine. Before suspend.")
        val result: String = suspendCoroutine {
            continuation ->
            log("in suspend block.")
            continuation.resume(calcMd5(continuation.context[FilePath]!!.path))
            log("after resume.")
        }
        log("in coroutine. After suspend. result = $result")
    }
    log("after coroutine")
}

/*
 * context
 */
class FilePath(val path: String): AbstractCoroutineContextElement(FilePath){
    companion object Key : CoroutineContext.Key<FilePath>
}

fun asyncCalcMd5(path: String, block: suspend () -> Unit) {
    val continuation = object : Continuation<Unit> {
        override val context: CoroutineContext
            get() = FilePath(path)

        override fun resume(value: Unit) {
            log("resume: $value")
        }

        override fun resumeWithException(exception: Throwable) {
            log(exception.toString())
        }
    }
    block.startCoroutine(continuation)
}

fun calcMd5(path: String): String{
    log("calc md5 for $path.")
    Thread.sleep(1000)
    return System.currentTimeMillis().toString()
}
