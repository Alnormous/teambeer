package com.teambeer.untappd;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.teambeer.untappd.model.Item;
import com.teambeer.untappd.model.UntappdResponseObject;

@Service
public class UntappdController implements Untappd {

	@Value("${untappd.client.id}")
	private String untappdClientId;

	@Value("${untappd.client.secret}")
	private String untappedClientSecret;

	@Value("${untapped.api.checkins.url}")
	private String untappdApiCheckinsUrl;

	private String constructUrl(String user) throws UnsupportedEncodingException {
		return String.format("%s/%s?client_id=%s&client_secret=%s", untappdApiCheckinsUrl,
				URLEncoder.encode(user, "UTF-8"), untappdClientId, untappedClientSecret);
	}

	@Override
	public List<Item> getCheckinsByUser(String user) throws UserNotFoundException {

		if (user == null || user.isEmpty()) {
			return new ArrayList<Item>();
		}

		String url;
		try {
			url = constructUrl(user);
		} catch (UnsupportedEncodingException uee) {
			throw new UserNotFoundException("Invalid user name!");
		}

		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<>(headers);
		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<UntappdResponseObject> response = restTemplate.exchange(url, HttpMethod.GET, entity,
				UntappdResponseObject.class);
		UntappdResponseObject untappdResponse = response.getBody();

		return untappdResponse.getResponse().getCheckins().getItems();
	}
}
