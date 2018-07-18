package com.echain.wallet;

import com.echain.service.WalletService;
import org.junit.Test;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;

public class WalletTest {
    @Test
    public void test() throws Exception {

        WalletService walletService = new WalletService();

        BigInteger balance = walletService.getETHBalance("0x2a0ac6022f3cf4ae45388420e3a428ea803f6308");
        System.out.println(balance);

//        walletService.transferToETH("0x65cacef2cc12e46f2a91be22fb3f1cdca756e597", 2000);

//        BigInteger value = Convert.toWei("1", Convert.Unit.ETHER).toBigInteger();
//        System.out.println(value);

        BigInteger balance2 = walletService.getETHBalance("0x2a0ac6022f3cf4ae45388420e3a428ea803f6308");
        System.out.println(balance2);

        BigInteger balance3 = walletService.getETHBalance("0x65cacef2cc12e46f2a91be22fb3f1cdca756e597");
        System.out.println(balance3);
    }

    @Test
    public void testWallet() {
        BigDecimal bigDecimal = Convert.fromWei("100000000", Convert.Unit.ETHER);
        System.out.println(bigDecimal.stripTrailingZeros().toPlainString());

        BigDecimal bigDecimal2 = Convert.toWei("0.000000000000001111111111111111111111111111111111111111111111111",
                Convert.Unit.ETHER).setScale(0, BigDecimal.ROUND_DOWN);
        System.out.println(bigDecimal2);
    }

    @Test
    public void testDec() {
//        BigInteger balance = new BigInteger("10000000000000");
//        BigDecimal decimal = new BigDecimal(balance).divide(BigDecimal.valueOf(Math.pow(10, 8)));
//        System.out.println(decimal);


        BigDecimal wei = new BigDecimal("0.00001111111111111111").multiply(BigDecimal.valueOf(Math.pow(10, 8)))
                .setScale(0, BigDecimal.ROUND_DOWN);
        System.out.println(wei);
    }
}
