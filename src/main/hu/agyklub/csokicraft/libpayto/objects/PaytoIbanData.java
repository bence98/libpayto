package hu.agyklub.csokicraft.libpayto.objects;

import java.net.URI;

public class PaytoIbanData extends AbstractPaytoBicData{
	public String iban;

	public PaytoIbanData(URI uri){
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
