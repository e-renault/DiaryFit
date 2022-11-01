package ca.uqac.diaryfit.ui.dialogs

import android.R
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ca.uqac.diaryfit.ui.datas.Exercice
import ca.uqac.diaryfit.ui.rvAdapters.EditSessionCardViewAdapter


class EditSessionFragment : DialogFragment(ca.uqac.diaryfit.R.layout.dialog_edit_session) {
    lateinit var recyclerView: RecyclerView
    lateinit var exerciceAdapter: EditSessionCardViewAdapter
    var exerciceList = ArrayList<Exercice>()

    override fun onCreateDialog(savedInstanceState: Bundle?) : Dialog {
        super.onCreateDialog(savedInstanceState);

        val dialog = Dialog(requireContext(),R.style.Theme_Material_Light_NoActionBar_Fullscreen)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)

        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        if (view != null) {
            for (i in 1..20) {
                exerciceList.add(Exercice(i%2 == 0))
            }

            recyclerView = view.findViewById(ca.uqac.diaryfit.R.id.editsession_rv_exercicelist) as RecyclerView
            exerciceAdapter = EditSessionCardViewAdapter(exerciceList)
            recyclerView.adapter = exerciceAdapter
            recyclerView.layoutManager = LinearLayoutManager(context)
        }
        return view
    }

    companion object {
        const val TAG = "EditSessionFragment"
    }
}