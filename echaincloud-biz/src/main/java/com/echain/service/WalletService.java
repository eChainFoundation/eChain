package com.echain.service;

import com.alibaba.fastjson.JSON;
import com.echain.common.util.CommonUtil;
import com.echain.common.util.RestHttpUtil;
import com.echain.conf.ParamsProperties;
import com.echain.solidity.ECPoints_sol_ECPoints;
import com.echain.vo.GetTokenTransferEventsResponseVo;
import com.echain.vo.TokenTransferEvent;
import com.okcoin.rest.stock.IStockRestApi;
import com.okcoin.rest.stock.impl.StockRestApi;
import com.okcoin.rest.vo.RateVo;
import com.okcoin.rest.vo.TickerVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class WalletService {

    @Autowired
    ParamsProperties paramsProperties;

    @Resource(name = "web3j")
    Web3j web3j;

    @Autowired
    IStockRestApi stockGet;

    @Resource(name = "httpsRestTemplate")
    RestTemplate httpsRestTemplate;

    @Autowired
    Credentials credentials;

    @Autowired
    ECPoints_sol_ECPoints contract;


    @Bean
    public IStockRestApi getStockGet() {
        return new StockRestApi(paramsProperties.getOkexUrl());
    }

    @Bean
    public Credentials getCredentials() throws Exception {
        return WalletUtils.loadCredentials(paramsProperties.getWalletPassword(), paramsProperties.getWalletFile());
    }

    @Bean
    public ECPoints_sol_ECPoints loadContract() {
        ECPoints_sol_ECPoints ese = ECPoints_sol_ECPoints.load(paramsProperties.getEpointsAddress(), web3j, credentials,
                ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);
        return ese;
    }


    public TickerVo ticker() throws Exception {
        String res = stockGet.ticker(paramsProperties.getTickerSymbol());
        TickerVo vo = JSON.parseObject(res, TickerVo.class);
        return vo;
    }

    public Float getRate() {
        String resp = RestHttpUtil.get(httpsRestTemplate, paramsProperties.getRateUrl(), String.class);
        RateVo vo = JSON.parseObject(resp, RateVo.class);
        return vo.getRate();
    }

    public void transferToETH(String accountAddress, float coin) throws Exception {
        String last = ticker().getTicker().getLast();
        Float rate = getRate();
//            String last = "6951.11";
//            Float rate = 6.403F;

        if (last == null || rate == null) {
            return;
        }

        float price = Float.parseFloat(last) * rate * 100;

        BigDecimal wei = BigDecimal.valueOf(coin / price)
                .multiply(BigDecimal.valueOf(Math.pow(10, 18)))
                .setScale(0, BigDecimal.ROUND_DOWN);
        System.out.println(wei);

        TransactionReceipt transactionReceipt = Transfer
                .sendFunds(web3j, credentials, accountAddress, wei, Convert.Unit.WEI).send();
        System.out.println(JSON.toJSONString(transactionReceipt));
    }

    public BigInteger getETHBalance(String accountId) throws Exception {
        EthGetBalance ethGetBalance = web3j.ethGetBalance(accountId, DefaultBlockParameter.valueOf("latest")).send();
        if (ethGetBalance != null) {
            return ethGetBalance.getBalance();
        }

        return null;
    }


    /*************************************** 以下是合约的 *********************************/

    public BigInteger balanceOf(String owner) throws Exception {
        BigInteger balance = contract.balanceOf(owner).send();
        return balance;
    }

    public TransactionReceipt mintToken(String target, BigInteger mintedAmount) throws Exception {
        TransactionReceipt receipt = contract.mintToken(target, mintedAmount).send();
        return receipt;
    }

    private TransactionReceipt approve(String spender, BigInteger value) throws Exception {
        TransactionReceipt receipt = contract.approve(spender, value).send();
        return receipt;
    }

    public TransactionReceipt transfer(String to, BigInteger value) throws Exception {
        TransactionReceipt receipt = contract.transfer(to, value).send();
        return receipt;
    }

    public TransactionReceipt transferFrom(String from, String to, BigInteger value) throws Exception {
        approve(from, value);
        TransactionReceipt receipt = contract.transferFrom(from, to, value).send();
        return receipt;
    }

    public List<ECPoints_sol_ECPoints.TransferEventResponse> getTransferEvents(TransactionReceipt receipt) {
        List<ECPoints_sol_ECPoints.TransferEventResponse> eventResponses = contract.getTransferEvents(receipt);
        return eventResponses;
    }

    public TransactionReceipt burn(BigInteger value) throws Exception {
        String from = contract.owner().send();
        approve(from, value);
        TransactionReceipt receipt = contract.burn(value).send();
        return receipt;
    }

    public TransactionReceipt burnFrom(String from, BigInteger value) throws Exception {
        approve(from, value);
        TransactionReceipt receipt = contract.burnFrom(from, value).send();
        return receipt;
    }

    public TransactionReceipt transferOwnership(String newOwner) throws Exception {
        TransactionReceipt receipt = contract.transferOwnership(newOwner).send();
        return receipt;
    }

    /**
     * 获取合约钱包地址的交易记录
     *
     * @param address
     * @param page
     * @param size
     * @param sort
     * @return
     */
    public List<TokenTransferEvent> getTokenTransferEvents(String address, int page, int size, String sort) {
        String response = RestHttpUtil.get(httpsRestTemplate,
                paramsProperties.getEtherscanUrl() + "?module=account&action=tokentx" +
                        "&contractaddress={1}&address={2}&page={3}&offset={4}&sort={5}&apikey=YourApiKeyToken",
                String.class, paramsProperties.getEpointsAddress(), address, page, size, sort);

        GetTokenTransferEventsResponseVo responseVo = JSON.parseObject(response, GetTokenTransferEventsResponseVo.class);
        List<TokenTransferEvent> result = responseVo.getResult();

        return CommonUtil.isEmpty(result) ? new ArrayList<>() : result;
    }

    /**
     * 获取合约的交易记录
     *
     * @param page
     * @param size
     * @param sort
     * @return
     */
    public List<TokenTransferEvent> getTokenTransferEvents(int page, int size, String sort) {
        String response = RestHttpUtil.get(httpsRestTemplate,
                paramsProperties.getEtherscanUrl() + "?module=account&action=tokentx" +
                        "&contractaddress={1}&page={2}&offset={3}&sort={4}&apikey=YourApiKeyToken",
                String.class, paramsProperties.getEpointsAddress(), page, size, sort);

        GetTokenTransferEventsResponseVo responseVo = JSON.parseObject(response, GetTokenTransferEventsResponseVo.class);
        List<TokenTransferEvent> result = responseVo.getResult();

        return CommonUtil.isEmpty(result) ? new ArrayList<>() : result;
    }
}
