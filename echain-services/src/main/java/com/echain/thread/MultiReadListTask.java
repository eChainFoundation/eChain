package com.echain.thread;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Repository;

import com.echain.util.SpringBeanUtil;
//import com.guanyong.fbimonitor.entity.FbmDataExport;
//import com.guanyong.fbimonitor.entity.FbmWorks;

/**
 * 简介：
 * 
 * @author jemond
 * @date 2017年10月20日 上午11:25:51
 */
@Repository("multiReadListThreadTest")
@Scope("prototype")
public class MultiReadListTask {

    @Resource(name = "taskExecutor")
    private TaskExecutor taskExecutor;

//    public void doReadList(List<FbmDataExport> dataExports, List<FbmWorks> worksBySQL, Long id)
//            throws InterruptedException, ExecutionException {
//
//        CopyOnWriteArrayList<FbmDataExport> list = new CopyOnWriteArrayList<FbmDataExport>(dataExports);
//        int size = list.size();
//        int sunSum = 10;
//        int listStart, listEnd;
//        if(sunSum > size){
//            sunSum = size;
//        }
//        for (int i = 0; i < sunSum; i++){
//            listStart = size / sunSum * i;
//            listEnd = size / sunSum * (i + 1);
//            if(i == sunSum - 1){
//                listEnd = size;
//            }
//            List<FbmDataExport> sunList = list.subList(listStart, listEnd);
//            ReadListThread thread = (ReadListThread) SpringBeanUtil.getBean("readListThread");
//            thread.setList(sunList);
//            thread.setWorks(worksBySQL);
//            thread.setId(id);
//            taskExecutor.execute(thread);
//        }
//    }

}
