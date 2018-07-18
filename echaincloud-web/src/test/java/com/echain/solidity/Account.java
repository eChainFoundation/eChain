package com.echain.solidity;

import com.echain.service.WalletService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.NewAccountIdentifier;
import org.web3j.protocol.admin.methods.response.PersonalListAccounts;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.ShhNewIdentity;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert.Unit;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Account {

    @Resource(name = "admin")
    Admin admin;

    @Resource(name = "web3j")
    Web3j web3j;

    @Autowired
    WalletService walletService;

    /**
     * Life
     * Like this
     * Like that
     * Also
     * It's not the same with you think
     *
     * @Author lzh
     */
    public List<String> getAccountlist() {

        try {
            return admin.personalListAccounts().send().getAccountIds();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Test
    public void test() throws IOException {
        String accId = walletService.createAccount("123456", admin);
        System.out.println("accId  ==  " + accId);

//    	try {
//			a.transfer("0x74923229cbacea5ff3ea8f11144cf6a3a72d9567", 10000f);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (TransactionException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

//    	EthBlockNumber blockNumber = web3j.ethBlockNumber().send();
//    	System.out.println(blockNumber.getBlockNumber());
//    	
//    	List<String> ls = a.getAccountlist();
//    	if(ls != null && ls.size() > 0) {
//    		for(String s : ls) {
////    			System.out.println(s + "    " + a.unlock(s,"123456"));
//    			System.out.println(blockNumber + "    " + blockNumber.getBlockNumber());
//    			System.out.println(s + "    " + a.getBalance(s,blockNumber.getBlockNumber()));
//    		}
//    	} else
//    		System.out.println(111);


//    	System.out.println(a.setString("123456789"));


//    	Greeter contract = Greeter.deploy(
//                web3j, credentials,
//                ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT,
//                "Hello blockchain world!").send();


    }

    public void transfer(String account, float coin)
            throws InterruptedException, IOException, TransactionException, Exception {
        String password = "123456";
        String walletfile = "/Users/roc/Downloads/wallet/UTC--2018-03-27T07-57-53.477762418Z--cf8e15d01d5f932a6c880757854bcfd3fd4b90e5";
        Credentials credentials = WalletUtils.loadCredentials(password, walletfile);
        TransactionReceipt transactionReceipt = Transfer
                .sendFunds(web3j, credentials, account, BigDecimal.valueOf(coin), Unit.ETHER).send();
        System.out.println(transactionReceipt.getStatus());
    }

    public String createAccount() {
        try {
//        	EthCoinbase identity = web3j.ethCoinbase().send();
            ShhNewIdentity identity = web3j.shhNewIdentity().send();
            return identity.getAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String createAccount(String password) {
        try {
            NewAccountIdentifier newAccountIdentifier = admin.personalNewAccount(password).send();
            return newAccountIdentifier.getAccountId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> getPersonalListAccounts() {

        try {
            PersonalListAccounts personalListAccounts = admin.personalListAccounts().send();

            return personalListAccounts.getAccountIds();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Boolean unlock(String accountAddr, String passphrase) {
        try {
            return admin.personalUnlockAccount(accountAddr, passphrase, BigInteger.valueOf(500l)).send().accountUnlocked();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public BigInteger getBalance(String accountId, BigInteger blockNumber) {
        try {
            DefaultBlockParameter defaultBlockParameter = new DefaultBlockParameterNumber(blockNumber);
            EthGetBalance ethGetBalance = admin.ethGetBalance(accountId, defaultBlockParameter).send();
            if (ethGetBalance != null) {
                return ethGetBalance.getBalance();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
