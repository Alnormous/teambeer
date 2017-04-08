package com.teambeer.untappd;

import java.util.Set;

import com.teambeer.untappd.model.Checkin;

/**
 * API to interface with Untappd.
 * 
 * @author alister.stielow
 *
 */
public interface Untappd {

	/**
	 * Retrieve the last 25 Untappd checkins given an Untappd userId.
	 * 
	 * @param user
	 *            Untappd userid
	 * @return Set of Checkins
	 */
	Set<Checkin> getCheckinsByUser(final String user);
}
