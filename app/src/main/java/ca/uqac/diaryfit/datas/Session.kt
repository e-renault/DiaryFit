package ca.uqac.diaryfit.datas

import android.os.Parcel
import android.os.Parcelable
import ca.uqac.diaryfit.datas.exercices.Exercice
import com.google.gson.Gson


class Session () : Parcelable {
    private var exerciceList = ArrayList<Object>()
    fun setExerciceList(list: ArrayList<Object>) { exerciceList = list}
    fun getExerciceList(): ArrayList<Object> { return exerciceList }

    fun exerciceListSet(list: ArrayList<Exercice>) { exerciceList = list as ArrayList<Object>}
    fun exerciceListGet(): ArrayList<Exercice> { return exerciceList as ArrayList<Exercice>}

    private var timeDate:String = ""
    fun settimeDate(_timeDate:String) {timeDate = _timeDate}
    fun gettimeDate() : String {return timeDate}

    private var name:String = "Default Name"
    fun getname(): String { return name }
    fun setname(_name: String) { name = _name }


    constructor(_name:String, _exerciceList:ArrayList<Exercice>, _timeDate:String) : this() {
        name = _name
        exerciceList = _exerciceList as ArrayList<Object>
        timeDate = _timeDate
    }

    private constructor(`in`: Parcel) : this() {
        name = `in`.readString().toString()
        timeDate = `in`.readString().toString()
        val temp = `in`.readString().toString()
        exerciceList = Gson().fromJson(`in`.readString(), kotlin.Any::class.java) as ArrayList<Object>
    }

    fun titleGet() = name

    fun exGet(index:Int) = exerciceList.get(index) as Exercice

    fun exAdd(ex: Exercice) { exerciceList.add(ex as Object) }

    fun exSet(index:Int, ex: Exercice) = exerciceList.set(index, ex as Object)

    fun size() = exerciceList.size

    fun descriptionGet() = "${exerciceList.size} exercices, ${timeDate}"

    companion object CREATOR: Parcelable.Creator<Session?> {
        override fun createFromParcel(`in`: Parcel): Session? {
            return Session(`in`)
        }

        override fun newArray(size: Int): Array<Session?> {
            return arrayOfNulls(size)
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(out: Parcel, flags: Int) {
        out.writeString(name)
        out.writeString(timeDate)

        out.writeString(Gson().toJson(exerciceList.toTypedArray()))
    }
}