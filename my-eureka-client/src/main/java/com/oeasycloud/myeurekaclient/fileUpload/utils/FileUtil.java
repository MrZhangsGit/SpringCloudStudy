package com.oeasycloud.myeurekaclient.fileUpload.utils;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @author zhangs
 * @Description
 * @createDate 2019/8/14
 */
public class FileUtil {
    /**
     * 文件上传工具类服务方法
     * @param file
     * @param filePath
     * @param fileName
     * @throws Exception
     */
    public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception{

        File targetFile = new File(filePath);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath + "\\" + fileName);
        out.write(file);
        out.flush();
        out.close();
    }
}
