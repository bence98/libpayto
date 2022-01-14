package hu.agyklub.csokicraft.libpayto.handlers;

import java.util.function.Consumer;

import hu.agyklub.csokicraft.libpayto.LibPayto;
import hu.agyklub.csokicraft.libpayto.PaytoHandlerRegistry;
import hu.agyklub.csokicraft.libpayto.objects.PaytoBicData;

/**
  * A handler for {@link PaytoBicData} data.
  * @see LibPayto#dispatchURI(java.net.URI)
  * @see PaytoHandlerRegistry#register(PaytoBicHandler)
  */
public interface PaytoBicHandler extends Consumer<PaytoBicData>{
}
