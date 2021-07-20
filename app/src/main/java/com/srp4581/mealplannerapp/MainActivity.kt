package com.srp4581.mealplannerapp

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import kotlinx.android.synthetic.main.activity_main.datePickerButton
import kotlinx.android.synthetic.main.activity_main.savedDateText
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.ChronoField

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Shows the saved date in the textView
        showSavedDate()

        // A button that launches a calendar picker. The picked time is saved.
        datePickerButton.setOnClickListener {
            val savedDate = getSavedDate() ?: "No saved date"
            showSaveDateDialog(savedDate)
        }
    }

    private fun showSavedDate() {
        savedDateText.text = getSavedDate()
    }

    private fun showSaveDateDialog(savedDate: String) {
        val savedEpochMillis = TimeUtils.parse(savedDate)
        var dateTime =
            if (savedEpochMillis == -1L) {
                LocalDateTime.now()
            } else {
                LocalDateTime.ofInstant(Instant.ofEpochMilli(savedEpochMillis), ZoneId.systemDefault())
            }

        val datePicker =
            DatePickerDialog(
                this,
                { _: DatePicker, year: Int, month: Int, day: Int ->
                    dateTime =
                        dateTime
                            .with(ChronoField.YEAR, year.toLong())
                            .with(ChronoField.MONTH_OF_YEAR, month.toLong())
                            .with(ChronoField.DAY_OF_MONTH, day.toLong())

                    updateSavedDate(TimeUtils.format(dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()))
                    showSavedDate()
                },
                dateTime.get(ChronoField.YEAR),
                dateTime.get(ChronoField.MONTH_OF_YEAR),
                dateTime.get(ChronoField.DAY_OF_MONTH)
            )

        datePicker.show()
    }

    private fun getSavedDate() = StorageUtils.getString(this, SAVED_DATE_KEY, "No date saved")

    private fun updateSavedDate(date: String) {
        StorageUtils.storeString(this, SAVED_DATE_KEY, date)
    }

    companion object {
        private val SAVED_DATE_KEY = "my_saved_date"
    }
}