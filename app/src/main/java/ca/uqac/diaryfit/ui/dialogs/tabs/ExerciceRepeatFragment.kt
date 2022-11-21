package ca.uqac.diaryfit.ui.dialogs.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import ca.uqac.diaryfit.R
import ca.uqac.diaryfit.ui.datas.MTime
import ca.uqac.diaryfit.ui.datas.MWeigth
import ca.uqac.diaryfit.ui.dialogs.ExerciceFragment
import ca.uqac.diaryfit.ui.dialogs.NumberPickerFragment
import ca.uqac.diaryfit.ui.dialogs.TimePickerFragment
import ca.uqac.diaryfit.ui.dialogs.WeightPickerFragment

private const val ARG_NBSERIE = "nbSeries"
private const val ARG_REP = "nbRepetition"
private const val ARG_WEIGHT = "weight"
private const val ARG_REST = "rest"

class ExerciceRepeatFragment : Fragment(){
    var nbSerie:Int = 1
    var nbRepetition:Int = 1
    var weight:MWeigth = MWeigth(0.0F, true)
    var resttime:MTime = MTime(0,0,0)

    lateinit var serie_bt: TextView
    lateinit var repetition_bt:TextView
    lateinit var weight_bt:TextView
    lateinit var restTime_bt:TextView

    var selected_obj:Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            nbSerie = it.getInt(ARG_NBSERIE)
            nbRepetition = it.getInt(ARG_REP)
            weight = it.getParcelable(ARG_WEIGHT)!!
            resttime = it.getParcelable(ARG_REST)!!
        }

        childFragmentManager.setFragmentResultListener("NumberPickerReturn", this) {
                requestKey, bundle ->
                val result = bundle.getInt("value")
                when(selected_obj) {
                    1 -> nbSerie = result
                    2 -> nbRepetition = result
                }
                updateView()
            }
        childFragmentManager.setFragmentResultListener("WeightPickerReturn", this) {
                requestKey, bundle ->
            val weight_val = bundle.getFloat("weight")
            val iskg = bundle.getBoolean("unit")

            weight = MWeigth(weight_val, iskg)
            updateView()

        }
        childFragmentManager.setFragmentResultListener("TimePickerReturn", this) {
                requestKey, bundle ->
            val hou = bundle.getInt("hou")
            val min = bundle.getInt("min")
            val sec = bundle.getInt("sec")

            resttime = MTime(sec, min, hou)
            updateView()
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
            NumberPickerFragment.newInstance(1, 10, nbSerie).show(childFragmentManager, ExerciceFragment.TAG)
        }

        repetition_bt = view.findViewById(R.id.exrepeat_et_repetition) as TextView
        repetition_bt.setOnClickListener {
            selected_obj = 2
            NumberPickerFragment.newInstance(1, 10, nbRepetition).show(childFragmentManager, ExerciceFragment.TAG)
        }

        weight_bt = view.findViewById(R.id.exrepeat_et_weight) as TextView
        weight_bt.setOnClickListener {
            WeightPickerFragment.newInstance(weight).show(childFragmentManager, ExerciceFragment.TAG)
        }

        restTime_bt = view.findViewById(R.id.exrepeat_et_rest) as TextView
        restTime_bt.setOnClickListener {
            TimePickerFragment.newInstance(resttime).show(childFragmentManager, ExerciceFragment.TAG)
        }

        updateView()

        return view
    }

    private fun updateView() {
        serie_bt.text = "${nbSerie}x"
        repetition_bt.text = "${nbRepetition} rep"
        weight_bt.text = weight.toString()
        restTime_bt.text = resttime.toString()
    }

    companion object {
        @JvmStatic
        fun newInstance1(_nbSerie:Int, _nbRep:Int, _weight:MWeigth, _rest:MTime) =
            ExerciceRepeatFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_NBSERIE, _nbSerie)
                    putInt(ARG_REP, _nbRep)
                    putParcelable(ARG_WEIGHT, _weight)
                    putParcelable(ARG_REST, _rest)
                }
            }
    }
}