package hu.agyklub.csokicraft.libpayto.objects;

import java.net.URI;

public class PaytoBicData extends AbstractPaytoBicData{
	public PaytoBicData(URI uri){
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
