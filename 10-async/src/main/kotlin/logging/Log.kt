package logging

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ThreadContextElement
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.coroutineContext
import org.slf4j.LoggerFactory
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

data class Log(
    val name: String
) : ThreadContextElement<String>, AbstractCoroutineContextElement(Log) {
    companion object Key : CoroutineContext.Key<Log>

    override fun toString(): String = name
    override fun updateThreadContext(context: CoroutineContext): String {
        val logName = context[Log]?.name ?: ""
        return logName
    }
    override fun restoreThreadContext(context: CoroutineContext, oldState: String) {
    }
}

suspend fun CoroutineScope.log(s: String) {
    LoggerFactory.getLogger(this.coroutineContext.get(Log)?.toString()?:"").info(s)
}
suspend fun <T> FlowCollector<in T>.log(s: String) {
    LoggerFactory.getLogger(currentCoroutineContext().get(Log)?.toString()?:"").info(s)
}
