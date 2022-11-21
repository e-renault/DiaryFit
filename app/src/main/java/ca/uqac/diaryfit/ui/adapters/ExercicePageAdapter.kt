import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import ca.uqac.diaryfit.ui.datas.MTime
import ca.uqac.diaryfit.ui.datas.MWeigth
import ca.uqac.diaryfit.ui.dialogs.tabs.ExerciceRepeatFragment
import ca.uqac.diaryfit.ui.dialogs.tabs.ExerciceTabataFragment
import ca.uqac.diaryfit.ui.dialogs.tabs.ExerciceTimeFragment

private const val NUM_TABS = 3

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle,
                       val nbRep:Int,
                       val listEx:IntArray,
                       val nbSerie:Int,
                       val rest:MTime,
                       val work:MTime,
                       val weight:MWeigth,
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        if (position == 0) {
            return ExerciceRepeatFragment.newInstance1(nbSerie, nbRep, weight, rest)
        } else if (position == 1) {
            return ExerciceTimeFragment.newInstance2(nbSerie, weight, rest, work)
        } else {//if (position == 2) {
            return ExerciceTabataFragment.newInstance3(nbSerie, listEx, rest, work)
        }
    }
}