package ca.uqac.diaryfit.ui.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.Toast
import androidx.fragment.app.Fragment
import ca.uqac.diaryfit.MainActivity
import ca.uqac.diaryfit.R
import ca.uqac.diaryfit.UserDB
import ca.uqac.diaryfit.databinding.FragmentCalendarBinding
import ca.uqac.diaryfit.datas.MDate
import ca.uqac.diaryfit.datas.Session
import ca.uqac.diaryfit.ui.dialogs.ARG_SESSION_DIALOG_RET
import ca.uqac.diaryfit.ui.dialogs.ARG_SESSION_NEW
import ca.uqac.diaryfit.ui.dialogs.EditSessionDialogFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CalendarFragment : Fragment() {
    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!

    private lateinit var calendar : CalendarView
    private lateinit var add_btn : FloatingActionButton

    private var selectedDate:MDate = MDate.getTodayDate()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        childFragmentManager.setFragmentResultListener(ARG_SESSION_DIALOG_RET, this) {
                requestKey, bundle ->
            val new = bundle.getParcelable<Session>(ARG_SESSION_NEW)

            if (new != null) {
                UserDB.addSession(MainActivity.profil, new.timeDate, new)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        val root: View = binding.root

        calendar = root.findViewById(R.id.vw_calendar)
        getDate()

        add_btn = root.findViewById(R.id.frgcalendar_fab)
        add_btn.setOnClickListener {
            newSession()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun getDate(){
        calendar.setOnDateChangeListener { view, year, month, dayOfMonth ->
            selectedDate = MDate(year, month, dayOfMonth)
            Toast.makeText(requireActivity(), selectedDate.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    fun newSession() {
        val session:Session = Session()
        session.timeDate = selectedDate.toString()
        EditSessionDialogFragment.editSessionInstance(session, ARG_SESSION_NEW)
            .show(childFragmentManager, EditSessionDialogFragment.TAG)
    }
}