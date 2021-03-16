package cc.anisimov.vlad.letscelebrate.util

import java.util.*


object DateUtils {
    fun getDate(day: Int, month: Int, year: Int): Date? {
        val calendar: Calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        return calendar.time
    }
}