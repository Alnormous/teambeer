package com.teambeer.beerApi;

import com.teambeer.beerApi.apiObjects.TescoApiResponseObject;
import com.teambeer.beerApi.apiObjects.TescoBeer;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.OptionalDouble;
import java.util.function.DoubleSupplier;

/**
 * Created by artyom.fedenka on 4/8/17.
 * ONWARDS!!!!
 */
@Service
public class BeerPriceApi {

	String url = "https://dev.tescolabs.com/grocery/products/";
	String key = "39ca54beb0d148a687839ed7d7d239c7";

	public double findBeerPrice(String query) {
		List<TescoBeer> tescoBeerList = queryBeers(query);
		return tescoBeerList.stream().filter((b) -> {
			return b.UnitQuantity.equals("LITRE");
		}).map((b) -> {
			return b.price;
		}).mapToDouble(p -> p).average().getAsDouble();
	}

	private List<TescoBeer> queryBeers(String query) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Ocp-Apim-Subscription-Key", key);

		HttpEntity<String> entity = new HttpEntity<>(headers);

		String formatedUrl = String.format("{0}?query={1}&limit=50&offset=0", url, query);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<TescoApiResponseObject> response = restTemplate.exchange(
				formatedUrl,
				HttpMethod.GET,
				entity,
				TescoApiResponseObject.class
		);

		TescoApiResponseObject tescoApiResponseObject = response.getBody();
		return tescoApiResponseObject.uk.ghs.products.results;
	}
}
