package ca.uqac.diaryfit.ui.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import ca.uqac.diaryfit.MainActivity
import ca.uqac.diaryfit.UserDB
import ca.uqac.diaryfit.androidcharts.LineView
import ca.uqac.diaryfit.databinding.FragmentStatsBinding
import kotlin.random.Random


class StatsFragment :
    Fragment(),
    AdapterView.OnItemSelectedListener {
    private var _binding: FragmentStatsBinding? = null
    private val binding get() = _binding!!

    //UI
    private lateinit var chooseExercice: Spinner
    private lateinit var chooseTimePeriode: Spinner
    private lateinit var chooseType: Spinner
    private lateinit var hsv: HorizontalScrollView
    private lateinit var lineview: LineView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatsBinding.inflate(inflater, container, false)
        val root: View = binding.root


        chooseExercice = root.findViewById(ca.uqac.diaryfit.R.id.frg_stats_sp_chooseexercice) as Spinner
        val chooseExerciceArrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
            root.context,
            android.R.layout.simple_spinner_dropdown_item,
            UserDB.getExerciceList(MainActivity.profil)
        )
        chooseExercice.adapter = chooseExerciceArrayAdapter
        chooseExercice.setSelection(0,false)
        chooseExercice.onItemSelectedListener = this

        chooseTimePeriode = root.findViewById(ca.uqac.diaryfit.R.id.frg_stats_sp_timeperiode) as Spinner
        val chooseTimePeriodeAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
            root.context,
            android.R.layout.simple_spinner_dropdown_item,
            arrayListOf("Month")
        )
        chooseTimePeriode.adapter = chooseTimePeriodeAdapter
        chooseTimePeriode.setSelection(0,false)
        chooseTimePeriode.onItemSelectedListener = this

        chooseType = root.findViewById(ca.uqac.diaryfit.R.id.frg_stats_sp_choose_type) as Spinner
        val chooseTypeArrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
            root.context,
            android.R.layout.simple_spinner_dropdown_item,
            arrayListOf("Weigth")
        )
        chooseType.adapter = chooseTypeArrayAdapter
        chooseType.setSelection(0,false)
        chooseType.onItemSelectedListener = this

        hsv = root.findViewById(ca.uqac.diaryfit.R.id.frg_stats_hsv) as HorizontalScrollView

        lineview = root.findViewById(ca.uqac.diaryfit.R.id.line_view) as LineView
        lineview.setDrawDotLine(false) //optional
        lineview.setShowPopup(LineView.SHOW_POPUPS_MAXMIN_ONLY) //optional
        lineview.setColorArray(intArrayOf(ca.uqac.diaryfit.R.color.secondaryColor))

        updateGraph()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemSelected(parent:AdapterView<*>?, view:View?, position:Int, id:Long) {
        Toast.makeText(context, "onItemSelected ${view == null} ${position}, ${id}", Toast.LENGTH_SHORT).show()
        updateGraph()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) { }


    fun updateGraph() {
        var strList:ArrayList<String> = ArrayList<String>()
        strList.addAll(arrayListOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"))
        lineview.setBottomTextList(strList)

        var dataLists:ArrayList<ArrayList<Float>> = ArrayList<ArrayList<Float>>()
        var dataList1:ArrayList<Float> = ArrayList<Float>()
        dataList1.addAll(List(12) { Random.nextFloat()*10 })
        dataLists.add(dataList1)
        lineview.setFloatDataList(dataLists)
        // lineview.setDataList(dataLists) //for int only
    }
}