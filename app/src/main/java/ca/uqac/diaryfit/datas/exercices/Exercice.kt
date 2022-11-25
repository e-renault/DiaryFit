package ca.uqac.diaryfit.datas.exercices

import android.os.Parcelable

abstract class Exercice: Parcelable{
    var isDone:Boolean = false
    fun getisDone(): Boolean { return isDone }
    fun setisDone(_isDone: Boolean)  { isDone = _isDone }

    abstract fun getTitle() : String

    abstract fun getDescription() : String

    abstract fun hasTool() : Boolean

    abstract fun deepCopy(): Exercice
}