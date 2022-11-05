package ca.uqac.diaryfit.ui.datas

class MWeigth(var weigthkg:Float, val isKG:Boolean = true) {

    fun getWeightkg() : Float {
        return weigthkg
    }

    fun getWeigthlb() : Float {
        return KGtoLB(weigthkg)
    }

    companion object {
        val kglb:Float = 2.20462F
        fun KGtoLB(kg: Float) : Float = kg * kglb
        fun LBtoKG(lb: Float) : Float = lb * 1/kglb
    }

    override fun toString(): String {
        if (isKG) {
            return getWeightkg().toString() + "kg"
        } else {
            return getWeigthlb().toString() + "lb"
        }
    }
}