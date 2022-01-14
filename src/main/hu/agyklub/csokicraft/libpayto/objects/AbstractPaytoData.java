package hu.agyklub.csokicraft.libpayto.objects;

import java.net.URI;
import java.net.URLEncoder;

import javax.validation.constraints.NotNull;

/**
  * Abstract base class for other data classes. Contains common parameters (section 5. Generic Options in RFC 8905) for all payment target types.
  */
public abstract class AbstractPaytoData{
	/** The amount of funds to transfer */
	public PaymentAmount amount;
	public String receiver_name, sender_name, message, instruction;

	/** Parses the {@link URI} for payment details.
	  * Extracts common parameters from the query segment of the URI.
	  */
	public AbstractPaytoData(@NotNull URI uri){
		String query=uri.getQuery();
		if(query!=null){
			for(String q:query.split("&")){
				String[] kv=q.split("=");
				if(kv.length!=2){
					// illegal query param
					throw new IllegalArgumentException(String.format("Illegal query param: %s", q));
				}
				switch(kv[0]){
					case "amount":
						amount=new PaymentAmount(kv[1]);
						break;
					case "receiver-name":
						receiver_name=kv[1];
						break;
					case "sender-name":
						sender_name=kv[1];
						break;
					case "message":
						message=kv[1];
						break;
					case "instruction":
						instruction=kv[1];
						break;
				}
			}
		}
	}

	private void addPart(String str, String label, StringBuilder sb){
		if(str!=null){
			if(sb.length()>0)
				sb.append('&');
			sb.append(label);
			sb.append(URLEncoder.encode(str));
		}
	}

	/** @return the query URI segment represented by this object */
	@NotNull
	@Override
	public String toString(){
		StringBuilder sb=new StringBuilder();
		if(amount!=null){
			sb.append("amount=");
			sb.append(amount);
		}
		addPart(receiver_name, "receiver-name=", sb);
		addPart(sender_name, "sender-name=", sb);
		addPart(message, "message=", sb);
		addPart(instruction, "instruction=", sb);
		return sb.toString();
	}
}
