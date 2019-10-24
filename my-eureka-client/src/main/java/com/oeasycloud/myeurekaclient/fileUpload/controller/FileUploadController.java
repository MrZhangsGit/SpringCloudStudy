package com.oeasycloud.myeurekaclient.fileUpload.controller;

import com.oeasycloud.myeurekaclient.fileUpload.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhangs
 * @Description
 * @createDate 2019/8/14
 */
@RestController
@Slf4j
@RequestMapping(value = "/file")
public class FileUploadController {

    @PostMapping(value = "/upload")
    public String upload(HttpServletRequest request, @RequestParam("file") MultipartFile file) throws Exception{
        /**
         * 注意，此处前端请求方式是以表单形式选取文件上传(走网络数据流)
         */
        if (null == file) {
            return "上传失败，无法找到文件！";
        }
        log.info("fileName:{}",file.getOriginalFilename());
        log.info("fileType:{}",file.getContentType());
        log.info("Bytes:{}", file.getBytes());

        //文件存放路径
        String filePath = "E:\\OEasyWorkSpace";
        //调用文件处理类FileUtil，处理文件将文件写入指定位置
        try {
            FileUtil.uploadFile(file.getBytes(), filePath, file.getOriginalFilename());
        } catch (Exception e) {
            log.error("E:{}", e);
        }

        return "SUCCESS";
    }
}
