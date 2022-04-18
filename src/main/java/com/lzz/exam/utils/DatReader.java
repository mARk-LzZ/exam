package com.lzz.exam.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DatReader {

    public static List<String> getDatFile(String fileName) {
        List<String> doubleValues = new ArrayList<>();

        try {
            if (fileName!=null){
                Scanner sc = new Scanner(new FileReader(fileName));
                while(sc.hasNext()){
                    String s = sc.next();
                    if ("NaN".equals(s)){
                        return null;
                    }
                    doubleValues.add(s);
                }
                return doubleValues;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        }
        return null;
    }

    public static List<String> getPartialDatFile(String fileName) {
        List<String> doubleValues = new ArrayList<>();

        try {
            if (fileName!=null){
                Scanner sc = new Scanner(new FileReader(fileName));
                while(sc.hasNext() && doubleValues.size()<=3000){
                    String s = sc.next();
                    if ("NaN".equals(s)){
                        return null;
                    }
                    doubleValues.add(s);
                }
                return doubleValues;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        }
        return null;
    }
}
