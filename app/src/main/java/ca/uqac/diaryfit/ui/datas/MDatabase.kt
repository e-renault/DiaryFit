package ca.uqac.diaryfit.ui.datas

import ca.uqac.diaryfit.ui.datas.exercices.ExerciceRepetition
import ca.uqac.diaryfit.ui.datas.exercices.ExerciceTabata
import ca.uqac.diaryfit.ui.datas.exercices.ExerciceTime
import java.util.*
import kotlin.collections.ArrayList

class MDatabase {
    val ExerciceNameList:ArrayList<String> = ArrayList()
    val Sessions:ArrayList<Session> = ArrayList()

    init {
        //TODO use real datas
        ExerciceNameList.add("Burpies")
        ExerciceNameList.add("Push-Up")
        ExerciceNameList.add("Cladding")

        val session1:Session = Session("Monday morning")
        session1.exerciceList.add(ExerciceRepetition(0,   1, 2, MWeigth(3.0F), MTime(4)))
        session1.exerciceList.add(ExerciceTime(1,         5, MTime(6), MWeigth(7.0F), MTime(8)))
        session1.exerciceList.add(ExerciceTabata(1,          9, MTime(10), MTime(11)))
        Sessions.add(session1)

        val session2:Session = Session("Monday evening")
        session2.exerciceList.add(ExerciceRepetition(0,   1, 2, MWeigth(3.0F), MTime(4)))
        session2.exerciceList.add(ExerciceTime(1,         5, MTime(6), MWeigth(7.0F), MTime(8)))
        session2.exerciceList.add(ExerciceTabata(2,          9, MTime(10), MTime(11)))
        Sessions.add(session2)

    }
    companion object {
        val db:MDatabase = MDatabase()
    }
}