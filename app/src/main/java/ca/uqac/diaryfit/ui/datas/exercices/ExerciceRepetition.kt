package ca.uqac.diaryfit.ui.datas.exercices

import ca.uqac.diaryfit.ui.datas.MTime
import ca.uqac.diaryfit.ui.datas.MWeigth

class ExerciceRepetition(ExerciceNameID:Int,
                         var nbSerie:Int = 1,
                         var nbRepetition:Int = 1,
                         var weigth: MWeigth = MWeigth(0.0F),
                         var rest: MTime = MTime(0)
) : ExerciceType(ExerciceNameID){

    override fun generateTitle(name: String): String {
        return "$name"
    }

    override fun getDescription(): String {
        return "$nbSerie x $nbRepetition - $weigth, rest:$rest"
    }

    override fun hasTool(): Boolean {
        return true
    }
}