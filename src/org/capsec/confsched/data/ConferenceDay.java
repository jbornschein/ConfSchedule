/**
 * 
 */
package org.capsec.confsched.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author joerg
 *
 */
public class ConferenceDay {
	public Date date;
	public List<ConferenceTrack> tracks = new ArrayList<ConferenceTrack>();
}
