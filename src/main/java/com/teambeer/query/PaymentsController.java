package com.teambeer.query;

import com.teambeer.starlingApi.StarlingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

import static org.springframework.http.HttpMethod.POST;

/**
 * Created by artyom.fedenka on 4/8/17.
 * ONWARDS!!!!
 */
@RestController
public class PaymentsController {
	@Autowired
	StarlingService starlingService;

	@RequestMapping(value = "/pay", method = RequestMethod.POST)
	public void makePayment(@RequestBody PaymentResource paymentResource) {
		starlingService.makeLocalPayment(paymentResource.contactId, paymentResource.amount);
	}

}
