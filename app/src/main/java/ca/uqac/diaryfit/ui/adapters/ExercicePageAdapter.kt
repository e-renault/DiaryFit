import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import ca.uqac.diaryfit.ui.datas.exercices.Exercice
import ca.uqac.diaryfit.ui.dialogs.NumberPickerFragment
import ca.uqac.diaryfit.ui.dialogs.tabs.ExerciceRepeatFragment
import ca.uqac.diaryfit.ui.dialogs.tabs.ExerciceTabataFragment
import ca.uqac.diaryfit.ui.dialogs.tabs.ExerciceTimeFragment

private const val NUM_TABS = 3

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle,val ex: Exercice) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return ExerciceRepeatFragment(ex)
            1 -> return ExerciceTimeFragment(ex)
            2 -> return ExerciceTabataFragment(ex)
        }
        return NumberPickerFragment()
    }
}