package ca.uqac.diaryfit.ui.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import ca.uqac.diaryfit.MainActivity
import ca.uqac.diaryfit.R
import ca.uqac.diaryfit.UserDB
import ca.uqac.diaryfit.androidcharts.LineView
import ca.uqac.diaryfit.databinding.FragmentStatsBinding


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

    //Data
    private var strList:ArrayList<String> = ArrayList<String>()
    private var dataLists:ArrayList<ArrayList<Int>> = ArrayList<ArrayList<Int>>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //TODO implement real stats (Erwan)

        chooseExercice = root.findViewById(ca.uqac.diaryfit.R.id.frg_stats_sp_chooseexercice) as Spinner
        val chooseExerciceArrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
            root.context,
            android.R.layout.simple_spinner_dropdown_item,
            UserDB.getExerciceList(MainActivity.profil)
        )
        chooseExercice.adapter = chooseExerciceArrayAdapter
        chooseExercice.onItemSelectedListener = this

        chooseTimePeriode = root.findViewById(ca.uqac.diaryfit.R.id.frg_stats_sp_timeperiode) as Spinner
        val chooseTimePeriodeAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
            root.context,
            android.R.layout.simple_spinner_dropdown_item,
            arrayListOf("Year", "Month", "Week")
        )
        chooseTimePeriode.adapter = chooseTimePeriodeAdapter
        chooseTimePeriode.onItemSelectedListener = this

        chooseType = root.findViewById(ca.uqac.diaryfit.R.id.frg_stats_sp_choose_type) as Spinner
        val chooseTypeArrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
            root.context,
            android.R.layout.simple_spinner_dropdown_item,
            arrayListOf("Repetition", "Weigth")
        )
        chooseType.adapter = chooseTypeArrayAdapter
        chooseType.onItemSelectedListener = this

        hsv = root.findViewById(ca.uqac.diaryfit.R.id.frg_stats_hsv) as HorizontalScrollView

        strList.addAll(arrayListOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"))
        var dataList1:ArrayList<Int> = ArrayList<Int>()
        dataList1.addAll(arrayListOf(20, 19, 22, 25, 30, 31, 32, 38, 42, 36, 37, 43))
        dataLists.add(dataList1)

        lineview = root.findViewById(ca.uqac.diaryfit.R.id.line_view) as LineView
        lineview.setDrawDotLine(false) //optional
        lineview.setShowPopup(LineView.SHOW_POPUPS_MAXMIN_ONLY) //optional
        lineview.setBottomTextList(strList)
        lineview.setColorArray(intArrayOf(ca.uqac.diaryfit.R.color.secondaryColor))
        lineview.setDataList(dataLists) //or lineView.setFloatDataList(floatDataLists)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        Toast.makeText(context, "new settings", Toast.LENGTH_SHORT).show()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        Toast.makeText(context, "Nothing changed", Toast.LENGTH_SHORT).show()
    }
}