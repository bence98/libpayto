package hu.agyklub.csokicraft.libpayto.test;

import hu.agyklub.csokicraft.libpayto.objects.PaymentAmount;

public class TestPaymentAmount{
	public static void main(String[] args){
		PaymentAmount am=new PaymentAmount("USD:123.69"),
				am2=new PaymentAmount("USD:420,000"),
				am3=new PaymentAmount("INR:111"),
				am4=new PaymentAmount("777");
		System.out.println(am);
		System.out.println(am.compareTo(am2));
		System.out.println(am.compareTo(am));
		System.out.println(am.equals(new PaymentAmount(am)));
		System.out.println(am.multiply(2));
		System.out.println(am.multiply(3).add(am2));
		try{
			System.out.println(am.add(am3));
			throw new IllegalStateException("Added different currencies!");
		}catch(IllegalArgumentException e){
			System.out.println(e.getMessage());
		}
		try{
			System.out.println(am.compareTo(am3));
			throw new IllegalStateException("Compared different currencies!");
		}catch(IllegalArgumentException e){
			System.out.println(e.getMessage());
		}
		try{
			System.out.println(am.add(am4));
			throw new IllegalStateException("Added different currencies!");
		}catch(IllegalArgumentException e){
			System.out.println(e.getMessage());
		}
		try{
			System.out.println(am4.add(am));
			throw new IllegalStateException("Added different currencies!");
		}catch(IllegalArgumentException e){
			System.out.println(e.getMessage());
		}
		System.out.println(am4.add(am4));
	}
}
