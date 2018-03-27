package com.echain.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class FtpConManager {
	public static void main(String[] args) throws Exception {

		// // =======上传测试============

		final String url = "192.168.1.162";
		final int port = 21;
		final String username = "roc";
		final String password = "lyd954342";

		for (int i = 0; i < 20; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					Random rand = new Random();
					FtpConManager.getInstance().downFile(url, port, username,
							password, "/home/roc/Documents",
							"/Users/roc/ftp" + rand.nextInt(1000));
				}
			}).start();

		}
		// // =======上传测试============

		// =======下载测试============
		// String localPath = "D:\\";
		// String url = "192.168.120.133";
		// int port = 21;
		// String username ="ftpuser";
		// String password ="ftpuser";
		// String remotePath ="/data/admftp/kw";
		// String fileName = "test.txt";
		// downFile(url, port, username, password, remotePath, fileName,
		// localPath);
		// =======下载测试============

	}

	private static FtpConManager instance;
	private FTPClient ftpClient = null;

	private FtpConManager() {

	}

	/**
	 * get FtpConManager
	 * 
	 * @return FtpConManager
	 */
	public synchronized static FtpConManager getInstance() {
//		if (instance == null) {
			instance = new FtpConManager();
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
	public void login(String s_url, int port, String uname, String pass) {
		// 连接
		try {
			ftpClient = new FTPClient();
			ftpClient.connect(s_url, port);
			ftpClient.login(uname, pass);
			// 检测连接是否成功
			int reply = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				this.closeCon();
				System.err.println("FTP server refused connection.");
				System.exit(1);
			}
		} catch (IOException e) {
			e.printStackTrace();
			this.closeCon();
		}
		
	}

	/**
	 * <p>
	 * 销毁ftp连接
	 * </p>
	 */
	public void closeCon() {
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
	public boolean uploadFile(String url, int port, String username,
			String password, String path, String filename, InputStream input) {
		boolean bool = false;
		try {
			FtpConManager.getInstance().login(url, port, username, password);
			ftpClient.changeWorkingDirectory(path);
			// 设置文件名上传的编码格式为 utf-8
			ftpClient.storeFile(new String(filename.getBytes("utf-8"),
					"iso-8859-1"), input);

			input.close();
			bool = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bool;
	}

	/**
	 * Description: 从FTP服务器下载文件
	 * 
	 * @param url
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
	public boolean downFile(String url, int port, String username,
			String password, String remotePath, String localPath) {
		boolean bool = false;
		try {
			File local = new File(localPath);
			if (!local.exists()) {
				local.mkdirs();
			}
			FtpConManager.getInstance().login(url, port, username, password);
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
	public boolean removeFile(String srcFname) {
		boolean flag = false;
		if (ftpClient != null) {
			try {
				flag = ftpClient.deleteFile(srcFname);
			} catch (IOException e) {
				e.printStackTrace();
				this.closeCon();
			}
		}
		return flag;
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