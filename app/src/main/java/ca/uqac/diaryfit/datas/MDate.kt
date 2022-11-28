package ca.uqac.diaryfit.datas

import java.text.Format
import java.text.SimpleDateFormat
import java.util.*

class MDate() {
    private var date:Date = Date()

    constructor(_date:Date) : this() {
        date = _date
    }

    constructor(year:Int, month:Int, dayOfMonth:Int) : this() {
        val cal = Calendar.getInstance()
        cal[Calendar.YEAR] = year
        cal[Calendar.MONTH] = month
        cal[Calendar.DAY_OF_MONTH] = dayOfMonth
        date = cal.time
    }

    override fun toString(): String {
        return run {
            val formatDate: Format = SimpleDateFormat("dd/MM/yyyy")
            val ret = formatDate.format(date)
            ret
        }

    }

    companion object{
        fun getTodayDate(): MDate {
            return MDate(Date())
        }
    }
}