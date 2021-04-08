package com.huimi.admin.controller.upload;

import com.alibaba.fastjson.JSON;
import com.huimi.admin.controller.BaseController;
import com.huimi.admin.ueditor.ActionEnter;
import com.huimi.common.tools.MD5;
import com.huimi.common.utils.OSSClientUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @description ueditor
 * @anthor shiyaxiang
 * @since 2019-12-04 20:10
 */
@RestController
@RequestMapping("/js/ueditor1.4.3.3/jsp")
public class UEditorController extends BaseController {

    @RequestMapping(value = "/config")
    public void config(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");
        String rootPath = this.getClass().getResource("/").getPath();
        PrintWriter writer = null;
        try {
            String exec = new ActionEnter(request, rootPath).exec();
            writer = response.getWriter();
            writer.print(exec);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != writer) {
                writer.close();
                writer.flush();
            }
        }

    }


    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public void upload(MultipartFile file, HttpServletResponse response) {
        PrintWriter writer = null;
        response.setContentType("application/json;charset=UTF-8");
        //上传到图片服务器
        try {
            writer = response.getWriter();
            String json;
            //判断图片是否为空
            if (file.isEmpty()) {
                json = JSON.toJSONString("图片为空");
            } else {
                //获取图片扩展名
                String originalFilename = file.getOriginalFilename();
                if (null == originalFilename) {
                    json = JSON.toJSONString("图片名为空");
                } else {
                    String extName = Objects.requireNonNull(file.getOriginalFilename()).substring((originalFilename.lastIndexOf(".")) + 1);
                    String fileName = MD5.getMD5ofStr(originalFilename) + "." + extName;
                    String url = OSSClientUtils.uploadFile(file.getBytes(), "/upload/" + fileName);
                    url = OSSClientUtils.ACCESS_BASE_PATH + "/" + url;
                    Map<String, Object> map = new LinkedHashMap<>();
                    map.put("state", "SUCCESS");
                    map.put("original", originalFilename);
                    map.put("size", file.getSize());
                    map.put("title", fileName);
                    map.put("type", "." + extName);
                    map.put("url", url);
                    json = JSON.toJSONString(map);
                }
            }
            writer.print(json);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != writer) {
                writer.close();
                writer.flush();
            }
        }
    }


}
