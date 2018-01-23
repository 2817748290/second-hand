package com.liangliang.bookmanager.utils;


import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * Created by lzp20 on 2017/4/29.
 */
@Component
public class FileUtil {

    /**
     * 获取application配置的项目文件储存目录
     */
    public static String WINDOWS_PATH;
    public static String LINUX_PATH;

    @Value("${com.liangliang.custom.windows-path}")
    public void setWindowsPath(String path) {
        WINDOWS_PATH = path;
    }

    @Value("${com.liangliang.custom.linux-path}")
    public void setLinuxPath(String path) {
        LINUX_PATH = path;
    }

    /**
     * 返回项目的文件存储目录
     * @return
     */
	public static String getProjectFilePath() {
        String path = null;
        if(System.getProperty("os.name").indexOf("Windows") != -1) {
            path = WINDOWS_PATH;
        }else {
            path = LINUX_PATH;
        }
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
            System.out.println("创建文件夹================");
        }
        System.out.println("存储目录:"+path);
        return path;
    }
    /**
     * 将MultipartFile保存到指定的路径下
     *
     * @param file
     *            Spring的MultipartFile对象
     * @param savePath
     *            保存路径
     * @return 保存的文件名，当返回NULL时为保存失败。
     * @throws IOException
     * @throws IllegalStateException
     */
    public static String save(MultipartFile file, String savePath) throws IllegalStateException, IOException {
        if (file != null && file.getSize() > 0) {
            File fileFolder = new File(savePath);
            if (!fileFolder.exists()) {
                fileFolder.mkdirs();
            }
            System.out.println("保存路径:"+savePath);
            File saveFile = getFile(savePath, file.getOriginalFilename());
            file.transferTo(saveFile);
            return saveFile.getName();
        }
        return null;
    }

    /**
     * 创建一个带有时间戳和原始图片名的文件
     * @param savePath
     * @param originalFilename
     * @return 创建的文件
     */
    private static File getFile(String savePath, String originalFilename) {
        String fileName = System.currentTimeMillis() + "_" + originalFilename;
        File file = new File(savePath + fileName);
        if (file.exists()) {
            return getFile(savePath, originalFilename);
        }
        return file;
    }

}
