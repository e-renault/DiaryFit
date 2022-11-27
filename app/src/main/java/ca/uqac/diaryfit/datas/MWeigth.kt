package ca.uqac.diaryfit.datas

import android.os.Parcel
import android.os.Parcelable

class MWeigth() : Parcelable {
    var isKG: Boolean = true
    //fun getisKG(): Boolean { return isKG }
    //fun setisKG(_isKG: Boolean)  { isKG = _isKG }

    private var weigthkg: Float = 0.0F
    fun getWeigthkg(): Float { return weigthkg }
    fun setWeigthkg(_weigthkg: Float)  { weigthkg = _weigthkg }

    constructor(_weight: Float, _iskg:Boolean=true) : this() {
        isKG = _iskg
        val weight = _weight
        weigthkg = if (this.isKG) weight else LBtoKG(weight)
    }

    private constructor(`in`:Parcel) : this() {
        isKG = `in`.readBoolean()
        val weight = `in`.readFloat()
        weigthkg = if (this.isKG) weight else LBtoKG(weight)
    }

    fun getWeightkg() : Float {
        return weigthkg
    }

    fun getWeigthlb() : Float {
        return KGtoLB(weigthkg)
    }

    override fun toString(): String {
        if (isKG) {
            return "%.1fkg".format(getWeightkg())
        } else {
            return "%.1flb".format(getWeigthlb())
        }
    }

    companion object CREATOR: Parcelable.Creator<MWeigth?> {
        val kglb:Float = 2.20462F
        fun KGtoLB(kg: Float) : Float = kg * kglb
        fun LBtoKG(lb: Float) : Float = lb * 1/ kglb
        val unitList:Array<String> = arrayOf("kg", "lb")
        override fun createFromParcel(`in`: Parcel): MWeigth? {
            return MWeigth(`in`)
        }

        override fun newArray(size: Int): Array<MWeigth?> {
            return arrayOfNulls(size)
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(out: Parcel, flags: Int) {
        out.writeFloat(weigthkg)
        out.writeBoolean(isKG)
    }

}
