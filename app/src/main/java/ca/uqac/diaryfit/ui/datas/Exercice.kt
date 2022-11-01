package ca.uqac.diaryfit.ui.datas

class Exercice (var _test:Boolean) {

    fun getTitle() : String {
        return "Push-up"
    }
    fun getDescription() : String {
        return "rest 30''"
    }
    fun getTool() : Boolean {
        return _test
    }
}