package com.coolsharp.animationclock;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

/**
 * 애니메이션 숫자 표시 뷰
 */
public class NumberView extends View {
	private final static int FRAME_COUNT = 14;
	private final static int FRAME_SPEED = 5;

	private final Interpolator interpolator = new AccelerateDecelerateInterpolator();
	private final Paint paint 				= new Paint();
	private final Paint paintFrame 			= new Paint();
	private final Path path 				= new Path();

	public final static float REFRENCE_WIDTH_VALUE  = 150f; // 기준 넓이
	public final static float REFRENCE_HEIGHT_VALUE = 190f; // 기준 높이

	// Numbers currently shown.
	private int currentNumber = 0;
	private int nextNumber    = 0;
	private float dp 		  = 0f;
	private float ratio 	  = 1f;

	private int frame = 0;

	private boolean isFirst = true;

	private final float[][][] arrPoint = {
			{ { 14.5f  , 100   }, { 70    , 18    }, { 126    , 100    }, { 70    , 180     }, { 14.5f  , 100 } }, // 0
			{ { 47     , 20.5f }, { 74.5f , 20.5f }, { 74.5f  , 181    }, { 74.5f , 181     }, { 74.5f  , 181 } }, // 1
			{ { 26     , 60    }, { 114.5f, 61    }, { 78     , 122    }, { 27    , 177     }, { 117    , 177 } }, // 2
			{ { 33.25f , 54    }, { 69.5f , 18    }, { 69.5f  , 96     }, { 70    , 180     }, { 26.5f  , 143 } }, // 3
			{ { 125    , 146   }, { 13    , 146   }, { 99     , 25     }, { 99    , 146     }, { 99     , 179 } }, // 4
			{ { 116    , 20    }, { 61    , 20    }, { 42     , 78     }, { 115   , 129     }, { 15     , 154 } }, // 5
			{ { 80     , 20    }, { 80    , 20    }, { 16     , 126    }, { 123   , 126     }, { 23.5f  , 100 } }, // 6
			{ { 17     , 21    }, { 128   , 21    }, { 90.67f , 73.34f }, { 53.34f, 126.67f }, { 16     , 181 } }, // 7
			{ { 81     , 96    }, { 81    , 19    }, { 81     , 96     }, { 81    , 179     }, { 81     , 96  } }, // 8
			{ { 116.5f , 100   }, { 17    , 74    }, { 124    , 74     }, { 60    , 180     }, { 60     , 180 } }  // 9
	};

	// The set of the "first" control points of each segment.
	private final float[][][] arrPointFirst = {
			{ { 14.5f , 60    }, { 103    , 18    }, { 126    , 140    }, { 37    , 180     } }, // 0
			{ { 47    , 20.5f }, { 74.5f  , 20.5f }, { 74.5f  , 181    }, { 74.5f , 181     } }, // 1
			{ { 29    , 2     }, { 114.5f , 78    }, { 64     , 138    }, { 27    , 177     } }, // 2
			{ { 33    , 27    }, { 126    , 18    }, { 128    , 96     }, { 24    , 180     } }, // 3
			{ { 125   , 146   }, { 13     , 146   }, { 99     , 25     }, { 99    , 146     } }, // 4
			{ { 61    , 20    }, { 42     , 78    }, { 67     , 66     }, { 110   , 183     } }, // 5
			{ { 80    , 20    }, { 41     , 79    }, { 22     , 208    }, { 116   , 66      } }, // 6
			{ { 17    , 21    }, { 128    , 21    }, { 90.67f , 73.34f }, { 53.34f, 126.67f } }, // 7
			{ { 24    , 95    }, { 134    , 19    }, { 24     , 96     }, { 134   , 179     } }, // 8
			{ { 94    , 136   }, { 12     , 8     }, { 122    , 108    }, { 60    , 180     } }  // 9

	};

	// The set of the "second" control points of each segment.
	private final float[][][] arrPointSecond = {
			{ { 37    , 18    }, { 126    , 60     }, { 103   , 180     }, { 14.5f , 140 } }, // 0
			{ { 74.5f , 20.5f }, { 74.5f  , 181    }, { 74.5f , 181     }, { 74.5f , 181 } }, // 1
			{ { 113   , 4     }, { 100    , 98     }, { 44    , 155     }, { 117   , 177 } }, // 2
			{ { 56    , 18    }, { 116    , 96     }, { 120   , 180     }, { 26    , 150 } }, // 3
			{ { 13    , 146   }, { 99     , 25     }, { 99    , 146     }, { 99    , 179 } }, // 4
			{ { 61    , 20    }, { 42     , 78     }, { 115   , 85      }, { 38    , 198 } }, // 5
			{ { 80    , 20    }, { 18     , 92     }, { 128   , 192     }, { 46    , 64  } }, // 6
			{ { 128   , 21    }, { 90.67f , 73.34f }, { 53.34f, 126.67f }, { 16    , 181 } }, // 7
			{ { 24    , 19    }, { 134    , 96     }, { 16    , 179     }, { 134   , 96  } }, // 8
			{ { 24    , 134   }, { 118    , -8     }, { 99    , 121     }, { 60    , 180 } }  // 9
	};


