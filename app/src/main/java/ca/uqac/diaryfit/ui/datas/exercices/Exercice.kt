package ca.uqac.diaryfit.ui.datas.exercices

abstract class Exercice(var ExerciceNameID:Int){
    abstract fun getTitle() : String

    abstract fun getDescription() : String

    abstract fun hasTool() : Boolean

    var isDone:Boolean = false
}