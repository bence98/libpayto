package hu.agyklub.csokicraft.libpayto.objects;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class PaymentAmount{
	public String currency;
	@Min(0)
	@Max(9007199254740992L) // 2^53
	public long units;
	@Min(0)
	@Max(99_999_999)
	public int microcents;

	public PaymentAmount(@NotNull String str){
		String[] arr=str.split(":");
		String amountStr;
		if(arr.length>1){
			currency=arr[0];
			amountStr=arr[1];
		}else{
			// nonstandard, use native currency
			amountStr=arr[0];
		}

		String[] amountArr=amountStr.split("\\.");
		units=Long.parseLong(amountArr[0]);
		if(amountArr.length>1){
			// TODO
			String ucStr=amountArr[1].trim();
			microcents=Integer.parseInt(ucStr);
			for(int i=ucStr.length(); i<8; i++)
				microcents*=10;
		}
	}

	@NotNull
	@Override
	public String toString(){
		return String.format("%s:%d.%08d", currency, units, microcents);
	}
}
