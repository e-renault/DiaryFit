package ca.uqac.diaryfit.ui.tabs

import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore.Images
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import ca.uqac.diaryfit.MainActivity
import ca.uqac.diaryfit.R
import ca.uqac.diaryfit.UserDB
import ca.uqac.diaryfit.UserDB.getSession
import ca.uqac.diaryfit.databinding.FragmentStatsBinding
import ca.uqac.diaryfit.datas.Session
import ca.uqac.diaryfit.datas.exercices.ExerciceRepetition
import ca.uqac.diaryfit.datas.exercices.ExerciceTime
import com.google.android.material.floatingactionbutton.FloatingActionButton
import im.dacer.androidcharts.LineView
import java.io.*
import java.util.*


private const val WEIGTH_STAT_TYPE = 0
private const val TIME_STAT_TYPE = 1
private const val REPETITION_STAT_TYPE = 2

private const val SEGEMENT_SIZE_TO_SHOW = 14

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
    private lateinit var share_bt: FloatingActionButton
    private lateinit var stat_layout_cl: ConstraintLayout


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
            arrayListOf(getString(R.string.month))
        )
        chooseTimePeriode.adapter = chooseTimePeriodeAdapter
        chooseTimePeriode.setSelection(0,false)
        chooseTimePeriode.onItemSelectedListener = this

        chooseType = root.findViewById(ca.uqac.diaryfit.R.id.frg_stats_sp_choose_type) as Spinner
        val chooseTypeArrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
            root.context,
            android.R.layout.simple_spinner_dropdown_item,
            arrayListOf(getString(R.string.Weight), getString(R.string.Time), getString(R.string.Repetition))
        )
        chooseType.adapter = chooseTypeArrayAdapter
        chooseType.setSelection(0,false)
        chooseType.onItemSelectedListener = this

        hsv = root.findViewById(ca.uqac.diaryfit.R.id.frg_stats_hsv) as HorizontalScrollView

        lineview = root.findViewById(ca.uqac.diaryfit.R.id.line_view) as LineView
        lineview.setDrawDotLine(false) //optional
        lineview.setShowPopup(LineView.SHOW_POPUPS_MAXMIN_ONLY) //optional
        lineview.setColorArray(intArrayOf(ca.uqac.diaryfit.R.color.secondaryColor))

        updateTimePeriod()

        share_bt = root.findViewById(R.id.stats_share_fab)
        share_bt.setOnClickListener {
            val screenShare = getBitmapFromView(stat_layout_cl, Color.WHITE)
            shareImage(screenShare)
        }

        stat_layout_cl = root.findViewById(R.id.stat_layout_cl)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemSelected(parent:AdapterView<*>?, view:View?, position:Int, id:Long) {
        when(parent?.id) {
            ca.uqac.diaryfit.R.id.frg_stats_sp_choose_type -> {
                StatType = when(position) {
                    0 -> WEIGTH_STAT_TYPE
                    1 -> TIME_STAT_TYPE
                    2 -> REPETITION_STAT_TYPE
                    else -> -1
                }
                updateType()
            }

            ca.uqac.diaryfit.R.id.frg_stats_sp_chooseexercice -> {
                ExerciceID = position
                updateData()
            }
            ca.uqac.diaryfit.R.id.frg_stats_sp_timeperiode -> {
                TimePeriode = position
                updateTimePeriod()
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) { }

    private var ExerciceID = 0
    private var StatType = 0
    private var TimePeriode = 0

    private var dataListWeigth:ArrayList<ArrayList<Float>> = ArrayList()
    private var dataListTime:ArrayList<ArrayList<Int>> = ArrayList()
    private var dataListRepetition:ArrayList<ArrayList<Int>> = ArrayList()


    private fun updateTimePeriod() {
        var strList:ArrayList<String> = ArrayList<String>()
        val month = Calendar.getInstance().get(Calendar.MONTH)
        val year = Calendar.getInstance().get(Calendar.YEAR)

        val monthlist = arrayListOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")

        for (i in -SEGEMENT_SIZE_TO_SHOW .. 0) {
            val index = Math.floorMod(i+month, 12)
            strList.add(monthlist.get(index))
        }

        lineview.setBottomTextList(strList)

        updateData()
    }

    private fun updateData() {

        var weights_list:ArrayList<Float> = ArrayList()
        var times_list:ArrayList<Int> = ArrayList()
        var repetitions_list:ArrayList<Int> = ArrayList()

        val current_month = Calendar.getInstance().get(Calendar.MONTH)
        val current_year = Calendar.getInstance().get(Calendar.YEAR)

        for (month_incr:Int in -SEGEMENT_SIZE_TO_SHOW..0) {
            val month: ArrayList<Session> = ArrayList()
            val month_index = Math.floorMod(current_month + month_incr, 12) +1
            val year_index = if (month_index == current_month + month_incr +1) current_year else current_year-1

            for (day_incr in 1..31) {
                month.addAll(
                    getSession(
                        MainActivity.profil,
                        "%04d-%02d-%02d".format(year_index, month_index, day_incr)
                    ) as ArrayList<Session>
                )
            }
            var weigth_counter:Int = 0
            var weigth_sum:Float = 0.0F

            var repetition_counter:Int = 0
            var repetition_sum:Int = 0

            var time_counter:Int = 0
            var time_sum:Int = 0

            for (session in month) {
                for (exercice in session.getExerciceList()) {
                    if (exercice is ExerciceRepetition) {
                        if (ExerciceID == exercice.exerciceNameID) {
                            weigth_sum += exercice.weigth.weightkgGet()
                            weigth_counter++

                            repetition_sum += exercice.nbRepetition
                            repetition_counter++
                        }
                    } else if (exercice is ExerciceTime) {
                        if (ExerciceID == exercice.exerciceNameID) {
                            weigth_sum += exercice.weigth.weightkgGet()
                            weigth_counter++

                            time_sum += exercice.effortTime.timeInSec
                            time_counter++
                        }
                    }
                }
            }
            val weigth_average = if (weigth_counter != 0) weigth_sum/weigth_counter else 0.0F
            val time_average = if (time_counter != 0) time_sum/time_counter else 0
            val repetition_average = if (repetition_counter != 0) repetition_sum/repetition_counter else 0
            weights_list.add(weigth_average)
            times_list.add(time_average)
            repetitions_list.add(repetition_average)
        }

        dataListWeigth.clear()
        dataListTime.clear()
        dataListRepetition.clear()

        dataListWeigth.add(weights_list)
        dataListTime.add(times_list)
        dataListRepetition.add(repetitions_list)

        updateType()
    }

    private fun updateType() {
        when (StatType) {
            WEIGTH_STAT_TYPE -> lineview.setFloatDataList(dataListWeigth)
            TIME_STAT_TYPE -> lineview.setDataList(dataListTime)
            REPETITION_STAT_TYPE -> lineview.setDataList(dataListRepetition)
        }
    }

    fun getBitmapFromView(view: View, defaultColor: Int): Bitmap {
        val bitmap = Bitmap.createBitmap(
            view.width, view.height, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        canvas.drawColor(defaultColor)
        view.draw(canvas)
        return bitmap
    }

    fun shareImage(image: Bitmap) {
        val values = ContentValues()
        values.put(Images.Media.TITLE, "title")
        values.put(Images.Media.MIME_TYPE, "image/jpeg")
        val uri: Uri? = context?.contentResolver?.insert(
            Images.Media.EXTERNAL_CONTENT_URI,
            values
        )

        val outstream: OutputStream?
        try {
            outstream = uri?.let { context?.contentResolver?.openOutputStream(it) }
            image.compress(CompressFormat.JPEG, 100, outstream)
            outstream?.close()
        } catch (e: Exception) {
            System.err.println(e.toString())
        }

        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, uri)
            type = "image/jpeg"
        }
        startActivity(Intent.createChooser(shareIntent, "Share Image"))






    }
}