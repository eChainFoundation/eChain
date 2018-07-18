package com.echain.entity;

import com.echain.vo.TokenTransferEvent;
import com.echain.vo.TransferEvent;

import javax.persistence.Id;
import java.math.BigDecimal;

public class EcTransactionRecord {
    /**
     * ID
     */
    @Id
    private Long id;

    /**
     * 比特币地址
     */
    private String address;

    /**
     * 比特币类型
     */
    private Integer type;

    private Long blockNumber;

    private String blockHash;

    private String hash;

    private Long nonce;

    private Long transactionIndex;

    private String from;

    private String to;

    private BigDecimal value;

    private String contractAddress;

    private Long gas;

    private Long gasPrice;

    private Long gasUsed;

    private String isError;

    private String txreceiptStatus;

    private Long cumulativeGasUsed;

    private Long confirmations;

    /**
     * 时间戳
     */
    private Long timeStamp;

    private String input;

    private Integer flag;

    /**
     * 获取ID
     *
     * @return id - ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置ID
     *
     * @param id ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取比特币地址
     *
     * @return address - 比特币地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置比特币地址
     *
     * @param address 比特币地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取比特币类型
     *
     * @return type - 比特币类型
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置比特币类型
     *
     * @param type 比特币类型
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * @return block_number
     */
    public Long getBlockNumber() {
        return blockNumber;
    }

    /**
     * @param blockNumber
     */
    public void setBlockNumber(Long blockNumber) {
        this.blockNumber = blockNumber;
    }

    /**
     * @return block_hash
     */
    public String getBlockHash() {
        return blockHash;
    }

    /**
     * @param blockHash
     */
    public void setBlockHash(String blockHash) {
        this.blockHash = blockHash;
    }

    /**
     * @return hash
     */
    public String getHash() {
        return hash;
    }

    /**
     * @param hash
     */
    public void setHash(String hash) {
        this.hash = hash;
    }

    /**
     * @return nonce
     */
    public Long getNonce() {
        return nonce;
    }

    /**
     * @param nonce
     */
    public void setNonce(Long nonce) {
        this.nonce = nonce;
    }

    /**
     * @return transaction_index
     */
    public Long getTransactionIndex() {
        return transactionIndex;
    }

    /**
     * @param transactionIndex
     */
    public void setTransactionIndex(Long transactionIndex) {
        this.transactionIndex = transactionIndex;
    }

    /**
     * @return from
     */
    public String getFrom() {
        return from;
    }

    /**
     * @param from
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * @return to
     */
    public String getTo() {
        return to;
    }

    /**
     * @param to
     */
    public void setTo(String to) {
        this.to = to;
    }

    /**
     * @return value
     */
    public BigDecimal getValue() {
        return value;
    }

    /**
     * @param value
     */
    public void setValue(BigDecimal value) {
        this.value = value;
    }

    /**
     * @return contract_address
     */
    public String getContractAddress() {
        return contractAddress;
    }

    /**
     * @param contractAddress
     */
    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    /**
     * @return gas
     */
    public Long getGas() {
        return gas;
    }

    /**
     * @param gas
     */
    public void setGas(Long gas) {
        this.gas = gas;
    }

    /**
     * @return gas_price
     */
    public Long getGasPrice() {
        return gasPrice;
    }

    /**
     * @param gasPrice
     */
    public void setGasPrice(Long gasPrice) {
        this.gasPrice = gasPrice;
    }

    /**
     * @return gas_used
     */
    public Long getGasUsed() {
        return gasUsed;
    }

    /**
     * @param gasUsed
     */
    public void setGasUsed(Long gasUsed) {
        this.gasUsed = gasUsed;
    }

    /**
     * @return is_error
     */
    public String getIsError() {
        return isError;
    }

    /**
     * @param isError
     */
    public void setIsError(String isError) {
        this.isError = isError;
    }

    /**
     * @return txreceipt_status
     */
    public String getTxreceiptStatus() {
        return txreceiptStatus;
    }

    /**
     * @param txreceiptStatus
     */
    public void setTxreceiptStatus(String txreceiptStatus) {
        this.txreceiptStatus = txreceiptStatus;
    }

    /**
     * @return cumulative_gas_used
     */
    public Long getCumulativeGasUsed() {
        return cumulativeGasUsed;
    }

    /**
     * @param cumulativeGasUsed
     */
    public void setCumulativeGasUsed(Long cumulativeGasUsed) {
        this.cumulativeGasUsed = cumulativeGasUsed;
    }

    /**
     * @return confirmations
     */
    public Long getConfirmations() {
        return confirmations;
    }

    /**
     * @param confirmations
     */
    public void setConfirmations(Long confirmations) {
        this.confirmations = confirmations;
    }

    /**
     * 获取时间戳
     *
     * @return time_stamp - 时间戳
     */
    public Long getTimeStamp() {
        return timeStamp;
    }

    /**
     * 设置时间戳
     *
     * @param timeStamp 时间戳
     */
    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    /**
     * @return input
     */
    public String getInput() {
        return input;
    }

    /**
     * @param input
     */
    public void setInput(String input) {
        this.input = input;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public EcTransactionRecord() {
    }

    public EcTransactionRecord(TransferEvent event, String address, int type) {
        this.address = address;
        this.type = type;
        this.blockNumber = Long.parseLong(event.getBlockNumber());
        this.blockHash = event.getBlockHash();
        this.hash = event.getHash();
        this.nonce = Long.parseLong(event.getNonce());
        this.transactionIndex = Long.parseLong(event.getTransactionIndex());
        this.from = event.getFrom();
        this.to = event.getTo();
        this.value = BigDecimal.valueOf(Double.parseDouble(event.getValue()));
        this.contractAddress = event.getContractAddress();
        this.gas = Long.parseLong(event.getGas());
        this.gasPrice = Long.parseLong(event.getGasPrice());
        this.gasUsed = Long.parseLong(event.getGasUsed());
        this.isError = event.getIsError();
        this.txreceiptStatus = event.getTxreceipt_status();
        this.cumulativeGasUsed = Long.parseLong(event.getCumulativeGasUsed());
        this.confirmations = Long.parseLong(event.getConfirmations());
        this.timeStamp = Long.parseLong(event.getTimeStamp());
        this.input = event.getInput();
    }

    public EcTransactionRecord(TokenTransferEvent event, String address, int type) {
        this.address = address;
        this.type = type;
        this.blockNumber = Long.parseLong(event.getBlockNumber());
        this.blockHash = event.getBlockHash();
        this.hash = event.getHash();
        this.nonce = Long.parseLong(event.getNonce());
        this.transactionIndex = Long.parseLong(event.getTransactionIndex());
        this.from = event.getFrom();
        this.to = event.getTo();
        this.value = BigDecimal.valueOf(Double.parseDouble(event.getValue()));
        this.contractAddress = event.getContractAddress();
        this.gas = Long.parseLong(event.getGas());
        this.gasPrice = Long.parseLong(event.getGasPrice());
        this.gasUsed = Long.parseLong(event.getGasUsed());
        this.cumulativeGasUsed = Long.parseLong(event.getCumulativeGasUsed());
        this.confirmations = Long.parseLong(event.getConfirmations());
        this.timeStamp = Long.parseLong(event.getTimeStamp());
        this.input = event.getInput();
    }
}