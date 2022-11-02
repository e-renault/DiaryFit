package ca.uqac.diaryfit.ui.datas

class Session () {
    val exerciceList = ArrayList<Exercice>()

    init {
        //populate (example)
        //TODO use real datas
        for (i in 1..3) { exerciceList.add(Exercice(i%2 == 0)) }
    }

    fun getTitle() : String {
        return "Vendredi Matin"
    }
    fun getDescription() : String {
        return "45min"
    }
    fun getDate() : String {
        return "date"
    }
}