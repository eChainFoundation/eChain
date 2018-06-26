package com.echain.vo.rest.response;

import com.echain.vo.EcTransactionVo;

import java.util.List;

public class GetTransactionListResponseVo {
    private List<EcTransactionVo> voList;

    private String transactionKey;

    private Long userId;

    private Long dappId;

    public List<EcTransactionVo> getVoList() {
        return voList;
    }

    public void setVoList(List<EcTransactionVo> voList) {
        this.voList = voList;
    }

    public String getTransactionKey() {
        return transactionKey;
    }

    public void setTransactionKey(String transactionKey) {
        this.transactionKey = transactionKey;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getDappId() {
        return dappId;
    }

    public void setDappId(Long dappId) {
        this.dappId = dappId;
    }

    public GetTransactionListResponseVo() {
    }

    public GetTransactionListResponseVo(List<EcTransactionVo> voList, String transactionKey, Long userId, Long dappId) {
        this.voList = voList;
        this.transactionKey = transactionKey;
        this.userId = userId;
        this.dappId = dappId;
    }
}
