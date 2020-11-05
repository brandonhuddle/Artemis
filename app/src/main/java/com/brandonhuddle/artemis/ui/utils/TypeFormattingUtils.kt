package com.brandonhuddle.artemis.ui.utils

import java.text.DecimalFormat
import java.util.*
import kotlin.math.*

fun formatNumericalCount(count: Int): String {
    val decimalFormat = DecimalFormat("#.#")
    val sign = count < 0
    var readableCount = abs(count).toFloat()
    var iteration = 0

    while (readableCount >= 1000) {
        readableCount /= 1000
        iteration++
    }

    if (sign) {
        readableCount = -readableCount
    }

    // TODO: We need to localize this. Both in making sure we use `,` instead of `.` in some
    //       scenarios and localizing the `t`, `b`, `m`, and `k`. I know that Chinese and Japanese
    //       favor 10k as its own classification in some scenarios so we may also have to account
    //       for that specifically. This is currently very IndoEuro-centric.
    return when (iteration) {
        4 -> decimalFormat.format(readableCount) + "T"
        3 -> decimalFormat.format(readableCount) + "B"
        2 -> decimalFormat.format(readableCount) + "M"
        1 -> decimalFormat.format(readableCount) + "k"
        else -> count.toString()
    }
}

/**
 * Format a `Date` into a relative time string such as:
 *  - 30s
 *  - 12m
 *  - 5h
 *  - 3w
 *  - 4mo
 *  - 6.3y
 */
fun formatRelativeTimeShort(date: Date): String {
    val diff = Calendar.getInstance().time.time - date.time

    val seconds = diff / 1000
    val minutes = seconds / 60
    val hours = minutes / 60
    val days = hours / 24
    val months = days / 7 / 4
    val years = days.toFloat() / 365

    // TODO: Same as the above function. We need to localize this.
    return when {
        years >= 1 -> {
            val decimalFormat = DecimalFormat("#.#")
            decimalFormat.format(years) + "y"
        }
        months >= 1 -> {
            months.toString() + "mo"
        }
        days >= 1 -> {
            days.toString() + "d"
        }
        hours >= 1 -> {
            hours.toString() + "h"
        }
        minutes >= 1 -> {
            minutes.toString() + "m"
        }
        else -> {
            seconds.toString() + "s"
        }
    }
}