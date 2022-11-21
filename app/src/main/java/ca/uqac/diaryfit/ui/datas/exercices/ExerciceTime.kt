package ca.uqac.diaryfit.ui.datas.exercices

import android.os.Parcel
import android.os.Parcelable
import ca.uqac.diaryfit.ui.datas.MDatabase
import ca.uqac.diaryfit.ui.datas.MTime
import ca.uqac.diaryfit.ui.datas.MWeigth
import com.google.gson.Gson

class ExerciceTime() : Exercice(){
    var ExerciceNameID:Int = -1
    var nbSerie:Int = 1
    var effortTime: MTime = MTime(0)
    var weigth: MWeigth = MWeigth(0.0F)
    var rest: MTime = MTime(0)

    constructor(
        _ExerciceNameID:Int,
        _nbSerie:Int,
        _effortTime: MTime,
        _weigth: MWeigth,
        _rest: MTime) :this() {
            ExerciceNameID = _ExerciceNameID
            nbSerie = _nbSerie
            effortTime =_effortTime
            weigth = _weigth
            rest = _rest
        }

    private constructor(`in`: Parcel) : this() {
        ExerciceNameID = `in`.readInt()
        nbSerie = `in`.readInt()
        val temp2 = `in`.readParcelable<MTime>(MWeigth::class.java.classLoader)
        if (temp2 != null) { effortTime = temp2 }
        val temp0 = `in`.readParcelable<MWeigth>(MWeigth::class.java.classLoader)
        if (temp0 != null) { weigth = temp0 }
        val temp1 = `in`.readParcelable<MTime>(MTime::class.java.classLoader)
        if (temp1 != null) { rest = temp1 }
    }

    override fun getTitle(): String {
        return MDatabase.getExerciceName(ExerciceNameID)
    }

    override fun getDescription(): String {
        if (weigth.getWeightkg() == 0.0F)
            return "$nbSerie x $effortTime, rest:$rest"
        else
            return "$nbSerie x $effortTime - $weigth, rest:$rest"
    }

    override fun hasTool(): Boolean {
        return true
    }

    override fun deepCopy():ExerciceTime {
        val JSON = Gson().toJson(this)
        return Gson().fromJson(JSON, ExerciceTime::class.java)
    }

    companion object CREATOR: Parcelable.Creator<ExerciceTime?> {
        override fun createFromParcel(`in`: Parcel): ExerciceTime? {
            return ExerciceTime(`in`)
        }

        override fun newArray(size: Int): Array<ExerciceTime?> {
            return arrayOfNulls(size)
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(out: Parcel, flags: Int) {
        out.writeInt(ExerciceNameID)
        out.writeInt(nbSerie)
        out.writeParcelable(effortTime, flags)
        out.writeParcelable(weigth, flags)
        out.writeParcelable(rest, flags)
    }
}