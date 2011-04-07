package org.capsec.confsched;

import java.io.IOException;

import org.capsec.confsched.data.Conference;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;


public class ConfSched extends Activity {
	private static final String TAG = "ConfSched";
		
	// Main datastructure for the current conference
    private Conference mConference;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //
        Context context = getApplicationContext();
        
        try {
        	mConference = Conference.parseXML(context);
        	
        	EventScheduleView evs = (EventScheduleView) findViewById(R.id.eventSchedule);
        	evs.setConferenceData(mConference.days.get(0));
        } catch (IOException e) {
        	Log.e(TAG, e.getMessage());
        } catch (XmlPullParserException e) {
        	Log.e(TAG, e.getMessage());
        }
    }
}