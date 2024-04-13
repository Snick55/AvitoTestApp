package com.snick55.avitotestapp.core

import javax.inject.Inject

interface DateFormatter {

    fun format(time: String): String

    class DateFormatterImpl @Inject constructor() : DateFormatter {

        override fun format(time: String): String {
            return time.substring(0,10)
        }
    }

}