package com.echain.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import com.echain.constant.EchainConstant;

public class FtpUtils {
	public static void main(String[] args) throws Exception {

		// // =======上传测试============

		final String url = "10.0.0.71";
		final int port = 1021;
		final String username = "fbi";
		final String password = "fbi123456";

//		for (int i = 0; i < 20; i++) {
//			new Thread(new Runnable() {
//				@Override
//				public void run() {
//					Random rand = new Random();
//					FtpUtils.downFile(url, port, username,
//							password, "/home/roc/Documents",
//							"/Users/roc/ftp" + rand.nextInt(1000));
//				}
//			}).start();
//
//		}
		
		String pid = "/var/lib/guacd/1_pid_155";
		
		String basePath = pid;
		
		List<FileFilterBean> ffbs = FtpUtils.selectAllFiles(url, port, username, password, basePath, "http://58.247.122.190"+EchainConstant.EVIDENCE_FILE_HTTP+pid);
		for(FileFilterBean ffb : ffbs) {
			System.out.println(ffb.getFileName());
		}
		
		//		FtpUtils.removeFile(url, port, username, password, "/home/roc/Documents/test");
	}

	private static FtpUtils instance;
//	private FTPClient ftpClient = null;

	private FtpUtils() {

	}

	/**
	 * get FtpConManager
	 * 
	 * @return FtpConManager
	 */
	public synchronized static FtpUtils getInstance() {
//		if (instance == null) {
			instance = new FtpUtils();
//		}
		return instance;
	}

	/**
	 * <p>
	 * ftp登录
	 * </p>
	 * 
	 * @param s_url
	 *            ftp服务地址
	 * @param port
	 *            ftp服务端口
	 * @param uname
	 *            用户名
	 * @param pass
	 *            密码
	 * @throws IOException
	 */
	private FTPClient login(String s_url, int port, String uname, String pass)
			throws IOException {
		FTPClient ftpClient = new FTPClient();
		// 连接
		ftpClient.connect(s_url, port);
		ftpClient.login(uname, pass);
		
		ftpClient.enterLocalPassiveMode(); 
		// 检测连接是否成功
		int reply = ftpClient.getReplyCode();
		if (!FTPReply.isPositiveCompletion(reply)) {
			this.closeCon(ftpClient);
			System.err.println("FTP server refused connection.");
			System.exit(1);
		}
		return ftpClient;
	}

