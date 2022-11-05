package ca.uqac.diaryfit.ui.datas

import ca.uqac.diaryfit.ui.datas.exercices.ExerciceRepetition
import ca.uqac.diaryfit.ui.datas.exercices.ExerciceTabata
import ca.uqac.diaryfit.ui.datas.exercices.ExerciceTime

class MDatabase {
    val ExerciceNameList:ArrayList<String> = ArrayList()
    val Sessions:ArrayList<Session> = ArrayList()

    init {
        //TODO use real datas

        ExerciceNameList.add("Burpies")
        ExerciceNameList.add("Push-Up")
        ExerciceNameList.add("Cladding")

        val session1:Session = Session("Monday morning")
        session1.exerciceList.add(ExerciceRepetition(ExerciceNameList[0],   1, 2, MWeigth(3.0F), MTime(4)))
        session1.exerciceList.add(ExerciceTime(ExerciceNameList[1],         5, MTime(6), MWeigth(7.0F), MTime(8)))
        session1.exerciceList.add(ExerciceTabata(ExerciceNameList,          9, MTime(10), MTime(11)))
        Sessions.add(session1)

        val session2:Session = Session("Monday evening")
        session2.exerciceList.add(ExerciceRepetition(ExerciceNameList[0],   1, 2, MWeigth(3.0F), MTime(4)))
        session2.exerciceList.add(ExerciceTime(ExerciceNameList[1],         5, MTime(6), MWeigth(7.0F), MTime(8)))
        session2.exerciceList.add(ExerciceTabata(ExerciceNameList,          9, MTime(10), MTime(11)))
        Sessions.add(session2)


    }
    companion object {
        val db:MDatabase = MDatabase()
    }
}