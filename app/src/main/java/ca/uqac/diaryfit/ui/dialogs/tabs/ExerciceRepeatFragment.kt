package ca.uqac.diaryfit.ui.dialogs.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import ca.uqac.diaryfit.R
import ca.uqac.diaryfit.ui.datas.exercices.ExerciceRepetition
import ca.uqac.diaryfit.ui.dialogs.ExerciceFragment
import ca.uqac.diaryfit.ui.dialogs.NumberPickerFragment


class ExerciceRepeatFragment(val ex: ExerciceRepetition) : Fragment(){
    lateinit var serie_bt: TextView
    lateinit var repetition_bt:TextView
    lateinit var weight_bt:TextView
    lateinit var restTime_bt:TextView

    var selected_obj:Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        childFragmentManager.setFragmentResultListener("requestKey", this) {
                requestKey, bundle ->
                when(selected_obj) {
                    1 -> {
                        val result = bundle.getInt("bundleKey")
                        ex.nbSerie = result
                        serie_bt.text = ex.nbSerie.toString()
                    }
                    2 -> {
                        val result = bundle.getInt("bundleKey")
                        ex.nbRepetition = result
                        repetition_bt.text = ex.nbRepetition.toString()
                    }
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.tab_exercice_repeat, container, false)
        if (view == null) return null

        serie_bt = view.findViewById(R.id.exrepeat_et_serie) as TextView
        serie_bt.setOnClickListener {
            selected_obj = 1
            val frag = NumberPickerFragment.newInstance(1, 10, ex.nbSerie)
            frag.show(childFragmentManager, ExerciceFragment.TAG)
        }

        repetition_bt = view.findViewById(R.id.exrepeat_et_repetition) as TextView
        repetition_bt.setOnClickListener {
            selected_obj = 2
            NumberPickerFragment.newInstance(1, 10, ex.nbRepetition).show(childFragmentManager, ExerciceFragment.TAG)
        }

        weight_bt = view.findViewById(R.id.exrepeat_et_weight) as TextView
        restTime_bt = view.findViewById(R.id.exrepeat_et_rest) as TextView

        serie_bt.text = ex.nbSerie.toString()
        repetition_bt.text = ex.nbRepetition.toString()

        return view
    }

    companion object {
        const val TAG = 1
    }
}