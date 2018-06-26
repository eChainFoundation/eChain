package com.echain.solidity;

import com.echain.conf.ParamsProperties;
import com.echain.constant.EchainConstant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ECtokenTest {

    @Resource(name = "web3j")
    Web3j web3j;

    @Autowired
    ParamsProperties paramsProperties;

    @Test
    public void test() throws Exception {

//		String walletfile = "/Users/roc/mainETH/UTC--2018-04-13T08-20-33.426Z--e1b7725ce3ef50f6d78e6202357bc80776e6dabe";
//		Credentials credentials = WalletUtils.loadCredentials("wallet_2018@echain", walletfile);

        //local
//		String walletfile = "/Users/roc/Downloads/wallet/UTC--2018-03-27T07-57-53.477762418Z--cf8e15d01d5f932a6c880757854bcfd3fd4b90e5";
        Credentials credentials = WalletUtils.loadCredentials("123456", paramsProperties.getWalletFile());
//		System.out.println(str);

//		ECPoints_sol_ECPoints contract = deploy(credentials);//deploy(credentials);
        ECPoints_sol_ECPoints contract = load(credentials);

        String symbol = contract.symbol().send();
        System.out.println(symbol);

//        String owner = contract.owner().send();
//        System.out.println(owner);

        //0x5df64d0349679310d70d39a9cf3c9cd96867a653
//        System.out.println("=============" + contract.getContractAddress());
//
//        System.out.println("111 ====  " + contract.balanceOf("0x2a0ac6022f3cf4ae45388420e3a428ea803f6308").send());
//        contract.approve("0x2a0ac6022f3cf4ae45388420e3a428ea803f6308", BigInteger.valueOf(500000000L)).send();
////		contract.burn(BigInteger.valueOf(500000000L)).send();
//        contract.burnFrom("0x2a0ac6022f3cf4ae45388420e3a428ea803f6308", BigInteger.valueOf(500000000L)).send();
//		contract.transfer("0xc05d932e1d0c63dbd0c5434fb34d007cebfbc22d", BigInteger.valueOf(500000000000l)).send();
//		
//        System.out.println("111 ====  " + contract.balanceOf("0x2a0ac6022f3cf4ae45388420e3a428ea803f6308").send());
//		contract.mintToken("0x2a0ac6022f3cf4ae45388420e3a428ea803f6308",BigInteger.valueOf(1000000000L)).send();
//        System.out.println("222 ====  " + contract.balanceOf("0x2a0ac6022f3cf4ae45388420e3a428ea803f6308").send());

//		System.out.println(contract.balanceOf("0xc05d932e1d0c63dbd0c5434fb34d007cebfbc22d").send());
//
//		System.out.println(contract.balanceOf("0x851334ae1ec2493181eb069e964a3876795ec8ea").send());
//
//		contract.mintToken("0xcf8e15d01d5f932a6c880757854bcfd3fd4b90e5", BigInteger.valueOf(10000000000l)).send();
//		System.out.println(contract.balanceOf("0xcf8e15d01d5f932a6c880757854bcfd3fd4b90e5").send());

//		TransactionReceipt t1 = contract.transfer("0x65cacef2cc12e46f2a91be22fb3f1cdca756e597", BigInteger.valueOf(2000000L)).send();
////		TransactionReceipt t2 = contract.transfer("0x74923229cbacea5ff3ea8f11144cf6a3a72d9567", BigInteger.valueOf(3000000l)).send();
//
//		System.out.println("333 ====  " + contract.balanceOf("0x2a0ac6022f3cf4ae45388420e3a428ea803f6308").send());
//		System.out.println("444 ====  " + contract.balanceOf("0x65cacef2cc12e46f2a91be22fb3f1cdca756e597").send());

//		contract.approve("0x37973061bf8dfec6e3d1799fa08d7e210d74588d", BigInteger.valueOf(30000000l)).send();
//		TransactionReceipt t3 = contract.transferFrom("0x37973061bf8dfec6e3d1799fa08d7e210d74588d","0x74923229cbacea5ff3ea8f11144cf6a3a72d9567", BigInteger.valueOf(30000000l)).send();


//		contract.approve("0x2a0ac6022f3cf4ae45388420e3a428ea803f6308", BigInteger.valueOf(1000000000L)).send();
//        TransactionReceipt t4 = contract.transferFrom("0x2a0ac6022f3cf4ae45388420e3a428ea803f6308",
//                "0x65cacef2cc12e46f2a91be22fb3f1cdca756e597", BigInteger.valueOf(1000000000L)).send();
//
//        List<ECPoints_sol_ECPoints.TransferEventResponse> tfs = contract.getTransferEvents(t4);
////
//        for (ECPoints_sol_ECPoints.TransferEventResponse tf : tfs) {
//            System.out.println("tf.from  =  " + tf.from);
//            System.out.println("tf.to  =  " + tf.to);
//            System.out.println("tf.value  =  " + tf.value);
//        }
//		
//		
//		System.out.println(contract.balanceOf("0x2a0ac6022f3cf4ae45388420e3a428ea803f6308").send());
//		System.out.println(contract.balanceOf("0x65cacef2cc12e46f2a91be22fb3f1cdca756e597").send());
//		System.out.println(contract.balanceOf("0x74923229cbacea5ff3ea8f11144cf6a3a72d9567").send());
//		
//		System.out.println(contract.allowance("0x74923229cbacea5ff3ea8f11144cf6a3a72d9567", "0x80f532bb797feaa867a97dd3debeee8b2492413a").send());

//		0xcf8e15d01d5f932a6c880757854bcfd3fd4b90e5    true
//0x74923229cbacea5ff3ea8f11144cf6a3a72d9567
//		1 0000 0000 0000 0000     000000000000000000

    }

    public ECPoints_sol_ECPoints deploy(Credentials credentials) throws Exception {
        ECPoints_sol_ECPoints sss = ECPoints_sol_ECPoints.deploy(web3j, credentials, EchainConstant.GAS_PRICE, Contract.GAS_LIMIT).send();

        return sss;
    }

    public ECPoints_sol_ECPoints load(Credentials credentials) throws Exception {
        ECPoints_sol_ECPoints sss = ECPoints_sol_ECPoints.load(paramsProperties.getEpointsAddress(), web3j,
                credentials, ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);

        return sss;
    }
}
