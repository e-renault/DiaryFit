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

        val e1 = ExerciceRepetition(0,   1, 2, MWeigth(3.0F), MTime(4))
        val e2 = ExerciceTime(1,         5, MTime(6), MWeigth(7.0F), MTime(8))
        val e3 = ExerciceTabata(intArrayOf(0, 1, 2), 9, MTime(10), MTime(11))
        var session1:Session = Session("Monday morning", arrayListOf(e1,e2,e3))
        SessionDB.add(session1)

        val b1 = ExerciceRepetition(0,   1, 2, MWeigth(3.0F), MTime(4))
        val b2 = ExerciceTime(1,         5, MTime(6), MWeigth(7.0F), MTime(8))
        val b3 = ExerciceTabata(intArrayOf(1), 9, MTime(10), MTime(11))
        var session2:Session = Session("Monday evening", arrayListOf(b1,b2,b3))
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
            if (sessionID >= 0 && sessionID < db.SessionDB.size) {
                if (exerciceID >= 0 && exerciceID < db.SessionDB.get(sessionID).size()) {
                    return db.SessionDB.get(sessionID).get(exerciceID)
                }
            }
            return null
        }

        fun setExercice(sessionID:Int, exerciceID: Int, exercie:Exercice) {
            if (sessionID >= 0 && sessionID < db.SessionDB.size) {
                if (exerciceID >= 0 && exerciceID < db.SessionDB.get(sessionID).size()) {
                    db.SessionDB.get(sessionID).set(exerciceID, exercie)
                }
            }
        }

        fun getSession(sessionID:Int) : Session? {
            if (sessionID >= 0 && sessionID < db.SessionDB.size) {
                return db.SessionDB.get(sessionID)
            }
            return null
        }

        fun setSession(sessionID:Int, session:Session) {
            if (sessionID >= 0 && sessionID < db.SessionDB.size) {
                db.SessionDB.set(sessionID, session)
            }

        }

        fun addSession(session:Session) {
            db.SessionDB.add(session)
        }


        fun getTodaySessions() : ArrayList<Session> {
            //TODO retrieve today session
            return db.SessionDB
        }
    }
}