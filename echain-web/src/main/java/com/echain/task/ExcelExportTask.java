package com.echain.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Repository;


/***
 * 简介：扫面报告导出表fbm_action_job_data_export中status为0的导出任务，然后执行导出EXCEL。
 * <p>
 * siteBaseMap以爬虫站点表fbm_site_crawler的ID作为key, 设置站点名称等其他信息。作品MAP以作品ID为key，设置作品相关的信息。
 * <p>
 * 查询出fbm_action_job_data_export表中所有 status为0的记录 LIST
 * <p>
 * 先循环LIST，把每条记录的status修改为2 （导出中）
 * <p>
 * 循环LIST，把导出任务添加到线程中去执行。
 * 
 * @author jemond
 */
@Repository("excelExportTask")
@Scope("prototype")
public class ExcelExportTask {

    /***
     * 记录日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelExportTask.class);

//    @Autowired
//    private FBMDataExportDao fbmDataExportDao;
//
//    @Resource
//    private FbmSiteDao fbmSiteDao;
//
//    @Resource
//    private FbmWorksDao fbmWorksDao;
//
//    @Resource(name = "taskExecutor")
//    private TaskExecutor taskExecutor;
//
//    @Resource
//    private FbmSiteBaseDao fbmSiteBaseDao;
//
//    public void process() throws Exception {
//        // LOGGER.info("开始扫描EXCEL任务导出表。扫描----fbm_action_job_data_export中status为0的任务！");
//        LOGGER.info("-- ( ^_^ )-- ( ^_^ )--定时任务启动，系统运行正常-- ( ^_^ )-- ( ^_^ )");
//        Map<Long, FbmSiteBase> siteBaseMap = listAllSiteBase();
//        Map<Long, FbmWorks> workMap = listAllWork();
//        if (siteBaseMap == null || siteBaseMap.size() == 0 || workMap == null || workMap.size() == 0) {
//            LOGGER.info("扫描任务导出表，查询站点MAP或作品MAP为空！");
//            return;
//        }
//
//        DataSourceContextHolder.clearDbType();
//        List<FbmDataExport> list = fbmDataExportDao.findAllByStatus("0");
//        if (list == null || list.size() == 0) {
//            // LOGGER.info("扫描=fbm_action_job_data_export中status为0的任务数量为0===========没有待导出的EXCEL。。");
//            return;
//        }
//
//        LOGGER.info("扫描到----fbm_action_job_data_export----中待导出报告EXCEL的数据量为：===== {}", list.size());
//
//        for (FbmDataExport item : list) {
//            updateIng(item);
//        }
//
//        for (FbmDataExport d : list) {
//            ExcelExportThread thread = (ExcelExportThread)SpringBeanUtil.getBean("excelExportThread");
//            d.setSiteBaseMap(siteBaseMap);
//            d.setWorkMap(workMap);
//            d.setFlag(true);
//            thread.setDataExport(d);
//            taskExecutor.execute(thread);
//        }
//    }
//
//    /**
//     * 简介：0:默认;1:导出成功;2:导出进行中;3:没有数据;5:导出失败
//     * 
//     * @param fde
//     */
//    public void updateIng(FbmDataExport fde) {
//        DataSourceContextHolder.clearDbType();
//        fbmDataExportDao.updateFbmActionJobStatus(fde);
//    }
//
//    /***
//     * 简介：0:默认;1:导出成功;2:导出进行中;3:没有数据;5:导出失败,批量更新
//     * 
//     * @author jemond
//     * @date 2017年8月15日 下午2:17:43
//     * @param list ID 集合
//     * @param status 状态
//     */
//    public void updateFbmDataExportBatch(List<Long> list) {
//        DataSourceContextHolder.clearDbType();
//        fbmDataExportDao.updateFbmActionJobStatusIngByIdS(list, "2");
//    }
//
//    /***
//     * 简介：通用导出站点MAP 以爬虫站点ID为key，value 为FbmSiteBase 设置站点名称通过站点ID，取MAP的value--FbmSiteBase。然后获取站点相关属性
//     * 
//     * @author jemond
//     * @date 2017年9月22日 上午11:14:39
//     * @return
//     */
//    public Map<Long, FbmSiteBase> listAllSiteBase() {
//        DataSourceContextHolder.clearDbType();
//        Map<Long, FbmSiteBase> base = fbmSiteBaseDao.listSiteBase();
//        return base;
//    }
//
//    /***
//     * 简介：查询所有的作品 ，可以存放到redis中
//     * 
//     * @return
//     */
//    public Map<Long, FbmWorks> listAllWork() {
//        Map<Long, FbmWorks> map = new HashMap<Long, FbmWorks>();
//        DataSourceContextHolder.clearDbType();
//        List<FbmWorks> workList = fbmWorksDao.listWorksAll();
//        if (workList != null && workList.size() > 0) {
//            for (FbmWorks work : workList) {
//                if (null == work || work.getId() == null) {
//                    continue;
//                }
//                if (!map.containsKey(work.getId())) {
//                    map.put(work.getId(), work);
//                }
//            }
//        }
//        return map;
//    }
//
//    /***
//     * 简介：测试运行导出EXCEL任务或者手动导出EXCEL任务，id为fbm_action_job_data_export表的主键ID
//     * 
//     * @author jemond
//     * @date 2017年9月22日 上午11:24:04
//     * @param id
//     */
//    public String processById(Long id, String isFbiScore) {
//
//        LOGGER.info("手动执行扫描EXCEL任务导出表。扫描----fbm_action_job_data_export中status为0的任务！");
//        Map<Long, FbmSiteBase> siteBaseMap = listAllSiteBase();
//        Map<Long, FbmWorks> workMap = listAllWork();
//        if (siteBaseMap == null || siteBaseMap.size() == 0 || workMap == null || workMap.size() == 0) {
//            LOGGER.info("扫描任务导出表，查询站点MAP或作品MAP为空！");
//            return "SUCCESS";
//        }
//
//        String sql = StringUtils.replace(OPERATE_SQL, "{id}", id + "");
//        LOGGER.info("当前查询fbm_action_job_data_export表中的SQL为：{}", sql);
//
//        DataSourceContextHolder.clearDbType();
//        List<FbmDataExport> list = fbmDataExportDao.getDataExportBySql(sql);
//        if (list == null || list.size() == 0) {
//            LOGGER.info("扫描=fbm_action_job_data_export中status为0的任务数量为0===========没有待导出的EXCEL。。");
//            return "没有此" + id + "的导出任务，或当前导出任务正在导出中，请耐心等待邮件通知！";
//        }
//
//        LOGGER.info("扫描到----fbm_action_job_data_export----中status为0的数据量为：===== {}", list.size());
//
//        for (FbmDataExport item : list) {
//            updateIng(item);
//        }
//
//        for (FbmDataExport d : list) {
//            if (StringUtils.isNotBlank(isFbiScore) && StringUtils.equals("1", isFbiScore)) {
//                d.setIsFbiScore(isFbiScore);
//            } else {
//                d.setIsFbiScore(null);
//            }
//            ExcelExportThread thread = (ExcelExportThread)SpringBeanUtil.getBean("excelExportThread");
//            d.setSiteBaseMap(siteBaseMap);
//            d.setWorkMap(workMap);
//            d.setFlag(true);
//            thread.setDataExport(d);
//            taskExecutor.execute(thread);
//        }
//        return "SUCCESS";
//    }
//
//    // private static final String OPERATE_SQL = "select * from fbm_action_job_data_export where id = {id} and status in
//    // (0, 1,
//    // 3, 4, 5)";
//
//    private static final String OPERATE_SQL = "select * from fbm_action_job_data_export where id = {id} ";
//
//    public static void main(String[] args) throws Exception {
//        ExcelExportTask task = (ExcelExportTask)SpringBeanUtil.getBean(ExcelExportTask.class);
//        task.processById(11229L, "1");
//    }
}
