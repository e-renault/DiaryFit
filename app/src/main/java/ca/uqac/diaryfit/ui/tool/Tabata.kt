package ca.uqac.diaryfit.ui.tool

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ca.uqac.diaryfit.R
import ca.uqac.diaryfit.datas.MTime
import ca.uqac.diaryfit.ui.dialogs.ExerciceFragment
import ca.uqac.diaryfit.ui.dialogs.NumberPickerFragment
import ca.uqac.diaryfit.ui.dialogs.TimePickerFragment
import ca.uqac.diaryfit.databinding.TabToolTabataBinding


private val ARG_WORK = "tabata_work_argv"
private val ARG_REST = "tabata_rest_argv"
private val ARG_CYCLE = "tabata_cycle_argv"
private val ARG_PREP = "tabata_prepear_argv"

class Tabata : Fragment() {
    private var _binding: TabToolTabataBinding? = null
    private val binding get() = _binding!!

    private var time_rest:MTime = MTime(0,0,0)
    private var time_work:MTime = MTime(0,0,0)
    private var time_prep:MTime = MTime(0,0,0)
    private var nbcycle:Int = 1

    //UI
    lateinit var cycle_bt: TextView
    lateinit var restTime_bt: TextView
    lateinit var workTime_bt: TextView
    lateinit var preptime_bt: TextView
    lateinit var display: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        childFragmentManager.setFragmentResultListener(ARG_WORK, this) {
                requestKey, bundle ->
            val hou = bundle.getInt("hou")
            val min = bundle.getInt("min")
            val sec = bundle.getInt("sec")
            val time = MTime(sec, min, hou)
            time_work = time

            updateView()
        }

        childFragmentManager.setFragmentResultListener(ARG_REST, this) {
                requestKey, bundle ->
            val hou = bundle.getInt("hou")
            val min = bundle.getInt("min")
            val sec = bundle.getInt("sec")
            val time = MTime(sec, min, hou)
            time_rest = time

            updateView()
        }
        childFragmentManager.setFragmentResultListener(ARG_PREP, this) {
                requestKey, bundle ->
            val hou = bundle.getInt("hou")
            val min = bundle.getInt("min")
            val sec = bundle.getInt("sec")
            val time = MTime(sec, min, hou)
            time_prep = time

            updateView()
        }

        childFragmentManager.setFragmentResultListener(ARG_CYCLE, this) {
                requestKey, bundle ->
            val result = bundle.getInt("value")

            nbcycle = result
            updateView()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = TabToolTabataBinding.inflate(inflater, container, false)
        val view = binding.root

        cycle_bt = view.findViewById(R.id.tw_tabata_bt_cycle)
        cycle_bt.setOnClickListener {
            NumberPickerFragment.newInstance(1, 50, nbcycle, ARG_CYCLE).show(childFragmentManager, ExerciceFragment.TAG)
        }

        restTime_bt = view.findViewById(R.id.tw_tabata_bt_rest)
        restTime_bt.setOnClickListener {
            TimePickerFragment.newInstance(time_rest, ARG_REST).show(childFragmentManager, ExerciceFragment.TAG)
        }

        workTime_bt = view.findViewById(R.id.tw_tabata_bt_work)
        workTime_bt.setOnClickListener {
            TimePickerFragment.newInstance(time_work, ARG_WORK).show(childFragmentManager, ExerciceFragment.TAG)
        }

        preptime_bt = view.findViewById(R.id.tw_tabata_bt_prepare)
        preptime_bt.setOnClickListener {
            TimePickerFragment.newInstance(time_prep, ARG_PREP).show(childFragmentManager, ExerciceFragment.TAG)
        }

        display = view.findViewById(R.id.tw_tabata_counter)
        updateView()
        return view
    }

    private fun updateView() {
        cycle_bt.setText(nbcycle.toString())
        restTime_bt.setText(time_rest.toString())
        workTime_bt.setText(time_work.toString())
        preptime_bt.setText(time_prep.toString())
        display.setText("TODO")
    }
}