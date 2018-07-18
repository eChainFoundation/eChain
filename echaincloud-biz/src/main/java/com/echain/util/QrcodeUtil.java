package com.echain.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Hashtable;

import static com.google.zxing.client.j2se.MatrixToImageConfig.BLACK;
import static com.google.zxing.client.j2se.MatrixToImageConfig.WHITE;

public class QrcodeUtil {

    /**
     * 生成二维码
     */
    public static String qrcode(String filePath, String qrHttpPath, String qrName, String urlPath) {
        File file = new File(filePath);// 生成图片目录
        if (!file.exists()) {
            file.mkdirs();
        }
        ByteArrayOutputStream stream = QRCode.from(qrHttpPath).to(ImageType.PNG).withSize(500, 500).stream();
        // 链接地址转化为二维码图片
        FileOutputStream out;
        try {
            out = new FileOutputStream(new File(file, qrName));// 将图片输出
            out.write(stream.toByteArray());

            out.flush();
            out.close();
//            System.out.println(qrName + "二维码生成完毕");
            return urlPath + qrName;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void main(String args[]) throws Exception {
//        TransferVo vo = transferToETH(2);
//        System.out.println(JSON.toJSONString(vo));

//        String httpPath = "0xe6b5caaa3612f04759201b5ebd65e987616eca7f";
//        String qrName = httpPath + ".png";
//        String qrcodePath = qrcode("/var/www/echain/static/wallet/", httpPath, qrName, "/static/wallet/");
//        System.out.println(qrcodePath);

//        transferToRMB("0.1");

        String content = "0xe6b5caaa3612f04759201b5ebd65e987616eca7f";
        String filePath = "/var/www/echain/static/wallet/" + content + ".png";
        createQrImg(content, filePath);
    }

    public static void createQrImg(String content, String filePath) throws Exception {
        File file = new File(filePath);// 生成图片目录
        if (!file.exists()) {
            file.mkdirs();
        }

        BufferedImage image = getNormalQRCode(content, 150, 150);
        ImageIO.write(image, "png", file);
    }

    private static BufferedImage getNormalQRCode(String content, int size, int size2) throws WriterException {
        Hashtable hints = new Hashtable();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.MARGIN, 0);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, size, size2, hints);
        //调用去除白边方法
        bitMatrix = deleteWhite(bitMatrix);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? BLACK : WHITE);
            }
        }
        return image;
    }

    private static BitMatrix deleteWhite(BitMatrix matrix) {
        int[] rec = matrix.getEnclosingRectangle();
        int resWidth = rec[2] + 1;
        int resHeight = rec[3] + 1;
        BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);
        resMatrix.clear();
        for (int i = 0; i < resWidth; i++) {
            for (int j = 0; j < resHeight; j++) {
                if (matrix.get(i + rec[0], j + rec[1])) {
                    resMatrix.set(i, j);
                }
            }
        }
        return resMatrix;
    }
}
