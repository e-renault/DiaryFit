package ca.uqac.diaryfit.ui.datas

class Exercice{
    var isDone:Boolean = false

    fun getTitle() : String {
        return "Push-up"
    }

    fun getDescription() : String {
        return "rest 30''"
    }

    fun getTool() : Boolean {
        return true
    }
}