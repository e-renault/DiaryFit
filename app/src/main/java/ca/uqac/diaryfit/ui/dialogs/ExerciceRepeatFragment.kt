package ca.uqac.diaryfit.ui.dialogs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.NumberPicker
import ca.uqac.diaryfit.R
import ca.uqac.diaryfit.ui.datas.exercices.Exercice
import ca.uqac.diaryfit.ui.datas.exercices.ExerciceRepetition
import ca.uqac.diaryfit.ui.datas.exercices.ExerciceTabata
import ca.uqac.diaryfit.ui.datas.exercices.ExerciceTime

class ExerciceRepeatFragment(val ex: Exercice) : Fragment() {
    lateinit var serie_np:NumberPicker
    lateinit var repetition_np:NumberPicker
    lateinit var weight_np:NumberPicker
    lateinit var unit_np:NumberPicker
    lateinit var restTime_et:EditText


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_exercice_repeat, container, false)
        if (view == null) return null

        serie_np = view.findViewById(R.id.exrepeat_np_serie) as NumberPicker
        setNumberPicker(serie_np, 100)
        repetition_np = view.findViewById(R.id.exrepeat_np_repetition) as NumberPicker
        setNumberPicker(repetition_np, 100)
        weight_np = view.findViewById(R.id.exrepeat_np_weigth) as NumberPicker
        setNumberPicker(weight_np, 320, 0)
        unit_np = view.findViewById(R.id.exrepeat_np_unit) as NumberPicker
        setNumberPicker(unit_np, arrayOf("Kg", "Lb"))
        restTime_et = view.findViewById(R.id.exrepeat_et_resttime) as EditText

        when(ex) {
            is ExerciceTime -> {
                serie_np.value = ex.nbSerie
                repetition_np.value = 1
                weight_np.value = ex.weigth.weigthkg.toInt()
                unit_np.value = if (ex.weigth.isKG) 0 else 1
                restTime_et.setText(ex.rest.toString())
            }
            is ExerciceRepetition -> {
                serie_np.value = ex.nbSerie
                repetition_np.value = ex.nbRepetition
                weight_np.value = ex.weigth.weigthkg.toInt()
                unit_np.value = if (ex.weigth.isKG) 0 else 1
                restTime_et.setText(ex.rest.toString())
            }
            is ExerciceTabata -> {
                serie_np.value = ex.nbCycle
                repetition_np.value = 1
                weight_np.value = 0
                unit_np.value = 0
                restTime_et.setText(ex.rest.toString())
            }
        }
        return view
    }
}

fun setNumberPicker(np:NumberPicker, max:Int, min:Int=1) {
    np.minValue = min
    np.maxValue = max
    np.wrapSelectorWheel = false
}

fun setNumberPicker(np:NumberPicker, list:Array<String>) {
    np.minValue = 0
    np.maxValue = list.size - 1
    np.displayedValues = list
    np.wrapSelectorWheel = false
}