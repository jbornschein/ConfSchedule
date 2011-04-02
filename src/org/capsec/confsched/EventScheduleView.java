/**
 * 
 */
package org.capsec.confsched;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
    	paint.setColor(0xEEff8888);
    	paint.setStyle(Paint.Style.STROKE);
    	paint.setAntiAlias(true);
    	
    	RectF rect = new RectF(10, 10, 100, 100);
    	
    	canvas.drawRect(rect, paint);
    }
    
}
