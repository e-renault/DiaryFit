package ca.uqac.diaryfit.ui.datas

import ca.uqac.diaryfit.ui.datas.exercices.Exercice
import ca.uqac.diaryfit.ui.datas.exercices.ExerciceRepetition
import ca.uqac.diaryfit.ui.datas.exercices.ExerciceTabata
import ca.uqac.diaryfit.ui.datas.exercices.ExerciceTime
import kotlin.collections.ArrayList

class MDatabase {
    private var ExerciceNameList:ArrayList<String> = ArrayList()
    private var SessionDB:ArrayList<Session> = ArrayList()

    init {
        //TODO use real datas
        ExerciceNameList.add("Burpies")
        ExerciceNameList.add("Push-Up")
        ExerciceNameList.add("Cladding")

        var session1:Session = Session("Monday morning")
        session1.exerciceList.add(ExerciceRepetition(0,   1, 2, MWeigth(3.0F), MTime(4)))
        session1.exerciceList.add(ExerciceTime(1,         5, MTime(6), MWeigth(7.0F), MTime(8)))
        session1.exerciceList.add(ExerciceTabata(intArrayOf(0, 1, 2), 9, MTime(10), MTime(11)))
        SessionDB.add(session1)

        var session2:Session = Session("Monday evening")
        session2.exerciceList.add(ExerciceRepetition(0,   1, 2, MWeigth(3.0F), MTime(4)))
        session2.exerciceList.add(ExerciceTime(1,         5, MTime(6), MWeigth(7.0F), MTime(8)))
        session2.exerciceList.add(ExerciceTabata(intArrayOf(1), 9, MTime(10), MTime(11)))
        SessionDB.add(session2)
    }


    companion object {
        var db:MDatabase = MDatabase()

        fun getExerciceName(index:Int): String {
            return if (index<db.ExerciceNameList.size && index>=0) db.ExerciceNameList.get(index) else "Error"
        }

        fun getExerciceList() : List<String>{
            return db.ExerciceNameList
        }

        fun getExercice(sessionID:Int, exerciceID:Int) : Exercice? {
            if (sessionID >= 0 && exerciceID >= 0)
                return db.SessionDB.get(sessionID).exerciceList.get(exerciceID)
            else
                return null
        }

        fun getSession(sessionID:Int) : Session {
            return db.SessionDB.get(sessionID)
        }

        fun setExercice(sessionID:Int, exerciceID: Int, exercie:Exercice) {
            db.SessionDB[sessionID].exerciceList.set(exerciceID, exercie)
        }

        fun getTodaySessions() : ArrayList<Session> {
            //TODO retrieve today session
            return db.SessionDB
        }
    }
}