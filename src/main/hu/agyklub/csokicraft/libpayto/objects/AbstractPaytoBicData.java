package hu.agyklub.csokicraft.libpayto.objects;

import java.net.URI;

import javax.validation.constraints.NotNull;

class AbstractPaytoBicData extends AbstractPaytoData{
	public String bic;

	public AbstractPaytoBicData(@NotNull URI uri){
		super(uri);
	}
}
