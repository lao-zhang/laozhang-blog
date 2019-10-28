package com.zx.blog.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FileUtils;
import com.alibaba.fastjson.JSONObject;
import com.zx.blog.service.PhotoService;

@Controller
@RequestMapping("/photo")
public class PhotoController {
	
	//图片在服务器所在路径
    private static String WEB_BASE_PATH = "D://blogPhotos/";
    //图片前缀域名（需搭建文件服务器）
    private static String WEB_PIC_HOST = "D://blogPhotos/";

	@Autowired
	PhotoService pthotoService;
	
	 //ckeditor图片上传
    @RequestMapping("uploadCkEditor")
    @ResponseBody
    public void uploadWangEdit(@RequestParam("upload") MultipartFile file, HttpServletResponse response) {
        FileResponse fileResponse = new FileResponse();
        try {
            PrintWriter out = response.getWriter();
            if (file.isEmpty()) {
                String error = fileResponse.error(0, "文件为空");
                out.println(error);
            }

            String filePath = WEB_BASE_PATH;
            File dir = new File(filePath);
            if(! dir.exists()) {
                dir.mkdir();
            }
            String orgName = file.getOriginalFilename();
            String[] split = orgName.split("\\.");
            String suffix = split[1];

            //检查图片格式
            boolean imgTypeIsRight = checkImgType(suffix);
            if (!imgTypeIsRight){
                String error = fileResponse.error(0, "无效状态异常");
                out.println(error);
            }

            String fileName = UUID.randomUUID().toString() +"."+suffix;
            String path = filePath + fileName;
            File tempFile = null;
            try {
                tempFile =  new File(path);
                FileUtils.copyInputStreamToFile(file.getInputStream(), tempFile);
            } catch (IllegalStateException e) {
                String error = fileResponse.error(0, "无效状态异常");
                out.println(error);
                return;
            } catch (IOException e) {
                String error = fileResponse.error(0, "IO异常");
                out.println(error);
                return;
            }

            String success = fileResponse.success(1, fileName, WEB_PIC_HOST+fileName, "");
            out.print(success);
        } catch (IOException ex){
            return;
        }
    }

    //ckEditor图片上传响应体
    public class FileResponse extends HashMap<String, Object> {

        Map<String,Object> msgMap = new HashMap<>();

        public String error(int code, String msg){
            FileResponse result = new FileResponse();
            msgMap.put("message",msg);
            result.put("uploaded",code);
            result.put("error",msgMap);

            return JSONObject.toJSON(result).toString();
        }

        public String success(int code, String fileName,String url,String msg){
            FileResponse result = new FileResponse();
            if(!StringUtils.isEmpty(msg)){
                msgMap.put("message",msg);
                result.put("error",msgMap);
            }
            result.put("uploaded",code);
            result.put("fileName",fileName);
            result.put("url",url);
            return JSONObject.toJSON(result).toString();
        }
    }

    //检查图片格式
    private boolean checkImgType(String suffix){
        if (!"jpg".equals(suffix)&&!"jpeg".equals(suffix)&&!"png".equals(suffix)&&!"gif".equals(suffix)){
            return false;
        } else {
            return true;
        }
    }
}