	/**
	 * <p>
	 * 销毁ftp连接
	 * </p>
	 */
	private static void closeCon(FTPClient ftpClient) {
		if (ftpClient != null) {
			if (ftpClient.isConnected()) {
				try {
					ftpClient.logout();
					ftpClient.disconnect();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Description: 向FTP服务器上传文件
	 * 
	 * @param url
	 *            FTP服务器 hostname
	 * @param port
	 *            FTP服务器端口 默认端 21
	 * @param username
	 *            FTP登录账号
	 * @param password
	 *            FTP登录密码
	 * @param path
	 *            FTP服务器保存目录
	 * @param filename
	 *            上传到FTP服务器上的文件名
	 * @param input
	 *            输入流
	 * @return 成功返回true，否则返回false
	 */
	public static boolean uploadFile(String ip, int port, String username,
			String password, String path, String filename, InputStream input) {
		boolean bool = false;
		FTPClient ftpClient = null;
		try {
			ftpClient = FtpUtils.getInstance().login(ip, port, username, password);
			ftpClient.changeWorkingDirectory(path);
			// 设置文件名上传的编码格式为 utf-8
			ftpClient.storeFile(new String(filename.getBytes("utf-8"),
					"iso-8859-1"), input);

			input.close();
			bool = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeCon(ftpClient);
		}
		return bool;
	}

	/**
	 * Description: 从FTP服务器下载文件
	 * 
	 * @param ip
	 *            FTP服务器hostname
	 * @param port
	 *            FTP服务器端口
	 * @param username
	 *            FTP登录账号
	 * @param password
	 *            FTP登录密码
	 * @param remotePath
	 *            FTP服务器上的相对路径
	 * @param localPath
	 *            下载后保存到本地的路径
	 * @return
	 */
	public static boolean downFile(String ip, int port, String username,
			String password, String remotePath, String localPath) {
		boolean bool = false;
		FTPClient ftpClient = null;
		try {
			File local = new File(localPath);
			if (!local.exists()) {
				local.mkdirs();
			}
			ftpClient = FtpUtils.getInstance().login(ip, port, username, password);
			ftpClient.changeWorkingDirectory(remotePath);// 转移到FTP服务器目录
			FTPFile[] fs = ftpClient.listFiles();
			for (FTPFile ff : fs) {
				String remotFileName = new String(ff.getName().getBytes(
						"iso-8859-1"), "utf-8");
				File localFile = new File(local, remotFileName);
				OutputStream os = new FileOutputStream(localFile);
				ftpClient.retrieveFile(ff.getName(), os);
				os.close();
			}

			bool = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeCon(ftpClient);
		}
		return bool;
	}

	/**
	 * 
	 * <p>
	 * 删除ftp上的文件
	 * </p>
	 * 
	 * @param srcFname
	 * @return true || false
	 */
	public static boolean removeFile(String ip, int port, String username,
			String password, String remotePath) {
		boolean flag = false;
		FTPClient ftpClient = null;
		try {
			ftpClient = FtpUtils.getInstance().login(ip, port, username, password);
//			ftpClient.changeWorkingDirectory(remotePath);// 转移到FTP服务器目录
//			FTPFile[] fs = ftpClient.listFiles();
//			for (FTPFile ff : fs) {
//				flag = ftpClient.deleteFile(ff.getName());
//			}
//			ftpClient.dele(remotePath);
			deleteServerDirFile(ftpClient,remotePath);
		} catch (IOException e) {
			e.printStackTrace();
			closeCon(ftpClient);
		}
		return flag;
	}
	
	public static void deleteServerDirFile(FTPClient ftpClient, String remotePath) throws IOException {
		ftpClient.changeWorkingDirectory(remotePath);
		FTPFile[] fs = ftpClient.listFiles(); 
		for (FTPFile ff : fs) {
			if(ff.isFile()) {
				ftpClient.deleteFile(ff.getName());
			} else if(ff.isDirectory()) {
				deleteServerDirFile(ftpClient,remotePath+"/"+ff.getName());
			}
		}
		ftpClient.dele(remotePath);
	}
	
	public static boolean removeSingleFile(String ip, int port, String username,
			String password, String remotePath) {
		boolean flag = false;
		FTPClient ftpClient = null;
		try {
			ftpClient = FtpUtils.getInstance().login(ip, port, username, password);
			String path = remotePath.substring(0, remotePath.lastIndexOf("/"));
			String name = remotePath.substring(remotePath.lastIndexOf("/")+1);
			flag = ftpClient.changeWorkingDirectory(path);
			flag = ftpClient.deleteFile(name);
//			ftpClient.deleteFile(remotePath);
		} catch (IOException e) {
			e.printStackTrace();
			closeCon(ftpClient);
		}
		return flag;
	}
	
	/**
	 * 查看远程文件夹下所有文件
	 * @param ip
	 * @param port
	 * @param username
	 * @param password
	 * @param remotePath
	 * @param httpUrl
	 * @return
	 */
	public static List<FileFilterBean> selectAllFiles(String ip, int port, String username,
			String password, String remotePath, String httpUrl) {
		FTPClient ftpClient = null;
		try {
			ftpClient = FtpUtils.getInstance().login(ip, port, username, password);
			List<FileFilterBean> ffbs = new ArrayList<FileFilterBean>();
			for(int i = 0; i < 2; i++) {
				String dir = "/";
				if(i == 0)
					dir += "shoot";
				else if(i == 1)
					dir += "video";
				ftpClient.changeWorkingDirectory(remotePath+dir);// 转移到FTP服务器目录
				FTPFile[] fs = ftpClient.listFiles();
				if(fs != null && fs.length > 0) {
					for (FTPFile ff : fs) {
						FileFilterBean fb = new FileFilterBean();
						if(ff.isDirectory()) {
							fb.setFileCategory(EchainConstant.FileStatus.WENJIANJIA.status);
							fb.setDirPath(ff.getName());
						} else {
							if(i == 0) {
								fb.setFileCategory(EchainConstant.FileStatus.TUPIAN.status);
								fb.setHttpPath(httpUrl+"/shoot/"+ff.getName());
							} else if(i == 1) {
								if(!ff.getName().trim().endsWith("_record.avi"))
									continue;

								fb.setFileCategory(EchainConstant.FileStatus.SHIPIN.status);
								fb.setHttpPath(httpUrl+"/video/"+ff.getName());
							}
						}
						fb.setFileName(ff.getName());
						ffbs.add(fb);
					}
				}
			}
			return ffbs;
		} catch (IOException e) {
			e.printStackTrace();
			closeCon(ftpClient);
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

}