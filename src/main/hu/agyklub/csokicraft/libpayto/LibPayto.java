package hu.agyklub.csokicraft.libpayto;

import java.net.URI;

import hu.agyklub.csokicraft.libpayto.handlers.PaytoHandlerRegistry;
import hu.agyklub.csokicraft.libpayto.objects.AbstractPaytoData;
import hu.agyklub.csokicraft.libpayto.objects.PaytoBicData;
import hu.agyklub.csokicraft.libpayto.objects.PaytoIbanData;

public final class LibPayto{
	public static AbstractPaytoData parseURI(URI uri){
		switch(uri.getRawAuthority()){
			case "bic":
				return new PaytoBicData(uri);
			case "iban":
				return new PaytoIbanData(uri);
		}
		throw new UnsupportedOperationException(String.format("Payment target type %s not supported yet!", uri.getRawAuthority()));
	}

	public static void dispatchURI(URI uri){
		AbstractPaytoData data=parseURI(uri);
		if(data instanceof PaytoIbanData)
			PaytoHandlerRegistry.INSTANCE.dispatch((PaytoIbanData) data);
		else if(data instanceof PaytoBicData)
			PaytoHandlerRegistry.INSTANCE.dispatch((PaytoBicData) data);
		else // unreachable...
			throw new IllegalStateException("Wrong data class returned from pareURI(), bug in lib?");
	}
}
