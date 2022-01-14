package hu.agyklub.csokicraft.libpayto.objects;

import java.net.URI;

import javax.validation.constraints.NotNull;

class AbstractPaytoBicData extends AbstractPaytoData{
	/** The Business Identifier Code (also known as SWIFT code) for the target financial institution.<br />
	  * Must not be <code>null</code> for {@link PaytoBicData}, may be <code>null</code> for {@link PaytoIbanData}.
	  */
	public String bic;

	public AbstractPaytoBicData(@NotNull URI uri){
		super(uri);
	}
}
