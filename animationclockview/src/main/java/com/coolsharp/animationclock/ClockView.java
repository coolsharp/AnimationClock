/* ________________________________________________________________________Copyright 2014 coolsharp
 * Description : 시계 기능의 뷰
 * Date : 2015. 2. 10
 * Author : coolsharp
 * History : [2015. 2. 10] 최초 소스 작성(coolsharp)
_________________________________________________________________________________________________*/

package com.coolsharp.animationclock;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 시계 기능을 하는 클레스이다.
 */
public class ClockView extends LinearLayout {
    // [final/static_property]====================[START]===================[final/static_property]
    // [final/static_property]=====================[END]====================[final/static_property]
    // [private/protected/public_property]========[START]=======[private/protected/public_property]

    /** 시간 십단위. */
    private NumberView numberViewHourFirst    = null;

    /** 시간 일단위. */
    private NumberView numberViewHourSecond   = null;

    /** 분 십단위. */
    private NumberView numberViewMinuteFirst  = null;

    /** 분 일단위. */
    private NumberView numberViewMinuteSecond = null;

    /** 초 십단위. */
    private NumberView numberViewSecondFirst  = null;

    /** 초 분단위. */
    private NumberView numberViewSecondSecond = null;

    private TextView tv_hm = null;

    private TextView tv_ms = null;

    private int width = 0;
    private int height = 0;

    private int innerStrokeWidth;
    private int outerStrokeWidth;

    private int innerStrokeColor;
    private int outerStrokeColor;

    private int colonColor;

    // [private/protected/public_property]=========[END]========[private/protected/public_property]
    // [interface/enum/inner_class]===============[START]==============[interface/enum/inner_class]
    // [interface/enum/inner_class]================[END]===============[interface/enum/inner_class]
    // [inherited/listener_method]================[START]===============[inherited/listener_method]
    // [inherited/listener_method]=================[END]================[inherited/listener_method]
    // [private_method]===========================[START]==========================[private_method]

    /**
     * View Attach 콜백
     */
    private ViewTreeObserver.OnGlobalLayoutListener globalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        public void onGlobalLayout() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
            } else {
                getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }

            height = getHeight() - 10;
            width = (int)((NumberView.REFRENCE_WIDTH_VALUE / NumberView.REFRENCE_HEIGHT_VALUE) * height);

            setNumberView(numberViewHourFirst   );
            setNumberView(numberViewHourSecond  );
            setNumberView(numberViewMinuteFirst );
            setNumberView(numberViewMinuteSecond);
            setNumberView(numberViewSecondFirst );
            setNumberView(numberViewSecondSecond);
            setTextView(tv_hm);
            setTextView(tv_ms);
        }
    };

    /**
     * 뷰 크기 조절
     * @param numberView 뷰
     */
    private void setNumberView(NumberView numberView) {
        LayoutParams layoutParams = (LayoutParams) numberView.getLayoutParams();
        layoutParams.height = height;
        layoutParams.width = width;
        numberView.requestLayout();
        numberView.setInnerStrokeWidth(innerStrokeWidth);
        numberView.setOuterStrokeWidth(outerStrokeWidth);
        numberView.setInnerStrokeColor(innerStrokeColor);
        numberView.setOuterStrokeColor(outerStrokeColor);
    }

    private void setTextView(TextView textView) {
        textView.setTextSize(height / 4);
        textView.setTextColor(colonColor);
    }

    /**
     * 초기화.
     *
     * @param context 컨텍스트
     */
    private void init(Context context) {
        // 레이아웃 초기화
        setLayout(context);
    }

    /**
     * 레이어 셋팅
     *
     * @param context 컨텍스트
     */
    private void setLayout(Context context) {
        LinearLayout.inflate(context, R.layout.clock, this);
        numberViewHourFirst    = (NumberView)findViewById(R.id.hourFirst);
        numberViewHourSecond   = (NumberView)findViewById(R.id.hourSecond);
        numberViewMinuteFirst  = (NumberView)findViewById(R.id.minuteFirst);
        numberViewMinuteSecond = (NumberView)findViewById(R.id.minuteSecond);
        numberViewSecondFirst  = (NumberView)findViewById(R.id.SecondFirst);
        numberViewSecondSecond = (NumberView)findViewById(R.id.SecondSecond);
        tv_hm = findViewById(R.id.tv_hm);
        tv_ms = findViewById(R.id.tv_ms);

        // View Attach 처리
        getViewTreeObserver().addOnGlobalLayoutListener(globalLayoutListener);
    }

    // [private_method]============================[END]===========================[private_method]
    // [life_cycle_method]========================[START]=======================[life_cycle_method]

    /**
     * 생성자.
     *
     * @param context 컨텍스트
     */
    public ClockView(Context context) {
        super(context);
        init(context);
    }

    /**
     * 생성자
     *
     * @param context 컨텍스트
     * @param attrs 속성
     */
    public ClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        innerStrokeWidth = context.obtainStyledAttributes(attrs, R.styleable.ClockView).getDimensionPixelSize(R.styleable.ClockView_innerStrokeWidth, 2);
        outerStrokeWidth = context.obtainStyledAttributes(attrs, R.styleable.ClockView).getDimensionPixelSize(R.styleable.ClockView_outerStrokeWidth, 3);
        innerStrokeColor = context.obtainStyledAttributes(attrs, R.styleable.ClockView).getColor(R.styleable.ClockView_innerStrokeColor, Color.WHITE);
        outerStrokeColor = context.obtainStyledAttributes(attrs, R.styleable.ClockView).getColor(R.styleable.ClockView_outerStrokeColor, Color.BLACK);
        colonColor = context.obtainStyledAttributes(attrs, R.styleable.ClockView).getColor(R.styleable.ClockView_colonColor, Color.BLACK);

        init(context);
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
    public void setTime(int hourFirst, int hourSecond, int minuteFirst, int minuteSecond, int secondFirst, int secondSecond) {
        numberViewHourFirst.setNumber(hourFirst);
        numberViewHourSecond.setNumber(hourSecond);
        numberViewMinuteFirst.setNumber(minuteFirst);
        numberViewMinuteSecond.setNumber(minuteSecond);
        numberViewSecondFirst.setNumber(secondFirst);
        numberViewSecondSecond.setNumber(secondSecond);
    }

    /**
     * 시간 설정
     *
     * @param second
     */
    public void setTime(int second) {
        int hours = second / 3600;
        int minutes = (second % 3600) / 60;
        int seconds = second % 60;

        setTime(hours / 10,
                hours % 10,
                minutes / 10,
                minutes % 10,
                seconds / 10,
                seconds % 10
        );
    }

    // [get/set]===================================[END]==================================[get/set]
}
