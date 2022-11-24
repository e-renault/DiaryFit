package ca.uqac.diaryfit.ui.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.Toast
import androidx.fragment.app.Fragment
import ca.uqac.diaryfit.R
import ca.uqac.diaryfit.databinding.FragmentCalendarBinding
import ca.uqac.diaryfit.ui.dialogs.EditSessionDialogFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class CalendarFragment : Fragment() {
    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!

    private lateinit var calendar : CalendarView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        val root: View = binding.root

        calendar = root.findViewById<CalendarView>(R.id.vw_calendar)

        displayDate()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun displayDate(){
        calendar.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val date = "Selected date is "+ dayOfMonth + "/" + (month+1) +"/"+year
            Toast.makeText(requireActivity(),date,Toast.LENGTH_SHORT).show()
        }
    }

}