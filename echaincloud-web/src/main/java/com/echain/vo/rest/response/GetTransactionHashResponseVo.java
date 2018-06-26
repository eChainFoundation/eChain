package com.echain.vo.rest.response;

import io.swagger.annotations.ApiModelProperty;

public class GetTransactionHashResponseVo {
    @ApiModelProperty(value = "Hash值")
    private String transactionHash;

    @ApiModelProperty(value = "数据存储在以太坊上的块的编号")
    private String blockNo;

    public String getTransactionHash() {
        return transactionHash;
    }

    public void setTransactionHash(String transactionHash) {
        this.transactionHash = transactionHash;
    }

    public String getBlockNo() {
        return blockNo;
    }

    public void setBlockNo(String blockNo) {
        this.blockNo = blockNo;
    }

    public GetTransactionHashResponseVo() {
    }

    public GetTransactionHashResponseVo(String transactionHash, String blockNo) {
        this.transactionHash = transactionHash;
        this.blockNo = blockNo;
    }
}
