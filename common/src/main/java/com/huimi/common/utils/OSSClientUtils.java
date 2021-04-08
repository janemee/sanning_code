package com.huimi.common.utils;

import com.aliyun.oss.OSSClient;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.function.Consumer;

public class OSSClientUtils {
    private static final String ENDPOINT = "http://oss-cn-hangzhou.aliyuncs.com";
    private static final String ACCESSKEYID = "LTAI4GJLH13DGbtJVeeyukX5";
    private static final String ACCESSKEYSECRET = "35CLga2w76Uid3iIwLrPhsPg50huGZ";
    private static final String BUCKETNAME =  "douguanjia";
    public static final String BASE_PATH = "apk/";
    public static final String ACCESS_BASE_PATH = "http://douguanjia.oss-cn-hangzhou.aliyuncs.com"; //访问前缀

    public static OSSClient getInstence(){
        OSSClient ossClient = new OSSClient(ENDPOINT,ACCESSKEYID,ACCESSKEYSECRET);
        ossClient.createBucket(BUCKETNAME);
        return ossClient;
    }

    /**
     * 通过httpclient 上传图片
     * @param imgFile 文件对象
     * @return 上传之后的图片路径
     */
    public static void doUpload(File imgFile, String filePath) throws Exception {
        OSSClient ossClient = getInstence();
        ossClient.putObject(BUCKETNAME,filePath,imgFile);
        ossClient.shutdown();
    }
    /**
     * 上传文件
     *
     * @param file_buff
     * @param filePath
     * @return
     */
    public static String uploadFile(final byte[] file_buff, final String filePath) {
        final String path = BASE_PATH + filePath;
        ossClient(o -> o.putObject(BUCKETNAME, path, new ByteArrayInputStream(file_buff)));
        return path;
    }
    private static void ossClient(Consumer<OSSClient> consumer) {
        OSSClient ossClient = new OSSClient(ENDPOINT, ACCESSKEYID, ACCESSKEYSECRET);
        try {
            consumer.accept(ossClient);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("文件上传失败");
        } finally {
            // 关闭client
            ossClient.shutdown();
        }
    }
}

