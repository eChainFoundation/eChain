package com.echain.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.core.io.Resource;

public final class SystemConfigHolder
{

    private static SystemConfig configHolder;

    public static SystemConfig createConfigHolder(Resource springResource)
            throws Exception
    {

        Properties p = loadProperties(springResource.getFile());
        configHolder = new SystemConfigImpl(p);
        return configHolder;
    }

    public static Properties loadProperties(File f)
    {
        Properties props = new Properties();
        if (f == null || false == f.isFile())
        {
            return props;
        }
        InputStream fis = null;
        try
        {
            fis = new FileInputStream(f);
            props.load(fis);
        }
        catch (Exception e)
        {

        }
        finally
        {
            if (fis != null)
            {
                try
                {
                    fis.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return props;
    }

    /**
     * 获取excel文件的上传路径
     * @return
     */
    public static String getExcelPath()
    {
        return configHolder.getString("excel_path", "");
    }

    /**
     * 获取映射原始excel文件在服务器上的路径
     * @return
     */
    public static String getInitExcelPath()
    {
        return configHolder.getString("init_order_download_path", "");
    }

    /**
     * 获取保存下载匹配通过的excel路径
     * @return
     */
    public static String getDownloadSavePath()
    {
        return configHolder.getString("order_download_path", "");
    }

    /**
     * 获取保存 提交审核结果的文件的路径
     * @return
     */
    public static String getSubmitServerSavePath()
    {
        return configHolder.getString("order_submit_audit_success", "");
    }

    /**
     * 获取作品登记的excel保存的路径
     */
    public static String getWSExcelPath()
    {
        return configHolder.getString("works_excel_path", "");
    }

    /**
     * 获取上传文件的路径
     */
    public static String getUploadFilePath()
    {
        return configHolder.getString("upload_file_path", "");
    }

    /**
     * 获取侵权作品名称
     * @return
     */
    public static String getMovieName()
    {
        return configHolder.getString("movie_name", "");
    }

    /**
     * 获取侵权公司
     * @return
     */
    public static String getCompany()
    {
        return configHolder.getString("company", "");
    }

    /**
     * 获取侵权链接信息
     * @return
     */
    public static String getMovieDetail()
    {
        return configHolder.getString("movie_detail", "");
    }

    /**
     * 获取发函时间
     * @return
     */
    public static String getSendDate()
    {
        return configHolder.getString("yyyy_mm_dd", "");
    }

    /**
     * 获取侵权公司的简称
     * @return
     */
    public static String getShortName()
    {
        return configHolder.getString("short_name", "");
    }

    /**
     * 获取侵权的终端信息
     * @return
     */
    public static String getPlat()
    {
        return configHolder.getString("plat", "");
    }

    /**
     * 获取客户名称
     * @return
     */
    public static String getClientName()
    {
        return configHolder.getString("client_name", "");
    }

    /**
     * 获取客户简称
     * @return
     */
    public static String getClientShortName()
    {
        return configHolder.getString("client_short_name", "");
    }

    /**
     * 获取侵权网站
     * @return
     */
    public static String getSite()
    {
        return configHolder.getString("site", "");
    }

    /**
     * 获取图片的虚拟目录
     * @return
     */
    public static String getVirtualPath()
    {
        return configHolder.getString("virtual_path", "");
    }

    /**
     * 获取批量发送下线函侵权作品的excel文件在服务器上的路径
     * @return
     */
    public static String getSentMailExcelPath()
    {
        return configHolder.getString("sent_mail_excel_path", "");
    }

    /**
     * 获取接口地址
     * @return
     */
    public static String getCustomerUrl()
    {
        return configHolder.getString("FBI_PORT_URL", "");
    }

    /**
     * 获取回调地址
     * @return
     */
    public static String getCallBackUrl()
    {
        return configHolder.getString("CALLBACK_URL", "");
    }

}
