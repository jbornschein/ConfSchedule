package org.capsec.confsched;

import java.io.IOException;

import org.capsec.confsched.data.Conference;
import org.capsec.confsched.data.ConferenceDay;
import org.capsec.confsched.data.ConferenceEvent;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TextView;


public class ConfSched extends Activity {
	private static final String TAG = "ConfSched";
	
	private static final int DIALOG_ABSTRACT = 1;
	private static final int DIALOG_ABOUT = 2;
		
	// 
    private Conference mConference;
    private int mCurrentDay = 0;
    private EventScheduleView mScheduleView;
    
	/** 
	 * Called when the activity is first created. 
	 */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //
        Context context = getApplicationContext();

        mScheduleView = (EventScheduleView) findViewById(R.id.eventSchedule);
    	mScheduleView.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
    			showDialog(DIALOG_ABSTRACT);
			}
    	});
        
        Button btn = (Button) findViewById(R.id.btn_day_prev);
        btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mCurrentDay > 0) {
					mCurrentDay -= 1;
					ConferenceDay confDay = mConference.days.get(mCurrentDay);
					mScheduleView.setConferenceData(confDay);
				}
			}
        });

        btn = (Button) findViewById(R.id.btn_day_next);
        btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int totalDays = mConference.days.size();
				if (mCurrentDay < (totalDays-1)) {
					mCurrentDay += 1;
					ConferenceDay confDay = mConference.days.get(mCurrentDay);
					mScheduleView.setConferenceData(confDay);
				}
			}
        });
        
    	// Parse XML file and set data
        try {
        	mConference = Conference.parseXML(context);
        	mScheduleView.setConferenceData(mConference.days.get(0));
        } catch (IOException e) {
        	Log.e(TAG, e.getMessage());
        } catch (XmlPullParserException e) {
        	Log.e(TAG, e.getMessage());
        }
    }
    
    /**
     * Create main menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    } 
    
    /**
     * on menu option selectd..
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        case R.id.menu_update:
        	return true;
        case R.id.menu_about:
        	showDialog(DIALOG_ABOUT);
        	return true;
        default:
        	return super.onOptionsItemSelected(item);
        }
    }
    
    /**
     * Create and prepare various dialogs...
     */
    @Override
    protected Dialog onCreateDialog(int id) {
    	Dialog dialog;
    	switch(id) {
    	case DIALOG_ABSTRACT:
    		dialog = new AbstractDialog(this);
    		break;
    	case DIALOG_ABOUT:
    		dialog = new Dialog(this);
    		dialog.setTitle("About this application");
    		dialog.setContentView(R.layout.dialog_about);
    		break;
    	default:
    		dialog = super.onCreateDialog(id);
    	}
    	return dialog;
    }
    
    protected void onPrepareDialog(int id, Dialog dialog) {
    	switch(id) {
    	case DIALOG_ABSTRACT:
    		AbstractDialog ad = (AbstractDialog)dialog;
    		ad.setEvent(mScheduleView.selectedEvent);
			break;
		default:
			super.onPrepareDialog(id, dialog);
    	}
    }
}
