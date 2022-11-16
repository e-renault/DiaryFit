package ca.uqac.diaryfit.ui.dialogs.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import ca.uqac.diaryfit.R
import ca.uqac.diaryfit.ui.datas.exercices.Exercice
import ca.uqac.diaryfit.ui.datas.exercices.ExerciceRepetition
import ca.uqac.diaryfit.ui.datas.exercices.ExerciceTabata
import ca.uqac.diaryfit.ui.datas.exercices.ExerciceTime
import ca.uqac.diaryfit.ui.datas.repetitionList
import ca.uqac.diaryfit.ui.datas.seriesList
import ca.uqac.diaryfit.ui.datas.unitList
import ca.uqac.diaryfit.ui.datas.weigthList
import ca.uqac.diaryfit.ui.dialogs.ExerciceFragment
import ca.uqac.diaryfit.ui.dialogs.NumberPickerFragment

class ExerciceRepeatFragment(val ex: Exercice) : Fragment() {
    lateinit var serie_bt: TextView
    lateinit var repetition_bt:TextView
    lateinit var weight_bt:TextView
    lateinit var unit_bt:TextView
    lateinit var restTime_bt:TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_exercice_repeat, container, false)
        if (view == null) return null

        serie_bt = view.findViewById(R.id.exrepeat_et_serie) as TextView
        serie_bt.setOnClickListener { NumberPickerFragment.newInstance(seriesList).show(childFragmentManager, ExerciceFragment.TAG) }

        repetition_bt = view.findViewById(R.id.exrepeat_et_repetition) as TextView
        repetition_bt.setOnClickListener { NumberPickerFragment.newInstance(repetitionList).show(childFragmentManager, ExerciceFragment.TAG) }

        weight_bt = view.findViewById(R.id.exrepeat_et_weight) as TextView
        weight_bt.setOnClickListener { NumberPickerFragment.newInstance(weigthList).show(childFragmentManager, ExerciceFragment.TAG) }

        unit_bt = view.findViewById(R.id.exrepeat_et_unit) as TextView
        unit_bt.setOnClickListener { NumberPickerFragment.newInstance(unitList).show(childFragmentManager, ExerciceFragment.TAG) }

        restTime_bt = view.findViewById(R.id.exrepeat_et_rest) as TextView

        when(ex) {
            is ExerciceRepetition -> {
                serie_bt.text = ex.nbSerie.toString()
                repetition_bt.text = ex.nbRepetition.toString()
                weight_bt.text = ex.weigth.getWeightkg().toString()
                unit_bt.text = if (ex.weigth.isKG) "kg" else "lb"
                restTime_bt.text = ex.rest.toString()
            }
            is ExerciceTime -> {

            }
            is ExerciceTabata -> {

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