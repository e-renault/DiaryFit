package ca.uqac.diaryfit.ui.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ca.uqac.diaryfit.R
import ca.uqac.diaryfit.databinding.FragmentCalendarBinding
import ca.uqac.diaryfit.ui.dialogs.EditSessionDialogFragment
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

        addSessionFAB.setOnClickListener { newSession() }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun newSession() {
        //TODO new Session
        EditSessionDialogFragment.newInstance(-1).show(childFragmentManager, EditSessionDialogFragment.TAG)
    }

}