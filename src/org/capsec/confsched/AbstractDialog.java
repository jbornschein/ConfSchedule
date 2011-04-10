/**
 * 
 */
package org.capsec.confsched;

import org.capsec.confsched.data.ConferenceEvent;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * @author Joerg Bornschein
 *
 */
public class AbstractDialog extends Dialog {
	final static String TAG = "ConfApp.AbstractDialog";
	
	public AbstractDialog(Context context, int theme) {
		super(context, theme);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.dialog_abstract);
		getWindow().setLayout(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
	}
	
	public AbstractDialog(Context context) {
		super(context);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.dialog_abstract);
		getWindow().setLayout(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		
		ImageButton ib = (ImageButton) findViewById(R.id.abstract_close_btn);
		ib.setImageResource(android.R.drawable.ic_menu_close_clear_cancel);
		ib.setBackgroundColor(0x00000000);
    	ib.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				dismiss();
			}
    	});
	}
	
	public void setEvent(ConferenceEvent event) {
		setTitle(event.title);
		
		TextView tv = (TextView)findViewById(R.id.abstract_title);
		tv.setText(event.title);
		
		tv = (TextView)findViewById(R.id.abstract_author);
		tv.setText(event.author);

		tv = (TextView)findViewById(R.id.abstract_text); 
		tv.setText(event.abstractText);
	}
}
