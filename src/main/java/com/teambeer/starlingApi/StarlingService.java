package com.teambeer.starlingApi;

import com.teambeer.beerApi.apiObjects.TescoApiResponseObject;
import com.teambeer.starlingApi.objects.MerchantLocation;
import com.teambeer.starlingApi.objects.StarlingTransaction;
import com.teambeer.starlingApi.objects.StarlingTransactionObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by artyom.fedenka on 4/8/17.
 * ONWARDS!!!!
 */
@Service
public class StarlingService {

	String LOCATION_API = "https://api-sandbox.starlingbank.com/api/v1/merchants/%s/locations/%s";
	String MASTER_CARD_TRANSACTIONS_LIST = "https://api-sandbox.starlingbank.com/api/v1/transactions/mastercard";

	String USER_TOKEN_HEADER = "Bearer Pzo2eMXoBPbSGuUcMFmuAPxl6V5MskFFr8Ew1zVd94m3YiWwtM8xEqhvLr26fS61";


	public List<StarlingTransaction> latestOutBoundTransactions() {
		List<StarlingTransaction> starlingTransactions = listTransactions();

		return starlingTransactions.stream().filter((t) -> {
			return t.direction.equals("OUTBOUND");
		}).collect(Collectors.toList());
	}

	public MerchantLocation merchantLocation(String merchantId, String locationId) {
		String locationApiCall = String.format(LOCATION_API, merchantId, locationId);
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", USER_TOKEN_HEADER);

		HttpEntity<String> entity = new HttpEntity<>(headers);

		String formatedUrl = String.format("%s", locationApiCall);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<MerchantLocation> response = restTemplate.exchange(
				formatedUrl,
				HttpMethod.GET,
				entity,
				MerchantLocation.class
		);

		return response.getBody();
	}

	public List<StarlingTransaction> listTransactions() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", USER_TOKEN_HEADER);

		HttpEntity<String> entity = new HttpEntity<>(headers);

		String formatedUrl = String.format("%s", MASTER_CARD_TRANSACTIONS_LIST);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<StarlingTransactionObject> response = restTemplate.exchange(
				formatedUrl,
				HttpMethod.GET,
				entity,
				StarlingTransactionObject.class
		);

		StarlingTransactionObject starlingTransactionObject = response.getBody();
		return starlingTransactionObject._embedded.transactions;
	}
}
