package com.echain.util;

  
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifIFD0Directory;

public class FileEXIFUtil {
	protected static Logger LOGGER = Logger.getLogger(FileEXIFUtil.class);  
	
	private static Map<String,Integer> rotateMap = new HashMap<String,Integer >();
	
	private static String DEFAULT_ROTATE="-100";
	
	
	
	static {
		rotateMap.put(DEFAULT_ROTATE, 0);
		rotateMap.put("6", 90);
	}
	public static String getOrientation(String fileName) {
		try {
			InputStream in = new FileInputStream(new File(fileName));
			return getOrientation(fileName,in);
		} catch (Exception e) {
		    LOGGER.error(e.getMessage(),e);
		}
		 return DEFAULT_ROTATE;
	}
	/**
	 * 只有jpg 文件 才需要判断是否需要翻转
	 * @param fileName
	 * @return
	 */
	public static boolean shouldRotate(String fileName){
		String fileExtendName = FileUtil.getFileExtendName(fileName);
		if(!"JPG".equalsIgnoreCase(fileExtendName)){
			return false;
		}
		return true;
	}

	
	public static String getOrientation( String fileName ,InputStream inputStream){ 
		if(!FileEXIFUtil.shouldRotate(fileName)){
			return DEFAULT_ROTATE;
		}
		  try {   
			   Metadata metadata = JpegMetadataReader.readMetadata(inputStream); 
			   Directory directoryt =   metadata.getDirectory(ExifIFD0Directory.class);
			   if(directoryt!=null){
				   for(Tag tag :directoryt.getTags()){
					   LOGGER.info(tag.getTagName()+" "+ tag.getDescription());
				   }
				   if(  directoryt.containsTag(ExifIFD0Directory.TAG_ORIENTATION)){
					   Integer orientation=  directoryt.getInt(ExifIFD0Directory.TAG_ORIENTATION);
					   LOGGER.info(orientation+" "+directoryt.getDescription(ExifIFD0Directory.TAG_ORIENTATION));
					   return orientation.toString();
				   } 
			   }
			
		   } catch (JpegProcessingException e) {   
			  // LOGGER.error(e.getMessage(),e);
		   } catch (Throwable e) {   
			   LOGGER.error(e.getMessage(),e);
		   }finally{
			   if(inputStream!=null){
				   try {
					inputStream.close();
				} catch (Throwable e) {
					  LOGGER.error(e.getMessage(),e);
				}
			   }
		   }  
		   return DEFAULT_ROTATE;
	}
	
	public  static Integer getRotate(String rotateValue){ 
		
		if(rotateMap.get(rotateValue)!=null){
			return rotateMap.get(rotateValue);
		}
		return rotateMap.get(DEFAULT_ROTATE);
	}
	
 
	public static String rotateFile( String orientation,InputStream inputStream,String outputFile) throws Exception{
		String fileExtendName = FileUtil.getFileExtendName(outputFile);
		if(StringUtil.isEmpty(orientation)){
			orientation =DEFAULT_ROTATE;
		} 
		Integer ratotaRate =  getRotate(orientation); 
	 
		BufferedImage oldImageBuffer = (BufferedImage)ImageIO.read(inputStream);   
	    LOGGER.info("ratotaRate == "+ratotaRate);
		//写到新的文件  
		FileOutputStream out = new FileOutputStream(outputFile);  
		if(ratotaRate > 0){ 
		 
		   AffineTransform newXform = new AffineTransform(0, 1, -1,0,oldImageBuffer.getHeight(), 0);// 向右翻转90
		    
		   // AffineTransform newXform = new AffineTransform(-1, 0, 0, 1,  oldImageBuffer.getWidth(), 0);// 水平翻转  镜子效果
		   
		  // AffineTransform newXform = new AffineTransform(0, -1, 1,0,0,oldImageBuffer.getWidth());// 向左翻转90
		    
		 // AffineTransform newXform = new AffineTransform(-1, 0, 0, -1, oldImageBuffer.getHeight(), oldImageBuffer.getWidth());// 旋转180度   
		    
		    AffineTransformOp op = new AffineTransformOp(newXform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);  
		    BufferedImage  newImageBuffer = op.filter(oldImageBuffer, null);    
			try{  
			    ImageIO.write(newImageBuffer,fileExtendName , out);  
			}finally{ 
				inputStream.close();
			    out.close();  
			} 
		}else{
			try{  
			    ImageIO.write(oldImageBuffer,fileExtendName , out);  
			}finally{  
				inputStream.close();
			    out.close();  
			} 
		} 
	
		return outputFile;
	}
	
 
	 
	 
	public static void testRotateFile(String inputFile,String outFile) throws Exception{	
 
	   try {
		BufferedImage oldImageBuffer = (BufferedImage) ImageIO .read(new File(inputFile)); 
 
		AffineTransform newXform = new AffineTransform(-1, 0, 0, -1, oldImageBuffer.getHeight(), oldImageBuffer.getWidth());// 旋转180度   
		AffineTransformOp op = new AffineTransformOp(newXform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		BufferedImage newImageBuffer = op.filter( oldImageBuffer, null); 
		//写到新的文件  
		FileOutputStream out = new FileOutputStream(outFile);
		try {
			ImageIO.write(newImageBuffer, "JPG", out);
			out.flush();
		} finally {
		 
			out.close();
		} 
	} catch (Exception e) {
		e.printStackTrace();
		new File(outFile).delete(); 
	} }
	
	  

	public static void main(String[] args) throws   Exception { 
		 //  testRotateFile( "f:/tmp/1.jpg","f:/tmp/generate.jpg");
		System.out.println(rotateFile("1",new FileInputStream("f:/tmp/1.jpg"),"f:/tmp/generate.jpg"));
	}

}
