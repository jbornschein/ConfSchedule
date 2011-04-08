/**
 * @author Joerg Bornschein <jb@capsec.org>
 * 
 */
package org.capsec.confsched;

import java.util.Calendar;

import java.lang.Math;
import org.capsec.confsched.data.ConferenceDay;
import org.capsec.confsched.data.ConferenceEvent;
import org.capsec.confsched.data.ConferenceTrack;



import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * EventScheduleView
 */
public class EventScheduleView extends View {

	/**
	 * General information about the schedule to be drawn
	 */
	protected ConferenceDay mConfDay;
	
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

    public void setConferenceData(ConferenceDay confDay) {
    	mConfDay = confDay;
    	
    	mNumTracks = confDay.tracks.size();
    	
    	// Find first/last hour of this conference
    	mFirstHour = 23;
    	mLastHour = 0;
    	for (ConferenceTrack track : confDay.tracks) {
    		int trackSize = track.events.size();
    		mFirstHour = Math.min(mFirstHour, track.events.get(0).startHour);
    		mLastHour = Math.max(mLastHour, track.events.get(trackSize-1).endHour);
    	}
    	mLastHour += 1;
    	
    	invalidate();
    }
    
    /**
     * onMeasure returns the preferred widget size
     */
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    	int widthMode = MeasureSpec.getMode(widthMeasureSpec);
    	int heightMode = MeasureSpec.getMode(heightMeasureSpec);
    	int proposedWidth = MeasureSpec.getSize(widthMeasureSpec);
    	int proposedHeight = MeasureSpec.getSize(heightMeasureSpec);
    	
    	// The width this widget would like to take...
    	int totalHours = mLastHour - mFirstHour;
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

    	// Recalculate scaling so that the schedule fits into widget
    	mTrackHeight = (height - mTimelineHeight - mBorder) / mNumTracks;
		mHourWidth = (width - 2 * mBorder) / totalHours;
    	
    	setMeasuredDimension(width, height);
    }

    /**
     * onDraw method redraws the whole widget when necessary.
     */
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
    	paint.setTypeface(Typeface.MONOSPACE);
    	paint.setTextSize(fontSize);
    	paint.setAntiAlias(true);
    	
    	int hours = mLastHour - mFirstHour + 1;
    	for(int h=0; h < hours; h++) {
    		int x = mBorder + h * mHourWidth;
    		String str = "" + (mFirstHour+h) + ":00";
    		
    		canvas.drawLine(x, 0, x, mTimelineHeight, paint);
    		canvas.drawText(str, x + 7, fontSize, paint);
    	}
    }

    
    /**
     * Draw all the events (rectangles)
     */
    protected void drawTrack(int trackNum, Canvas canvas) {
    	int fontSize = 16;

    	
    	Paint fillPaint = new Paint();
    	fillPaint.setColor(Color.WHITE);
    	fillPaint.setAlpha(0xaa);
    	fillPaint.setStyle(Paint.Style.FILL);
    	
    	Paint strokePaint = new Paint();
    	strokePaint.setColor(Color.BLACK);
    	strokePaint.setStyle(Paint.Style.STROKE);
    	strokePaint.setAntiAlias(true);
    	
    	TextPaint fontPaint = new TextPaint();
    	fontPaint.setColor(Color.BLACK);
    	fontPaint.setAntiAlias(true);
    	fontPaint.setTypeface(Typeface.DEFAULT);
    	fontPaint.setTextSize(fontSize);
    	
    	ConferenceTrack track = mConfDay.tracks.get(trackNum);
    	for (ConferenceEvent event : track.events) {
    		int relStartHour = event.startHour - mFirstHour;
    		int relEndHour = event.endHour - mFirstHour;
    		
    		int ulx = mBorder + relStartHour * mHourWidth + event.startMin * mHourWidth / 60 + mPadding;
    		int uly = mTimelineHeight + trackNum * mTrackHeight + mPadding;
    		int lrx = mBorder + relEndHour * mHourWidth + event.endMin * mHourWidth / 60 - mPadding;
    		int lry = mTimelineHeight + (trackNum+1) * mTrackHeight - mPadding;
    		int width = lrx - ulx;   
    		int height = lry - uly;
    		
    		if (canvas.quickReject(ulx, uly, lrx, lry, Canvas.EdgeType.AA)) {
    			continue;
    		}

    		Matrix savedMatrix = canvas.getMatrix();
    		canvas.translate(ulx, uly);
    		
    		// Draw box
    		RectF rect = new RectF(0, 0, width, height);
    		canvas.drawRoundRect(rect, 7, 7, fillPaint);
    		canvas.drawRoundRect(rect, 7, 7, strokePaint);

    		// Title
    		fontPaint.setTypeface(Typeface.DEFAULT_BOLD);
    		StaticLayout layout = new StaticLayout(event.title, fontPaint, width-10, Layout.Alignment.ALIGN_NORMAL, 1.0f, 1.0f, true);
    		layout.draw(canvas);
    		
    		// Author
    		fontPaint.setTypeface(Typeface.DEFAULT);
    		canvas.drawText(event.author, ulx+5, lry-2, strokePaint);

    		canvas.setMatrix(savedMatrix);
    	}
    }
    
    /**
     *  Draw the red line indicating the current time.
     */
    protected void drawCurrentTime(Canvas canvas) {
    	if (!curTimeVisible())
    		return; 
    	
    	// get current date/time
    	Calendar rightNow = Calendar.getInstance();
       	int curHour = rightNow.get(Calendar.HOUR_OF_DAY);
    	int curMinute = rightNow.get(Calendar.MINUTE);
    	
    	int hours = curHour - mFirstHour; 
    	int x = mBorder + hours * mHourWidth + curMinute * mHourWidth / 60;
    	int totalHeight = mTimelineHeight + mNumTracks * mTrackHeight + mBorder;
    	
    	Paint paint = new Paint();
    	paint.setColor(Color.RED);
    	paint.setStyle(Paint.Style.STROKE);
    	paint.setStrokeWidth(2.3f);
    	
    	canvas.drawLine(x, 0, x, totalHeight, paint);
    }
    	
    /**
     * Return wether a screen update might be neccessary because the current day/time
     * is displayed in this widget.
     */
    protected boolean curTimeVisible() {
    	Calendar rightNow = Calendar.getInstance();
    	int curHour = rightNow.get(Calendar.HOUR_OF_DAY);

    	// XXX check day 
    	
    	
    	if (curHour < (mFirstHour-1) || curHour > (mLastHour+1)) 
    		return false;
    	
    	return true;
    }
}
