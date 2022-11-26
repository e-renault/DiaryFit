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
import ca.uqac.diaryfit.MainActivity
import ca.uqac.diaryfit.R
import ca.uqac.diaryfit.UserDB
import ca.uqac.diaryfit.datas.MTime
import ca.uqac.diaryfit.ui.dialogs.*
import com.google.gson.Gson

private const val ARG_NBSERIE = "tabata_nbSeries"
private const val ARG_WORK = "tabata_work"
private const val ARG_REST = "tabata_rest"
private const val ARG_LISTEX = "tabata_exerciceList"

class ExerciceTabataFragment : Fragment() {
    var nbCycle:Int = 1
    var otherex:List<Int> = ArrayList<Int>()
    var worktime: MTime = MTime(0,0,0)
    var resttime: MTime = MTime(0,0,0)

    lateinit var exerciceList_bt: TextView
    lateinit var serie_bt: TextView
    lateinit var worktime_bt: TextView
    lateinit var resttime_bt: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            nbCycle = it.getInt(ARG_NBSERIE)
            otherex = Gson().fromJson(it.getString(ARG_LISTEX), kotlin.Any::class.java) as ArrayList<Int>
            worktime = it.getParcelable(ARG_WORK)!!
            resttime = it.getParcelable(ARG_REST)!!
        }

        childFragmentManager.setFragmentResultListener(ARG_NBSERIE, this) {
                requestKey, bundle ->
            val result = bundle.getInt("value")
            nbCycle = result
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
        childFragmentManager.setFragmentResultListener(ARG_WORK, this) {
                requestKey, bundle ->
            val hou = bundle.getInt("hou")
            val min = bundle.getInt("min")
            val sec = bundle.getInt("sec")
            val time = MTime(sec, min, hou)
            worktime = time

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
            val exerciceArray:Array<String> = UserDB.getExerciceList(MainActivity.profil).toTypedArray()
            createCheckBoxDialog(exerciceArray, view.context)
        }

        serie_bt = view.findViewById(R.id.extabata_et_cycle)
        serie_bt.setOnClickListener {
            NumberPickerFragment.newInstance(1, 20, nbCycle, ARG_NBSERIE).show(childFragmentManager, ExerciceFragment.TAG)
        }

        worktime_bt = view.findViewById(R.id.extabata_et_work)
        worktime_bt.setOnClickListener {
            TimePickerFragment.newInstance(worktime, ARG_WORK).show(childFragmentManager, ExerciceFragment.TAG)
        }

        resttime_bt = view.findViewById(R.id.extabata_et_rest)
        resttime_bt.setOnClickListener {
            TimePickerFragment.newInstance(resttime, ARG_REST).show(childFragmentManager, ExerciceFragment.TAG)
        }

        updateView()
        return view
    }

    private fun updateView() {
        var ret = ""
        for (i in otherex) {
            ret += "${UserDB.getExerciceName(MainActivity.profil, i)} "
        }
        exerciceList_bt.text = ret
        serie_bt.text = "${nbCycle}x"
        worktime_bt.text = worktime.toString()
        resttime_bt.text = resttime.toString()

        val otherex_str:String = Gson().toJson(otherex)
        setFragmentResult(ARG_TABATA, bundleOf(
            "otherex" to otherex_str,
            "nbSerie" to nbCycle,
            "resttime" to resttime,
            "worktime" to worktime))

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
                    (otherex as ArrayList<Int>).add(index)
                } else {
                    (otherex as ArrayList<Int>).remove(index)
                }
            })

        builder.setPositiveButton("OK",
            DialogInterface.OnClickListener { dialogInterface, i ->
                //TODO check non empty list
                if (otherex.isEmpty()) {
                    Toast.makeText(context, "You must choose at least one exercice", Toast.LENGTH_LONG)
                }
                updateView()
            })

        builder.setNeutralButton("Clear All",
            DialogInterface.OnClickListener { dialogInterface, i ->
                for (j in 0 until selectedExercices.size) {
                    selectedExercices[j] = false
                }
                selectedExercices[otherex.get(0)] = true
                otherex = ArrayList<Int>(0)
                updateView()
            })
        builder.show()
    }

    companion object {
        @JvmStatic
        fun newInstance3(_nbSerie:Int, _listEx:List<Int>, _rest: MTime, _work: MTime) =
            ExerciceTabataFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_NBSERIE, _nbSerie)
                    putString(ARG_LISTEX, Gson().toJson(_listEx))
                    putParcelable(ARG_WORK, _work)
                    putParcelable(ARG_REST, _rest)
                }
            }
    }
}