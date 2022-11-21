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
import ca.uqac.diaryfit.ui.datas.MTime
import ca.uqac.diaryfit.ui.datas.MWeigth

private const val ARG_SECOND = "np_sec"
private const val ARG_MINUTE = "np_min"
private const val ARG_HOUR = "np_hou"


class TimePickerFragment : DialogFragment(R.layout.dialog_time_picker) {
    private var hou: Int = 0
    private var min: Int = 0
    private var sec: Int = 0

    private lateinit var fdialog: Dialog

    lateinit var time_hou_np:NumberPicker
    lateinit var time_min_np:NumberPicker
    lateinit var time_sec_np:NumberPicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            hou = it.getInt(ARG_SECOND)
            min = it.getInt(ARG_MINUTE)
            sec = it.getInt(ARG_HOUR)
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
        val view = inflater.inflate(R.layout.dialog_time_picker, container, false)
        if (view == null) return null

        time_hou_np = view.findViewById(R.id.timepicker_hou_np) as NumberPicker
        time_min_np = view.findViewById(R.id.timepicker_min_np) as NumberPicker
        time_sec_np = view.findViewById(R.id.timepicker_sec_np) as NumberPicker

        time_hou_np.minValue = 0
        time_hou_np.maxValue = 23
        time_hou_np.wrapSelectorWheel = true
        time_hou_np.value = sec

        time_min_np.minValue = 0
        time_min_np.maxValue = 59
        time_min_np.wrapSelectorWheel = true
        time_min_np.value = min

        time_sec_np.minValue = 0
        time_sec_np.maxValue = 59
        time_sec_np.wrapSelectorWheel = true
        time_sec_np.value = hou

        return view
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        setFragmentResult("TimePickerReturn", bundleOf("hou" to time_hou_np.value, "min" to time_min_np.value, "sec" to time_sec_np.value))
    }

    companion object {
        @JvmStatic
        fun newInstance(time:MTime) =
            TimePickerFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECOND, time.getSeconds())
                    putInt(ARG_MINUTE, time.getMinute())
                    putInt(ARG_HOUR, time.getHour())
                }
            }
    }

}