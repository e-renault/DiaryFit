package ca.uqac.diaryfit.ui.dialogs

import ViewPagerAdapter
import android.R.layout
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.DialogFragment
import androidx.viewpager2.widget.ViewPager2
import ca.uqac.diaryfit.R
import ca.uqac.diaryfit.ui.datas.MDatabase
import ca.uqac.diaryfit.ui.datas.exercices.Exercice
import ca.uqac.diaryfit.ui.datas.exercices.ExerciceRepetition
import ca.uqac.diaryfit.ui.datas.exercices.ExerciceTabata
import ca.uqac.diaryfit.ui.datas.exercices.ExerciceTime
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ExerciceFragment(private val ex: Exercice) :
    DialogFragment(R.layout.dialog_edit_exercice),
    AdapterView.OnItemSelectedListener {

    //Fragment related elements
    private lateinit var fdialog: Dialog

    //UI
    private lateinit var okBtn : Button
    private lateinit var cancelBtn : Button
    private lateinit var spinner : Spinner

    private lateinit var tabLayout : TabLayout
    private lateinit var pagerView : ViewPager2
    private lateinit var adapter : ViewPagerAdapter

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)

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
        adapter = ViewPagerAdapter(childFragmentManager, lifecycle, ex)
        pagerView.adapter = adapter

        spinner = view.findViewById(R.id.editexercice_sp_name)
        val spinnerArrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
            view.context,
            layout.simple_spinner_dropdown_item,
            MDatabase.db.ExerciceNameList
        )
        spinner.adapter = spinnerArrayAdapter
        spinner.setSelection(ex.ExerciceNameID)
        spinner.onItemSelectedListener = this

        val tabsName = arrayOf("Repeat", "Time", "Tabata")
        TabLayoutMediator(tabLayout, pagerView) { tab, position -> tab.text = tabsName[position]}.attach()

        when(ex) {
            is ExerciceRepetition -> pagerView.setCurrentItem(0, false)
            is ExerciceTime -> pagerView.setCurrentItem(1, false)
            is ExerciceTabata -> pagerView.setCurrentItem(2, false)
        }

        okBtn = view.findViewById(ca.uqac.diaryfit.R.id.editexercice_bt_ok) as Button
        okBtn.setOnClickListener {
            fdialog.dismiss()
        }

        cancelBtn = view.findViewById(ca.uqac.diaryfit.R.id.editexercice_bt_cancel) as Button
        cancelBtn.setOnClickListener {
            fdialog.dismiss()
        }

        return view
    }

    companion object {
        const val TAG = "ExerciceFragment"
    }
    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        ex.ExerciceNameID = p2
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}