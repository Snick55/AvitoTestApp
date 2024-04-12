package com.snick55.avitotestapp.core

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

interface DateFormater {

    fun format(time: String): String

    class DateFormaterImpl @Inject constructor() : DateFormater {

        override fun format(time: String): String {
            return time.substring(0,10)
        }
    }

}