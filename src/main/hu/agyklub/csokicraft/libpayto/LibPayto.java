package hu.agyklub.csokicraft.libpayto;

import java.net.URI;

import javax.validation.constraints.NotNull;

import hu.agyklub.csokicraft.libpayto.objects.AbstractPaytoData;
import hu.agyklub.csokicraft.libpayto.objects.PaytoBicData;
import hu.agyklub.csokicraft.libpayto.objects.PaytoIbanData;

/**
  * libpayto: A Java library for handling <code>payto://</code> URIs, as described in RFC 8905
  * 
  * @author CsokiCraft
  * 
  * This library is free software; you can redistribute it and/or
  * modify it under the terms of the GNU Lesser General Public
  * License as published by the Free Software Foundation; either
  * version 2.1 of the License, or (at your option) any later version.
  */
public final class LibPayto{
	/** Parse a <code>payto://</code> URI into useful payment details data.
	  * While useful on its own, implementors are encouraged to use {@link #dispatchURI(URI)} instead.
	  * @see #dispatchURI(URI)
	  *
	  * @param uri the RFC 8905 format {@link URI} to parse
	  * @return a subclass of {@link AbstractPaytoData} representing payment data
	  */
	public static AbstractPaytoData parseURI(@NotNull URI uri){
		switch(uri.getRawAuthority()){
			case "bic":
				return new PaytoBicData(uri);
			case "iban":
				return new PaytoIbanData(uri);
		}
		throw new UnsupportedOperationException(String.format("Payment target type %s not supported yet!", uri.getRawAuthority()));
	}

	/** Parse a <code>payto://</code> URI and notify registered handlers of the new data being available.
	  * @see PaytoHandlerRegistry
	  *
	  * @param uri the RFC 8905 format {@link URI} to parse
	  */
	public static void dispatchURI(@NotNull URI uri){
		AbstractPaytoData data=parseURI(uri);
		if(data instanceof PaytoIbanData)
			PaytoHandlerRegistry.INSTANCE.dispatch((PaytoIbanData) data);
		else if(data instanceof PaytoBicData)
			PaytoHandlerRegistry.INSTANCE.dispatch((PaytoBicData) data);
		else // unreachable...
			throw new IllegalStateException("Wrong data class returned from parseURI(), bug in lib?");
	}
}
