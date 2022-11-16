package ca.uqac.diaryfit.ui.dialogs.tabs

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.viewpager.widget.ViewPager

class AdjustingViewPager: ViewPager {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) :     super(context, attrs)
    private lateinit var currentView:View

    fun measureCurrentView(view: View) {
        this.currentView = view
        requestLayout()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var newViewHeight = heightMeasureSpec
        val mode = MeasureSpec.getMode(heightMeasureSpec)
        /* until the parent has not assigned the size to the view
           and it is either not determined or at its maximum, assign it
           the new measurement as it comes in focus
         */
        if (mode == MeasureSpec.UNSPECIFIED || mode == MeasureSpec.AT_MOST) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
            if (currentView == null) {
                super.onMeasure(widthMeasureSpec, heightMeasureSpec)
                return
            }
            try {
                /* measuring the current dimensions */
                currentView.measure(
                    widthMeasureSpec, MeasureSpec.makeMeasureSpec(
                        0,
                        MeasureSpec.UNSPECIFIED
                    )
                )
                var currentViewHeight = currentView.measuredHeight

                newViewHeight = MeasureSpec.makeMeasureSpec(currentViewHeight, MeasureSpec.EXACTLY)
            } catch (e: NullPointerException) {
                Log.e(AdjustingViewPager::class.simpleName, ": " + e.message)
            }
        }
        super.onMeasure(widthMeasureSpec, newViewHeight)
    }
}