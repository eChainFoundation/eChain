package com.echain.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;;

public class ResourceConfig {
	private static Logger LOGGER = Logger.getLogger(ResourceConfig.class);

	private String[] resouces = new String[] { "fbimonitor-mail.properties", "fbimonitor-services.properties",
			"RSA.properties" };
	private static ResourceConfig config;
	private Properties properties = new Properties();

	private ResourceConfig() {

	}

	public String getValue(String key) {
		return this.properties.getProperty(key);
	}

	private void init() {
		properties.clear();
		for (String resouce : resouces) {
			InputStream inputstream = null;
			try {
				inputstream = this.getClass().getClassLoader().getResourceAsStream(resouce);
				properties.load(inputstream);
				inputstream.close();
			} catch (IOException e) {
				LOGGER.fatal(e.getMessage(), e);
				System.exit(0);
			}

		}

	}

	public synchronized static ResourceConfig getInstance() {
		if (config == null) {
			config = new ResourceConfig();
			config.init();
		}

		return config;
	}

	public String getUploadPicPath() {
		return FileUtil.appendSplitPath(getValue("upload_pic_path"));
	}

	public String getCheckExcelPath() {
		return getValue("check_excel_path");
	}

	public String getCheckExcelHttp() {
		return getValue("check_excel_http");
	}

	public String getEsIp() {
		return getValue("es_ip");
	}

	public String getEsPort() {
		return getValue("es_port");
	}

	public String getUploadBasePath() {
		String headImgUrl = this.getValue("http.file.upload.path");
		return headImgUrl;
	}

	public String getUploadAdminBasePath() {
		String path = this.getValue("http.file.upload.path.admin");
		return path;
	}

	public String getAdminUrl() {
		String headImgUrl = this.getValue("http.url.admin.path");
		return headImgUrl;
	}

	public String getEnvironmentType() {
		String environmentType = this.getValue("environment_type");
		return environmentType;
	}

	public String getExportExcelPath() {
		return this.getValue("export_excel_path");
	}

	public String getExportExcelHttp() {
		return this.getValue("export_excel_http");
	}

	public String getExportExcelHttpII() {
		return this.getValue("export_excel_httpII");
	}

	public String getEmailClickHttp() {
		return this.getValue("email_click_http");
	}

	public String getInitOrderDownloadPath() {
		String path = this.getValue("init_order_download_path");
		return path;
	}

	public String getFbiPortUrl() {
		String path = this.getValue("FBI_PORT_URL");
		return path;
	}

	public String getCallbackUrl() {
		String path = this.getValue("CALLBACK_URL");
		return path;
	}

	public String getExcelPath() {
		String path = this.getValue("excel_path");
		return path;
	}

	public String getOrderSubmitAuditSuccess() {
		String path = this.getValue("order_submit_audit_success");
		return path;
	}

	public String getWorksExcelPath() {
		String path = this.getValue("works_excel_path");
		return path;
	}

	public String getUploadFilePath() {
		String path = this.getValue("upload_file_path");
		return path;
	}

	public String getVirtualPath() {
		String path = this.getValue("virtual_path");
		return path;
	}

	public String getSentMailExcelPath() {
		String path = this.getValue("sent_mail_excel_path");
		return path;
	}

	public String getMovieName() {
		String path = this.getValue("movie_name");
		return path;
	}

	public String getCompany() {
		String path = this.getValue("company");
		return path;
	}

	public String getMovieDetail() {
		String path = this.getValue("movie_detail");
		return path;
	}

	public String getYyyyMmDd() {
		String path = this.getValue("yyyy_mm_dd");
		return path;
	}

	public String getShortName() {
		String path = this.getValue("short_name");
		return path;
	}

	public String getPlat() {
		String path = this.getValue("plat");
		return path;
	}

	public String getClientName() {
		String path = this.getValue("client_name");
		return path;
	}

	public String getClientShortName() {
		String path = this.getValue("client_short_name");
		return path;
	}

	public String getSite() {
		String path = this.getValue("site");
		return path;
	}

	// 版权库接口 上传电影
	public String getCopyrightStorehouseMovieUrl() {
		String path = this.getValue("copyright_storehouse_movie_url");
		return path;
	}

	// 版权库接口 上传电视剧
	public String getCopyrightStorehouseTvUrl() {
		String path = this.getValue("copyright_storehouse_tv_url");
		return path;
	}

	// 版权库接口 上传样本
	public String getCopyrightImportSample() {
		String path = this.getValue("copyright_import_sample");
		return path;
	}

