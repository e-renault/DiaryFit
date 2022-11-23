package ca.uqac.diaryfit.datas

import android.os.Parcel
import android.os.Parcelable
import ca.uqac.diaryfit.datas.exercices.Exercice

class Session () : Parcelable {
    private var exerciceList = ArrayList<Exercice>()
    var name:String = "Default Name"

    constructor(_name:String, _exerciceList:ArrayList<Exercice>) : this() {
        name = _name
        exerciceList = _exerciceList
    }

    private constructor(`in`: Parcel) : this() {
        name = `in`.readString().toString()
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

    fun getExerciceList(): ArrayList<Exercice> {
        return exerciceList
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
        out.writeParcelableArray(exerciceList.toTypedArray(), flags)
    }
}