/**
 * 
 */
package org.capsec.confsched.data;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.capsec.confsched.R;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.util.Log;

/**
 * @author Joerg Bornschein
 *
 */
public class Conference {
	private static final String TAG = "ConfSched";
	private enum ParserState {Top, Conference, ConferenceName, ConferenceURL, Day, DayDate, 
		Track, TrackRoom, Event, EventTitle, EventAuthor, EventStart, EventEnd, EventAbstract };
	
	public String name = "";
	public String url = "";
	public ArrayList<ConferenceDay> days = new ArrayList<ConferenceDay>();


	/**
	 * Parse XML conference description...
	 * 
	 * @return Conference object
	 */
	public static Conference parseXML(Context ctx) 
			throws IOException, XmlPullParserException
	{
		Log.d(TAG, "Starting to parse XML file...");

		
		XmlPullParser xpp = ctx.getResources().getXml(R.xml.conference);

		Conference conf = null;
		ConferenceDay confDay = null;
		ConferenceTrack confTrack = null;
		ConferenceEvent confEvent = null;
		
		ParserState state = ParserState.Top;
		int eventType = xpp.getEventType();
		while (eventType != XmlPullParser.END_DOCUMENT) {
			if (eventType == XmlPullParser.START_TAG) {
				String tag = xpp.getName();
				switch (state) {
				case Top:
					if (tag.equals("conference")) {
						conf = new Conference();
						state = ParserState.Conference;
					} else {
						Log.i(TAG, "Unknown element: "+tag);
					}
					break;
				case Conference:
					if (tag.equalsIgnoreCase("name")) {
						state = ParserState.ConferenceName;
					} else if (tag.equalsIgnoreCase("url")) {
						state = ParserState.ConferenceURL;
					} else if (tag.equalsIgnoreCase("day")) {
						state = ParserState.Day;
						confDay = new ConferenceDay();
					} else {
						Log.i(TAG, "Unknown element: "+tag);
					}
					break;
				case Day:
					if (tag.equalsIgnoreCase("date")) {
						state = ParserState.DayDate;
					} else if (tag.equalsIgnoreCase("track")) {
						state = ParserState.Track;
						confTrack = new ConferenceTrack();
					} else {
						Log.i(TAG, "Unknown element: "+tag);
					}
					break;
				case Track:
					if (tag.equalsIgnoreCase("room")) {
						state = ParserState.TrackRoom;
					} else if (tag.equalsIgnoreCase("event")) {
						confEvent = new ConferenceEvent();
						state = ParserState.Event;
					} else {
						Log.i(TAG, "Unknown element: "+tag);
					}
					break;
				case Event:
					if (tag.equalsIgnoreCase("title")) {
						state = ParserState.EventTitle;
					} else if (tag.equalsIgnoreCase("author")) {
						state = ParserState.EventAuthor;
					} else if (tag.equalsIgnoreCase("abstract")) {
						state = ParserState.EventAbstract;
					} else if (tag.equalsIgnoreCase("start")) {
						state = ParserState.EventStart;
					} else if (tag.equalsIgnoreCase("end")) {
						state = ParserState.EventEnd;
					} else {
						Log.i(TAG, "Unknown element: "+tag);
					}
					break;
				}
			} else if (eventType == XmlPullParser.END_TAG) {
				String tag = xpp.getName();
				switch (state) {
				case EventEnd:
					state = ParserState.Event;
					break;
				case EventStart:
					state = ParserState.Event;
					break;
				case EventAbstract:
					state = ParserState.Event;
					break;
				case EventAuthor:
					state = ParserState.Event;
					break;
				case EventTitle:
					state = ParserState.Event;
					break;
				case Event:
					confTrack.events.add(confEvent);
					confEvent = null;
					state = ParserState.Track;
					Log.d(TAG, "Parsed a ConfernceEvent");
					break;
				case TrackRoom:
					state = ParserState.Track;
					break;
				case Track:
					confDay.tracks.add(confTrack);
					confTrack = null;
					state = ParserState.Day;
					Log.d(TAG, "Parsed a ConferenceTrack");
					break;
				case DayDate:
					state = ParserState.Day;
					break;
				case Day:
					conf.days.add(confDay);
					confDay = null;
					state = ParserState.Conference;
					Log.d(TAG, "Parsed a ConferenceDay");
					break;
				case ConferenceName:
					state = ParserState.Conference;
					break;
				case ConferenceURL:
					state = ParserState.Conference;
					break;
				case Conference:
					break;
				} 
			} else if (eventType == XmlPullParser.TEXT) {
				try {
					switch (state) {
					case ConferenceName:
						conf.name += xpp.getText();
						break;
					case ConferenceURL:
						conf.url += xpp.getText();
						break;
					case DayDate:
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
						confDay.date = sdf.parse(xpp.getText());
						break;
					case TrackRoom:
						confTrack.room += xpp.getText();
						break;
					case EventTitle:
						confEvent.title += xpp.getText();
						break;
					case EventAuthor:
						confEvent.author += xpp.getText();
						break;
					case EventAbstract:
						confEvent.abstractText += xpp.getText();
						break;
					case EventStart:
						SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm", Locale.US);
						Date date1 = sdf1.parse(xpp.getText());
						confEvent.startHour = date1.getHours();
						confEvent.startMin = date1.getMinutes();
						break;
					case EventEnd:
						SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm", Locale.US);
						Date date2 = sdf2.parse(xpp.getText());
						confEvent.endHour = date2.getHours();
						confEvent.endMin = date2.getMinutes();
						break;
					}
				} catch (ParseException e) {
					Log.e(TAG, "Error parsing XML file: "+e.getMessage());
				}
			}
			eventType = xpp.next();
		}
		
		Log.d(TAG, "Sucsessfully parsed XML file...");
		return conf;
	}

}
