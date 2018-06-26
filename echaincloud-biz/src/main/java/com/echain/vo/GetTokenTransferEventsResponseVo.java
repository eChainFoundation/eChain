package com.echain.vo;

import java.util.List;

public class GetTokenTransferEventsResponseVo {
    private String status;

    private String message;

    private List<TokenTransferEvent> result;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<TokenTransferEvent> getResult() {
        return result;
    }

    public void setResult(List<TokenTransferEvent> result) {
        this.result = result;
    }
}
