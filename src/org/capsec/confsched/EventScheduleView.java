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
import android.util.AttributeSet;
import android.view.View;

/**
 * @author Joerg Bornschein
 *
 */
public class EventScheduleView extends View {

    public EventScheduleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public EventScheduleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onDraw(Canvas canvas) {
    	Paint paint = new Paint();
    	paint.setColor(0x888888ff);
    	paint.setStyle(Paint.Style.FILL_AND_STROKE);
    	paint.setAntiAlias(true);
    	
    	RectF rect = new RectF(50, 10, 400, 200);
    	canvas.drawRoundRect(rect, 7, 7, paint);
    	
    }
    
}
