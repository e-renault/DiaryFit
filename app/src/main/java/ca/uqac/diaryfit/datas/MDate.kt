package ca.uqac.diaryfit.datas

import java.util.*

class MDate() {
    private var date:Calendar = Calendar.getInstance()

    constructor(_date:Calendar) : this() {
        date = _date
    }

    override fun toString(): String {
        return run {
            val day = date.get(Calendar.DAY_OF_MONTH)
            val month = date.get(Calendar.MONTH)
            val year = date.get(Calendar.YEAR)
            "%02d/%02d/%04d".format(day, month, year)
        }

    }

    companion object{
        fun getTodayDate(): MDate {
            return MDate(Calendar.getInstance())
        }
    }
}