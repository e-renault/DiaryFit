package ca.uqac.diaryfit.ui.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ca.uqac.diaryfit.R
import ca.uqac.diaryfit.databinding.FragmentCalendarBinding
import ca.uqac.diaryfit.ui.dialogs.EditSessionFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CalendarFragment : Fragment() {
    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!

    private lateinit var addSessionFAB: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        val root: View = binding.root

        addSessionFAB = root.findViewById(R.id.frgcalendar_fab)
        addSessionFAB.setOnClickListener { newSession() }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun newSession() {
        //AddSessionFragment().show(childFragmentManager, AddSessionFragment.TAG)
        EditSessionFragment().show(childFragmentManager, EditSessionFragment.TAG)
    }

}