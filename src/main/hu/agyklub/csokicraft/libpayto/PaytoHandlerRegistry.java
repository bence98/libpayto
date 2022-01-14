package hu.agyklub.csokicraft.libpayto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import hu.agyklub.csokicraft.libpayto.handlers.PaytoBicHandler;
import hu.agyklub.csokicraft.libpayto.handlers.PaytoIbanHandler;
import hu.agyklub.csokicraft.libpayto.objects.PaytoBicData;
import hu.agyklub.csokicraft.libpayto.objects.PaytoIbanData;

/**
  * A registry for payment data handlers.
  */
public final class PaytoHandlerRegistry{
	/** Singleton instance */
	public static PaytoHandlerRegistry INSTANCE=new PaytoHandlerRegistry();
	private List<PaytoIbanHandler> ibanHandlers=new ArrayList<>();
	private List<PaytoBicHandler> bicHandlers=new ArrayList<>();

	protected PaytoHandlerRegistry(){}

	/** Register a new handler for IBAN data. A handler can be:
	  * <ul>
	  * <li>a void function (static or member) taking a {@link PaytoIbanData} argument</li>
	  * <li>a lambda mapping a {@link PaytoIbanData} to void</li>
	  * <li>a class implementing {@link PaytoIbanHandler}</li>
	  * </ul>
	  */
	public void register(@NotNull PaytoIbanHandler handler){
		synchronized(ibanHandlers){
			ibanHandlers.add(handler);
		}
	}

	/** Register a new handler for BIC data. A handler can be:
	  * <ul>
	  * <li>a void function (static or member) taking a {@link PaytoBicData} argument</li>
	  * <li>a lambda mapping a {@link PaytoBicData} to void</li>
	  * <li>a class implementing {@link PaytoBicHandler}</li>
	  * </ul>
	  */
	public void register(@NotNull PaytoBicHandler handler){
		synchronized(bicHandlers){
			bicHandlers.add(handler);
		}
	}

	void dispatch(PaytoIbanData data){
		synchronized(ibanHandlers){
			for(PaytoIbanHandler hndl:ibanHandlers)
				hndl.accept(data);
		}
	}

	void dispatch(PaytoBicData data){
		synchronized(bicHandlers){
			for(PaytoBicHandler hndl:bicHandlers)
				hndl.accept(data);
		}
	}
}
