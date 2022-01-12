package hu.agyklub.csokicraft.libpayto.test;

import hu.agyklub.csokicraft.libpayto.objects.PaymentAmount;

public class TestPaymentAmount {
    public static void main(String[] args) {
        PaymentAmount am=new PaymentAmount("USD:123.69");
        System.out.println(am);
    }
}
