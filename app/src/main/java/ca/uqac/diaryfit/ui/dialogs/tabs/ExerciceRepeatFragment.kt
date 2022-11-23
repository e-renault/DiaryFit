package ca.uqac.diaryfit.ui.dialogs.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import ca.uqac.diaryfit.R
import ca.uqac.diaryfit.datas.MTime
import ca.uqac.diaryfit.datas.MWeigth
import ca.uqac.diaryfit.ui.dialogs.*

private const val ARG_NBSERIE = "repeat_nbSeries"
private const val ARG_REST = "repeat_rest"
private const val ARG_REPETITION = "repeat_nbRepetition"
private const val ARG_WEIGHT = "repeat_weight"

class ExerciceRepeatFragment : Fragment(){
    var nbSerie:Int = 1
    var nbRepetition:Int = 1
    var weight: MWeigth = MWeigth(0.0F, true)
    var resttime: MTime = MTime(0,0,0)

    lateinit var serie_bt: TextView
    lateinit var repetition_bt:TextView
    lateinit var weight_bt:TextView
    lateinit var restTime_bt:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            nbSerie = it.getInt(ARG_NBSERIE)
            nbRepetition = it.getInt(ARG_REPETITION)
            weight = it.getParcelable(ARG_WEIGHT)!!
            resttime = it.getParcelable(ARG_REST)!!
        }

        childFragmentManager.setFragmentResultListener(ARG_NBSERIE, this) {
                requestKey, bundle ->
            val result = bundle.getInt("value")
            nbSerie = result

            updateView()
        }
        childFragmentManager.setFragmentResultListener(ARG_REPETITION, this) {
                requestKey, bundle ->
            val result = bundle.getInt("value")
            nbRepetition = result

            updateView()
        }
        childFragmentManager.setFragmentResultListener(ARG_WEIGHT, this) {
                requestKey, bundle ->
            val weight_val = bundle.getFloat("weight")
            val iskg = bundle.getBoolean("unit")

            weight = MWeigth(weight_val, iskg)
            updateView()

        }
        childFragmentManager.setFragmentResultListener(ARG_REST, this) {
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
            NumberPickerFragment.newInstance(1, 10, nbSerie, ARG_NBSERIE).show(childFragmentManager, ExerciceFragment.TAG)
        }

        repetition_bt = view.findViewById(R.id.exrepeat_et_repetition) as TextView
        repetition_bt.setOnClickListener {
            NumberPickerFragment.newInstance(1, 1000, nbRepetition, ARG_REPETITION).show(childFragmentManager, ExerciceFragment.TAG)
        }

        weight_bt = view.findViewById(R.id.exrepeat_et_weight) as TextView
        weight_bt.setOnClickListener {
            WeightPickerFragment.newInstance(weight, ARG_WEIGHT).show(childFragmentManager, ExerciceFragment.TAG)
        }

        restTime_bt = view.findViewById(R.id.exrepeat_et_rest) as TextView
        restTime_bt.setOnClickListener {
            TimePickerFragment.newInstance(resttime, ARG_REST).show(childFragmentManager, ExerciceFragment.TAG)
        }

        updateView()

        return view
    }

    private fun updateView() {
        serie_bt.text = "${nbSerie}x"
        repetition_bt.text = "${nbRepetition} rep"
        weight_bt.text = weight.toString()
        restTime_bt.text = resttime.toString()

        setFragmentResult(ARG_REPEAT, bundleOf(
            "weight" to weight,
            "nbSerie" to nbSerie,
            "resttime" to resttime,
            "nbRepetition" to nbRepetition)
        )
    }

    companion object {
        @JvmStatic
        fun newInstance1(_nbSerie:Int, _nbRep:Int, _weight: MWeigth, _rest: MTime) =
            ExerciceRepeatFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_NBSERIE, _nbSerie)
                    putInt(ARG_REPETITION, _nbRep)
                    putParcelable(ARG_WEIGHT, _weight)
                    putParcelable(ARG_REST, _rest)
                }
            }
    }
}