package ca.uqac.diaryfit.datas

import android.os.Parcel
import android.os.Parcelable

class MTime() : Parcelable {
    var timeInSec:Int = 0
    //fun gettimeInSec(): Int { return timeInSec }
    //fun settimeInSec(_timeInSec: Int)  { timeInSec = _timeInSec }

    constructor(_timeInSec:Int):this(){
        timeInSec = _timeInSec
    }

    constructor(_millis:Long) : this() {
        timeInSec = (_millis /1000).toInt()
    }
    constructor(second:Int, minute:Int = 0, hour:Int = 0) : this() {
        timeInSec = second + minute*60 + hour*60*60
    }

    private constructor(`in`: Parcel) : this() {
        timeInSec = `in`.readInt()
    }

    override fun toString(): String {
        if (timeInSec <= 60) return "$timeInSec'"
        if (timeInSec/60 < 60) return "${minuteGet()}''${secondsGet()}'"
        return "${hourGet()}h ${minuteGet()}m ${secondsGet()}s"
    }

    fun secondsGet(): Int = timeInSec % 60

    fun minuteGet(): Int = (timeInSec - secondsGet()) / 60 % 60

    fun hourGet(): Int = ((timeInSec - secondsGet()) / 60 - minuteGet()) / 60

    fun millisGet(): Long = timeInSec.toLong() * 1000

    companion object CREATOR: Parcelable.Creator<MTime?> {
        override fun createFromParcel(`in`: Parcel): MTime? {
            return MTime(`in`)
        }

        override fun newArray(size: Int): Array<MTime?> {
            return arrayOfNulls(size)
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(out: Parcel, flags: Int) {
        out.writeInt(timeInSec)
    }
}
