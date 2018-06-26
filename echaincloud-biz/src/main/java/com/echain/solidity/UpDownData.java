package com.echain.solidity;

import com.echain.common.util.DateUtil;
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
import java.util.Date;
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
        Credentials credentials = WalletUtils.loadCredentials(paramsProperties.getWalletPassword(), paramsProperties.getWalletFile());
        SaveData_sol_SaveData contract = load(credentials);//deploy(credentials);

        return contract;
    }

    public Map<String, String> uploadData(Long userId, Long dappId, String upData) throws Exception {

        Map<String, String> map = new HashMap<String, String>();
        SaveData_sol_SaveData contract = getContract();
        if (contract != null) {
            String date = DateUtil.convert2String(new Date(), "yyyyMMdd");
            String key = date + ":" + userId + ":" + dappId;
            TransactionReceipt transaction = contract.setstring(key, upData).send();
//			TransactionReceipt transaction = contract.setstring(date+":"+userId+":"+dappId, upData).send();
            if (transaction.getTransactionHash() != null) {
                map.put("hash", transaction.getTransactionHash());
                map.put("block_no", transaction.getBlockNumber().toString());
                List<SetStringEventResponse> ls = contract.getSetStringEvents(transaction);
                if (ls.size() == 1) {
                    for (SetStringEventResponse s : ls) {
                        System.out.println(s.key);
                        map.put("key", s.key.toString());
                    }
                }
            }
        }

        return map;
    }

    public String download(String key) throws Exception {

        SaveData_sol_SaveData contract = getContract();
        if (contract != null) {
            return contract.getString(key).send();
        }

        return null;
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
