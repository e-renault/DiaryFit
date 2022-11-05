package ca.uqac.diaryfit.ui.datas.exercices

import ca.uqac.diaryfit.ui.datas.MTime

class ExerciceTabata(var exerciceList:ArrayList<String> = ArrayList(),
                     var nbCycle:Int = 1,
                     var rest: MTime = MTime(0),
                     var effortTime: MTime = MTime(0)
) : Exercice() {

    override fun getTitle(): String {
        return "Tabata"
    }

    override fun getDescription(): String {
        return exerciceList.toString()
    }

    override fun hasTool(): Boolean {
        return true
    }

}