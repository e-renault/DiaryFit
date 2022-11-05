package ca.uqac.diaryfit.ui.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ca.uqac.diaryfit.R
import ca.uqac.diaryfit.ui.datas.Session
import ca.uqac.diaryfit.ui.tabs.MainFragment

class TodaySessionCardViewAdapter(val dataSet: List<Session>,
                                  val parentActivity: MainFragment
) : RecyclerView.Adapter<TodaySessionCardViewAdapter.ExerciceViewHolder>() {

    private lateinit var exerciceAdapter: ExerciceCardViewAdapter

    class ExerciceViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title_et: TextView = view.findViewById(R.id.cw_todaysess_tv1)
        val done_cb: CheckBox = view.findViewById(R.id.cw_todaysess_cb_main)
        val exercicelist_rv: RecyclerView = view.findViewById(R.id.cw_todaysess_rv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_today_session, parent, false)
        return ExerciceViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ExerciceViewHolder, index: Int) {
        viewHolder.title_et.text = dataSet[index].getTitle()

        System.out.println(dataSet.size)
        System.out.println(index)
        //TODO retrieve today session
        val session:Session = dataSet[index]
        exerciceAdapter = ExerciceCardViewAdapter(session.exerciceList, parentActivity)
        viewHolder.exercicelist_rv.adapter = exerciceAdapter
        viewHolder.exercicelist_rv.layoutManager = LinearLayoutManager(parentActivity.context)
    }

    override fun getItemCount() = dataSet.size
}