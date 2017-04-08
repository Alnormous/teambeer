package com.teambeer.starlingApi.objects;

/**
 * Created by artyom.fedenka on 4/8/17.
 * ONWARDS!!!!
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * {"merchantUid":"92e7dabd-3d5d-4195-8f8e-2f5487239c3a",
 * "merchant":{"href":"/api/v1/merchants/92e7dabd-3d5d-4195-8f8e-2f5487239c3a","templated":false},
 * "merchantLocationUid":"50d4564b-0442-46c1-af11-6672414e94bc",
 * "merchantName":"SUPER COOL PUB",
 * "locationName":"SUPER COOL PUB",
 * "mastercardMerchantCategoryCode":5812}⏎
 }⏎
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MerchantLocation {
	public String merchantUid;
	public String merchantLocationUid;
	public String merchantName;
	public String locationName;
	public String address;
	public String googlePlaceId;
	public Integer mastercardMerchantCategoryCode;
}
