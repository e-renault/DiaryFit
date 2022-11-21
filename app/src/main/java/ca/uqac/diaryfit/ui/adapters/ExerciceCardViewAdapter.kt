package ca.uqac.diaryfit.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import ca.uqac.diaryfit.R
import ca.uqac.diaryfit.ui.datas.MDatabase
import ca.uqac.diaryfit.ui.datas.exercices.Exercice
import ca.uqac.diaryfit.ui.dialogs.ExerciceFragment
import ca.uqac.diaryfit.ui.tabs.MainFragment

class ExerciceCardViewAdapter(private var dataSet: ArrayList<Exercice>,
                              val sessionID: Int,
                              val fm: FragmentManager,
                              val lifecycle: LifecycleOwner
) : RecyclerView.Adapter<ExerciceCardViewAdapter.ExerciceViewHolder>() {

    class ExerciceViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val title_et: TextView = view.findViewById(R.id.cw_exercice_editsession_tv_title)
        val content_et: TextView = view.findViewById(R.id.cw_exercice_editsession_tv_sub)
        val done_cb: CheckBox = view.findViewById(R.id.cw_exercice_session_cb)
        val tool_bt: ImageButton = view.findViewById(R.id.cw_exercice_session_ib_tool)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_exercice_session, parent, false)

        return ExerciceViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ExerciceViewHolder, exerciceID: Int) {
        val ex: Exercice? = MDatabase.getExercice(sessionID, exerciceID)
        if (ex != null) {
            viewHolder.title_et.text = ex.getTitle()
            viewHolder.content_et.text = ex.getDescription()
            viewHolder.tool_bt.visibility =
                if (ex.hasTool() == true && !ex.isDone) View.VISIBLE else View.GONE
            viewHolder.done_cb.isChecked = ex.isDone == true
            viewHolder.done_cb.setOnClickListener {
                dataSet[exerciceID].isDone = viewHolder.done_cb.isChecked
                viewHolder.tool_bt.visibility =
                    if (!dataSet[exerciceID].isDone) View.VISIBLE else View.GONE
            }
            viewHolder.view.setOnClickListener {
                ExerciceFragment.editExercice(ex).show(fm, ExerciceFragment.TAG)
            }
            fm.setFragmentResultListener("ExerciceDialogReturn", lifecycle) {
                    requestKey, bundle ->
                val result = bundle.getParcelable<Exercice>("Exercice")

                if (result != null) {
                    MDatabase.setExercice(sessionID, exerciceID, result)
                    dataSet.set(exerciceID,result)
                    Toast.makeText(viewHolder.done_cb.context, "updated!", Toast.LENGTH_SHORT).show()
                    System.out.println(result.getDescription())
                    System.out.println(dataSet[exerciceID].getDescription())
                }
            }
        } else {
            //TODO Error check
        }
    }

    override fun getItemCount() = dataSet.size
}