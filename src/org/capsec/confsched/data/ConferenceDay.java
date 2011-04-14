/**
 * 
 */
package org.capsec.confsched.data;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author joerg
 *
 */
public class ConferenceDay {
	protected Date date;
	protected String name = "";
	protected ArrayList<ConferenceTrack> tracks = new ArrayList<ConferenceTrack>();
	
	/************************************************************************************
	 * Getter / setter methods...
	 */
	
	public Date getDate() {
		return date;
	}
	
	public String getName() {
		return name;
	}
	
	public ConferenceTrack getTrack(int track) {
		return tracks.get(track);
	}
	
	public int getTotalTracks() {
		return tracks.size();
	}
}
