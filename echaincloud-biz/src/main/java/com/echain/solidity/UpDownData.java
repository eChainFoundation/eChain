package com.echain.solidity;

import com.echain.conf.ParamsProperties;
import com.echain.constant.EchainConstant;
import com.echain.solidity.SaveData_sol_SaveData.SetStringEventResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UpDownData {

    @Autowired
    ParamsProperties paramsProperties;

    @Resource(name = "web3j")
    Web3j web3j;

    public SaveData_sol_SaveData getContract() throws Exception {
        return getContract(web3j);
    }

    public SaveData_sol_SaveData getContract(Web3j web3j) throws Exception {
        return getContract(web3j, paramsProperties.getWalletPassword(), paramsProperties.getWalletFile());
    }

    public SaveData_sol_SaveData getContract(Web3j web3j, BigInteger price) throws Exception {
        return getContract(web3j, paramsProperties.getWalletPassword(), paramsProperties.getWalletFile(), price);
    }

    public SaveData_sol_SaveData getContract(Web3j web3j, String walletPassword, String walletFile) throws Exception {
        return getContract(web3j, walletPassword, walletFile, EchainConstant.GAS_PRICE);
    }

    public SaveData_sol_SaveData getContract(Web3j web3j, String walletPassword, String walletFile,
                                             BigInteger price) throws Exception {
        price = (price == null || price.longValue() <= 0) ? EchainConstant.GAS_PRICE : price;
        Credentials credentials = WalletUtils.loadCredentials(walletPassword, walletFile);
        SaveData_sol_SaveData contract = SaveData_sol_SaveData.load(paramsProperties.getSaveDataAddress(),
                web3j, credentials, price, EchainConstant.GAS_LIMIT);

        return contract;
    }

    public Map<String, String> uploadData(Long userId, Long dappId, String upData, String date) throws Exception {
        SaveData_sol_SaveData contract = getContract();
        return uploadData(userId, dappId, upData, date, contract);
    }

    public Map<String, String> uploadData(Long userId, Long dappId, String upData, String date, Web3j web3j, BigInteger price) throws Exception {
        SaveData_sol_SaveData contract = getContract(web3j, price);
        return uploadData(userId, dappId, upData, date, contract);
    }

    private Map<String, String> uploadData(Long userId, Long dappId, String upData, String date, SaveData_sol_SaveData contract) throws Exception {
        Map<String, String> map = new HashMap<>();

        if (contract != null) {
            String key = date + ":" + userId + ":" + dappId;
            TransactionReceipt transaction = contract.setstring(key, upData).send();
            if (transaction.getTransactionHash() != null) {
                map.put("hash", transaction.getTransactionHash());
                map.put("block_no", transaction.getBlockNumber().toString());
                List<SetStringEventResponse> ls = contract.getSetStringEvents(transaction);
                if (ls.size() == 1) {
                    for (SetStringEventResponse s : ls) {
                        System.out.println(s.key);
                        map.put("key", s.key);
                    }
                }
            }
        }
        return map;
    }

    public String download(String key) throws Exception {
        SaveData_sol_SaveData contract = getContract();
        return contract != null ? contract.getString(key).send() : null;
    }

    private SaveData_sol_SaveData deploy(Credentials credentials) throws Exception {
        SaveData_sol_SaveData sss = SaveData_sol_SaveData.deploy(web3j, credentials,
                EchainConstant.GAS_PRICE, EchainConstant.GAS_LIMIT).send();
        return sss;
    }

    private SaveData_sol_SaveData load(Credentials credentials) throws Exception {
        SaveData_sol_SaveData sss = SaveData_sol_SaveData.load(paramsProperties.getSaveDataAddress(),
                web3j, credentials, EchainConstant.GAS_PRICE, EchainConstant.GAS_LIMIT);
        return sss;
    }
}
