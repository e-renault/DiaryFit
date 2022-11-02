package ca.uqac.diaryfit.ui.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ca.uqac.diaryfit.R
import ca.uqac.diaryfit.databinding.FragmentMainBinding
import ca.uqac.diaryfit.ui.datas.Exercice
import ca.uqac.diaryfit.ui.datas.Session
import ca.uqac.diaryfit.ui.dialogs.ExerciceFragment
import ca.uqac.diaryfit.ui.rvAdapters.ExerciceCardViewAdapter

class MainFragment : Fragment() {
    // This property is only valid between onCreateView and onDestroyView.
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    //UI
    private lateinit var recyclerView: RecyclerView
    private lateinit var exerciceAdapter: ExerciceCardViewAdapter

    //datas
    //TODO should be generated from database
    var session = Session()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val root: View = binding.root

        recyclerView = root.findViewById(R.id.frgmain_rv) as RecyclerView
        exerciceAdapter = ExerciceCardViewAdapter(session.exerciceList, this)
        recyclerView.adapter = exerciceAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun editExercice(ex: Exercice) {
        ExerciceFragment(ex).show(childFragmentManager, ExerciceFragment.TAG)
    }
}