package com.lzz.exam.utils;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.tools.tar.TarEntry;
import org.apache.tools.tar.TarInputStream;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;


@Slf4j
public class TarUtil {
    /**
     * 解压tar.gz 文件
     * @param file 要解压的tar.gz文件对象
     * @param outputDir 要解压到某个指定的目录下
     * @throws IOException
     */
    public static List<String> unTarGz(File file, String outputDir) throws IOException {
        if (file == null || outputDir == null){
            return null;
        }

        TarInputStream tarIn = null;
        List<String> paths = new ArrayList<>();
        try{
            tarIn = new TarInputStream(new GZIPInputStream(
                    new BufferedInputStream(new FileInputStream(file))),
                    1024 * 2);

            log.info(outputDir);

            String directory=createDirectory(outputDir, null);//创建输出目录
            if (directory != null) {
                paths.add(directory);
            }
            TarEntry entry;
            while( (entry = tarIn.getNextEntry()) != null ){
                if(entry.isDirectory()){//是目录
                    log.info(entry.getName());
                    String directory1=createDirectory(outputDir, entry.getName());//创建空目录
                    if (directory1 != null) {
                        paths.add(directory1);
                    }
                }else{//是文件
                    log.info(outputDir + "/" + entry.getName());
                    File tmpFile = new File(outputDir + "/" + entry.getName());
                    try (OutputStream out=new FileOutputStream(tmpFile)) {
                        int length;

                        byte[] b=new byte[2048];

                        while ((length=tarIn.read(b)) != -1) {
                            out.write(b, 0, length);
                        }

                    }
                    catch (Exception e) {
                        log.info("error in here");
                    }
                }
            }
        }catch(IOException ex){
            throw new IOException("解压归档文件出现异常",ex);
        } finally{
            try{
                if(tarIn != null){
                    tarIn.close();
                }
            }catch(IOException ex){
                throw new IOException("关闭tarFile出现异常",ex);
            }
        }
        return paths;
    }

    private static String createDirectory(String outputDir, String subDir){
        File file = new File(outputDir);
        String absolutePath=file.getAbsolutePath();
        if(!(subDir == null || "".equals(subDir.trim()))){//子目录不为空
            file = new File(outputDir + "/" + subDir);
            absolutePath = file.getAbsolutePath();
            String sub =StringUtils.substringAfterLast(absolutePath, "/");
            if (sub.matches("^mp-.*_sg-.*-.*")){
                System.out.println("path= " + absolutePath);
                return absolutePath;
            }

        }
        if(!file.exists()){
            if(!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            file.mkdirs();
        }
        return absolutePath;
    }
}