package com.echain.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

/**
 * 简介：定时扫描 fbm_action_job_data_export 表，检查status 为2 （导出中）的任务 。 若create_time 大于当前时间向前移动三个小时则是正常 ， 若 create_time 小于当前时间相前移动三个小时
 * ，则这条任务执行时间已经超过三个小时，暂定为不正常情况，则修改其状态为0.
 * 
 * @author jemond
 * @date 2017年8月10日 上午9:46:10
 */
@Repository("checkExcelExportStatusTask")
@Scope("prototype")
public class CheckExcelExportStatusTask {

    /**
     * 记录日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckExcelExportStatusTask.class);

//    @Autowired
//    private FbmActionJobDataExportDao fbmActionJobDataExportDao;
//
//    public void process() throws Exception {
//        if(LOGGER.isInfoEnabled()){
//            LOGGER.info("定时扫描导出任务状态为2------");
//        }
//        List<FbmActionJobDataExport> list = fbmActionJobDataExportDao.getFbmActionJobByStatus("2");
//        if(list == null || list.size() == 0) return;
//        for (FbmActionJobDataExport item : list){
//            Date createTime = item.getCreateTime();
//            String status = item.getStatus();
//            Date hour = DateUtil.getAddDayOfHour(new Date(), -3);
//            if(createTime != null && DateUtil.compareDate(createTime, hour)) continue;
//            if(!StringUtils.equals("2", status)) continue;
//            // updateFbmDataExportStatus(item);
//        }
//    }
//
//    /***
//     * 简介：0:默认;1:导出成功;2:导出进行中;3:没有数据;5:导出失败
//     * 
//     * @author jemond
//     * @date 2017年8月10日 上午10:10:59
//     * @param item
//     */
//    public void updateFbmDataExportStatus(FbmActionJobDataExport item) {
//        if(item == null || item.getId() == null) return;
//        int i = fbmActionJobDataExportDao.updateDataExportStatusById(item.getId(), "0");
//        System.out.println("定时扫描导出任务状态为2，更新导出任务状态为0 ,更新结果为：" + i);
//        if(LOGGER.isInfoEnabled()){
//            LOGGER.info("定时扫描导出任务状态为2，更新导出任务状态为0 ，更新结果为{}", i);
//        }
//    }

}
