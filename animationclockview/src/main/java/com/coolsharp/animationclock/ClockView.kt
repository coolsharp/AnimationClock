package com.coolsharp.animationclock

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.AttributeSet
import android.view.ViewTreeObserver
import android.widget.LinearLayout
import android.widget.TextView

/**
 * 시계 기능을 하는 클레스이다.
 */
class ClockView : LinearLayout {
    // [final/static_property]====================[START]===================[final/static_property]
    // [final/static_property]=====================[END]====================[final/static_property]
    // [private/protected/public_property]========[START]=======[private/protected/public_property]

    /** 시간 십단위.  */
    private var numberViewHourFirst: NumberView? = null

    /** 시간 일단위.  */
    private var numberViewHourSecond: NumberView? = null

    /** 분 십단위.  */
    private var numberViewMinuteFirst: NumberView? = null

    /** 분 일단위.  */
    private var numberViewMinuteSecond: NumberView? = null

    /** 초 십단위.  */
    private var numberViewSecondFirst: NumberView? = null

    /** 초 분단위.  */
    private var numberViewSecondSecond: NumberView? = null

    private var tv_hm: TextView? = null

    private var tv_ms: TextView? = null

    private var widthValue = 0
    private var heightValue = 0

    private var innerStrokeWidth: Int? = 0
    private var outerStrokeWidth: Int? = 0

    private var innerStrokeColor: Int? = 0
    private var outerStrokeColor: Int? = 0

    private var colonColor: Int? = 0

    // [private/protected/public_property]=========[END]========[private/protected/public_property]
    // [interface/enum/inner_class]===============[START]==============[interface/enum/inner_class]
    // [interface/enum/inner_class]================[END]===============[interface/enum/inner_class]
    // [inherited/listener_method]================[START]===============[inherited/listener_method]
    // [inherited/listener_method]=================[END]================[inherited/listener_method]
    // [private_method]===========================[START]==========================[private_method]

    /**
     * View Attach 콜백
     */
    private val globalLayoutListener = object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
            } else {
                viewTreeObserver.removeGlobalOnLayoutListener(this)
            }

            heightValue = getHeight() - 10
            widthValue = (NumberView.REFRENCE_WIDTH_VALUE / NumberView.REFRENCE_HEIGHT_VALUE * heightValue).toInt()

            setNumberView(numberViewHourFirst!!)
            setNumberView(numberViewHourSecond!!)
            setNumberView(numberViewMinuteFirst!!)
            setNumberView(numberViewMinuteSecond!!)
            setNumberView(numberViewSecondFirst!!)
            setNumberView(numberViewSecondSecond!!)
            setTextView(tv_hm!!)
            setTextView(tv_ms!!)
        }
    }

    /**
     * 뷰 크기 조절
     * @param numberView 뷰
     */
    private fun setNumberView(numberView: NumberView) {
        val layoutParams = numberView.layoutParams as LinearLayout.LayoutParams
        layoutParams.height = heightValue
        layoutParams.width = widthValue
        numberView.requestLayout()
        numberView.setInnerStrokeWidth(innerStrokeWidth!!)
        numberView.setOuterStrokeWidth(outerStrokeWidth!!)
        numberView.setInnerStrokeColor(innerStrokeColor!!)
        numberView.setOuterStrokeColor(outerStrokeColor!!)
    }

    private fun setTextView(textView: TextView) {
        textView.textSize = (heightValue / 4).toFloat()
        textView.setTextColor(colonColor!!)
    }

    /**
     * 초기화.
     *
     * @param context 컨텍스트
     */
    private fun init(context: Context) {
        // 레이아웃 초기화
        setLayout(context)
    }

    /**
     * 레이어 셋팅
     *
     * @param context 컨텍스트
     */
    private fun setLayout(context: Context) {
        LinearLayout.inflate(context, R.layout.clock, this)
        numberViewHourFirst = findViewById<NumberView>(R.id.hourFirst)
        numberViewHourSecond = findViewById<NumberView>(R.id.hourSecond)
        numberViewMinuteFirst = findViewById<NumberView>(R.id.minuteFirst)
        numberViewMinuteSecond = findViewById<NumberView>(R.id.minuteSecond)
        numberViewSecondFirst = findViewById<NumberView>(R.id.SecondFirst)
        numberViewSecondSecond = findViewById<NumberView>(R.id.SecondSecond)
        tv_hm = findViewById(R.id.tv_hm)
        tv_ms = findViewById(R.id.tv_ms)

        // View Attach 처리
        viewTreeObserver.addOnGlobalLayoutListener(globalLayoutListener)
    }

    // [private_method]============================[END]===========================[private_method]
    // [life_cycle_method]========================[START]=======================[life_cycle_method]

    /**
     * 생성자.
     *
     * @param context 컨텍스트
     */
    constructor(context: Context) : super(context) {
        init(context)
    }

    /**
     * 생성자
     *
     * @param context 컨텍스트
     * @param attrs 속성
     */
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        innerStrokeWidth = context.obtainStyledAttributes(attrs, R.styleable.ClockView_)
            .getDimensionPixelSize(R.styleable.ClockView__innerStrokeWidth, 2)
        outerStrokeWidth = context.obtainStyledAttributes(attrs, R.styleable.ClockView_)
            .getDimensionPixelSize(R.styleable.ClockView__outerStrokeWidth, 3)
        innerStrokeColor = context.obtainStyledAttributes(attrs, R.styleable.ClockView_)
            .getColor(R.styleable.ClockView__innerStrokeColor, Color.WHITE)
        outerStrokeColor = context.obtainStyledAttributes(attrs, R.styleable.ClockView_)
            .getColor(R.styleable.ClockView__outerStrokeColor, Color.BLACK)
        colonColor = context.obtainStyledAttributes(attrs, R.styleable.ClockView_)
            .getColor(R.styleable.ClockView__colonColor, Color.BLACK)

        init(context)
    }

    // [life_cycle_method]=========================[END]========================[life_cycle_method]
    // [public_method]============================[START]===========================[public_method]
    // [public_method]=============================[END]============================[public_method]
    // [get/set]==================================[START]=================================[get/set]

    /**
     * 시간 설정
     *
     * @param hourFirst 시간 십단위
     * @param hourSecond 시간 일단위
     * @param minuteFirst 분 십단위
     * @param minuteSecond 분 일단위
     * @param secondFirst 초 십단위
     * @param secondSecond 초 일단위
     */
    fun setTime(
        hourFirst: Int,
        hourSecond: Int,
        minuteFirst: Int,
        minuteSecond: Int,
        secondFirst: Int,
        secondSecond: Int
    ) {
        numberViewHourFirst!!.number = hourFirst
        numberViewHourSecond!!.number = hourSecond
        numberViewMinuteFirst!!.number = minuteFirst
        numberViewMinuteSecond!!.number = minuteSecond
        numberViewSecondFirst!!.number = secondFirst
        numberViewSecondSecond!!.number = secondSecond
    }

    /**
     * 시간 설정
     *
     * @param second
     */
    fun setTime(second: Int) {
        val hours = second / 3600
        val minutes = second % 3600 / 60
        val seconds = second % 60

        setTime(
            hours / 10,
            hours % 10,
            minutes / 10,
            minutes % 10,
            seconds / 10,
            seconds % 10
        )
    }

    // [get/set]===================================[END]==================================[get/set]
}