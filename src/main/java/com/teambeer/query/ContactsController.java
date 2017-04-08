package com.teambeer.query;

import com.teambeer.starlingApi.StarlingService;
import com.teambeer.starlingApi.objects.StarlingContact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by artyom.fedenka on 4/8/17.
 * ONWARDS!!!!
 */
@RestController
public class ContactsController {

	@Autowired
	StarlingService starlingService;

	@RequestMapping("/contacts/donatable")
	public List<StarlingContact> listDonatableContacts() {
		return starlingService.listDonatableContacts();
	}
}
