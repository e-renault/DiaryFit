package ca.uqac.diaryfit.ui.datas.exercices

import ca.uqac.diaryfit.ui.datas.MDatabase
import ca.uqac.diaryfit.ui.datas.MTime

class ExerciceTabata(mainExerciceID:Int,
                     var nbCycle:Int = 1,
                     var rest: MTime = MTime(0),
                     var effortTime: MTime = MTime(0)
) : Exercice(mainExerciceID) {

    override fun getTitle(): String {
        return "Tabata"
    }

    override fun getDescription(): String {
        var ret:String = MDatabase.db.ExerciceNameList.get(ExerciceNameID)
        return ret
    }

    override fun hasTool(): Boolean {
        return true
    }

}