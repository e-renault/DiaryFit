package ca.uqac.diaryfit.ui.datas.exercices

abstract class Exercice{
    abstract fun getTitle() : String

    abstract fun getDescription() : String

    abstract fun hasTool() : Boolean

    var isDone:Boolean = false
}