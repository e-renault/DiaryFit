package ca.uqac.diaryfit.ui.datas

import ca.uqac.diaryfit.ui.datas.exercices.Exercice

class Session (var name:String) {
    val exerciceList = ArrayList<Exercice>()

    fun getTitle() : String {
        return name
    }

    fun getDescription() : String {
        return "description"
    }
}