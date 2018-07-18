package com.echain.solidity;

import com.echain.common.util.DateUtil;
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

import javax.annotation.Resource;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestSaveTest {

    @Resource(name = "web3j")
    Web3j web3j;

    @Autowired
    ParamsProperties paramsProperties;

    @Autowired
    UpDownData upDownData;

    public SaveData_sol_SaveData deploy(Credentials credentials) throws Exception {
        SaveData_sol_SaveData sss = SaveData_sol_SaveData.deploy(web3j, credentials, EchainConstant.GAS_PRICE, Contract.GAS_LIMIT).send();

        return sss;
    }

    public SaveData_sol_SaveData load(Credentials credentials) throws Exception {
        SaveData_sol_SaveData sss = SaveData_sol_SaveData.load(paramsProperties.getSaveDataAddress(), web3j, credentials, EchainConstant.GAS_PRICE, Contract.GAS_LIMIT);

        return sss;
    }

    @Test
    public void test() throws Exception {
//		String walletfile = "/Users/roc/Downloads/wallet/UTC--2018-03-27T07-57-53.477762418Z--cf8e15d01d5f932a6c880757854bcfd3fd4b90e5";
//		Credentials credentials = WalletUtils.loadCredentials("123456", walletfile);
        String walletfile = "/Users/roc/mainETH/UTC--2018-04-13T08-20-33.426Z--e1b7725ce3ef50f6d78e6202357bc80776e6dabe";
        Credentials credentials = WalletUtils.loadCredentials("wallet_2018@echain", walletfile);

        SaveData_sol_SaveData contract = load(credentials);//deploy
        System.out.println("==========" + contract.getContractAddress());
        String date = DateUtil.convert2String(new Date(), "yyyyMMdd");
        upDownData.uploadData(100l, 10l, "1210c84e62546c716573425ff95aad5ee936", date);

//        upDownData.uploadData(100l, 11l, "1c84e62546c716573425ff95aad5ee936", date);

//        upDownData.uploadData(100l, 12l, "2c84e62546c716573425ff95aad5ee936", date);
//		TransactionReceipt transaction = contract.setstring(date+":"+100+":"+1,"22345560000123123411").send();

//		System.out.println(transaction.getTransactionHash());
//		System.out.println(transaction.getBlockNumber());

//		List<SetStringEventResponse> ls = contract.getSetStringEvents(transaction);
//		for(SetStringEventResponse s : ls) {
//			System.out.println(s.key);
//			System.out.println(s.types);
        System.out.println("key ========  " + date + ":" + 100 + ":" + 10);
        System.out.println(contract.getString(date + ":" + 100 + ":" + 10).send());

//        System.out.println("key ========  " + date + ":" + 100 + ":" + 11);
//        System.out.println(contract.getString(date + ":" + 100 + ":" + 11).send());
//
//        System.out.println("key ========  " + date + ":" + 100 + ":" + 12);
//        System.out.println(contract.getString(date + ":" + 100 + ":" + 12).send());
//		}


//				


//		0xe1b7725CE3EF50f6d78e6202357bc80776E6DAbe
//		0xe1b7725CE3EF50f6d78e6202357bc80776E6DAbe
//		System.out.println(contract.getContractAddress());

//		TransactionReceipt t1 = contract.transfer("0x08b970c45d5af01aea5b9ce3556ccf34375a4f95", BigInteger.valueOf(2000000l)).send();
//		TransactionReceipt t2 = contract.transfer("0x74923229cbacea5ff3ea8f11144cf6a3a72d9567", BigInteger.valueOf(3000000l)).send();

//		contract.approve("0x37973061bf8dfec6e3d1799fa08d7e210d74588d", BigInteger.valueOf(30000000l)).send();
//		TransactionReceipt t3 = contract.transferFrom("0x37973061bf8dfec6e3d1799fa08d7e210d74588d","0x74923229cbacea5ff3ea8f11144cf6a3a72d9567", BigInteger.valueOf(30000000l)).send();


//		contract.approve("0xcf8e15d01d5f932a6c880757854bcfd3fd4b90e5", BigInteger.valueOf(300000000000000l)).send();
//		TransactionReceipt t4 = contract.transferFrom("0xcf8e15d01d5f932a6c880757854bcfd3fd4b90e5","37973061bf8dfec6e3d1799fa08d7e210d74588d", BigInteger.valueOf(300000000000000l)).send();

//		
//		System.out.println(contract.balanceOf("0xcf8e15d01d5f932a6c880757854bcfd3fd4b90e5").send());
//		System.out.println(contract.balanceOf("0x37973061bf8dfec6e3d1799fa08d7e210d74588d").send());
//		System.out.println(contract.balanceOf("0x74923229cbacea5ff3ea8f11144cf6a3a72d9567").send());
//		
//		System.out.println(contract.allowance("0x74923229cbacea5ff3ea8f11144cf6a3a72d9567", "0x80f532bb797feaa867a97dd3debeee8b2492413a").send());

//		0xcf8e15d01d5f932a6c880757854bcfd3fd4b90e5    true
//0x74923229cbacea5ff3ea8f11144cf6a3a72d9567

    }

}
