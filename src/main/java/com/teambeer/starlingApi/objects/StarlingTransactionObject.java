package com.teambeer.starlingApi.objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by artyom.fedenka on 4/8/17.
 * ONWARDS!!!!
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class StarlingTransactionObject {
	public TransactionsBody _embedded;
}
