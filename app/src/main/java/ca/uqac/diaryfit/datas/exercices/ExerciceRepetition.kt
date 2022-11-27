package ca.uqac.diaryfit.datas.exercices

import android.os.Parcel
import android.os.Parcelable
import ca.uqac.diaryfit.MainActivity
import ca.uqac.diaryfit.UserDB
import ca.uqac.diaryfit.datas.MTime
import ca.uqac.diaryfit.datas.MWeigth
import com.google.gson.Gson

class ExerciceRepetition() : Exercice() {
    var exerciceNameID:Int = -1
    //fun getExerciceNameID(): Int { return exerciceNameID }
    //fun setExerciceNameID(_exerciceNameID: Int)  { exerciceNameID = _exerciceNameID }
    
    var nbSerie:Int = 1
    //fun getNbSerie(): Int { return nbSerie }
    //fun setNbSerie(_nbSerie: Int)  { nbSerie = _nbSerie }
    
    var nbRepetition:Int = 1
    //fun getnbRepetition(): Int { return nbRepetition }
    //fun setnbRepetition(_nbRepetition: Int)  { nbRepetition = _nbRepetition }
    
    var weigth: MWeigth = MWeigth(0.0F)
    //fun getweigth(): MWeigth { return weigth }
    //fun setweigth(_weigth: MWeigth)  { weigth = _weigth }
    
    var rest: MTime = MTime(0)
    //fun getrest(): MTime { return rest }
    //fun setrest(_rest: MTime)  { rest = _rest }
    
    constructor(_ExerciceNameID:Int,
                _nbSerie:Int,
                _nbRepetition:Int,
                _weigth: MWeigth,
                _rest: MTime
    ) : this() {
        exerciceNameID = _ExerciceNameID
        nbSerie = _nbSerie
        nbRepetition = _nbRepetition
        weigth = _weigth
        rest = _rest
    }

    private constructor(`in`: Parcel) : this() {
        exerciceNameID = `in`.readInt()
        nbSerie = `in`.readInt()
        nbRepetition = `in`.readInt()
        val temp0 = `in`.readParcelable<MWeigth>(MWeigth::class.java.classLoader)
        if (temp0 != null) { weigth = temp0 }
        val temp1 = `in`.readParcelable<MTime>(MTime::class.java.classLoader)
        if (temp1 != null) { rest = temp1 }
    }

    override fun titleGet(): String {
        return UserDB.getExerciceName(MainActivity.profil, exerciceNameID)
    }

    override fun descriptionGet(): String {
        var ret = ""
        if (nbSerie != 0)
            ret += "${nbSerie}x"
        if (nbRepetition != 0)
            ret += "${nbRepetition}"
        if (weigth.weightkgGet() != 0.0F)
            ret += " - ${weigth}"
        if (rest.timeInSec != 0)
            ret += ", rest:$rest"

        return ret
    }

    override fun hasTool(): Boolean {
        return false
    }

    override fun deepCopy(): ExerciceTime {
        val JSON = Gson().toJson(this)
        return Gson().fromJson(JSON, ExerciceTime::class.java)
    }

    companion object CREATOR: Parcelable.Creator<ExerciceRepetition?> {
        override fun createFromParcel(`in`: Parcel): ExerciceRepetition? {
            return ExerciceRepetition(`in`)
        }

        override fun newArray(size: Int): Array<ExerciceRepetition?> {
            return arrayOfNulls(size)
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(out: Parcel, flags: Int) {
        out.writeInt(exerciceNameID)
        out.writeInt(nbSerie)
        out.writeInt(nbRepetition)
        out.writeParcelable(weigth, flags)
        out.writeParcelable(rest, flags)
    }
}