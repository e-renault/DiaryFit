package ca.uqac.diaryfit.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import ca.uqac.diaryfit.datas.MTime
import ca.uqac.diaryfit.datas.MWeigth
import ca.uqac.diaryfit.ui.dialogs.tabs.ExerciceRepeatFragment
import ca.uqac.diaryfit.ui.dialogs.tabs.ExerciceTabataFragment
import ca.uqac.diaryfit.ui.dialogs.tabs.ExerciceTimeFragment

private const val NUM_TABS = 3

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle,
                       private val nbRep:Int,
                       private val listEx:ArrayList<Int>,
                       private val nbSerie:Int,
                       private val rest: MTime,
                       private val work: MTime,
                       private val weight: MWeigth,
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {ExerciceRepeatFragment.newInstance1(nbSerie, nbRep, weight, rest)}
            1 -> {ExerciceTimeFragment.newInstance2(nbSerie, weight, rest, work)}
            else -> {ExerciceTabataFragment.newInstance3(nbSerie, listEx, rest, work)}
        }
    }
}