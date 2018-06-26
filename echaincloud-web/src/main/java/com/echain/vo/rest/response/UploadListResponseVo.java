package com.echain.vo.rest.response;

import com.echain.entity.EcLogisticsCompany;
import com.echain.entity.EcTransaction;

import java.util.List;

public class UploadListResponseVo {
    private List<EcTransaction> succesList;
    private List<EcTransaction> faildedList;
    private List<EcTransaction> checkingList;
    private List<EcLogisticsCompany> lcList;

    public List<EcTransaction> getSuccesList() {
        return succesList;
    }

    public void setSuccesList(List<EcTransaction> succesList) {
        this.succesList = succesList;
    }

    public List<EcTransaction> getFaildedList() {
        return faildedList;
    }

    public void setFaildedList(List<EcTransaction> faildedList) {
        this.faildedList = faildedList;
    }

    public List<EcTransaction> getCheckingList() {
        return checkingList;
    }

    public void setCheckingList(List<EcTransaction> checkingList) {
        this.checkingList = checkingList;
    }

    public List<EcLogisticsCompany> getLcList() {
        return lcList;
    }

    public void setLcList(List<EcLogisticsCompany> lcList) {
        this.lcList = lcList;
    }

    public UploadListResponseVo() {
    }

    public UploadListResponseVo(List<EcTransaction> succesList, List<EcTransaction> faildedList, List<EcTransaction> checkingList, List<EcLogisticsCompany> lcList) {
        this.succesList = succesList;
        this.faildedList = faildedList;
        this.checkingList = checkingList;
        this.lcList = lcList;
    }
}
