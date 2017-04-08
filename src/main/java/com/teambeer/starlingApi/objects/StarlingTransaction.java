package com.teambeer.starlingApi.objects;

/**
 * Created by artyom.fedenka on 4/8/17.
 * ONWARDS!!!!
 */

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;

/**
 * "id": "65eeb01d-e5c9-4eb0-ba05-e388f1b50f73",
 "currency": "GBP",
 "amount": -26.77,
 "direction": "OUTBOUND",
 "created": "2017-04-08T12:47:08.156Z",
 "narrative": "Mastercard",
 "source": "MASTER_CARD",
 "mastercardTransactionMethod": "CHIP_AND_PIN",
 "status": "SETTLED",
 "sourceAmount": -26.77,
 "sourceCurrency": "GBP",
 "merchantId": "c052f76f-e919-427d-85fc-f46a75a3ff26",
 "merchantLocationId": "b7b9f347-99ab-4a83-9317-ec9895f47755"
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class StarlingTransaction {
	public String id;
	public double amount;
	public String direction;
	public LocalDateTime created;
	public String narrative;
	public String source;
	public String mastercardTransactionMethod;
	public String status;
	public double sourceAmount;
	public String sourceCurrency;
	public String merchantId;
	public String merchantLocationId;

	@JsonIgnore
	public MerchantLocation merchantLocation;
}
