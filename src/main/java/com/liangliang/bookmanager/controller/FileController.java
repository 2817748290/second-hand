package com.liangliang.bookmanager.controller;

import com.liangliang.bookmanager.bean.Message;
import com.liangliang.bookmanager.utils.CommonUtil;
import com.liangliang.bookmanager.utils.DownloadUtil;
import com.liangliang.bookmanager.utils.UploadUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by lzp on 2017/6/18.
 * 上传下载示例
 */
@Controller
public class FileController {
    @RequestMapping(value = "/uploadview", method = RequestMethod.GET)
    public String uploadTest() {
        return "upload";
    }

    @RequestMapping(value = "/public/upload", method = RequestMethod.POST)
    @ResponseBody
    public Message upload(@RequestParam MultipartFile file) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        if (file.isEmpty()) {
            return new Message(Message.ERROR, "文件为空", null);
        } else {
            String save = "upload";

            Map map = UploadUtil.singleFileUpload(file, save, CommonUtil.getIpAddr(request));
            if ((Boolean) map.get("status")) {
                return new Message(Message.SUCCESS, "保存成功", "/download?filename=" + map.get("path"));
            } else {
                return new Message(Message.ERROR, "保存失败", null);
            }
        }
    }

    @RequestMapping(value = "/public/download", method = RequestMethod.GET)
    @ResponseBody
    public void downFile(
            @RequestParam(value = "filename") String odexName,
            HttpServletResponse response,
            HttpServletRequest request) {
        DownloadUtil.downUploadFile(odexName, request, response);
    }

}
