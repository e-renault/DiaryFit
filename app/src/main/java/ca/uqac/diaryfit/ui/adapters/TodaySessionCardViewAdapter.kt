package ca.uqac.diaryfit.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ca.uqac.diaryfit.R
import ca.uqac.diaryfit.ui.datas.Session
import ca.uqac.diaryfit.ui.datas.exercices.Exercice
import ca.uqac.diaryfit.ui.tabs.MainFragment

class TodaySessionCardViewAdapter(val dataSet: List<Session>,
                                  val parentActivity: MainFragment
) : RecyclerView.Adapter<TodaySessionCardViewAdapter.ExerciceViewHolder>() {

    private lateinit var exerciceAdapter: ExerciceCardViewAdapter
    private var isCollapsed = false

    class ExerciceViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title_et: TextView = view.findViewById(R.id.cw_session_display_tv1)
        val done_cb: CheckBox = view.findViewById(R.id.cw_session_display_cb_main)
        val exercicelist_rv: RecyclerView = view.findViewById(R.id.cw_session_display_rv)
        val collaps_bt: ImageButton = view.findViewById(R.id.cw_session_display_ib_collaps)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_session_display, parent, false)
        return ExerciceViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ExerciceViewHolder, index: Int) {
        viewHolder.title_et.text = dataSet[index].getTitle()

        //TODO retrieve today session
        val session:Session = dataSet[index]
        exerciceAdapter = ExerciceCardViewAdapter(session.exerciceList, parentActivity)
        viewHolder.exercicelist_rv.adapter = exerciceAdapter
        viewHolder.exercicelist_rv.layoutManager = LinearLayoutManager(parentActivity.context)

        var status = true
        for (ex: Exercice in dataSet[index].exerciceList) {
            if (!ex.isDone) { status = false; break; }
        }
        viewHolder.done_cb.isChecked =status

            viewHolder.done_cb.setOnClickListener {
            for (ex: Exercice in dataSet[index].exerciceList) {
                ex.isDone = viewHolder.done_cb.isChecked
                viewHolder.exercicelist_rv.getAdapter()!!.notifyDataSetChanged()
            }
        }

        viewHolder.collaps_bt.setOnClickListener {
            val params = viewHolder.exercicelist_rv.layoutParams
            params.height =  if (isCollapsed) ViewGroup.LayoutParams.WRAP_CONTENT else 0
            viewHolder.exercicelist_rv.layoutParams = params
            isCollapsed = !isCollapsed
        }
    }

    override fun getItemCount() = dataSet.size
}