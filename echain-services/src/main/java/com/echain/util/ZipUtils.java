package com.echain.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtils {
	
	private ZipUtils(){
		
    }
    
    public static void doCompress(String srcFile, String zipFile) throws IOException {
        doCompress(new File(srcFile), new File(zipFile));
    }
    
    /**
     * 文件压缩
     * @param srcFile 目录或者单个文件
     * @param zipFile 压缩后的ZIP文件
     */
    public static void doCompress(File srcFile, File zipFile) throws IOException {
        ZipOutputStream out = null;
        try {
            out = new ZipOutputStream(new FileOutputStream(zipFile));
            doCompress(srcFile, out);
        } catch (Exception e) {
            throw e;
        } finally {
            out.close();//记得关闭资源
        }
    }
    
    public static void doCompress(String filelName, ZipOutputStream out) throws IOException{
        doCompress(new File(filelName), out);
    }
    
    public static void doCompress(File file, ZipOutputStream out) throws IOException{
        doCompress(file, out, "");
    }
    
    public static void doCompress(File inFile, ZipOutputStream out, String dir) throws IOException {
        if ( inFile.isDirectory() ) {
            File[] files = inFile.listFiles();
            if (files!=null && files.length>0) {
                for (File file : files) {
                    String name = inFile.getName();
                    if (!"".equals(dir)) {
                        name = dir + "/" + name;
                    }
                    ZipUtils.doCompress(file, out, name);
                }
            }
        } else {
             ZipUtils.doZip(inFile, out, dir);
        }
    }
    
    public static void doZip(File inFile, ZipOutputStream out, String dir) throws IOException {
        String entryName = null;
        if (!"".equals(dir)) {
            entryName = dir + "/" + inFile.getName();
        } else {
            entryName = inFile.getName();
        }
        ZipEntry entry = new ZipEntry(entryName);
        out.putNextEntry(entry);
        
        int len = 0 ;
        byte[] buffer = new byte[1024];
        FileInputStream fis = new FileInputStream(inFile);
        while ((len = fis.read(buffer)) > 0) {
            out.write(buffer, 0, len);
            out.flush();
        }
        out.closeEntry();
        fis.close();
    }
    
    
    
    public static void main(String[] args) throws IOException {
//    	String path = "/static/upload/offline_excel/2017/09-15-033416/94ffcf2217284d9fb2f8916932c8f895.xlsx";
    	String path = "D:\\test_excel\\test\\111.xlsx";
    	System.out.println(File.separator);
    	String[] str = path.split(File.separator);
    	String s1 = str[str.length-1];
    	String s2 = str[str.length-2];
    	
    	int lastIndex1 = path.lastIndexOf(s1);  
    	int lastIndex2 = path.lastIndexOf(s2);  
    	String ss1 = path.substring(0, lastIndex1);
    	String ss2 = path.substring(0, lastIndex2);
    	System.out.println(s1);
    	System.out.println(s2);
    	System.out.println(ss1);
    	System.out.println(ss2);
    	
    	doCompress(ss1, ss2+"is2.zip");
    	
//        doCompress("D:\\test_excel\\test\\", "D:\\test_excel\\111.zip");
    }
    
}
