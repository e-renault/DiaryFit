package ca.uqac.diaryfit.ui.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.viewpager2.widget.ViewPager2
import ca.uqac.diaryfit.databinding.FragmentToolsBinding
import ca.uqac.diaryfit.datas.exercices.Exercice
import ca.uqac.diaryfit.datas.exercices.ExerciceTabata
import ca.uqac.diaryfit.datas.exercices.ExerciceTime
import ca.uqac.diaryfit.ui.tool.ToolPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

const val EXERCICE_TOOL_SHORTCUT = "exercice_tool_shortcut"
const val EXERCICE_TOOL_SHORTCUT_ARG = "exercice_tool_shortcut_arg"

val toolsArray = arrayOf(
    "Chrono",
    "Timer",
    "Tabata"
)

class ToolsFragment : Fragment() {
    // This property is only valid between onCreateView and
    // onDestroyView.
    private var _binding: FragmentToolsBinding? = null
    private val binding get() = _binding!!
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragmentResultListener(EXERCICE_TOOL_SHORTCUT) { requestKey, bundle ->
            val ex:Exercice? = bundle.getParcelable(EXERCICE_TOOL_SHORTCUT_ARG)
            if (ex != null) {
                if(ex::class == ExerciceTime::class){
                    viewPager.setCurrentItem(1, false)
                    Toast.makeText(context, "Shortcut clicked (${ex.titleGet()}) = time", Toast.LENGTH_SHORT).show()
                }
                if(ex::class == ExerciceTabata::class){
                    viewPager.setCurrentItem(2, false)
                    Toast.makeText(context, "Shortcut clicked (${ex.titleGet()}) = tabata", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentToolsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewPager = binding.toolsViewPager
        tabLayout = binding.toolsTabLayout

        val adapter = ToolPagerAdapter(childFragmentManager, lifecycle)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = toolsArray[position]
        }.attach()


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}