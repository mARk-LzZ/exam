package com.lzz.exam.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CifReader {
    public static List<String> getCifFile(String fileName){
        List<String> list = new ArrayList<>();

        try {
            if (fileName !=null){
                Scanner sc = new Scanner(new FileReader(fileName));
                while (sc.hasNextLine()){
                    list.add(sc.nextLine());
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return list;

    }

}
