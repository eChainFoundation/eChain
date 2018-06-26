package com.echain.solidity;

import com.echain.conf.ParamsProperties;
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
import java.math.BigInteger;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TokenLockTest {

    @Resource(name = "web3j")
    Web3j web3j;

    @Autowired
    ParamsProperties paramsProperties;

//	public static void main(String[] args) {
//		Date date = new Date();
//		System.out.println(date.getTime()/1000);
//		Long ll = (date.getTime()+5000)/1000;
//		System.out.println(ll);
//	}

    @Test
    public void test() throws Exception {

//		String walletfile = "/Users/roc/mainETH/UTC--2018-04-13T08-20-33.426Z--e1b7725ce3ef50f6d78e6202357bc80776e6dabe";
//		Credentials credentials = WalletUtils.loadCredentials("wallet_2018@echain", walletfile);

        //local
        String walletfile = "/Users/roc/Downloads/wallet/UTC--2018-03-27T07-57-53.477762418Z--cf8e15d01d5f932a6c880757854bcfd3fd4b90e5";
        Credentials credentials = WalletUtils.loadCredentials("123456", walletfile);
//		System.out.println(str);

        TokenTimelock_sol_TokenTimelock contract = deploy(credentials);//deploy(credentials);

        System.out.println("=============" + contract.getContractAddress());

        System.out.println("=============" + new Date());


//		TransactionReceipt t1 = contract.release().send();
//		System.out.println("token === "+contract.token().send());
//		System.out.println("beneficiary === "+contract.beneficiary().send());
//		System.out.println("releaseTime === "+contract.releaseTime().send());

//		contract.approve("0xcf8e15d01d5f932a6c880757854bcfd3fd4b90e5", BigInteger.valueOf(5000000)).send();
//		contract.burn(BigInteger.valueOf(5000000)).send();
//		
//		System.out.println(contract.balanceOf("0xcf8e15d01d5f932a6c880757854bcfd3fd4b90e5").send());

//		TransactionReceipt t1 = contract.transfer("0x08b970c45d5af01aea5b9ce3556ccf34375a4f95", BigInteger.valueOf(2000000l)).send();
//		TransactionReceipt t2 = contract.transfer("0x74923229cbacea5ff3ea8f11144cf6a3a72d9567", BigInteger.valueOf(3000000l)).send();

//		contract.approve("0x37973061bf8dfec6e3d1799fa08d7e210d74588d", BigInteger.valueOf(30000000l)).send();
//		TransactionReceipt t3 = contract.transferFrom("0x37973061bf8dfec6e3d1799fa08d7e210d74588d","0x74923229cbacea5ff3ea8f11144cf6a3a72d9567", BigInteger.valueOf(30000000l)).send();


//		contract.approve("0xcf8e15d01d5f932a6c880757854bcfd3fd4b90e5", BigInteger.valueOf(300000000000000l)).send();
//		TransactionReceipt t4 = contract.transferFrom("0xcf8e15d01d5f932a6c880757854bcfd3fd4b90e5","37973061bf8dfec6e3d1799fa08d7e210d74588d", BigInteger.valueOf(300000000000000l)).send();

//		System.out.println(contract.balanceOf("0xcf8e15d01d5f932a6c880757854bcfd3fd4b90e5").send());

//		System.out.println(contract.balanceOf("0x37973061bf8dfec6e3d1799fa08d7e210d74588d").send());
//		System.out.println(contract.balanceOf("0x74923229cbacea5ff3ea8f11144cf6a3a72d9567").send());
//		
//		System.out.println(contract.allowance("0x74923229cbacea5ff3ea8f11144cf6a3a72d9567", "0x80f532bb797feaa867a97dd3debeee8b2492413a").send());

//		0xcf8e15d01d5f932a6c880757854bcfd3fd4b90e5    true
//0x74923229cbacea5ff3ea8f11144cf6a3a72d9567
//		1 0000 0000 0000 0000     000000000000000000

    }

    public TokenTimelock_sol_TokenTimelock deploy(Credentials credentials) throws Exception {
//		TokenTimelock_sol_TokenTimelock sss = TokenTimelock_sol_TokenTimelock.deploy(web3j, credentials, ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT).send();
        TokenTimelock_sol_TokenTimelock sss = TokenTimelock_sol_TokenTimelock.deploy(web3j, credentials, ManagedTransaction.GAS_PRICE,
                Contract.GAS_LIMIT, paramsProperties.getEpointsAddress(), "0x851334ae1ec2493181eb069e964a3876795ec8ea", BigInteger.valueOf(60)).send();

        return sss;
    }

    public TokenTimelock_sol_TokenTimelock load(Credentials credentials) throws Exception {
        TokenTimelock_sol_TokenTimelock sss = TokenTimelock_sol_TokenTimelock.load("0x29e223ad02460380ad04f24cffff3d649456b52c", web3j, credentials, ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);

        return sss;
    }
}
