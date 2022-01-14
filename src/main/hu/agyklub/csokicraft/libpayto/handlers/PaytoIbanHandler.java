package hu.agyklub.csokicraft.libpayto.handlers;

import java.util.function.Consumer;

import hu.agyklub.csokicraft.libpayto.LibPayto;
import hu.agyklub.csokicraft.libpayto.PaytoHandlerRegistry;
import hu.agyklub.csokicraft.libpayto.objects.PaytoIbanData;

/**
 * A handler for {@link PaytoIbanData} data.
 * @see LibPayto#dispatchURI(java.net.URI)
 * @see PaytoHandlerRegistry#register(PaytoIbanHandler)
 */
public interface PaytoIbanHandler extends Consumer<PaytoIbanData>{
}
