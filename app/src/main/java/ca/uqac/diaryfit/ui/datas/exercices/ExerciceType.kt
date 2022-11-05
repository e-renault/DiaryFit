package ca.uqac.diaryfit.ui.datas.exercices

abstract class ExerciceType(var ExerciceName:String) : Exercice(){

    override fun getTitle(): String {
        return generateTitle(ExerciceName)
    }

    abstract fun generateTitle(name:String): String
}