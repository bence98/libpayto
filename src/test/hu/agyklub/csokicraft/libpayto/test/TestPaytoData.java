package hu.agyklub.csokicraft.libpayto.test;

import java.net.URI;
import java.net.URISyntaxException;

import hu.agyklub.csokicraft.libpayto.objects.PaytoBicData;
import hu.agyklub.csokicraft.libpayto.objects.PaytoIbanData;

public class TestPaytoData{
	public static void main(String[] args) throws URISyntaxException{
		URI ibanUri=new URI("payto://iban/DE75512108001245126199?receiver-name=Alice&amount=INR:200"),
			ibanUri2=new URI("payto://iban/SOGEDEFFXXX/DE75512108001245126199"),
			bicUri=new URI("payto://bic/SOGEDEFFXXX?amount=EUR:10.5");

		var ibanData=new PaytoIbanData(ibanUri);
		var ibanData2=new PaytoIbanData(ibanUri2);
		var bicData=new PaytoBicData(bicUri);

		System.out.println(ibanData);
		System.out.println(ibanData2);
		System.out.println(bicData);
	}
}
