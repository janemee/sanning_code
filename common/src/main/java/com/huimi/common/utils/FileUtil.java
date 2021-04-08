package com.huimi.common.utils;

import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author ylzzZ
 * @date 2019/10/8 16:41
 **/
public class FileUtil {

    /**
     * 下载文件
     *
     * @param fileUrl 文件路径
     * @param path    保存路径
     */
    public static void downloadPicture(String fileUrl, String path) {
        URL url;
        try {
            url = new URL(fileUrl);
            DataInputStream dataInputStream = new DataInputStream(url.openStream());
            FileOutputStream fileOutputStream = new FileOutputStream(new File(path));
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = dataInputStream.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            fileOutputStream.write(output.toByteArray());
            dataInputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件转base64
     */
    public static String outImgBase64(String imgPath) throws Exception {
        BufferedImage read = ImageIO.read(new File(imgPath));
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ImageIO.write(read, "jpg", bo);
        return Base64.getEncoder().encodeToString(bo.toByteArray());
    }

    /**
     * 文件转base64
     */
    public static String outImgBase64File(File file) throws Exception {
        BufferedImage read = ImageIO.read(file);
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ImageIO.write(read, "jpg", bo);
        return Base64.getEncoder().encodeToString(bo.toByteArray());
    }

    /**
     * base64转文件
     */
    public static void decoderBase64File(String base64Code, String targetPath) throws Exception {
        byte[] buffer = new BASE64Decoder().decodeBuffer(base64Code);
        FileOutputStream out = new FileOutputStream(targetPath);
        out.write(buffer);
        out.close();
    }

    public static void alpha(String path, String savePath) {
        BufferedImage bufferedImage;
        try {
            // read image file
            bufferedImage = ImageIO.read(new File(path));
            // create a blank, RGB, same width and height, and a white
            // background
            BufferedImage newBufferedImage = new BufferedImage(
                    bufferedImage.getWidth(), bufferedImage.getHeight(),
                    BufferedImage.TYPE_INT_RGB);
            // TYPE_INT_RGB:创建一个RBG图像，24位深度，成功将32位图转化成24位
            newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0,
                    Color.red, null);
            // write to jpeg file
            ImageIO.write(newBufferedImage, "PNG", new File(savePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 图片背景透明色
     */
    public static void transferAlphaFile(String imgSrc, String imgTarget) {
        File file = new File(imgSrc);
        InputStream is = null;
        boolean result;
        try {
            is = new FileInputStream(file);
            // 如果是MultipartFile类型，那么自身也有转换成流的方法：is = file.getInputStream();
            BufferedImage bi = ImageIO.read(is);
            ImageIcon imageIcon = new ImageIcon(bi);
            BufferedImage bufferedImage = new BufferedImage(imageIcon.getIconWidth(), imageIcon.getIconHeight(),
                    BufferedImage.TYPE_4BYTE_ABGR);
            Graphics2D g2D = (Graphics2D) bufferedImage.getGraphics();
            g2D.drawImage(imageIcon.getImage(), 0, 0, imageIcon.getImageObserver());
            int alpha = 0;
            for (int j1 = bufferedImage.getMinY(); j1 < bufferedImage.getHeight(); j1++) {
                for (int j2 = bufferedImage.getMinX(); j2 < bufferedImage.getWidth(); j2++) {
                    int rgb = bufferedImage.getRGB(j2, j1);
                    int r = (rgb & 0xff0000) >> 16;
                    int g = (rgb & 0xff00) >> 8;
                    int b = (rgb & 0xff);
                    if (((255 - r) < 30) && ((255 - g) < 30) && ((255 - b) < 30)) {
                        rgb = ((alpha + 1) << 24) | (rgb & 0x00ffffff);
                    }
                    bufferedImage.setRGB(j2, j1, rgb);
                }
            }
            g2D.drawImage(bufferedImage, 0, 0, imageIcon.getImageObserver());
            result = ImageIO.write(bufferedImage, "png", new File(imgTarget));// 直接输出文件
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    public static void download(HttpServletRequest request, HttpServletResponse response, InputStream inputStream, String fileName) {
        BufferedOutputStream bos = null;
        try {
            byte[] buffer = new byte[1024];
            String userAgent = request.getHeader("user-agent").toLowerCase();
            if (userAgent.contains("msie") || userAgent.contains("like gecko")) {
                fileName = URLEncoder.encode(fileName, "UTF-8");
            } else {
                fileName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
            }

            response.setCharacterEncoding("utf-8");
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            bos = new BufferedOutputStream(response.getOutputStream());
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 将文件转换成byte数组
     *
     * @param tradeFile
     * @return
     */
    public static byte[] getFileByteArray(File tradeFile) {

        byte[] buffer = null;
        try {
            FileInputStream fis = new FileInputStream(tradeFile);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }
}
