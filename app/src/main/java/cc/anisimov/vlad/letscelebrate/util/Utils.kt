package cc.anisimov.vlad.letscelebrate.util

import cc.anisimov.vlad.letscelebrate.domain.model.Age
import java.util.*
import java.util.Calendar.DAY_OF_MONTH
import java.util.Calendar.MONTH


object DateUtils {
    fun getDate(day: Int, month: Int, year: Int): Date? {
        val calendar: Calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        return calendar.time
    }

    fun getAge(date: Date): Age {
        val birthDate = Calendar.getInstance()
        val todayDate = Calendar.getInstance()
        birthDate.time = date
        var years = todayDate[Calendar.YEAR] - birthDate[Calendar.YEAR]
        var months = if (todayDate[MONTH] >= birthDate[MONTH]) {
            todayDate[MONTH] - birthDate[MONTH]
        } else {
            todayDate[MONTH] - birthDate[MONTH] + 12
            years--
        }
        if (todayDate[DAY_OF_MONTH] < birthDate[DAY_OF_MONTH]) {
            if (months == 0) {
                years--
                months = 11
            } else {
                months--
            }
        }
        return Age(years,months)
    }


}