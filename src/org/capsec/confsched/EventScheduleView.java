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
	protected int mNumTracks = 2;
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

    protected void onDraw(Canvas canvas) {
    	drawTimeline(canvas);
    	
    	for (int t=0; t<mNumTracks; t++) {
    		drawTrack(t, canvas);
    	}
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
    	Paint paint = new Paint();
    	paint.setColor(Color.WHITE);
    	paint.setAlpha(0x88);
    	paint.setStyle(Paint.Style.FILL_AND_STROKE);
    	
    	int hours = mLastHour - mFirstHour + 1;
    	for(int h=0; h < hours; h++) {
    		//if (h % (trackNum+1) == 0)
    		//	continue;
    		
    		int x = mBorder + h * mHourWidth + mPadding;
    		int y = mTimelineHeight + trackNum * mTrackHeight + mPadding;
    		
    		RectF rect = new RectF(x, y, x + mHourWidth - mPadding, y + mTrackHeight - mPadding);
    		canvas.drawRoundRect(rect, 7, 7, paint);
    	}
    		
    	
    }
    
}
