package com.coolsharp.animationclock

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator

/**
 * 애니메이션 숫자 표시 뷰
 */
class NumberView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val interpolator = AccelerateDecelerateInterpolator()
    private val paint = Paint()
    private val paintFrame = Paint()
    private val path = Path()

    // Numbers currently shown.
    private var currentNumber = 0
    var number = 0
        set(mNext) {
            var mNext = mNext
            if (0 > mNext) mNext = 0
            if (9 < mNext) mNext = 9
            field = mNext
            postInvalidateDelayed(FRAME_SPEED.toLong())
        }
    private var dp = 0f
    var ratio = 1f

    private var frame = 0

    private var isFirst = true

    private val arrPoint = arrayOf(
        arrayOf(floatArrayOf(14.5f , 100f) , floatArrayOf(70f   , 18f)  , floatArrayOf(126f  , 100f)  , floatArrayOf(70f   , 180f)   , floatArrayOf(14.5f, 100f)), // 0
        arrayOf(floatArrayOf(47f   , 20.5f), floatArrayOf(74.5f , 20.5f), floatArrayOf(74.5f , 181f)  , floatArrayOf(74.5f , 181f)   , floatArrayOf(74.5f, 181f)), // 1
        arrayOf(floatArrayOf(26f   , 60f)  , floatArrayOf(114.5f, 61f)  , floatArrayOf(78f   , 122f)  , floatArrayOf(27f   , 177f)   , floatArrayOf(117f , 177f)), // 2
        arrayOf(floatArrayOf(33.25f, 54f)  , floatArrayOf(69.5f , 18f)  , floatArrayOf(69.5f , 96f)   , floatArrayOf(70f   , 180f)   , floatArrayOf(26.5f, 143f)), // 3
        arrayOf(floatArrayOf(125f  , 146f) , floatArrayOf(13f   , 146f) , floatArrayOf(99f   , 25f)   , floatArrayOf(99f   , 146f)   , floatArrayOf(99f  , 179f)), // 4
        arrayOf(floatArrayOf(116f  , 20f)  , floatArrayOf(61f   , 20f)  , floatArrayOf(42f   , 78f)   , floatArrayOf(115f  , 129f)   , floatArrayOf(15f  , 154f)), // 5
        arrayOf(floatArrayOf(80f   , 20f)  , floatArrayOf(80f   , 20f)  , floatArrayOf(16f   , 126f)  , floatArrayOf(123f  , 126f)   , floatArrayOf(23.5f, 100f)), // 6
        arrayOf(floatArrayOf(17f   , 21f)  , floatArrayOf(128f  , 21f)  , floatArrayOf(90.67f, 73.34f), floatArrayOf(53.34f, 126.67f), floatArrayOf(16f  , 181f)), // 7
        arrayOf(floatArrayOf(81f   , 96f)  , floatArrayOf(81f   , 19f)  , floatArrayOf(81f   , 96f)   , floatArrayOf(81f   , 179f)   , floatArrayOf(81f  , 96f)) , // 8
        arrayOf(floatArrayOf(116.5f, 100f) , floatArrayOf(17f   , 74f)  , floatArrayOf(124f  , 74f)   , floatArrayOf(60f   , 180f)   , floatArrayOf(60f  , 180f))  // 9
    )

    // The set of the "first" control points of each segment.
    private val arrPointFirst = arrayOf(
        arrayOf(floatArrayOf(14.5f, 60f)  , floatArrayOf(103f  , 18f)  , floatArrayOf(126f  , 140f)  , floatArrayOf(37f   , 180f))   , // 0
        arrayOf(floatArrayOf(47f  , 20.5f), floatArrayOf(74.5f , 20.5f), floatArrayOf(74.5f , 181f)  , floatArrayOf(74.5f , 181f))   , // 1
        arrayOf(floatArrayOf(29f  , 2f)   , floatArrayOf(114.5f, 78f)  , floatArrayOf(64f   , 138f)  , floatArrayOf(27f   , 177f))   , // 2
        arrayOf(floatArrayOf(33f  , 27f)  , floatArrayOf(126f  , 18f)  , floatArrayOf(128f  , 96f)   , floatArrayOf(24f   , 180f))   , // 3
        arrayOf(floatArrayOf(125f , 146f) , floatArrayOf(13f   , 146f) , floatArrayOf(99f   , 25f)   , floatArrayOf(99f   , 146f))   , // 4
        arrayOf(floatArrayOf(61f  , 20f)  , floatArrayOf(42f   , 78f)  , floatArrayOf(67f   , 66f)   , floatArrayOf(110f  , 183f))   , // 5
        arrayOf(floatArrayOf(80f  , 20f)  , floatArrayOf(41f   , 79f)  , floatArrayOf(22f   , 208f)  , floatArrayOf(116f  , 66f))    , // 6
        arrayOf(floatArrayOf(17f  , 21f)  , floatArrayOf(128f  , 21f)  , floatArrayOf(90.67f, 73.34f), floatArrayOf(53.34f, 126.67f)), // 7
        arrayOf(floatArrayOf(24f  , 95f)  , floatArrayOf(134f  , 19f)  , floatArrayOf(24f   , 96f)   , floatArrayOf(134f  , 179f))   , // 8
        arrayOf(floatArrayOf(94f  , 136f) , floatArrayOf(12f   , 8f)   , floatArrayOf(122f  , 108f)  , floatArrayOf(60f   , 180f))     // 9
    )

    // The set of the "second" control points of each segment.
    private val arrPointSecond = arrayOf(
        arrayOf(floatArrayOf(37f  , 18f)  , floatArrayOf(126f  , 60f)   , floatArrayOf(103f  , 180f)   , floatArrayOf(14.5f, 140f)), // 0
        arrayOf(floatArrayOf(74.5f, 20.5f), floatArrayOf(74.5f , 181f)  , floatArrayOf(74.5f , 181f)   , floatArrayOf(74.5f, 181f)), // 1
        arrayOf(floatArrayOf(113f , 4f)   , floatArrayOf(100f  , 98f)   , floatArrayOf(44f   , 155f)   , floatArrayOf(117f , 177f)), // 2
        arrayOf(floatArrayOf(56f  , 18f)  , floatArrayOf(116f  , 96f)   , floatArrayOf(120f  , 180f)   , floatArrayOf(26f  , 150f)), // 3
        arrayOf(floatArrayOf(13f  , 146f) , floatArrayOf(99f   , 25f)   , floatArrayOf(99f   , 146f)   , floatArrayOf(99f  , 179f)), // 4
        arrayOf(floatArrayOf(61f  , 20f)  , floatArrayOf(42f   , 78f)   , floatArrayOf(115f  , 85f)    , floatArrayOf(38f  , 198f)), // 5
        arrayOf(floatArrayOf(80f  , 20f)  , floatArrayOf(18f   , 92f)   , floatArrayOf(128f  , 192f)   , floatArrayOf(46f  , 64f)) , // 6
        arrayOf(floatArrayOf(128f , 21f)  , floatArrayOf(90.67f, 73.34f), floatArrayOf(53.34f, 126.67f), floatArrayOf(16f  , 181f)), // 7
        arrayOf(floatArrayOf(24f  , 19f)  , floatArrayOf(134f  , 96f)   , floatArrayOf(16f   , 179f)   , floatArrayOf(134f , 96f)) , // 8
        arrayOf(floatArrayOf(24f  , 134f) , floatArrayOf(118f  , -8f)   , floatArrayOf(99f   , 121f)   , floatArrayOf(60f  , 180f))  // 9
    )


    init {

        //		setWillNotDraw(false);

        // A new paint with the style as stroke.
        paint.isAntiAlias = true
        paint.color = Color.WHITE
        paint.strokeWidth = 2f
        paint.style = Paint.Style.STROKE

        paintFrame.isAntiAlias = true
        paintFrame.color = Color.BLACK
        paintFrame.strokeWidth = 3f
        paintFrame.style = Paint.Style.STROKE
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        getDp(context)

        setRatio()
    }

    public override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val currentFrame: Int
        if (frame < 2) {
            currentFrame = 0
        } else if (frame > FRAME_COUNT + 2) {
            currentFrame = FRAME_COUNT
        } else {
            currentFrame = frame - 2
        }

        // A factor of the difference between current
        // and next frame based on interpolation.
        val factor = interpolator.getInterpolation(currentFrame / FRAME_COUNT.toFloat())

        // Reset the path.
        path.reset()

        val current = arrPoint[currentNumber]
        val next = arrPoint[number]

        val curr1 = arrPointFirst[currentNumber]
        val next1 = arrPointFirst[number]

        val curr2 = arrPointSecond[currentNumber]
        val next2 = arrPointSecond[number]

        val dpRatio = dp * ratio

        // First point.
        path.moveTo(
            current[0][0] * dpRatio + (next[0][0] * dpRatio - current[0][0] * dpRatio) * factor,
            current[0][1] * dpRatio + (next[0][1] * dpRatio - current[0][1] * dpRatio) * factor
        )

        // Rest of the points connected as bezier curve.
        for (i in 1..4) {
            path.cubicTo(
                curr1[i - 1][0] * dpRatio + (next1[i - 1][0] * dpRatio - curr1[i - 1][0] * dpRatio) * factor,
                curr1[i - 1][1] * dpRatio + (next1[i - 1][1] * dpRatio - curr1[i - 1][1] * dpRatio) * factor,
                curr2[i - 1][0] * dpRatio + (next2[i - 1][0] * dpRatio - curr2[i - 1][0] * dpRatio) * factor,
                curr2[i - 1][1] * dpRatio + (next2[i - 1][1] * dpRatio - curr2[i - 1][1] * dpRatio) * factor,
                current[i][0] * dpRatio + (next[i][0] * dpRatio - current[i][0] * dpRatio) * factor,
                current[i][1] * dpRatio + (next[i][1] * dpRatio - current[i][1] * dpRatio) * factor
            )
        }

        // Draw the path.
        canvas.drawPath(path, paintFrame)
        canvas.drawPath(path, paint)

        frame++

        // Each number change has frames. Reset.
        if (frame >= FRAME_COUNT + 4) {
            // Reset to zero.
            frame = 0

            currentNumber = number
            if (isFirst) isFirst = false
        } else {
            if (currentNumber != number || isFirst) postInvalidateDelayed(FRAME_SPEED.toLong())
        }
    }

    private fun getDp(context: Context) {
        val resources = context.resources
        val metrics = resources.displayMetrics
        dp = metrics.densityDpi / 160f
    }

    fun setRatio() {
        val width = layoutParams.width.toFloat() / (REFRENCE_WIDTH_VALUE * dp)
        val height = layoutParams.height.toFloat() / (REFRENCE_HEIGHT_VALUE * dp)

        ratio = if (width <= height) width else height
    }

    fun setInnerStrokeWidth(width: Int) {
        paint.strokeWidth = width.toFloat()
    }

    fun setOuterStrokeWidth(width: Int) {
        paintFrame.strokeWidth = width.toFloat()
    }

    fun setInnerStrokeColor(color: Int) {
        paint.color = color
    }

    fun setOuterStrokeColor(color: Int) {
        paintFrame.color = color
    }

    companion object {
        private val FRAME_COUNT = 14
        private val FRAME_SPEED = 5

        val REFRENCE_WIDTH_VALUE = 150f // 기준 넓이
        val REFRENCE_HEIGHT_VALUE = 190f // 기준 높이
    }
}