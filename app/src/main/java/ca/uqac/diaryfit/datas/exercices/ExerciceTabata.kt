package ca.uqac.diaryfit.datas.exercices

import android.content.res.Resources
import android.os.Parcel
import android.os.Parcelable
import ca.uqac.diaryfit.MainActivity
import ca.uqac.diaryfit.R
import ca.uqac.diaryfit.UserDB
import ca.uqac.diaryfit.datas.MTime
import com.google.gson.Gson

class ExerciceTabata() : Exercice() {
    var otherExerciceList:List<Int> = ArrayList<Int>()

    var nbCycle:Int = 1

    var rest: MTime = MTime(0)

    var effortTime: MTime = MTime(0)

    constructor(_otherExerciceList:List<Int>,
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
        val temp2:List<Int> = ArrayList<Int>()
        `in`.readList(temp2, Int::class.java.classLoader)
        if (temp2.size != 0) { otherExerciceList = temp2}
        nbCycle = `in`.readInt()
        val temp0 = `in`.readParcelable<MTime>(MTime::class.java.classLoader)
        if (temp0 != null) { rest = temp0 }
        val temp1 = `in`.readParcelable<MTime>(MTime::class.java.classLoader)
        if (temp1 != null) { effortTime = temp1 }
    }


    override fun titleGet(): String {
        return "Tabata"
    }

    override fun descriptionGet(): String {
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
            ret+= "${UserDB.getExerciceName(MainActivity.profil, i)} "
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
        out.writeList(otherExerciceList)
        out.writeInt(nbCycle)
        out.writeParcelable(rest, flags)
        out.writeParcelable(effortTime, flags)
    }
}