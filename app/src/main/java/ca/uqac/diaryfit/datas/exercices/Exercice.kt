package ca.uqac.diaryfit.datas.exercices

import android.os.Parcelable

abstract class Exercice: Parcelable{
    var isDone:Boolean = false

    abstract fun titleGet() : String

    abstract fun descriptionGet() : String

    abstract fun hasTool() : Boolean

    abstract fun deepCopy(): Exercice
}