package com.srp4581.mealplannerapp

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

object TimeUtils {
    /** Formats the number of milliseconds since epoch into a human-readable display */
    fun format(epochMillis: Long) =
        if (epochMillis >= 0) {
            val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
            formatter.format(
                LocalDateTime.ofInstant(Instant.ofEpochMilli(epochMillis), ZoneId.systemDefault())
            )
        } else {
            "Not set"
        }

    /**
     * Parses a date in human-readable format (as set by setupDateTimePicker) into number of
     * milliseconds since epoch
     */
    fun parse(dateString: String) =
        try {
            val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
            LocalDateTime.parse(dateString, formatter)
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli()
        } catch (e: DateTimeParseException) {
            -1L
        }
}