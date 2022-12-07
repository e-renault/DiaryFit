package ca.uqac.diaryfit.datas

import android.os.Parcel
import android.os.Parcelable
import ca.uqac.diaryfit.datas.exercices.Exercice

class Session () : Parcelable {
    private var exerciceList:List<Exercice> = ArrayList()
    fun setExerciceList(list: List<Exercice>) { exerciceList = list}
    fun getExerciceList(): List<Exercice> { return exerciceList }

    fun exerciceListSet(list: ArrayList<Exercice>) { exerciceList = list}
    fun exerciceListGet(): ArrayList<Exercice> = exerciceList as ArrayList<Exercice>

    var timeDate:String = ""

    var name:String = "Default Name"


    constructor(_name:String, _exerciceList:ArrayList<Exercice>, _timeDate:String) : this() {
        name = _name
        exerciceList = _exerciceList
        timeDate = _timeDate
    }

    private constructor(`in`: Parcel) : this() {
        name = `in`.readString().toString()
        timeDate = `in`.readString().toString()
        val temp = `in`.readParcelableArray(Exercice::class.java.classLoader)
        if (temp != null) {
            for (i in 0..temp.size) {
                (exerciceList as ArrayList<Exercice>).add(temp.get(i) as Exercice)
            }
        }
    }

    fun titleGet() = name

    fun exGet(index:Int) = exerciceList.get(index)

    fun exAdd(ex: Exercice) { (exerciceList as ArrayList<Exercice>).add(ex) }

    fun exSet(index:Int, ex: Exercice) = (exerciceList as ArrayList<Exercice>).set(index, ex)

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
        out.writeParcelableArray(exerciceList.toTypedArray(), flags)
    }
}