package ca.uqac.diaryfit.ui.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ca.uqac.diaryfit.R
import ca.uqac.diaryfit.databinding.FragmentMainBinding
import ca.uqac.diaryfit.ui.datas.Exercice
import ca.uqac.diaryfit.ui.rvAdapters.ExerciceCardViewAdapter

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    lateinit var recyclerView: RecyclerView
    lateinit var exerciceAdapter: ExerciceCardViewAdapter
    var exerciceList = ArrayList<Exercice>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val root: View = binding.root


        //populate
        for (i in 1..20) {
            exerciceList.add(Exercice(i%2 == 1))
        }

        recyclerView = root.findViewById(R.id.frgmain_rv) as RecyclerView
        exerciceAdapter = ExerciceCardViewAdapter(exerciceList)
        recyclerView.adapter = exerciceAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}