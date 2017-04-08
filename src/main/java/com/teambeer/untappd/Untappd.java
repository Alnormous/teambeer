package com.teambeer.untappd;

import java.util.List;

import com.teambeer.untappd.model.Item;

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
	List<Item> getCheckinsByUser(final String user) throws UserNotFoundException;
}
