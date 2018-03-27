package com.echain.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;  

public class CutImages {
  

	 
    /** 
     * /** 缩放图像 
     *  
     * @param file 
     *            源图像文件地址 
     * @param result 
     *            缩放后的图像地址 
     * @param widths 
     *            图片宽度 
     * @param heights 
     *            图片高度 
     * @return 
     */  
    public static File scale(File file, int widths, int heights) {
//        String newPath = CutImages.getPath(file);
    	String filePath = file.getAbsolutePath();
    	String fileName = filePath.substring(filePath.lastIndexOf(".")+1, filePath.length());
        try {  
            BufferedImage src = ImageIO.read(file); // 读入文件  
            Image image = src.getScaledInstance(widths, heights, Image.SCALE_SMOOTH);
            BufferedImage tag = new BufferedImage(widths, heights, BufferedImage.SCALE_SMOOTH);
            Graphics g = tag.getGraphics();
            g.drawImage(image, 0, 0, null); // 绘制缩小后的图  
            g.dispose();
            ImageIO.write(tag, fileName, file);// 输出到文件流  
        } catch (IOException e) {
            e.printStackTrace();  
        }  
        return file;  
    }  
  
 
    
    /** 
     * 缩放图片 
     *  
     * @param width 
     *            输出宽度 
     * @param height 
     *            输出高度 
     * @param input 
     *            输入流 
     * @param output 
     *            输出流 
     * @param format 
     *            输出格式 
     * @return 
     * @throws Exception 
     */  
    public static boolean convert(int width, int height, InputStream input,  
            OutputStream output, String format) throws Exception {  
        // 输入  
        BufferedImage inputImage = ImageIO.read(input);  
        // 转换  
        RenderedImage im = (RenderedImage) convert(height, height, inputImage);  
        // 输出  
        return ImageIO.write(im, format, output);  
    }  
  
    /** 
     * 转换压缩算法 
     *  
     * @param input 
     *            输入文件 
     * @param output 
     *            输出文件 
     * @return 
     * @throws Exception 
     */  
    public static boolean convert(File input, File output) throws Exception {  
        // 输入  
        BufferedImage inputImage = ImageIO.read(input);  
  
        // 转换  
        int width = inputImage.getWidth();  
        int height = inputImage.getHeight();  
  
        RenderedImage im = (RenderedImage) convert(width, height, inputImage);  
        String outputFilename = output.getName();  
        String format = outputFilename.substring(outputFilename  
                .lastIndexOf('.') + 1);  
        // 输出  
        return ImageIO.write(im, format, output);  
    }  
  
    /** 
     * 缩放图片 
     *  
     * @param width 
     *            输出宽度 
     * @param height 
     *            输出高度 
     * @param input 
     *            输入文件 
     * @param output 
     *            输出文件 
     * @return 
     * @throws Exception 
     */  
    public static boolean convert(int width, int height, BufferedImage image, File output)  
            throws Exception {  
        try {
			// 输入  
			//  BufferedImage inputImage = ImageIO.read(input);  
			// 转换  
			RenderedImage im = (RenderedImage) convert(width, height, image);
			String outputFilename = output.getName();
			String format = outputFilename.substring(outputFilename
					.lastIndexOf('.') + 1);
			// 输出  
			return ImageIO.write(im, format, output);
		} finally  {
			
		}  
    }  
    
    public static boolean convert(int width, int height,File input, File output)  
            throws Exception {  
    	 BufferedImage inputImage = ImageIO.read(input); 
        // 输出  
        return  convert(width, height,inputImage, output);  
    } 
  
    /** 
     * 缩放图片 
     *  
     * @param width 
     *            输出宽度 
     * @param height 
     *            输出高度 
     * @param input 
     *            输入路径 
     * @param output 
     *            输出路径 
     * @return 
     * @throws Exception 
     */  
    public static boolean convert(int width, int height, String inputPath,  
            String outputPath) throws Exception {  
        return convert(width, height, new File(inputPath), new File(outputPath));  
    }  
  
    /** 
     * 转换 
     *  
     * @param width 
     *            输出宽度 
     * @param height 
     *            输出高度 
     * @param input 
     *            BufferedImage 
     * @return BufferedImage 
     * @throws Exception 
     */  
    private static BufferedImage convert(int width, int height,  
            BufferedImage input) throws Exception {  
        // 初始化输出图片  
        BufferedImage output = new BufferedImage(width, height,  
                BufferedImage.SCALE_SMOOTH);  
  
        // 重新绘图  
        Image image = input.getScaledInstance(output.getWidth(), output  
                .getHeight(), output.getType());  
  
        output.createGraphics().drawImage(image, null, null);  
  
        return output;  
    }  
  
