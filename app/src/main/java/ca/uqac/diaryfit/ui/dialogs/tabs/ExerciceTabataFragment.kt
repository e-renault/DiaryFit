package ca.uqac.diaryfit.ui.dialogs.tabs

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import ca.uqac.diaryfit.R
import ca.uqac.diaryfit.ui.datas.MDatabase
import ca.uqac.diaryfit.ui.datas.MTime
import ca.uqac.diaryfit.ui.dialogs.ExerciceFragment
import ca.uqac.diaryfit.ui.dialogs.NumberPickerFragment
import ca.uqac.diaryfit.ui.dialogs.TimePickerFragment

private const val ARG_NBSERIE = "nbSeries"
private const val ARG_LISTEX = "exerciceList"
private const val ARG_WORK = "work"
private const val ARG_REST = "rest"

class ExerciceTabataFragment : Fragment() {
    var nbCycle:Int = 1
    var otherex:IntArray = IntArray(0)
    var worktime: MTime = MTime(0,0,0)
    var resttime:MTime = MTime(0,0,0)

    lateinit var exerciceList_bt: TextView
    lateinit var serie_bt: TextView
    lateinit var worktime_bt: TextView
    lateinit var resttime_bt: TextView

    var selected_obj:Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        arguments?.let {
            nbCycle = it.getInt(ARG_NBSERIE)
            otherex = it.getIntArray(ARG_LISTEX)!!
            worktime = it.getParcelable(ARG_WORK)!!
            resttime = it.getParcelable(ARG_REST)!!
        }

        childFragmentManager.setFragmentResultListener("NumberPickerReturn", this) {
                requestKey, bundle ->
            val result = bundle.getInt("value")
            nbCycle = result
            updateView()
        }
        childFragmentManager.setFragmentResultListener("TimePickerReturn", this) {
                requestKey, bundle ->
            val hou = bundle.getInt("hou")
            val min = bundle.getInt("min")
            val sec = bundle.getInt("sec")
            val time = MTime(sec, min, hou)
            when(selected_obj) {
                1 -> resttime = time
                2 -> worktime = time
            }
            updateView()

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.tab_exercice_tabata, container, false)
        if (view == null) return null

        exerciceList_bt = view.findViewById(R.id.extabata_et_exlist)
        exerciceList_bt.setOnClickListener {
            val exerciceArray:Array<String> = MDatabase.getExerciceList().toTypedArray()
            createCheckBoxDialog(exerciceArray, view.context)
        }

        serie_bt = view.findViewById(R.id.extabata_et_cycle)
        serie_bt.setOnClickListener {
            NumberPickerFragment.newInstance(1, 20, nbCycle).show(childFragmentManager, ExerciceFragment.TAG)
        }

        worktime_bt = view.findViewById(R.id.extabata_et_work)
        worktime_bt.setOnClickListener {
            selected_obj = 2
            TimePickerFragment.newInstance(worktime).show(childFragmentManager, ExerciceFragment.TAG)
        }

        resttime_bt = view.findViewById(R.id.extabata_et_rest)
        resttime_bt.setOnClickListener {
            selected_obj = 1
            TimePickerFragment.newInstance(resttime).show(childFragmentManager, ExerciceFragment.TAG)
        }

        updateView()
        return view
    }

    private fun updateView() {
        var ret = ""
        for (i in otherex) {
            ret += "${MDatabase.getExerciceName(i)} "
        }
        exerciceList_bt.text = ret
        serie_bt.text = "${nbCycle.toString()}x"
        worktime_bt.text = worktime.toString()
        resttime_bt.text = resttime.toString()
    }

    private fun createCheckBoxDialog(
        exerciceArray:Array<String>,
        ctx: Context
    ) {
        var selectedExercices: BooleanArray = BooleanArray(exerciceArray.size)
        for (i in otherex) selectedExercices[i] = true

        val builder: AlertDialog.Builder = AlertDialog.Builder(ctx)
        builder.setCancelable(true)
        builder.setMultiChoiceItems(exerciceArray, selectedExercices,
            DialogInterface.OnMultiChoiceClickListener { dialogInterface, index, checked ->
                if (checked) {
                    otherex = otherex + index
                } else {
                    otherex = otherex.filter{it != index}.toIntArray()
                }
            })

        builder.setPositiveButton("OK",
            DialogInterface.OnClickListener { dialogInterface, i ->
                //TODO check non empty list
                if (otherex.isEmpty()) {
                    otherex = otherex + 0
                    Toast.makeText(context, "You must choose at least one exercice", Toast.LENGTH_LONG)
                }
                setFragmentResult("TabataFragment", bundleOf("newName" to otherex.get(0)))
                updateView()
            })

        builder.setNeutralButton("Clear All",
            DialogInterface.OnClickListener { dialogInterface, i ->
                for (j in 0 until selectedExercices.size) {
                    selectedExercices[j] = false
                }
                selectedExercices[otherex.get(0)] = true
                otherex = otherex.filter{it == otherex.get(0)}.toIntArray()
                updateView()
            })
        builder.show()
    }

    companion object {
        @JvmStatic
        fun newInstance3(_nbSerie:Int, _listEx:IntArray, _work:MTime, _rest:MTime) =
            ExerciceTabataFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_NBSERIE, _nbSerie)
                    putIntArray(ARG_LISTEX, _listEx)
                    putParcelable(ARG_WORK, _work)
                    putParcelable(ARG_REST, _rest)
                }
            }
    }
}