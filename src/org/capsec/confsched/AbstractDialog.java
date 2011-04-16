/**
 * 
 */
package org.capsec.confsched;

import org.capsec.confsched.data.ConferenceEvent;

import android.app.Dialog;
import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * This dialog displas an conference abstract and allows minimal interaction.
 * 
 * @author Joerg Bornschein
 */
public class AbstractDialog extends Dialog {
	final static String TAG = "ConfApp.AbstractDialog";
	
	protected ImageButton closeBtn;
	protected CheckBox favBtn;
	
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
		
		favBtn = (CheckBox) findViewById(R.id.abstract_fav_btn);
		favBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// TODO
			}
		});
		
		closeBtn = (ImageButton) findViewById(R.id.abstract_close_btn);
		closeBtn.setImageResource(android.R.drawable.ic_menu_close_clear_cancel);
		closeBtn.setBackgroundColor(0x00000000);
    	closeBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				dismiss();
			}
    	});
	}
	
	public void setEvent(ConferenceEvent event) {
		setTitle(event.title);
		
		String text = "";
		text += "<big>" + event.title + "</big><br/>\n";
		text += "<i>" + event.author + "</i><br/><br/>\n\n";
		text += event.abstractText;
		
		TextView tv = (TextView)findViewById(R.id.abstract_text); 
		tv.setText(Html.fromHtml(text));
	}
}
