package ru.elnorte.tinkoffeduapp.utils

import android.util.Log

/**
 * Log function.
 * Log is accessible in logcat, with "ellog" filter
 * Must be deleted once app is ready
 *
 * @param T type parameter
 * @param item the message that will be written to log
 */
fun <T> ellog(item: T) {
    Log.d("ellog", item.toString())
}
