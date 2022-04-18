package com.lzz.exam.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FolderReader {
    private static final Map<String, String> FILES=new HashMap<>();
    public static Map<String, String> readfile(String filepath) throws FileNotFoundException, IOException {
        if (filepath == null){
            return null;
        }

        try {
            File file = new File(filepath);
            if (!file.isDirectory()) {
                FILES.put(file.getName(),file.getAbsolutePath());
            } else if (file.isDirectory()) {
                String[] filelist = file.list();
                for (int i=0; i < Objects.requireNonNull(filelist).length; i++) {
                    File readfile = new File(filepath + "/" + filelist[i]);
                    if (!readfile.isDirectory()) {
                        FILES.put(readfile.getName() , readfile.getAbsolutePath());
                    } else if (readfile.isDirectory()) {
                        readfile(filepath + "/" + filelist[i]);
                    }
                }

            }
        } catch (FileNotFoundException e) {
            System.out.println("readfile()   Exception:" + e.getMessage());
        }
        return FILES;
    }

    public static void clear(Map<String, String> map){
        if (map.size() != 0){
            map.clear();
        }
    }
}