	public NumberView(Context context, AttributeSet attrs) {
		super(context, attrs);

//		setWillNotDraw(false);

		// A new paint with the style as stroke.
		paint.setAntiAlias(true);
		paint.setColor(Color.WHITE);
		paint.setStrokeWidth(2);
		paint.setStyle(Paint.Style.STROKE);

		paintFrame.setAntiAlias(true);
		paintFrame.setColor(Color.BLACK);
		paintFrame.setStrokeWidth(3);
		paintFrame.setStyle(Paint.Style.STROKE);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);

		getDp(getContext());

		setRatio();
	}

	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		final int currentFrame;
		if (frame < 2) {
			currentFrame = 0;
		} else if (frame > FRAME_COUNT + 2) {
			currentFrame = FRAME_COUNT;
		} else {
			currentFrame = frame - 2;
		}

		// A factor of the difference between current
		// and next frame based on interpolation.
		final float factor = interpolator.getInterpolation(currentFrame
				/ (float) FRAME_COUNT);

		// Reset the path.
		path.reset();

		final float[][] current = arrPoint[currentNumber];
		final float[][] next    = arrPoint[nextNumber];

		final float[][] curr1   = arrPointFirst[currentNumber];
		final float[][] next1   = arrPointFirst[nextNumber];

		final float[][] curr2   = arrPointSecond[currentNumber];
		final float[][] next2   = arrPointSecond[nextNumber];

		float dpRatio = dp * ratio;

		// First point.
		path.moveTo(
				(current[0][0] * dpRatio) + (((next[0][0] * dpRatio) - (current[0][0] * dpRatio)) * factor),
				(current[0][1] * dpRatio) + (((next[0][1] * dpRatio) - (current[0][1] * dpRatio)) * factor)
				);

		// Rest of the points connected as bezier curve.
		for (int i = 1; i < 5; i++) {
			path.cubicTo(
					(curr1  [i - 1][0] * dpRatio) + (((next1[i - 1][0] * dpRatio) - (curr1  [i - 1][0] * dpRatio)) * factor),
					(curr1  [i - 1][1] * dpRatio) + (((next1[i - 1][1] * dpRatio) - (curr1  [i - 1][1] * dpRatio)) * factor),
					(curr2  [i - 1][0] * dpRatio) + (((next2[i - 1][0] * dpRatio) - (curr2  [i - 1][0] * dpRatio)) * factor),
					(curr2  [i - 1][1] * dpRatio) + (((next2[i - 1][1] * dpRatio) - (curr2  [i - 1][1] * dpRatio)) * factor),
					(current[i    ][0] * dpRatio) + (((next [i    ][0] * dpRatio) - (current[i    ][0] * dpRatio)) * factor),
					(current[i    ][1] * dpRatio) + (((next [i    ][1] * dpRatio) - (current[i    ][1] * dpRatio)) * factor)
					);
		}

		// Draw the path.
		canvas.drawPath(path, paintFrame);
		canvas.drawPath(path, paint);

		frame++;

		// Each number change has frames. Reset.
		if (frame >= FRAME_COUNT + 4) {
			// Reset to zero.
			frame = 0;

			currentNumber = nextNumber;
			if (isFirst) isFirst = false;
		}
		else {
			if ((currentNumber != nextNumber) || isFirst) postInvalidateDelayed(FRAME_SPEED);
		}
	}

	private void getDp(Context context) {
		Resources resources = context.getResources();
	    DisplayMetrics metrics = resources.getDisplayMetrics();
	    dp = metrics.densityDpi / 160f;
	}

	public void setRatio() {
		float width  = (float)getLayoutParams().width / (REFRENCE_WIDTH_VALUE * dp);
		float height = (float)getLayoutParams().height / (REFRENCE_HEIGHT_VALUE * dp);

		ratio = (width<=height?width:height);
	}

	public int getNumber() {
		return nextNumber;
	}

	public void setNumber(int mNext) {
		if (0 > mNext) mNext = 0;
		if (9 < mNext) mNext = 9;
		this.nextNumber = mNext;
		postInvalidateDelayed(FRAME_SPEED);
	}

	public float getRatio() {
		return ratio;
	}

	public void setRatio(float ratio) {
		this.ratio = ratio;
	}

	public void setInnerStrokeWidth(int width) {
		paint.setStrokeWidth(width);
	}

	public void setOuterStrokeWidth(int width) {
		paintFrame.setStrokeWidth(width);
	}

	public void setInnerStrokeColor(int color) {
		paint.setColor(color);
	}

	public void setOuterStrokeColor(int color) {
		paintFrame.setColor(color);
	}
}
