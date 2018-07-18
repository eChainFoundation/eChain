package com.echain.service;

import com.alibaba.fastjson.JSON;
import com.echain.common.exception.LogicException;
import com.echain.common.util.CommonUtil;
import com.echain.common.util.IdWorker;
import com.echain.common.util.RestHttpUtil;
import com.echain.conf.ParamsProperties;
import com.echain.dao.EcBlockNumberDao;
import com.echain.dao.EcWalletDao;
import com.echain.dao.EcWalletRecordDao;
import com.echain.dao.EcWithdrawRecordDao;
import com.echain.entity.EcBlockNumber;
import com.echain.entity.EcWallet;
import com.echain.entity.EcWalletRecord;
import com.echain.entity.EcWithdrawRecord;
import com.echain.enumaration.WalletType;
import com.echain.manager.RedisKeyMag;
import com.echain.solidity.ECPoints_sol_ECPoints;
import com.echain.vo.*;
import com.okcoin.rest.stock.IStockRestApi;
import com.okcoin.rest.stock.impl.StockRestApi;
import com.okcoin.rest.vo.RateVo;
import com.okcoin.rest.vo.TickerVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.NewAccountIdentifier;
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

    private static final Logger logger = LoggerFactory.getLogger(WalletService.class);

    @Autowired
    EcWalletDao walletDao;

    @Autowired
    EcWalletRecordDao walletRecordDao;

    @Autowired
    EcBlockNumberDao blockNumberDao;

    @Autowired
    EcWithdrawRecordDao withdrawRecordDao;

    @Autowired
    ParamsProperties paramsProperties;

    @Resource(name = "web3j")
    Web3j web3j;

    @Resource(name = "admin")
    Admin admin;

    @Autowired
    IStockRestApi stockGet;

    @Resource(name = "httpsRestTemplate")
    RestTemplate httpsRestTemplate;

    @Autowired
    PointService pointService;

    @Autowired
    RedisKeyMag redisKeyMag;

    @Bean
    public IStockRestApi getStockGet() {
        return new StockRestApi(paramsProperties.getOkexUrl());
    }

    public Credentials getCredentials() throws Exception {
        return getCredentials(paramsProperties.getWalletPassword(), paramsProperties.getWalletFile());
    }

    public Credentials getCredentials(String walletPassword, String walletFile) throws Exception {
        return WalletUtils.loadCredentials(walletPassword, walletFile);
    }

    public ECPoints_sol_ECPoints loadContract() throws Exception {
        ECPoints_sol_ECPoints ese = ECPoints_sol_ECPoints.load(paramsProperties.getEpointsAddress(), web3j, getCredentials(),
                ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);
        return ese;
    }

    public Float getTicker() throws Exception {
        Float result = redisKeyMag.getValue("walletTicker", Float.class);

        if (result == null) {
            TickerVo vo = ticker();
            result = Float.parseFloat(vo.getTicker().getLast());
        }
        return result;
    }

    public TickerVo ticker() throws Exception {
        String res = stockGet.ticker(paramsProperties.getTickerSymbol());
        TickerVo vo = JSON.parseObject(res, TickerVo.class);
        return vo;
    }

    public Float getRate() {
        Float result = redisKeyMag.getValue("walletRate", Float.class);

        if (result == null) {
            result = rate();
        }
        return result;
    }

    public Float rate() {
        String resp = RestHttpUtil.get(httpsRestTemplate, paramsProperties.getRateUrl(), String.class);
        RateVo vo = JSON.parseObject(resp, RateVo.class);
        return vo.getRate();
    }

    @Transactional
    public void transferETH(Long userId, int points) throws Exception {
        //判断比特币数量是否满足交易需求
        EcWallet wallet = selectByUserIdType(userId, WalletType.ETH.getIndex());
        BigDecimal balance = wallet.getValue();//获取账户ETH余额

        Float last = getTicker();
        Float rate = getRate();
//            String last = "6951.11";wallet.getValue()
//            Float rate = 6.403F;

        if (last == null || rate == null || points == 0) {
            throw new LogicException("系统出现异常，请稍后再试");
        }

        float price = last * rate * 100;

        //充值ETC所需ETH
        BigDecimal eth = BigDecimal.valueOf(points / price).setScale(18, BigDecimal.ROUND_HALF_UP);

        if (balance.compareTo(eth) == -1) {
            throw new LogicException("ETH余额不足");
        }

        //更新数据库中ec_wallet value
        int flag = 0;
        int flag2 = 0;
        while (flag == 0) {
            wallet = selectByUserIdType(userId, WalletType.ETH.getIndex());
            flag = updateValue(userId, WalletType.ETH.getIndex(), wallet.getValue().subtract(eth),
                    wallet.getValue());
        }

        while (flag2 == 0) {
            EcWallet wallet2 = selectByUserIdType(userId, WalletType.ECP.getIndex());
            flag2 = updateValue(userId, WalletType.ECP.getIndex(), wallet2.getValue()
                    .add(new BigDecimal(points)), wallet2.getValue());
        }

        /**
         * 保存充值记录
         */
        EcWalletRecord record = new EcWalletRecord(userId, wallet.getWallet(), wallet.getWallet(), WalletType.ETH.getIndex(),
                eth.multiply(new BigDecimal(-1)), "充值" + WalletType.ECP.getName() + ": -" + eth + WalletType.ETH.getName());

        EcWalletRecord record2 = new EcWalletRecord(userId, wallet.getWallet(), wallet.getWallet(), WalletType.ECP.getIndex(),
                new BigDecimal(points), "充值" + WalletType.ECP.getName() + ": +" + points + WalletType.ECP.getName());

        record.setId(IdWorker.generateId());
        saveRecord(record);

        record2.setId(IdWorker.generateId());
        saveRecord(record2);

        //积分划转
        pointService.changePoints2(userId, Integer.toUnsignedLong(points), "积分充值", '0');
    }

    public void transferToETH(String accountAddress, BigDecimal eth) throws Exception {
        transferToETH(accountAddress, eth, getCredentials());
    }

    @Async
    public void transferToETH(String accountAddress, BigDecimal eth, Credentials credentials) throws Exception {
        TransactionReceipt transactionReceipt = Transfer.sendFunds(web3j, credentials, accountAddress, eth, Convert.Unit.ETHER).send();
    }

    public BigInteger getETHBalance(String accountId) throws Exception {
        EthGetBalance ethGetBalance = web3j.ethGetBalance(accountId, DefaultBlockParameter.valueOf("latest")).send();
        return ethGetBalance != null ? ethGetBalance.getBalance() : null;
    }

    public String createAccount(String password, Admin admin) {
        try {
            NewAccountIdentifier newAccountIdentifier = admin.personalNewAccount(password).send();
            return newAccountIdentifier.getAccountId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取钱包地址的交易记录
     *
     * @param address
     * @param startBlock
     * @param endBlock
     * @param sort
     * @return
     */
    public List<TransferEvent> getTransferEvents(String address, BigInteger startBlock, BigInteger endBlock, String sort) {
        if (StringUtils.isEmpty(sort)) {
            sort = "asc";
        }

        String response = RestHttpUtil.get(httpsRestTemplate,
                paramsProperties.getEtherscanUrl() + "?module=account&action=txlist&address={1}&startblock={2}" +
                        "&endblock={3}&sort={4}&apikey=YourApiKeyToken",
                String.class, address, startBlock, endBlock, sort);

        GetTransferEventsResponseVo responseVo = JSON.parseObject(response, GetTransferEventsResponseVo.class);
        List<TransferEvent> result = responseVo.getResult();

        return CommonUtil.isEmpty(result) ? new ArrayList<>() : result;
    }


    /*************************************** 以下是合约的 *********************************/

    public BigInteger balanceOf(String owner) throws Exception {
        BigInteger balance = loadContract().balanceOf(owner).send();
        return balance;
    }

    public TransactionReceipt mintToken(String target, BigInteger mintedAmount) throws Exception {
        TransactionReceipt receipt = loadContract().mintToken(target, mintedAmount).send();
        return receipt;
    }

    private TransactionReceipt approve(String spender, BigInteger value) throws Exception {
        TransactionReceipt receipt = loadContract().approve(spender, value).send();
        return receipt;
    }

    public TransactionReceipt transfer(String to, BigInteger value) throws Exception {
        TransactionReceipt receipt = loadContract().transfer(to, value).send();
        return receipt;
    }

    public TransactionReceipt transferFrom(String from, String to, BigInteger value) throws Exception {
        approve(from, value);
        TransactionReceipt receipt = loadContract().transferFrom(from, to, value).send();
        return receipt;
    }

    public List<ECPoints_sol_ECPoints.TransferEventResponse> getTransferEvents(TransactionReceipt receipt) throws Exception {
        List<ECPoints_sol_ECPoints.TransferEventResponse> eventResponses = loadContract().getTransferEvents(receipt);
        return eventResponses;
    }

    public TransactionReceipt burn(BigInteger value) throws Exception {
        String from = loadContract().owner().send();
        approve(from, value);
        TransactionReceipt receipt = loadContract().burn(value).send();
        return receipt;
    }

    public TransactionReceipt burnFrom(String from, BigInteger value) throws Exception {
        approve(from, value);
        TransactionReceipt receipt = loadContract().burnFrom(from, value).send();
        return receipt;
    }

    public TransactionReceipt transferOwnership(String newOwner) throws Exception {
        TransactionReceipt receipt = loadContract().transferOwnership(newOwner).send();
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

    /**
     * 获取合约钱包地址的交易记录
     *
     * @param address
     * @param startBlock
     * @param endBlock
     * @param sort
     * @return
     */
    public List<TokenTransferEvent> getTokenTransferEvents(String address, BigInteger startBlock, BigInteger endBlock, String sort) {
        if (StringUtils.isEmpty(sort)) {
            sort = "asc";
        }

        String response = RestHttpUtil.get(httpsRestTemplate,
                paramsProperties.getEtherscanUrl() + "?module=account&action=tokentx&address={1}&startblock={2}" +
                        "&endblock={3}&sort={4}&apikey=YourApiKeyToken",
                String.class, address, startBlock, endBlock, sort);

        GetTokenTransferEventsResponseVo responseVo = JSON.parseObject(response, GetTokenTransferEventsResponseVo.class);
        List<TokenTransferEvent> result = responseVo.getResult();

        return CommonUtil.isEmpty(result) ? new ArrayList<>() : result;
    }

    /**
     * 代币换算为以太币
     *
     * @param coin
     * @throws Exception
     */
    public TransferVo transferToETH(String coin) throws Exception {
        Float last = getTicker();
        Float rate = getRate();

//        String last = "6951.11";
//        Float rate = 6.403F;

        if (last == null || rate == null || StringUtils.isEmpty(coin)) {
            return new TransferVo();
        }

        float price = last * rate * 100;

        return new TransferVo(BigDecimal.valueOf(1 / price).setScale(8, BigDecimal.ROUND_DOWN).toPlainString(),
                BigDecimal.valueOf(Float.parseFloat(coin) / price).setScale(8, BigDecimal.ROUND_DOWN).toPlainString(),
                Float.toString(Float.parseFloat(coin) / 100));
    }

    public String transferToRMB(String eth) throws Exception {
        Float last = getTicker();
        Float rate = getRate();
//        String last = "6951.11";
//        Float rate = 6.403F;

        if (last == null || rate == null || StringUtils.isEmpty(eth)) {
            return "0";
        }

        float price = last * rate;

        String rmb = BigDecimal.valueOf(Double.parseDouble(eth))
                .multiply(BigDecimal.valueOf(price))
                .setScale(2, BigDecimal.ROUND_DOWN).toPlainString();
        return rmb;
    }


    /************************************* 数据库操作 ***********************************/

    /**
     * 保存钱包
     *
     * @param wallet
     * @param password
     * @param type
     * @return
     */
    public Long save(String wallet, String password, Integer type) {
        return walletDao.save(wallet, password, type);
    }

    /**
     * 更新
     *
     * @param wallet
     * @return
     */
    public int updateByPrimaryKeySelective(EcWallet wallet) {
        return walletDao.updateByPrimaryKeySelective(wallet);
    }

    /**
     * 更新钱包地址二维码
     *
     * @param wallet
     * @param type
     * @param qimgFile
     * @return
     */
    public int updateImg(String wallet, Integer type, String qimgFile) {
        return walletDao.updateImg(wallet, type, qimgFile);
    }

    /**
     * 查询钱包文件为空的钱包
     *
     * @return
     */
    public List<EcWallet> selectByWithoutFile() {
        return walletDao.selectByWithoutFile();
    }

    /**
     * 查询未绑定用户的钱包
     *
     * @return
     */
    public EcWallet selectByTypeWithoutUser(Integer type) {
        return walletDao.selectByTypeWithoutUser(type);
    }

    /**
     * 更新userId
     */
    public int updateUserId(EcWallet wallet) {
        return walletDao.updateUserId(wallet);
    }

    /**
     * 更新value
     */
    public int updateValue(Long userId, Integer type, BigDecimal value, BigDecimal oldValue) {
        return walletDao.updateValue(userId, type, value, oldValue);
    }

    /**
     * 根据用户ID查询钱包信息
     */
    public List<EcWallet> selectByUserId(Long userId) {
        List<EcWallet> list = walletDao.selectByUserId(userId);
        return CommonUtil.isEmpty(list) ? new ArrayList<>() : list;
    }

    /**
     * 根据用户ID查询钱包信息
     */
    public EcWallet selectByUserIdType(Long userId, Integer type) {
        return walletDao.selectByUserIdType(userId, type);
    }

    /**
     * 查询已绑定用户的钱包
     */
    public List<EcWallet> getByUsed() {
        List<EcWallet> list = walletDao.getByUsed();
        return CommonUtil.isEmpty(list) ? new ArrayList<>() : list;
    }


    /******************************************** wallet record *******************************************/

    /**
     * 保存钱包记录
     */
    public int saveRecord(EcWalletRecord record) {
        return walletRecordDao.save(record);
    }

    /**
     * 根据钱包类型获取记录
     */
    public List<EcWalletRecord> getRecordByType(Long userId, Integer type) {
        List<EcWalletRecord> list = walletRecordDao.getRecordByType(userId, type);
        return CommonUtil.isEmpty(list) ? new ArrayList<>() : list;
    }

    public EcWalletRecord getRecordById(Long id) {
        return walletRecordDao.getById(id);
    }

    /******************************************** block number *******************************************/

    public int saveBlockNumber(EcBlockNumber blockNumber) {
        return blockNumberDao.save(blockNumber);
    }

    public Long getNumberByWallet(String wallet, Integer type) {
        return blockNumberDao.getNumberByWalletAndType(wallet, type);
    }

    public int updateNumber(String wallet, Long number, Integer type) {
        return blockNumberDao.updateNumberByType(wallet, number, type);
    }

    /******************************************** withdraw record *******************************************/
    public int saveWithDrawRecord(EcWithdrawRecord record) {
        return withdrawRecordDao.save(record);
    }

    public List<EcWithdrawRecord> getWithdrawRecordListByUser(Long userId, Integer type) {
        List<EcWithdrawRecord> list = withdrawRecordDao.getListByUser(userId, type);
        return CommonUtil.isEmpty(list) ? new ArrayList<>() : list;
    }
}
