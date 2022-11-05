package ca.uqac.diaryfit.ui.datas

class MTime(second:Int, minute:Int = 0, hour:Int = 0) {
    var timeInSec = second + minute*60 + hour*60*60

    override fun toString(): String {
        if (timeInSec <= 60) return "$timeInSec'"
        if (timeInSec/60 <= 60) return "${getMinute()}'' ${getSeconds()}'"
        return "${getHour()}h ${getMinute()}m ${getSeconds()}s"
    }

    fun getSeconds(): Int = timeInSec % 60

    fun getMinute(): Int = (timeInSec - getSeconds()) / 60 % 60

    fun getHour(): Int = (timeInSec - getSeconds()) / 60 - getMinute() / 60
}