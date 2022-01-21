package hu.agyklub.csokicraft.libpayto.objects;

import java.util.regex.Pattern;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
  * Payment data describing the amount of funds to be transferred.
  * Stored as a currency code plus a fix-point value, with 8 decimal places after the point. 
  */
public class PaymentAmount implements Comparable<PaymentAmount>{
	/** A regex for determining whether a string is a valid amount string.
	  * This matches only the part after the colon. Commas need to be stripped beforehand.
	  */
	private static Pattern amountRegex=Pattern.compile("\\+?\\d*(?:\\.\\d{0,8})?");

	/** Identifier of the currency to be used for the transaction.
	  * May be an arbitrary {@link String}, but is most often 3 characters long.<br />
	  *
	  * "If a 3-letter {@link #currency} is used, it MUST be an ISO 4217
	  * alphabetic code.  A payment target type MAY define semantics
	  * beyond ISO 4217 for currency codes that are not 3 characters."
	  * (from RFC 8905)<br />
	  *
	  * As per standard, the {@link #currency} is mandatory, but some nonstandard
	  * implementations may leave it optional (<code>null</code>), in which case the default currency for
	  * the user SHOULD be used.
	  */
	public String currency;
	/** The units (whole) part of the amount. A non-negative integer less than <code>2^53</code> */
	@Min(0)
	@Max(9007199254740992L) // 2^53
	public long units;
	/** The fractions part of the amount, in micro-cents (1/10^8-ths). */
	@Min(0)
	@Max(99_999_999)
	public int microcents;

	/** Parse a {@link String} into {@link PaymentAmount} */
	public PaymentAmount(@NotNull String str){
		String[] arr=str.split(":");
		String amountStr;
		if(arr.length>1){
			currency=arr[0];
			if(currency.isEmpty())
				throw new IllegalArgumentException("Currency may not be empty string!");
			amountStr=arr[1];
		}else{
			// nonstandard, use native currency
			amountStr=arr[0];
		}
		amountStr=amountStr.replace(",", "");
		if(!amountRegex.matcher(amountStr).matches())
			throw new IllegalArgumentException(String.format("%s is not a valid positive fixpoint value! (from '%s')", amountStr, str));

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

	/** Create a copy of the {@link PaymentAmount} */
	public PaymentAmount(PaymentAmount val){
		currency=val.currency;
		units=val.units;
		microcents=val.microcents;
	}

	@NotNull
	@Override
	public String toString(){
		return String.format("%s:%d.%08d", currency, units, microcents);
	}

	/** Check if a {@link PaymentAmount}'s currency is the same as this one's.
	  * Attempting to do operations on {@link PaymentAmount}s of different currencies
	  * will result in an {@link IllegalArgumentException}.
	  * @return true if both currencies are equal (or null)
	  */
	public boolean areCurrenciesEqual(PaymentAmount val){
		if(currency!=null)
			return currency.equals(val.currency);
		else
			return val.currency==null;
	}

	private void assertEqualCurrencies(PaymentAmount val, String op){
		if(!areCurrenciesEqual(val))
			throw new IllegalArgumentException(String.format("Different currencies %s and %s cannot be %s!", currency, val.currency, op));
	}

	/** Compare two {@link PaymentAmount}s.
	  * @throws IllegalArgumentException the currencies do not match
	  */
	@Override
	public int compareTo(PaymentAmount val){
		assertEqualCurrencies(val, "compared");
		long units_diff=units-val.units;
		if(units_diff<0)
			return -1;
		else if(units_diff>0)
			return +1;
		return microcents-val.microcents;
	}

	@Override
	public boolean equals(Object obj){
		if(!(obj instanceof PaymentAmount))
			return false;
		return compareTo((PaymentAmount) obj)==0;
	}

	/** Add a {@link PaymentAmount} and this value.
	  * Does not modify the original object.
	  * @throws IllegalArgumentException the currencies do not match
	  * @return the sum of the two values
	  */
	public PaymentAmount add(PaymentAmount val){
		assertEqualCurrencies(val, "added");
		PaymentAmount ret=new PaymentAmount(this);
		ret.microcents+=val.microcents;
		int carry=ret.microcents/100_000_000;
		ret.microcents%=100_000_000;
		ret.units+=val.units+carry;
		return ret;
	}

	/** Multiply {@link PaymentAmount} by a scalar.
	  * Does not modify the original object.
	  * @return the product
	  */
	public PaymentAmount multiply(int val){
		if(val<0)
			throw new IllegalArgumentException("Cannot multiply by negative value!");
		PaymentAmount ret=new PaymentAmount(this);
		ret.microcents*=val;
		int carry=ret.microcents/100_000_000;
		ret.microcents%=100_000_000;
		ret.units*=val;
		ret.units+=carry;
		return ret;
	}
}
