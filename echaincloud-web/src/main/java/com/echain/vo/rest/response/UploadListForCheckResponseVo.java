package com.echain.vo.rest.response;

import com.echain.entity.EcTransaction;
import com.echain.entity.EcUser;

import java.util.List;
import java.util.Map;

public class UploadListForCheckResponseVo {

    private List<EcTransaction> list;

    private Integer index;

    private Map<Long, EcUser> users;

    public List<EcTransaction> getList() {
        return list;
    }

    public void setList(List<EcTransaction> list) {
        this.list = list;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Map<Long, EcUser> getUsers() {
        return users;
    }

    public void setUsers(Map<Long, EcUser> users) {
        this.users = users;
    }

    public UploadListForCheckResponseVo() {
    }

    public UploadListForCheckResponseVo(List<EcTransaction> list, Integer index, Map<Long, EcUser> users) {
        this.list = list;
        this.index = index;
        this.users = users;
    }
}
