package hu.agyklub.csokicraft.libpayto.handlers;

import hu.agyklub.csokicraft.libpayto.objects.PaytoIbanData;

public interface PaytoIbanHandler{
    void onIbanData(PaytoIbanData ibanData);
}
