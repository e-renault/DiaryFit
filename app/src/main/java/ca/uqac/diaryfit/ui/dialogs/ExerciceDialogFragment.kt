package ca.uqac.diaryfit.ui.dialogs

import ViewPagerAdapter
import android.R.layout
import android.app.ActionBar
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import androidx.appcompat.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.core.os.bundleOf
import androidx.core.view.marginLeft
import androidx.core.view.setPadding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.viewpager2.widget.ViewPager2
import ca.uqac.diaryfit.MainActivity
import ca.uqac.diaryfit.R
import ca.uqac.diaryfit.UserDB
import ca.uqac.diaryfit.datas.MTime
import ca.uqac.diaryfit.datas.MWeigth
import ca.uqac.diaryfit.datas.exercices.Exercice
import ca.uqac.diaryfit.datas.exercices.ExerciceRepetition
import ca.uqac.diaryfit.datas.exercices.ExerciceTabata
import ca.uqac.diaryfit.datas.exercices.ExerciceTime
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


private const val ARG_EXTYPE = "exerciceType"
private const val ARG_EXERCICENAME = "exerciceName"
private const val ARG_NBSERIE = "nbSeries"
private const val ARG_WORK = "work"
private const val ARG_REST = "rest"
private const val ARG_REPETITION = "nbRepetition"
private const val ARG_LISTEX = "exerciceList"
private const val ARG_WEIGHT = "weight"

const val ARG_REPEAT = "repeat_frg"
const val ARG_TIME = "time_frg"
const val ARG_TABATA = "tabata_frg"

