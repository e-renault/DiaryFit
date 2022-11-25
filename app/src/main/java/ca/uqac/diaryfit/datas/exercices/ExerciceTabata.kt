package ca.uqac.diaryfit.datas.exercices

import android.os.Parcel
import android.os.Parcelable
import ca.uqac.diaryfit.UserDB
import ca.uqac.diaryfit.datas.MTime
import com.google.gson.Gson

class ExerciceTabata() : Exercice() {
    var otherExerciceList:IntArray = intArrayOf()
    fun getotherExerciceList(): IntArray { return otherExerciceList }
    fun setotherExerciceList(_otherExerciceList: IntArray)  { otherExerciceList = _otherExerciceList }

    var nbCycle:Int = 1
    fun getnbCycle(): Int { return nbCycle }
    fun setnbCycle(_nbCycle: Int)  { nbCycle = _nbCycle }

    var rest: MTime = MTime(0)
    fun getrest(): MTime { return rest }
    fun setrest(_rest: MTime)  { rest = _rest }

    var effortTime: MTime = MTime(0)
    fun geteffortTime(): MTime { return effortTime }
    fun seteffortTime(_effortTime: MTime)  { effortTime = _effortTime }

    constructor(_otherExerciceList:IntArray,
                _nbCycle:Int,
                _rest: MTime,
                _effortTime: MTime
    ) :this() {
        otherExerciceList = _otherExerciceList
        nbCycle = _nbCycle
        rest = _rest
        effortTime = _effortTime
                }

    private constructor(`in`: Parcel) : this() {
        val temp2 = IntArray(0)
        `in`.readIntArray(temp2)
        if (temp2.size != 0) { otherExerciceList = temp2 }
        nbCycle = `in`.readInt()
        val temp0 = `in`.readParcelable<MTime>(MTime::class.java.classLoader)
        if (temp0 != null) { rest = temp0 }
        val temp1 = `in`.readParcelable<MTime>(MTime::class.java.classLoader)
        if (temp1 != null) { effortTime = temp1 }
    }


    override fun getTitle(): String {
        return "Tabata"
    }

    override fun getDescription(): String {
        var ret = ""
        if (nbCycle != 0)
            ret += "${nbCycle}x"
        if (effortTime.timeInSec != 0)
            ret += "${effortTime}"
        if (effortTime.timeInSec != 0)
            ret += ", rest:$rest"
        ret += "\n${printExerciceList()}"

        return ret
    }

    fun printExerciceList() :String {
        var ret:String = ""
        for (i in otherExerciceList) {
            ret+= "${UserDB.getExerciceName(i)} "
        }
        return ret
    }

    override fun hasTool(): Boolean {
        return true
    }

    override fun deepCopy(): ExerciceTime {
        val JSON = Gson().toJson(this)
        return Gson().fromJson(JSON, ExerciceTime::class.java)
    }

    companion object CREATOR: Parcelable.Creator<ExerciceTabata?> {
        override fun createFromParcel(`in`: Parcel): ExerciceTabata? {
            return ExerciceTabata(`in`)
        }

        override fun newArray(size: Int): Array<ExerciceTabata?> {
            return arrayOfNulls(size)
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(out: Parcel, flags: Int) {
        out.writeIntArray(otherExerciceList)
        out.writeInt(nbCycle)
        out.writeParcelable(rest, flags)
        out.writeParcelable(effortTime, flags)
    }
}