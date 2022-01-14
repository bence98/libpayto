package hu.agyklub.csokicraft.libpayto.objects;

import java.net.URI;

import javax.validation.constraints.NotNull;

/**
  * Data contained in <code>payto://iban/</code> URIs.
  */
public class PaytoIbanData extends AbstractPaytoBicData{
	/** The IBAN number for the target account. May not be <code>null</code>. */
	@NotNull
	public String iban;

	/** Parses the {@link URI} for payment details.
	  * Extracts the targeted BIC (if any), IBAN, as well as common payment details.
	  * @see AbstractPaytoData#AbstractPaytoData(URI)
	  * @throws IllegalArgumentException if the URI is not of the form <code>payto://iban/&lt;bic&gt;/&lt;iban&gt;</code> or <code>payto://iban/&lt;iban&gt;</code>
	  * @throws NullPointerException if the URI doesn't have a path
	  */
	public PaytoIbanData(@NotNull URI uri){
		super(uri);
		String path=uri.getRawPath();
		if(path!=null){
			String[] pathEls=path.split("/");
			if(pathEls.length==3){
				bic=pathEls[1];
				iban=pathEls[2];
			}else if(pathEls.length==2)
				iban=pathEls[1];
			else
				throw new IllegalArgumentException(String.format("Malformed URI path '%s'!", path));

		}else throw new NullPointerException("No URI path given!");
	}

	/** @return the URI represented by this object */
	@NotNull
	@Override
	public String toString(){
		StringBuilder sb=new StringBuilder("payto://iban/");
		if(bic!=null){
			sb.append(bic);
			sb.append('/');
		}
		sb.append(iban);
		String query=super.toString();
		if(query!=null&&!query.isEmpty()){
			sb.append('?');
			sb.append(query);
		}
		return sb.toString();
	}
}