class ExerciceFragment :
    DialogFragment(R.layout.dialog_edit_exercice),
    AdapterView.OnItemSelectedListener {

    //Fragment related elements
    private lateinit var fdialog: Dialog

    //UI
    private lateinit var okBtn : Button
    private lateinit var cancelBtn : Button
    private lateinit var addBtn : ImageButton
    private lateinit var spinner : Spinner
    private lateinit var tabLayout : TabLayout
    private lateinit var pagerView : ViewPager2
    private lateinit var adapter : ViewPagerAdapter

    private var ExerciceID = -1
    private var ExerciceTYPE = -1
    private var nbRep:Int = 1
    private var listEx:IntArray = intArrayOf()
    private var nbSerie:Int = 1
    private var rest: MTime = MTime(0,0,0)
    private var work: MTime = MTime(0,0,0)
    private var weight: MWeigth = MWeigth(0.0F, true)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        childFragmentManager.setFragmentResultListener(ARG_REPEAT, this) {
                requestKey, bundle ->
            weight = bundle.getParcelable("weight")!!
            nbSerie = bundle.getInt("nbSerie")
            rest = bundle.getParcelable("resttime")!!
            nbRep = bundle.getInt("nbRepetition")
        }
        childFragmentManager.setFragmentResultListener(ARG_TIME, this) {
                requestKey, bundle ->
            weight = bundle.getParcelable("weight")!!
            nbSerie = bundle.getInt("nbSerie")
            rest = bundle.getParcelable("resttime")!!
            work = bundle.getParcelable("worktime")!!
        }
        childFragmentManager.setFragmentResultListener(ARG_TABATA, this) {
                requestKey, bundle ->
            listEx = bundle.getIntArray("otherex")!!
            nbSerie = bundle.getInt("nbSerie")
            rest = bundle.getParcelable("resttime")!!
            work = bundle.getParcelable("worktime")!!
            if (listEx.isNotEmpty())
                ExerciceID = listEx.get(0)
            updateView()
        }

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)

        arguments?.let {
            ExerciceID = it.getInt(ARG_EXERCICENAME)
            ExerciceTYPE = it.getInt(ARG_EXTYPE)
            if (ExerciceID != -1)
                listEx = intArrayOf(ExerciceID)
            else
                listEx = intArrayOf()

            when (ExerciceTYPE){
                0 -> {
                    nbRep = it.getInt(ARG_REPETITION)
                    nbSerie = it.getInt(ARG_NBSERIE)
                    rest = it.getParcelable(ARG_REST)!!
                    weight = it.getParcelable(ARG_WEIGHT)!!
                }
                1 -> {
                    nbSerie = it.getInt(ARG_NBSERIE)
                    rest = it.getParcelable(ARG_REST)!!
                    work = it.getParcelable(ARG_WORK)!!
                    weight = it.getParcelable(ARG_WEIGHT)!!
                }
                2 -> {
                    nbSerie = it.getInt(ARG_NBSERIE)
                    listEx = it.getIntArray(ARG_LISTEX)!!
                    rest = it.getParcelable(ARG_REST)!!
                    work = it.getParcelable(ARG_WORK)!!
                }
            }
        }


        fdialog = Dialog(requireContext())
        fdialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        fdialog.setCancelable(true)

        return fdialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        if (view == null) return null

        tabLayout = view.findViewById(R.id.editexercice_tl) as TabLayout
        pagerView = view.findViewById(R.id.editexercice_pv) as ViewPager2
        adapter = ViewPagerAdapter(childFragmentManager, lifecycle, nbRep, listEx, nbSerie, rest, work, weight)
        pagerView.adapter = adapter

        spinner = view.findViewById(R.id.editexercice_sp_name)
        spinner.onItemSelectedListener = this

        val tabsName = arrayOf("Repeat", "Time", "Tabata")
        TabLayoutMediator(tabLayout, pagerView) { tab, position -> tab.text = tabsName[position]}.attach()
        pagerView.setCurrentItem(ExerciceTYPE, false)

        okBtn = view.findViewById(R.id.editexercice_bt_ok) as Button
        okBtn.setOnClickListener {
            if (ExerciceID == -1) {
                Toast.makeText(context, "You must select at least one exercice", Toast.LENGTH_LONG).show()
            } else {
                if (listEx.isEmpty()) listEx = intArrayOf(ExerciceID)
                var ret: Exercice? = when (pagerView.currentItem) {
                    0 -> ExerciceRepetition(ExerciceID, nbSerie, nbRep, weight, rest)
                    1 -> ExerciceTime(ExerciceID, nbSerie, work, weight, rest)
                    2 -> ExerciceTabata(listEx, nbSerie, rest, work)
                    else -> null
                }
                setFragmentResult("ExerciceDialogReturn", bundleOf("Exercice" to ret))
                fdialog.dismiss()
            }
        }

        cancelBtn = view.findViewById(R.id.editexercice_bt_cancel) as Button
        cancelBtn.setOnClickListener {
            fdialog.dismiss()
        }

        addBtn = view.findViewById(R.id.editexercice_bt_add) as ImageButton
        addBtn.setOnClickListener {

            val alert = AlertDialog.Builder(ContextThemeWrapper(context, R.style.Theme_DiaryFit))

            val edittext = EditText(context)
            alert.setTitle("New exercice :")
            //alert.setMessage("New exercice :")

            alert.setView(edittext)

            alert.setPositiveButton(R.string.ok, { dialog, whichButton ->
                val newExercice = edittext.text.toString()
                Toast.makeText(context, newExercice, Toast.LENGTH_LONG).show()
                UserDB.addExercice(MainActivity.profil, newExercice)
                updateView()
            })

            alert.setNegativeButton(R.string.cancel, { dialog, whichButton ->
            })

            alert.show()

        }
        updateView()
        return view
    }

    private fun updateView() {
        val spinnerArrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
            spinner.context,
            layout.simple_spinner_dropdown_item,
            UserDB.getExerciceList(MainActivity.profil)
        )
        spinner.adapter = spinnerArrayAdapter
        if (ExerciceID == -1) {
            spinner.setSelection(ExerciceID)
        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        ExerciceID = p2
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}

    companion object {
        const val TAG = "ExerciceFragment"

        @JvmStatic
        fun editExercice(ex: Exercice) =
            ExerciceFragment().apply {
                arguments = Bundle().apply {
                    when (ex){
                        is ExerciceRepetition -> {
                            putInt(ARG_EXERCICENAME, ex.exerciceNameID)
                            putInt(ARG_EXTYPE, 0)
                            putInt(ARG_REPETITION, ex.nbRepetition)
                            putInt(ARG_NBSERIE, ex.nbSerie)
                            putParcelable(ARG_REST, ex.rest)
                            putParcelable(ARG_WEIGHT, ex.weigth)
                        }
                        is ExerciceTime -> {
                            putInt(ARG_EXERCICENAME, ex.exerciceNameID)
                            putInt(ARG_EXTYPE, 1)
                            putInt(ARG_NBSERIE, ex.nbSerie)
                            putParcelable(ARG_REST, ex.rest)
                            putParcelable(ARG_WEIGHT, ex.weigth)
                            putParcelable(ARG_WORK, ex.effortTime)
                        }
                        is ExerciceTabata -> {
                            putInt(ARG_EXERCICENAME, ex.otherExerciceList.get(0))
                            putInt(ARG_EXTYPE, 2)
                            putInt(ARG_NBSERIE, ex.nbCycle)
                            putIntArray(ARG_LISTEX, ex.otherExerciceList)
                            putParcelable(ARG_REST, ex.rest)
                            putParcelable(ARG_WORK, ex.effortTime)
                        }
                    }
                }
            }
    }
}