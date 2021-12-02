package course.kotlin.spring.extensions

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import sun.jvm.hotspot.oops.DataLayout
import java.time.LocalDateTime
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField
import java.util.*
import kotlin.reflect.KCallable
import kotlin.reflect.full.memberProperties

fun LocalDateTime.format() = this.format(englishDateFormatter)

private val daysLookup = (1..31).associate { it.toLong() to getOrdinal(it) }

private fun getOrdinal(n: Int) = when {
    n in 11..13 -> "${n}th"
    n % 10 == 1 -> "${n}st"
    n % 10 == 2 -> "${n}nd"
    n % 10 == 3 -> "${n}rd"
    else -> "${n}th"
}

private val englishDateFormatter = DateTimeFormatterBuilder()
    .appendPattern("yyyy-MM-dd")
    .appendLiteral(" ")
    .appendText(ChronoField.DAY_OF_MONTH, daysLookup)
    .appendLiteral(" ")
    .appendPattern("yyyy")
    .toFormatter(Locale.ENGLISH)

fun String.toSlug() = lowercase()
    .replace("\n", " ")
    .replace("[^a-z\\d\\s]".toRegex(), " ")
    .split(" ")
    .joinToString("-")
    .replace("-+".toRegex(), "-")

// Logging
inline fun <reified T> T.log(): Logger {
    return LoggerFactory.getLogger(T::class.java)
}

inline fun <reified T: Any, M, reified C : KCallable<M>> T.toModel(constructor: C) = with(constructor) {
    val propertiesByName = T::class.memberProperties.associateBy { it.name }
    callBy(parameters.associate { parameter -> parameter to
            propertiesByName[parameter.name]?.get(this@toModel) })
}

