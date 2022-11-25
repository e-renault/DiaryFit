package ca.uqac.diaryfit.ui.tool
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import ca.uqac.diaryfit.ui.tool.Chrono
import ca.uqac.diaryfit.ui.tool.Timer
import ca.uqac.diaryfit.ui.tool.Tabata


private const val NUM_TABS = 3

class ToolPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return Chrono()
            1 -> return Timer()
        }
        return Tabata()
    }
}