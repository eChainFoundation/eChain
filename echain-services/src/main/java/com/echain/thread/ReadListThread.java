package com.echain.thread;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
//import com.guanyong.fbimonitor.entity.FbmDataExport;
//import com.guanyong.fbimonitor.entity.FbmWorks;

/**
 * 简介：
 * 
 * @author jemond
 * @date 2017年10月20日 下午2:03:01
 */
@Service("readListThread")
@Scope("prototype")
public class ReadListThread implements Runnable {

//    private List<FbmDataExport> list;
//
//    private List<FbmWorks> works;
//
//    private Long id;
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public List<FbmWorks> getWorks() {
//        return works;
//    }
//
//    public void setWorks(List<FbmWorks> works) {
//        this.works = works;
//    }
//
//    public List<FbmDataExport> getList() {
//        return list;
//    }
//
//    public void setList(List<FbmDataExport> list) {
//        this.list = list;
//    }

    /* 
     * 
     */
    @Override
    public void run() {
//        ExportExcelUtil.setFbiScoreByWorks(list, works, id);
    }

}
