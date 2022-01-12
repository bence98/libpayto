package hu.agyklub.csokicraft.libpayto.handlers;

import java.util.ArrayList;
import java.util.List;

import hu.agyklub.csokicraft.libpayto.objects.PaytoBicData;
import hu.agyklub.csokicraft.libpayto.objects.PaytoIbanData;

public final class PaytoHandlerRegistry{
	public static PaytoHandlerRegistry INSTANCE=new PaytoHandlerRegistry();
	private List<PaytoIbanHandler> ibanHandlers=new ArrayList<>();
	private List<PaytoBicHandler> bicHandlers=new ArrayList<>();

	protected PaytoHandlerRegistry(){}

	public void register(PaytoIbanHandler handler){
		synchronized(ibanHandlers){
			ibanHandlers.add(handler);
		}
	}

	public void register(PaytoBicHandler handler){
		synchronized(bicHandlers){
			bicHandlers.add(handler);
		}
	}

	public void dispatch(PaytoIbanData data){
		synchronized(ibanHandlers){
			for(PaytoIbanHandler hndl:ibanHandlers)
				hndl.onIbanData(data);
		}
	}

	public void dispatch(PaytoBicData data){
		synchronized(bicHandlers){
			for(PaytoBicHandler hndl:bicHandlers)
				hndl.onBicData(data);
		}
	}
}
