/**
 * 
 */
package org.capsec.confsched;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author Joerg Bornschein <jb@capsec.org>
 *
 */
public class EventScheduleView extends View {

	/**
	 * General information about the schedule to be drawn
	 */
	protected int mNumTracks = 3;
	protected int mFirstHour = 10;
	protected int mLastHour = 18;
	
	
	/**
     * Pixel scale information
	 */
	protected int mBorder = 20;
	protected int mPadding = 2;
	protected int mTimelineHeight = 30;
	protected int mTrackHeight= 140;
	protected int mHourWidth = 300;
	
	
    public EventScheduleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public EventScheduleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    	int widthMode = MeasureSpec.getMode(widthMeasureSpec);
    	int heightMode = MeasureSpec.getMode(heightMeasureSpec);
    	int proposedWidth = MeasureSpec.getSize(widthMeasureSpec);
    	int proposedHeight = MeasureSpec.getSize(heightMeasureSpec);
    	
    	// The width this widget would like to take...
    	int totalHours = mLastHour - mFirstHour + 1;
    	int width = 2 * mBorder + totalHours * mHourWidth; 
    	
    	if (widthMode == MeasureSpec.EXACTLY) {
    		width = proposedWidth;
    	} else if (widthMode == MeasureSpec.AT_MOST) {
    		width = Math.min(proposedWidth, width);
    	};

    	int height = mTimelineHeight + mNumTracks * mTrackHeight; 
    	if (heightMode == MeasureSpec.EXACTLY) {
    		height = proposedHeight;
    	} else if (heightMode == MeasureSpec.AT_MOST) {
    		height = Math.min(proposedHeight, height);
    	}

    	// Recalculate scaling so that schdule fits into widget
    	mTrackHeight = (height - mTimelineHeight - mBorder) / mNumTracks;
		mHourWidth = (width - 2 * mBorder) / totalHours;
    	
    	setMeasuredDimension(width, height);
    }

    protected void onDraw(Canvas canvas) {
    	drawTimeline(canvas);
    	
    	for (int t=0; t<mNumTracks; t++) {
    		drawTrack(t, canvas);
    	}
    	
    	drawCurrentTime(canvas);
    }

    protected void drawTimeline(Canvas canvas) {
    	int fontSize = 16;
    	
    	Paint paint = new Paint();
    	paint.setColor(Color.WHITE);
    	paint.setStyle(Paint.Style.STROKE);
    	paint.setTextSize(fontSize);
    	paint.setTypeface(Typeface.MONOSPACE);
    	paint.setAntiAlias(true);
    	
    	int hours = mLastHour - mFirstHour + 1;
    	for(int h=0; h < hours; h++) {
    		int x = mBorder + h * mHourWidth;
    		String str = "" + (mFirstHour+h) + ":00";
    		
    		canvas.drawLine(x, 0, x, mTimelineHeight, paint);
    		canvas.drawText(str, x + 7, fontSize, paint);
    	}
    }

    protected void drawTrack(int trackNum, Canvas canvas) {
    	Paint fillPaint = new Paint();
    	fillPaint.setColor(Color.WHITE);
    	fillPaint.setAlpha(0xaa);
    	fillPaint.setStyle(Paint.Style.FILL);
    	Paint strokePaint = new Paint();
    	strokePaint.setColor(Color.BLACK);
    	strokePaint.setStyle(Paint.Style.STROKE);
    	strokePaint.setAntiAlias(true);
    	
    	int hours = mLastHour - mFirstHour + 1;
    	for(int h=0; h < hours; h++) {
    		if (h % (trackNum+1) == 1)
    			continue;
    		
    		int x = mBorder + h * mHourWidth + mPadding;
    		int y = mTimelineHeight + trackNum * mTrackHeight + mPadding;
    		
    		RectF rect = new RectF(x, y, x + mHourWidth - mPadding, y + mTrackHeight - mPadding);
    		canvas.drawRoundRect(rect, 7, 7, fillPaint);
    		canvas.drawRoundRect(rect, 7, 7, strokePaint);
    	}
    }
    
    protected void drawCurrentTime(Canvas canvas) {
    	// get current date/time
    	// XXX
    	
    	Paint paint = new Paint();
    	paint.setColor(Color.RED);
    	paint.setStyle(Paint.Style.STROKE);
    	paint.setStrokeWidth(2.3f);
    	
    	int totalHeight = mTimelineHeight + mNumTracks * mTrackHeight;
    	int x = 235;
    	
    	canvas.drawLine(x, 0, x, totalHeight, paint);
    }
    	
    
}
