package hu.agyklub.csokicraft.libpayto.objects;

import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotNull;

/**
  * Abstract base class for other data classes. Contains common parameters (section 5. Generic Options in RFC 8905) for all payment target types.
  */
public abstract class AbstractPaytoData{
	/** The amount of funds to transfer */
	public PaymentAmount amount;
	public String receiver_name, sender_name, message, instruction;
	public Map<String, String> otherParams=new HashMap<>();

	/** Parses the {@link URI} for payment details.
	  * Extracts common parameters from the query segment of the URI.
	  */
	public AbstractPaytoData(@NotNull URI uri){
		String query=uri.getRawQuery();
		if(query!=null){
			for(String q:query.split("&")){
				String[] kv=q.split("=");
				if(kv.length!=2){
					// illegal query param
					throw new IllegalArgumentException(String.format("Illegal query param: %s", q));
				}
				String val=URLDecoder.decode(kv[1], StandardCharsets.UTF_8);
				switch(kv[0]){
					case "amount":
						amount=new PaymentAmount(val);
						break;
					case "receiver-name":
						receiver_name=val;
						break;
					case "sender-name":
						sender_name=val;
						break;
					case "message":
						message=val;
						break;
					case "instruction":
						instruction=val;
						break;
					default:
						otherParams.put(kv[0], val);
				}
			}
		}
	}

	private void addPart(String str, String label, StringBuilder sb){
		if(str!=null){
			if(sb.length()>0)
				sb.append('&');
			sb.append(label);
			sb.append(URLEncoder.encode(str, StandardCharsets.UTF_8));
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
		otherParams.forEach((k, v)->{addPart(v, k+"=", sb);});
		return sb.toString();
	}
}
