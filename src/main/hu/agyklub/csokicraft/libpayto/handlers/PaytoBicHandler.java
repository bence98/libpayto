package hu.agyklub.csokicraft.libpayto.handlers;

import hu.agyklub.csokicraft.libpayto.objects.PaytoBicData;

public interface PaytoBicHandler{
	void onBicData(PaytoBicData bicData);
}
