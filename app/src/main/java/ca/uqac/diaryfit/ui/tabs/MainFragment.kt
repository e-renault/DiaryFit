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
import ca.uqac.diaryfit.ui.datas.MDatabase
import ca.uqac.diaryfit.ui.datas.exercices.Exercice
import ca.uqac.diaryfit.ui.dialogs.ExerciceFragment
import ca.uqac.diaryfit.ui.Adapters.TodaySessionCardViewAdapter

class MainFragment : Fragment() {
    // This property is only valid between onCreateView and onDestroyView.
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    //UI
    private lateinit var recyclerView: RecyclerView
    private lateinit var exerciceAdapter: TodaySessionCardViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val root: View = binding.root


        //TODO retrieve today sessions
        //TODO implement double recycleview
        recyclerView = root.findViewById(R.id.frgmain_rv) as RecyclerView
        exerciceAdapter = TodaySessionCardViewAdapter(MDatabase.db.Sessions, this)
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