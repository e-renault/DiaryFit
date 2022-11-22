package ca.uqac.diaryfit.ui.datas.exercices

import android.os.Parcelable

abstract class Exercice: Parcelable{
    abstract fun getTitle() : String

    abstract fun getDescription() : String

    abstract fun hasTool() : Boolean

    abstract fun deepCopy(): Exercice

    var isDone:Boolean = false
}