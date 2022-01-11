package hu.agyklub.csokicraft.libpayto.objects;

import java.net.URI;

public abstract class AbstractPaytoData{
    public PaymentAmount amount;
    public String receiver_name, sender_name, message, instruction;

    public AbstractPaytoData(URI uri){
        String query=uri.getQuery();
        if(query!=null){
            for(String q:query.split("&")){
                String[] kv=q.split("=");
                if(kv.length!=2){
                    // illegal query param
                    throw new IllegalArgumentException(String.format("Illegal query param: %s", q));
                }
                switch (kv[0]){
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
}
