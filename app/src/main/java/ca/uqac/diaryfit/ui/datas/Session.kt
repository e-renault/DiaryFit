package ca.uqac.diaryfit.ui.datas

import android.os.Parcel
import android.os.Parcelable
import ca.uqac.diaryfit.ui.datas.exercices.Exercice

class Session () : Parcelable {
    var exerciceList = ArrayList<Exercice>()
    var name:String = "Default Name"

    constructor(_name:String) : this() {
        name = _name
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