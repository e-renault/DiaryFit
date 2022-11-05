package ca.uqac.diaryfit.ui.dialogs

import ViewPagerAdapter
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageButton
import android.widget.Spinner
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import ca.uqac.diaryfit.ui.datas.exercices.Exercice
import ca.uqac.diaryfit.ui.datas.exercices.ExerciceRepetition
import ca.uqac.diaryfit.ui.datas.exercices.ExerciceTabata
import ca.uqac.diaryfit.ui.datas.exercices.ExerciceTime
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class ExerciceFragment(private val ex: Exercice) : DialogFragment(ca.uqac.diaryfit.R.layout.dialog_exercice) {
    //Fragment related elements
    private lateinit var fdialog: Dialog

    //UI
    private lateinit var okBtn : Button
    private lateinit var cancelBtn : Button
    private lateinit var editBtn : ImageButton
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

        tabLayout = view.findViewById(ca.uqac.diaryfit.R.id.exercice_tl) as TabLayout
        pagerView = view.findViewById(ca.uqac.diaryfit.R.id.exercice_pv) as ViewPager2
        adapter = ViewPagerAdapter(childFragmentManager, lifecycle, ex)
        pagerView.adapter = adapter

        val tabsName = arrayOf("Repeat", "Time", "Tabata")
        TabLayoutMediator(tabLayout, pagerView) { tab, position -> tab.text = tabsName[position]}.attach()

        when(ex) {
            is ExerciceTime -> pagerView.currentItem = 0
            is ExerciceRepetition -> pagerView.currentItem = 1
            is ExerciceTabata -> pagerView.currentItem = 2
        }

        okBtn = view.findViewById(ca.uqac.diaryfit.R.id.exercice_bt_ok) as Button
        okBtn.setOnClickListener {
            saveDialog()
        }

        cancelBtn = view.findViewById(ca.uqac.diaryfit.R.id.exercice_bt_cancel) as Button
        cancelBtn.setOnClickListener {
            cancelDialog()
        }

        return view
    }

    companion object {
        const val TAG = "ExerciceFragment"
    }


    //cancel fragment & opération (recover previous datas)
    private fun cancelDialog() {
        fdialog.dismiss()
    }

    //return or save state and terminate fragment
    private fun saveDialog() {
        //TODO return or save state and terminate fragment
        fdialog.dismiss()
    }
}