    /** 
     * 等比缩放图片 
     *  
     * @param width 
     *            输出宽度 
     * @param height 
     *            输出高度 
     * @param input 
     *            输入流 
     * @param output 
     *            输出流 
     * @return 
     * @throws Exception 
     */  
    public static boolean equimultipleConvert(int width, int height,  
            String input, String output) throws Exception {  
        return equimultipleConvert(width, height, new File(input), new File(  
                output));  
    }  
  
    /** 
     * 等比缩放图片 
     *  
     * @param width 
     *            输出宽度 
     * @param height 
     *            输出高度 
     * @param input 
     *            输入流 
     * @param output 
     *            输出流 
     * @return 
     *  
     * @throws Exception 
     */  
    public static boolean equimultipleConvert(int width, int height,  
            File input, File output) throws Exception {  
        // 输入  
        BufferedImage image = ImageIO.read(new FileInputStream(input));  
        // 转换 输出  
        return equimultipleConvert(width, height, image, output);  
    } 
    
    public static boolean equimultipleConvert(int width, int height,  
    		InputStream inputStream, File output) throws Exception {  
        // 输入  
        BufferedImage image = ImageIO.read(inputStream);  
        // 转换 输出  
        return equimultipleConvert(width, height, image, output);  
    } 
    
    private static boolean equimultipleConvert(int width, int height,  
    		  BufferedImage image , File output) throws Exception {   
        // 重新核算尺寸  
        if (image.getWidth() > 0 && image.getHeight() > 0) {  
            if ((image.getWidth() / image.getHeight()) >= (width / height)) {  
                if (image.getWidth() > width) {  
                    height = (image.getHeight() * width) / image.getWidth();  
                } else {  
                    width = image.getWidth();  
                    height = image.getHeight();  
                }  
            } else {  
                if (image.getHeight() > height) {  
                    width = (image.getWidth() * height) / image.getHeight();  
                } else {  
                    width = image.getWidth();  
                    height = image.getHeight();  
                }  
            }  
        }  
  
        // 转换 输出  
        return convert(width, height, image, output);  
    } 
    
    /**
     * 缩放图像（按高度和宽度缩放）
     * @param srcImageFile 源图像文件地址
     * @param result 缩放后的图像地址
     * @param height 缩放后的高度
     * @param width 缩放后的宽度
     * @param bb 比例不对时是否需要补白：true为补白; false为不补白;
     * @throws Exception 
     */
    public final static void scale2(String srcImageFile, String result, int height, int width, boolean bb) throws Exception {
        try {
            double ratio = 0.0; // 缩放比例
            File f = new File(srcImageFile);
            BufferedImage bi = ImageIO.read(f);
            Image itemp = bi.getScaledInstance(width, height, bi.SCALE_SMOOTH);
            // 计算比例
            if ((bi.getHeight() > height) || (bi.getWidth() > width)) {
                if (bi.getHeight() > bi.getWidth()) {
                    ratio = (new Integer(height)).doubleValue()
                            / bi.getHeight();
                } else {
                    ratio = (new Integer(width)).doubleValue() / bi.getWidth();
                }
                AffineTransformOp op = new AffineTransformOp(AffineTransform
                        .getScaleInstance(ratio, ratio), null);
                itemp = op.filter(bi, null);
            }
            if (bb) {//补白
                BufferedImage image = new BufferedImage(width, height,
                        BufferedImage.SCALE_SMOOTH);
                Graphics2D g = image.createGraphics();
                g.setColor(Color.white);
                g.fillRect(0, 0, width, height);
                if (width == itemp.getWidth(null))
                    g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2,
                            itemp.getWidth(null), itemp.getHeight(null),
                            Color.white, null);
                else
                    g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0,
                            itemp.getWidth(null), itemp.getHeight(null),
                            Color.white, null);
                g.dispose();
                itemp = image;
            }
//            convert(width, height, f, new File(result));
            ImageIO.write((BufferedImage) itemp, "JPEG", new File(result));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	 

	public static void main(String[] args) throws Exception {

		
	}

}
