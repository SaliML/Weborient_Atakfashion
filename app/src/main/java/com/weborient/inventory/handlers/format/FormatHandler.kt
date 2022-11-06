package com.weborient.inventory.handlers.format

import java.text.SimpleDateFormat
import java.util.*

object FormatHandler {
    fun dateToCustomFormat(date: Date, pattern: String): String{
        val dateTimeFormat = SimpleDateFormat(pattern)

        return dateTimeFormat.format(date)
    }
}