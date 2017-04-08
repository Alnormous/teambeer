package com.teambeer.beerApi;

import com.teambeer.beerApi.apiObjects.TescoApiResponseObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

/**
 * Created by artyom.fedenka on 4/8/17.
 * ONWARDS!!!!
 */
public class BeerPriceApi {

	String url = "https://dev.tescolabs.com/grocery/products/";
	String key = "39ca54beb0d148a687839ed7d7d239c7";

	public BigDecimal findBeerPrice(String query) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Ocp-Apim-Subscription-Key", key);

		HttpEntity<String> entity = new HttpEntity<>(headers);

		String formatedUrl = String.format("{0}?query={1}&limit=50&offset=0", url, query);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<TescoApiResponseObject> tescoApiResponseObject = restTemplate.exchange(
				formatedUrl,
				HttpMethod.GET,
				entity,
				TescoApiResponseObject.class
		);

		return null;
	}
}
