package com.example.cloneapp.utils

import android.content.Context
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

object Utils {

    fun formatNumbersAsCode(s: CharSequence): String {
        val df = DecimalFormat("#,###", DecimalFormatSymbols(Locale.US))
        val s1 = when (s.length) {
            4 -> {
                String.format(
                    "%s-%s",
                    s.subSequence(0, 1),
                    s.subSequence(1, 4)
                )
            }
            7 -> String.format(
                "%s-%s",
                s.subSequence(0, 4),
                s.subSequence(4, 7)
            )
            10 -> String.format(
                "%s-%s-%s",
                s.subSequence(0, 4),
                s.subSequence(4, 7),
                s.subSequence(7, 10)
            )
            13 -> String.format(
                "%s-%s-%s-%s",
                s.subSequence(0, 4),
                s.subSequence(4, 7),
                s.subSequence(7, 10),
                s.subSequence(10, 13)
            )
            else -> df.format(s.toString().trim().toLong()).replace(".", "-").replace(",", "-")
        }

        return s1
    }
}




