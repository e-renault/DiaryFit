package ca.uqac.diaryfit.ui.dialogs

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.NumberPicker
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import ca.uqac.diaryfit.R

private const val ARG_INDEX = "np_index"
private const val ARG_MIN = "np_min"
private const val ARG_MAX = "np_max"
private const val ARG_NP_RET = "np_ret"


class NumberPickerFragment : DialogFragment(R.layout.dialog_number_picker) {
    private var index: Int = 0
    private var min: Int = 0
    private var max: Int = 0
    private var retARG: String = "Error"

    private lateinit var fdialog: Dialog
    lateinit var numberPicker:NumberPicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            index = it.getInt(ARG_INDEX)
            min = it.getInt(ARG_MIN)
            max = it.getInt(ARG_MAX)
            retARG = it.getString(ARG_NP_RET).toString()
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
        val view = inflater.inflate(R.layout.dialog_number_picker, container, false)
        if (view == null) return null

        numberPicker = view.findViewById(R.id.numberpicker_np) as NumberPicker
        numberPicker.minValue = min
        numberPicker.maxValue = max
        numberPicker.value = index
        numberPicker.wrapSelectorWheel = false

        return view
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        setFragmentResult(retARG, bundleOf("value" to numberPicker.value))
    }

    companion object {
        @JvmStatic
        fun newInstance(min: Int, max: Int, index: Int, argRet:String) =
            NumberPickerFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_INDEX, index)
                    putInt(ARG_MIN, min)
                    putInt(ARG_MAX, max)
                    putString(ARG_NP_RET, argRet)
                }
            }
    }
}