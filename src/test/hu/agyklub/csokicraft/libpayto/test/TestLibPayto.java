package hu.agyklub.csokicraft.libpayto.test;

import java.net.URI;
import java.net.URISyntaxException;

import hu.agyklub.csokicraft.libpayto.LibPayto;
import hu.agyklub.csokicraft.libpayto.PaytoHandlerRegistry;
import hu.agyklub.csokicraft.libpayto.handlers.PaytoBicHandler;
import hu.agyklub.csokicraft.libpayto.handlers.PaytoIbanHandler;
import hu.agyklub.csokicraft.libpayto.objects.PaytoBicData;
import hu.agyklub.csokicraft.libpayto.objects.PaytoIbanData;

public class TestLibPayto{
	public static void main(String[] args) throws URISyntaxException{
		URI ibanUri=new URI("payto://iban/DE75512108001245126199?receiver-name=Alice&amount=INR:200"),
			ibanUri2=new URI("payto://iban/SOGEDEFFXXX/DE75512108001245126199"),
			bicUri=new URI("payto://bic/SOGEDEFFXXX?amount=EUR:10.5");

		PaytoHandlerRegistry.INSTANCE.register(TestLibPayto::bicHandler);
		PaytoHandlerRegistry.INSTANCE.register(ibanHandler);
		IbanLogger logger=new IbanLogger("IBAN: ");
		PaytoHandlerRegistry.INSTANCE.register(logger::log);

		LibPayto.dispatchURI(ibanUri);
		LibPayto.dispatchURI(ibanUri2);
		LibPayto.dispatchURI(bicUri);
	}

	private static void bicHandler(PaytoBicData data){
		System.out.println(data);
	}
	
	static PaytoIbanHandler ibanHandler=(data) -> {
		System.out.println(data);
	};
	
	private static class IbanLogger{
		private String name;
		public IbanLogger(String str){
			name=str;
		}
		public void log(PaytoIbanData data){
			System.out.print(name);
			System.out.println(data);
		}
	}
}
