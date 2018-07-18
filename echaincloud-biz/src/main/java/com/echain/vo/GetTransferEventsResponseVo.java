package com.echain.vo;

import java.util.List;

public class GetTransferEventsResponseVo {
    private String status;

    private String message;

    private List<TransferEvent> result;


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

    public List<TransferEvent> getResult() {
        return result;
    }

    public void setResult(List<TransferEvent> result) {
        this.result = result;
    }
}