	// 版权库接口 根据作品名 模糊查询作品集合
	public String getCopyrightSearchWorksInfo() {
		String path = this.getValue("copyright_search_works_info");
		return path;
	}

	// 版权库接口 根据workCode 查询作品信息
	public String getCopyrightSearchWorksBycode() {
		String path = this.getValue("copyright_search_work_bycode");
		return path;
	}

	// 版权库回调函数
	public String getCallBackHttp() {
		String path = this.getValue("call_back_http");
		return path;
	}

	// 任务审核人员邮箱调用
	public String getJobAuditorAddress() {
		String path = this.getValue("job_auditor_address");
		return path;
	}

	// 任务审核人员抄送邮箱函数
	public String getJobAuditorCcAddress() {
		String path = this.getValue("job_auditor_cc_address");
		return path;
	}

	public String getFboUrl() {
		String path = this.getValue("fbo_url");
		return path;
	}

	public String getFboYunUrl() {
		String path = this.getValue("fbo_yun_url");
		return path;
	}

	public String getFboSoaUrl() {
		String path = this.getValue("fbo_soa_url");
		return path;
	}

	// 工作台退出跳转url
	public String getFbmUrlLogin() {
		String path = this.getValue("fbm_url_login");
		return path;
	}

	// platform上传图片
	public String getUploadPicPathII() {
		String path = this.getValue("upload_pic_path_II");
		return path;
	}

	public String getWorksFirstCategory() {
		String path = this.getValue("works_first_Category");
		return path;
	}

	public String getWorksSecondCategory() {
		String path = this.getValue("works_second_category");
		return path;
	}

	public String getWorksDetail() {
		String path = this.getValue("works_detail");
		return path;
	}

	public String getWorksName() {
		String path = this.getValue("works_name");
		return path;
	}

	public String getTortSiteName() {
		String path = this.getValue("tort_site_name");
		return path;
	}

	public String getTortDomainName() {
		String path = this.getValue("tort_domain_name");
		return path;
	}

	public String getTortSiteSubject() {
		String path = this.getValue("tort_site_subject");
		return path;
	}

	public String getSiteFirstCategory() {
		String path = this.getValue("site_first_category");
		return path;
	}

	public String getSiteSecondCategory() {
		String path = this.getValue("site_second_category");
		return path;
	}

	public String getTortPictures() {
		String path = this.getValue("tort_pictures");
		return path;
	}

	public String getTortTitleUrls() {
		String path = this.getValue("tort_title_urls");
		return path;
	}

	public String getTortUrls() {
		String path = this.getValue("tort_urls");
		return path;
	}

	public String getMedNameBiz() {
		String path = this.getValue("med_name_biz");
		return path;
	}

	public String getTaskComplainInterfaceWeibo() {
		String path = this.getValue("task_complain_interface_weibo");
		return path;
	}

	public String getTaskComplainInterfaceSougou() {
		String path = this.getValue("task_complain_interface_sougou");
		return path;
	}

	public String getTaskComplainInterfaceBaidu() {
		String path = this.getValue("task_complain_interface_baidu");
		return path;
	}

	public static void main(String[] args) {
		String url = ResourceConfig.getInstance().getRedisHost();
		String url2 = ResourceConfig.getInstance().getRedisPort();
		String url3 = ResourceConfig.getInstance().getRedisPassword();
		System.out.println(url + "  " + url2 + "  " + url3);

	}

	public String getTortUrlsArr() {
		String path = this.getValue("tort_urls_arr");
		return path;
	}

	public String getTortHost() {
		String path = this.getValue("tort_host");
		return path;
	}

	public String getTortMD5Arr() {
		String path = this.getValue("tort_md5_arr");
		return path;
	}

	public String getSampleUrls() {
		String path = this.getValue("sample_url");
		return path;
	}

	public String getSampleTitleUrls() {
		String path = this.getValue("sample_title_url");
		return path;
	}

	public String getSampleUrlArr() {
		String path = this.getValue("sample_url_arr");
		return path;
	}

	public String getSampleUrlAndUrl() {
		String path = this.getValue("sample_url_and_url");
		return path;
	}

	public String getRedisHost() {
		String path = this.getValue("redis_host");
		return path;
	}

	public String getRedisPort() {
		String path = this.getValue("redis_port");
		return path;
	}

	public String getRedisPassword() {
		String path = this.getValue("redis_password");
		return path;
	}

	public String getTortUrlArrNoMd5() {
		String path = this.getValue("tort_url_arr_no_md5");
		return path;
	}

	public String getBookNameAndUrl() {
		String path = this.getValue("book_name_and_url");
		return path;
	}
}
