package com.teambeer.starlingApi.objects;

/**
 * Created by artyom.fedenka on 4/8/17.
 * ONWARDS!!!!
 * {  'destinationAccountUid': '9632dd91-9632-dd91-9632-dd919632dd91',
 * 'payment': {    'amount': 12.34,    'currency': 'GBP'  },
 * 'reference': 'text'}
 */
public class PaymentPostObject {
	public String destinationAccountUid;
	public PaymentPostInfoObject payment;
	public String reference;
}
