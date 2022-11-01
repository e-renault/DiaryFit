package ca.uqac.diaryfit.ui.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ca.uqac.diaryfit.R
import ca.uqac.diaryfit.databinding.FragmentCalendarBinding
import ca.uqac.diaryfit.ui.datas.Exercice
import ca.uqac.diaryfit.ui.dialogs.EditSessionFragment
import ca.uqac.diaryfit.ui.rvAdapters.ExerciceCardViewAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CalendarFragment : Fragment() {

    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!

    lateinit var addSession_fab: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        val root: View = binding.root

        addSession_fab = root.findViewById(R.id.frgcalendar_fab)
        addSession_fab.setOnClickListener { newSession() }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun newSession() {
        EditSessionFragment().show(childFragmentManager, EditSessionFragment.TAG)
    }

}