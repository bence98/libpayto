package hu.agyklub.csokicraft.libpayto.objects;

import java.net.URI;

import javax.validation.constraints.NotNull;

/**
  * Data contained in <code>payto://bic/</code> URIs.
  */
public class PaytoBicData extends AbstractPaytoBicData{
	/** Parses the {@link URI} for payment details.
	  * Extracts the targeted BIC, as well as common payment details.
	  * @see AbstractPaytoData#AbstractPaytoData(URI)
	  * @throws IllegalArgumentException if the URI is not of the form <code>payto://bic/&lt;bic&gt;</code>
	  * @throws NullPointerException if the URI doesn't have a path
	  */
	public PaytoBicData(@NotNull URI uri){
		super(uri);
		String path=uri.getRawPath();
		if(path!=null){
			String[] pathEls=path.split("/");
			if(pathEls.length==2)
				bic=pathEls[1];
			else
				throw new IllegalArgumentException(String.format("Malformed URI path '%s'!", path));

		}else throw new NullPointerException("No URI path given!");
	}

	/** @return the URI represented by this object */
	@NotNull
	@Override
	public String toString(){
		StringBuilder sb=new StringBuilder("payto://bic/");
		sb.append(bic);
		String query=super.toString();
		if(query!=null&&!query.isEmpty()){
			sb.append('?');
			sb.append(query);
		}
		return sb.toString();
	}
}
