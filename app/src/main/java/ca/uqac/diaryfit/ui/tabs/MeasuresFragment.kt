package ca.uqac.diaryfit.ui.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import ca.uqac.diaryfit.R
import ca.uqac.diaryfit.databinding.FragmentMeasuresBinding
import ca.uqac.diaryfit.ui.dialogs.EditSessionDialogFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MeasuresFragment : Fragment() {
    // This property is only valid between onCreateView and
    // onDestroyView.
    private var _binding: FragmentMeasuresBinding? = null
    private val binding get() = _binding!!

    private lateinit var addSessionFAB: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMeasuresBinding.inflate(inflater, container, false)
        val root: View = binding.root

        addSessionFAB = root.findViewById(R.id.measure_test_bt)
        addSessionFAB.setOnClickListener { newSession() }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun newSession() {
        //TODO new Session
        EditSessionDialogFragment.newInstance(0).show(childFragmentManager, EditSessionDialogFragment.TAG)
    }
}