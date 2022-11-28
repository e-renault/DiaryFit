package ca.uqac.diaryfit.ui.dialogs.tabs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import ca.uqac.diaryfit.R
import ca.uqac.diaryfit.datas.MTime
import ca.uqac.diaryfit.datas.MWeigth
import ca.uqac.diaryfit.ui.dialogs.*

private const val ARG_NBSERIE = "time_nbSeries"
private const val ARG_WORK = "time_work"
private const val ARG_REST = "time_rest"
private const val ARG_WEIGHT = "time_weight"

class ExerciceTimeFragment : Fragment() {

    var nbSerie:Int = 1
    var weight: MWeigth = MWeigth(0.0F, true)
    var worktime: MTime = MTime(0,0,0)
    var resttime: MTime = MTime(0,0,0)

    lateinit var serie_bt: TextView
    lateinit var weight_bt: TextView
    lateinit var restTime_bt: TextView
    lateinit var workTime_bt: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            nbSerie = it.getInt(ARG_NBSERIE)
            weight = it.getParcelable(ARG_WEIGHT)!!
            worktime = it.getParcelable(ARG_WORK)!!
            resttime = it.getParcelable(ARG_REST)!!
        }

        childFragmentManager.setFragmentResultListener(ARG_NBSERIE, this) {
                requestKey, bundle ->
            val result = bundle.getInt("value")

            nbSerie = result
            updateView()
        }
        childFragmentManager.setFragmentResultListener(ARG_WEIGHT, this) {
                requestKey, bundle ->
            val weight_val = bundle.getFloat("weight")
            val iskg = bundle.getBoolean("unit")

            this.weight = MWeigth(weight_val, iskg)
            updateView()

        }
        childFragmentManager.setFragmentResultListener(ARG_WORK, this) {
                requestKey, bundle ->
            val hou = bundle.getInt("hou")
            val min = bundle.getInt("min")
            val sec = bundle.getInt("sec")
            val time = MTime(sec, min, hou)
            worktime = time

            updateView()
        }
        childFragmentManager.setFragmentResultListener(ARG_REST, this) {
                requestKey, bundle ->
            val hou = bundle.getInt("hou")
            val min = bundle.getInt("min")
            val sec = bundle.getInt("sec")
            val time = MTime(sec, min, hou)
            resttime = time

            updateView()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.tab_exercice_time, container, false)
        if (view == null) return null

        serie_bt = view.findViewById(R.id.extime_et_serie)
        serie_bt.setOnClickListener {
            NumberPickerFragment.newInstance(1, 50, nbSerie, ARG_NBSERIE).show(childFragmentManager, ExerciceFragment.TAG)
        }

        weight_bt = view.findViewById(R.id.extime_et_weight)
        weight_bt.setOnClickListener {
            WeightPickerFragment.newInstance(weight, ARG_WEIGHT).show(childFragmentManager, ExerciceFragment.TAG)
        }

        restTime_bt = view.findViewById(R.id.extime_et_rest)
        restTime_bt.setOnClickListener {
            TimePickerFragment.newInstance(resttime, ARG_REST).show(childFragmentManager, ExerciceFragment.TAG)
        }

        workTime_bt = view.findViewById(R.id.extime_et_work)
        workTime_bt.setOnClickListener {
            TimePickerFragment.newInstance(worktime, ARG_WORK).show(childFragmentManager, ExerciceFragment.TAG)
        }

        updateView()

        return view
    }

    private fun updateView() {
        serie_bt.text = "${nbSerie}x"
        weight_bt.text = weight.toString()
        restTime_bt.text = resttime.toString()
        workTime_bt.text = worktime.toString()

        setFragmentResult(ARG_TIME, bundleOf(
            "nbSerie" to nbSerie,
            "weight" to weight,
            "resttime" to resttime,
            "worktime" to worktime))
    }

    companion object {
        @JvmStatic
        fun newInstance2(_nbSerie:Int, _weight: MWeigth, _rest: MTime, _work: MTime) =
            ExerciceTimeFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_NBSERIE, _nbSerie)
                    putParcelable(ARG_WEIGHT, _weight)
                    putParcelable(ARG_WORK, _work)
                    putParcelable(ARG_REST, _rest)
                }
            }
    }
}