package ca.uqac.diaryfit.ui.tool

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ca.uqac.diaryfit.R
import ca.uqac.diaryfit.databinding.FragmentTabataBinding

class Tabata : Fragment() {

    private var _binding: FragmentTabataBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTabataBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
}