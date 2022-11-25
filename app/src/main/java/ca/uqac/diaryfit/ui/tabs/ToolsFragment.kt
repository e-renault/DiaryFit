package ca.uqac.diaryfit.ui.tabs

import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Chronometer
import android.widget.Toast
import androidx.fragment.app.Fragment
import ca.uqac.diaryfit.R
import ca.uqac.diaryfit.databinding.FragmentToolsBinding
import ca.uqac.diaryfit.ui.tool.ToolPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator


val animalsArray = arrayOf(
    "Chrono",
    "Timer",
    "Tabata"
)

class ToolsFragment : Fragment() {
    // This property is only valid between onCreateView and
    // onDestroyView.
    private var _binding: FragmentToolsBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentToolsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val viewPager = binding.toolsViewPager
        val tabLayout = binding.toolsTabLayout

        val adapter = ToolPagerAdapter(childFragmentManager, lifecycle)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = animalsArray[position]
        }.attach()

        /**/



        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}