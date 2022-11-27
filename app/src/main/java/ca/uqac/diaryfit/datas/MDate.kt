package ca.uqac.diaryfit.datas

import java.text.Format
import java.text.SimpleDateFormat
import java.util.*

class MDate() {
    private var date:Date = Date()

    constructor(_date:Date) : this() {
        date = _date
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