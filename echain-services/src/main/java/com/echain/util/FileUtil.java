package com.echain.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.log4j.Logger;

import com.echain.constant.EchainConstant;

public class FileUtil {
	public static Logger LOGGER = Logger.getLogger(FileUtil.class);

	private static final int BUFFER_SIZE = 100 * 1024;
	public static final String LINE_SEPARATOR = System
			.getProperty("line.separator");

	/**
	 * 得到文件的扩展名
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getFileExtendName(String fileName) {
		String name = new File(fileName).getName();
		int len = name.lastIndexOf("."); // 取得最后点
		if (len > -1) {
			return name.substring(len + 1); // 裁剪
		}
		return "";

	}

	/**
	 * 删除一个文件
	 * 
	 * @param fileName
	 */
	public static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		if (file.exists() && file.isFile()) {
			file.delete();
			return true;
		}
		return false;
	}
	
	
	/**
	 * 从文本文件里读出用户名密码.
	 * 文件里存的是用户名和密码键值对.
	 * 文件必须是用户名和密码用regular隔开．一行写一对用户名密码对.用户名为key
	 * @author 宋冲	<mailto:song316@gmail.com/>
	 * @param path	文件路径　
	 * @return	返回用户名密码组成的Map．	Map<username,password>;
	 * @throws Exception
	 * @param regular
	 * @throws IOException 
	 */
	public static Map<String, String> readFile2Map(String path,String regular) throws IOException{
		if(StringUtils.isBlank(regular)){
			return null;	
		}
		if(StringUtils.isBlank(path)){
			return null;	
		}
		Map<String, String> map = new HashMap<String, String>();
		File file = new File(path);
		FileReader fReader = new FileReader(file);
		BufferedReader bReader = new BufferedReader(fReader);
		String temp = null;
		String [] tempArr = null;
		while((temp = bReader.readLine()) != null){
			if(StringUtils.isNotBlank(temp)){
				tempArr = temp.split(regular);
				if(tempArr.length > 1){
					String username = tempArr[0];
					String password = tempArr[1];
					if(StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)){
						map.put(username.trim(), password.trim());
					}
				}
			}
			temp = null;
			tempArr = null;
		}
		bReader.close();
		fReader.close();
		file = null;
		return map;
	}
	

	/**
	 * 删除上传文件
	 */
	public static void deleteImgFile(String fileName) {
		try {

			if (deleteFile(ResourceConfig.getInstance().getUploadBasePath()
					+ fileName)) {
				deleteFile(ResourceConfig.getInstance()
						.getUploadAdminBasePath() + fileName);
			}

		} catch (Throwable e) {// 捕获一切异常 不抛错

		}
	}

	public static String getHttpPath(String absPath) {
		if (StringUtil.isNotEmpty(absPath)) {
			int idx = absPath.indexOf("/static");
			if (idx > 0) {
				return absPath.substring(idx);
			}
		}

		return null;
	}

	public static String getCustomrizeFileName(String fileName) {
		String newFileName = "";
		if (fileName.indexOf(".") != -1) {
			newFileName = UuidUtil.generateRandomNo() + "."
					+ FileUtil.getFileExtendName(fileName);
		} else {
			newFileName = UuidUtil.generateRandomNo() + "." + fileName;
		}

		return newFileName;
	}

	/**
	 * 保存文件
	 * 
	 * @param myfiles
	 * @return
	 * @throws IOException
	 */
	public static String saveFile(String uploadFilePath, String fileName,
			InputStream inputStream) throws IOException {
		String newFileName = getCustomrizeFileName(fileName);
		mkdirs(uploadFilePath, true);
		LOGGER.debug("save user img file to : " + uploadFilePath + " fileName:"
				+ newFileName);
		String absPath = uploadFilePath + "/" + newFileName;
		FileUtils.copyInputStreamToFile(inputStream, new File(uploadFilePath,
				newFileName));
		return getHttpPath(absPath);
		// return extendPath+"/"+newFileName;
	}

	/**
	 * 保存文件
	 * 
	 * @param myfiles
	 * @return
	 * @throws IOException
	 */
	public static String saveFile(int width, int height, String uploadFilePath,
			String fileName, InputStream inputStream) throws Exception {
		String newFileName = UuidUtil.generateRandomNo() + "."
				+ FileUtil.getFileExtendName(fileName);
		String tmpFileName = UuidUtil.generateRandomNo() + "_tmp."
				+ FileUtil.getFileExtendName(fileName);
		mkdirs(uploadFilePath, true);
		LOGGER.debug("save user img file to : " + uploadFilePath + " fileName:"
				+ newFileName);
		String absPath = uploadFilePath + "/" + newFileName;
		if (FileEXIFUtil.shouldRotate(newFileName)) {
			String tmpOutputPath = uploadFilePath + "/" + tmpFileName;
			byte[] bytes = convertToByte(inputStream);
			String orientation = FileEXIFUtil.getOrientation(tmpOutputPath,
					new ByteArrayInputStream(bytes));
			FileEXIFUtil.rotateFile(orientation,
					new ByteArrayInputStream(bytes), tmpOutputPath); // 手机图片会反转，先用这个保存本地
																		// 再读取试试
			CutImages.equimultipleConvert(width, height,
					new File(tmpOutputPath), new File(absPath));
			new File(tmpOutputPath).delete();// TODO
		} else {
			CutImages.equimultipleConvert(width, height, inputStream, new File(
					absPath));
		}

		return getHttpPath(absPath);
		//return absPath;
		// return extendPath+"/"+newFileName;
	}

	/**
	 * 如果文件不是以/结尾 自动加上
	 * 
	 * @param path
	 * @return
	 */
	public static String appendSplitPath(String path) {
		if (StringUtil.isNotEmpty(path)) {
			path = path.trim();
			if (path.endsWith("/")) {
				return path;
			} else {
				return path + "/";
			}
		}

		return path;
	}

	public static void saveFile(InputStream inputStream, String outputFile)
			throws Exception {
		OutputStream outputStream = null;
		try {
			mkdirs(outputFile, false);
			outputStream = new FileOutputStream(outputFile);
			int bytesWritten = 0;
			int byteCount = 0;
			byte[] bytes = new byte[1024];
			while ((byteCount = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, bytesWritten, byteCount);
			}
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}

			if (outputStream != null) {
				outputStream.close();
			}

		}

	}

	public static void saveFile(byte[] bytes, String outputFile)
			throws Exception {
		OutputStream outputStream = null;
		try {
			mkdirs(outputFile, false);
			outputStream = new FileOutputStream(outputFile);
			outputStream.write(bytes);
		} finally {
			if (outputStream != null) {
				outputStream.close();
			}
		}

	}

	/**
	 * inputstream 转换成 bytes
	 * 
	 * @param inputStream
	 * @return
	 * @throws Exception
	 */
	public static byte[] convertToByte(InputStream inputStream)
			throws Exception {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			int bytesWritten = 0;
			int byteCount = 0;
			byte[] bytes = new byte[1024];
			while ((byteCount = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, bytesWritten, byteCount);
			}
			return outputStream.toByteArray();
		} finally {
			inputStream.close();
			outputStream.close();
		}

	}

	/**
	 * 删除文件
	 * 
	 * @param headImg
	 */
	public static void deleteUploadFile(String uploadFile) {
		String basePath = ResourceConfig.getInstance().getUploadBasePath();
		FileUtil.deleteFile(basePath + "/" + uploadFile);
	}

	/**
	 * 得到admin上传的图片地址
	 * 
	 * @param path
	 * @return
	 */
	public static String getUrlForAdmin(String path) {
		return ResourceConfig.getInstance().getAdminUrl() + path;
	}

	public static boolean mkdirs(String src, boolean isDir) {
		File srcFile = new File(src);
		File baseDir = null;
		if (isDir) {
			baseDir = srcFile;
		} else {
			baseDir = srcFile.getParentFile();
		}

		if (baseDir.exists() && baseDir.isDirectory()) {
			return true;
		} else {
			return baseDir.mkdirs();
		}

	}

	// 文件上传
	public static String copy(String src, String dst) {
		try {
			InputStream in = null;
			OutputStream out = null;
			mkdirs(dst, false);
			try {
				in = new BufferedInputStream(new FileInputStream(src),
						BUFFER_SIZE);
				out = new BufferedOutputStream(new FileOutputStream(dst),
						BUFFER_SIZE);
				byte[] buffer = new byte[BUFFER_SIZE];
				while (in.read(buffer) > 0) {
					out.write(buffer);
				}
				return dst;
			} finally {
				if (null != in) {
					in.close();
				}
				if (null != out) {
					out.close();
				}

			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 把输入流转换成String返回.
	 * 
	 * @param is
	 *            输入流
	 * @param encoding
	 *            编码
	 * @return 转换后的String
	 */
	public static String readInputStream(InputStream is, String encoding) {
		if (is == null) {
			return null;
		}
		if (StringUtil.isEmpty(encoding)) {
			return null;
		}
		StringBuffer sb = null;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(is, encoding));
			String tempbf;
			sb = new StringBuffer(100);
			while ((tempbf = br.readLine()) != null) {
				sb.append(tempbf);
				tempbf = null;
			}
			return sb.toString();
		} catch (UnsupportedEncodingException e) {
			LOGGER.error(e.getMessage(), e);
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					LOGGER.error(e.getMessage(), e);
				}
				br = null;
			}
		}
		return null;
	}

	/**
	 * 自己裁剪已经存好的本地文件 并另存为
	 * 
	 * @param fileName
	 * @param width
	 * @param height
	 * @return
	 * @throws Exception
	 */
	public static String cutImage(String fileName, Integer width, Integer height) {
		try {
			String parentPath = "";
			int indx = fileName.lastIndexOf("/");
			parentPath = fileName.substring(0, indx + 1);
			String cutFileName = parentPath + UuidUtil.generateRandomNo() + "."
					+ FileUtil.getFileExtendName(fileName);
			CutImages.equimultipleConvert(width, height, new File(fileName),
					new File(cutFileName));
			return getHttpPath(cutFileName);
		} catch (Throwable e) {
			LOGGER.error(e.getMessage(), e);
		}
		return "";
	}

	/**
	 * 读取文件转换为流
	 * 
	 * @param filePath
	 * @return
	 * @throws Exception
	 */

	public static InputStream readFileStream(String filePath) throws Exception {
		return new FileInputStream(filePath);
	}

	public static InputStream string2Inputstream(String str) {
		return new ByteArrayInputStream(str.getBytes());
	}

	public static String readFile(String filePath, String encoding)
			throws Exception {
		return FileUtil.readInputStream(readFileStream(filePath), encoding);
	}

	public static String readFile(String filePath) throws Exception {
		return readFile(filePath, "utf-8");
	}

	/**
	 * 根据目录 和扩展名得到文件名
	 * 
	 * @param basePath
	 * @param fileExtendName
	 * @return
	 */
	public static String generateLocalFileName(String basePath,
			String fileExtendName) {
		String storeLocalFileName = "";
		while (true) {
			String prefix = DateUtil.formatDate(new Date(), "yyyyMMddHHmmss")
					+ "-" + RandomUtils.nextInt();
			storeLocalFileName = prefix + "." + fileExtendName;
			if (!new File(basePath + "/" + storeLocalFileName).exists()) {
				break;
			}
		}
		return storeLocalFileName;
	}

	public static File[] listFiles(String path) {
		File file = new File(path);
		if (file.exists()) {
			if (file.isDirectory()) {
				return file.listFiles();
			}
			return new File[] { file };
		}
		return null;
	}

	public static List<FileFilterBean> getFiles(String basePath,
			String evidenceLocalServerPath) {
		File[] files = listFiles(basePath);

		if (files != null && files.length > 0) {
			List<FileFilterBean> lists = new ArrayList<FileFilterBean>();
			for (File file : files) {
				FileFilterBean fb = new FileFilterBean();
				String fileName = file.getName();
				fb.setFileName(fileName);
				String filePath = null;
				if (basePath.endsWith("/"))
					filePath = evidenceLocalServerPath + fileName;
				else
					filePath = evidenceLocalServerPath + "/" + fileName;
				if (file.isDirectory()) {
					fb.setFileCategory(EchainConstant.FileStatus.WENJIANJIA.status);
					fb.setDirPath(filePath);
				} else if (fileName.endsWith(".jpg")
						|| fileName.endsWith(".png")
						|| fileName.endsWith(".gif")
						|| fileName.endsWith(".jpeg")) {
					fb.setFileCategory(EchainConstant.FileStatus.TUPIAN.status);
					fb.setHttpPath(filePath);
				} else if (fileName.endsWith(".mp4")
						|| fileName.endsWith(".gov")) {
					fb.setFileCategory(EchainConstant.FileStatus.SHIPIN.status);
					fb.setHttpPath(filePath);
				}

				lists.add(fb);
			}
			return lists;
		}

		return null;
	}

	public static void deleteDirFile(File file) {
		if (file.isFile() || file.list().length == 0) {
			file.delete();
		} else {
			File[] files = file.listFiles();
			for (File f : files) {
				deleteDirFile(f);// 递归删除每一个文件
				f.delete();// 删除该文件夹
			}
		}
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		System.out.println(FileUtil.readFile("E:/document/all_publish.sql"));
	}

}
