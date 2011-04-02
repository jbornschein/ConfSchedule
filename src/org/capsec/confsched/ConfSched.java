package org.capsec.confsched;

import android.app.Activity;
import android.os.Bundle;
import android.widget.AnalogClock;
import android.widget.LinearLayout;


public class ConfSched extends Activity {
    private LinearLayout mScrollLayout;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
}