package com.teambeer.starlingApi.objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by artyom.fedenka on 4/8/17.
 * ONWARDS!!!!
 *  "accountNumber": "text",
 "id": "00000d1b-0000-0d1b-0000-0d1b00000d1b",
 "name": "text",
 "self": {
 "deprecation": "https://api.starlingbank.com/notreal/url",
 "href": "text",
 "hreflang": "text",
 "name": "text",
 "profile": "https://api.starlingbank.com/notreal/uri",
 "templated": true,
 "title": "text",
 "type": "text"
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class StarlingAccount {
	public String id;

}
