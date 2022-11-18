package ca.uqac.diaryfit.ui.datas.exercices

import ca.uqac.diaryfit.ui.datas.MDatabase

abstract class ExerciceType(ExerciceNameID:Int) : Exercice(ExerciceNameID){

    override fun getTitle(): String {
        return generateTitle(MDatabase.db.ExerciceNameList.get(ExerciceNameID))
    }

    abstract fun generateTitle(name:String): String
}