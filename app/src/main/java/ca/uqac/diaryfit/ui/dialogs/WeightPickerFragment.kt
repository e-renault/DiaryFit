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
import ca.uqac.diaryfit.ui.datas.MWeigth

private const val ARG_WEIGHT_VAL = "np_weight"
private const val ARG_WEIGHT_DIV = "np_weight_div"
private const val ARG_ISKG = "np_iskg"
private const val ARG_WEIGHT_RET = "np_ret"


class WeightPickerFragment : DialogFragment(R.layout.dialog_weight_picker) {
    private var weight: Int = 0
    private var weight_div: Int = 0
    private var iskg: Boolean = true
    private var retARG: String = "Error"

    private lateinit var fdialog: Dialog

    lateinit var weightPicker:NumberPicker
    lateinit var weight_div_Picker:NumberPicker
    lateinit var weight_unit_Picker:NumberPicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            weight = it.getInt(ARG_WEIGHT_VAL)
            weight_div = it.getInt(ARG_WEIGHT_DIV)
            iskg = it.getBoolean(ARG_ISKG)
            retARG = it.getString(ARG_WEIGHT_RET).toString()
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
        val view = inflater.inflate(R.layout.dialog_weight_picker, container, false)
        if (view == null) return null

        weightPicker = view.findViewById(R.id.timepicker_hou_np) as NumberPicker
        weight_div_Picker = view.findViewById(R.id.weightpicker_div_np) as NumberPicker
        weight_unit_Picker = view.findViewById(R.id.timepicker_min_np) as NumberPicker

        weightPicker.minValue = 0
        weightPicker.maxValue = 600
        weightPicker.wrapSelectorWheel = false
        weightPicker.value = weight

        weight_div_Picker.minValue = 0
        weight_div_Picker.maxValue = 9
        weight_div_Picker.wrapSelectorWheel = false
        weight_div_Picker.value = weight_div

        weight_unit_Picker.minValue = 0
        weight_unit_Picker.maxValue = 1
        weight_unit_Picker.displayedValues = MWeigth.unitList
        weight_unit_Picker.wrapSelectorWheel = false
        weight_unit_Picker.value = if(iskg) 0 else 1

        return view
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        val ret:Float = weightPicker.value + weight_div_Picker.value.toFloat()/10
        val unit:Boolean = weight_unit_Picker.value == 0
        setFragmentResult(retARG, bundleOf("weight" to ret, "unit" to unit))
    }

    companion object {
        @JvmStatic
        fun newInstance(weight:MWeigth, retARG:String) =
            WeightPickerFragment().apply {
                arguments = Bundle().apply {
                    val w0 = if (weight.isKG) { weight.getWeightkg() } else { weight.getWeigthlb() }
                    val w1 = w0.toInt()
                    val w2 = (w0%1 *10).toInt()

                    putInt(ARG_WEIGHT_VAL, w1)
                    putInt(ARG_WEIGHT_DIV, w2)
                    putBoolean(ARG_ISKG, weight.isKG)
                    putString(ARG_WEIGHT_RET, retARG)
                }
            }
    }
}