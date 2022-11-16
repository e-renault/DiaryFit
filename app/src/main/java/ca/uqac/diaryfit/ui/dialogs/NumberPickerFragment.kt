package ca.uqac.diaryfit.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.*
import android.widget.NumberPicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import ca.uqac.diaryfit.R


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_LIST = "list1"

/**
 * A simple [Fragment] subclass.
 * Use the [NumberPickerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NumberPickerFragment : DialogFragment(ca.uqac.diaryfit.R.layout.fragment_number_picker) {
    // TODO: Rename and change types of parameters
    private lateinit var fdialog: Dialog

    private var list: Array<String>? = null

    lateinit var numberPicker:NumberPicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            list = it.getStringArray(ARG_LIST)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?) : Dialog {
        super.onCreateDialog(savedInstanceState)
        fdialog = Dialog(requireContext())
        fdialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        return fdialog
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_number_picker, container, false)
        if (view == null) return null

        numberPicker = view.findViewById(R.id.frag_np) as NumberPicker
        numberPicker.minValue = 0
        numberPicker.maxValue = list!!.size - 1
        numberPicker.displayedValues = list
        numberPicker.wrapSelectorWheel = false

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         * @return A new instance of fragment NumberPickerFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(list: Array<String>) =
            NumberPickerFragment().apply {
                arguments = Bundle().apply {
                    putStringArray(ARG_LIST, list)
                }
            }
    }
}