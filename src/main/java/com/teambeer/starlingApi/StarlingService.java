package com.teambeer.starlingApi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teambeer.beerApi.apiObjects.TescoApiResponseObject;
import com.teambeer.query.PaymentResource;
import com.teambeer.query.repository.ExpensesRepository;
import com.teambeer.ruleengine.Expense;
import com.teambeer.starlingApi.objects.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
	String MAKE_PAYMENT_API = "https://api-sandbox.starlingbank.com/api/v1/payments/local";
	String CONTACT_API = "https://api-sandbox.starlingbank.com/api/v1/contacts";
	String CONTACT_ACCOUNT_API = "https://api-sandbox.starlingbank.com/api/v1/contacts/%s/accounts";
	String USER_TOKEN_HEADER = "Bearer tDZXn7EG4RpikocmU6tlm8FdhNXfn7VcgiVo4N8X4hnlC4AUGzy3ofjwuCfFiJTS";

	@Autowired
	ExpensesRepository expensesRepository;

	public void payKarma(String contactId, double amount, String expenseId) {
		this.makeLocalPayment(contactId, amount);
		this.markBillKarmaFree(expenseId);
	}

	private void markBillKarmaFree(String expenseId) {
		Expense expense = this.expensesRepository.getByIdExpense(expenseId);
		expense.paid = true;
		this.expensesRepository.updateExpense(expense);
	}

	public void makeLocalPayment(String contactId, double amount) {
		StarlingAccount stralingAccount = getContactsAccount(contactId);

		String url = String.format(CONTACT_ACCOUNT_API, contactId);

		PaymentPostInfoObject paymentPostInfoObject = new PaymentPostInfoObject();
		paymentPostInfoObject.amount = amount;
		paymentPostInfoObject.currency = "GBP";

		PaymentPostObject paymentPostObject = new PaymentPostObject();
		paymentPostObject.destinationAccountUid = stralingAccount.id;
		paymentPostObject.payment = paymentPostInfoObject;
		paymentPostObject.reference = "Shame on me!";

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", USER_TOKEN_HEADER);
		headers.set("Content-Type","application/json");

		ObjectMapper mapper = new ObjectMapper();
		String json = "";
		try {
			json = mapper.writeValueAsString(paymentPostObject);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		HttpEntity<String> entity = new HttpEntity<>(json, headers);

		String formatedUrl = String.format("%s", MAKE_PAYMENT_API);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<ObjectMapper> response = restTemplate.exchange(
				formatedUrl,
				HttpMethod.POST,
				entity,
				ObjectMapper.class
		);
	}

	public StarlingAccount getContactsAccount(String contactId) {
		String url = String.format(CONTACT_ACCOUNT_API, contactId);
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", USER_TOKEN_HEADER);

		HttpEntity<String> entity = new HttpEntity<>(headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<StarlingAccountObject> response = restTemplate.exchange(
				url,
				HttpMethod.GET,
				entity,
				StarlingAccountObject.class
		);

		return response.getBody().contactAccounts.get(0);
	}

	public List<StarlingContact> listDonatableContacts() {
		return this.listContacts();
	}

	public List<StarlingContact> listContacts() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", USER_TOKEN_HEADER);

		HttpEntity<String> entity = new HttpEntity<>(headers);

		String formatedUrl = String.format("%s", CONTACT_API);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<StarlingContactObject> response = restTemplate.exchange(
				formatedUrl,
				HttpMethod.GET,
				entity,
				StarlingContactObject.class
		);

		return response.getBody()._embedded.contacts;
	}

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
