package com.echain.vo.rest.response;

import com.echain.entity.EcDapp;
import com.echain.entity.EcPoints;

import java.util.List;

public class AppPointsDetailResponseVo {
    private EcDapp ecDapp;

    private List<EcPoints> list;

    private Long points;

    public EcDapp getEcDapp() {
        return ecDapp;
    }

    public void setEcDapp(EcDapp ecDapp) {
        this.ecDapp = ecDapp;
    }

    public List<EcPoints> getList() {
        return list;
    }

    public void setList(List<EcPoints> list) {
        this.list = list;
    }

    public Long getPoints() {
        return points;
    }

    public void setPoints(Long points) {
        this.points = points;
    }

    public AppPointsDetailResponseVo() {
    }

    public AppPointsDetailResponseVo(EcDapp ecDapp, List<EcPoints> list, Long points) {
        this.ecDapp = ecDapp;
        this.list = list;
        this.points = points;
    }
}
