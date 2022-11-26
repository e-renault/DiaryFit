package ca.uqac.diaryfit.datas

import android.os.Parcel
import android.os.Parcelable
import ca.uqac.diaryfit.datas.exercices.Exercice
import java.util.Objects

class Session () : Parcelable {
    private var exerciceList = ArrayList<Exercice>()
    fun setExerciceList(list: ArrayList<Object>) { exerciceList = list as ArrayList<Exercice>}
    fun getExerciceList(): ArrayList<Object> { return exerciceList as ArrayList<Object> }

    fun setExerciceList2(list: ArrayList<Exercice>) { exerciceList = list}
    fun getExerciceList2(): ArrayList<Exercice> { return exerciceList }

    private var timeDate:String = ""
    fun setTimeDate(_timeDate:String) {timeDate = _timeDate}
    fun getTimeDate() : String {return timeDate}

    private var name:String = "Default Name"
    fun getName(): String { return name }
    fun setName(_name: String) { name = _name }


    constructor(_name:String, _exerciceList:ArrayList<Exercice>) : this() {
        name = _name
        exerciceList = _exerciceList
    }

    private constructor(`in`: Parcel) : this() {
        name = `in`.readString().toString()
        timeDate = `in`.readString().toString()
        val temp = `in`.readParcelableArray(Exercice::class.java.classLoader)
        if (temp != null) {
            for (i in 0..temp.size) {
                exerciceList.add(temp.get(i) as Exercice)
            }
        }
    }

    fun getTitle() : String {
        return name
    }

    fun get(index:Int): Exercice {
        return exerciceList.get(index)
    }

    fun add(ex: Exercice) {
        exerciceList.add(ex)
    }

    fun set(index:Int, ex: Exercice): Exercice {
        return exerciceList.set(index, ex)
    }

    fun size(): Int {
        return exerciceList.size
    }

    fun getDescription() : String {
        //TODO generate description
        return "description TODO"
    }

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
        out.writeParcelableArray(exerciceList.toTypedArray(), flags)
    }
}