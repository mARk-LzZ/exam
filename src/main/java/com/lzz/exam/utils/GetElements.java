package com.lzz.exam.utils;

import java.util.ArrayList;
import java.util.List;

public class GetElements {
    public static List<String> getElements(String formula){
        String[] s = formula.split("");
        List<String> elements = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        for (String str : s) {
            if (str.matches("[a-z]")){
                stringBuilder.append(str);
            }else if (str.matches("[A-Z]")){
                if (stringBuilder.toString().matches("^[A-Z].*")){
                    elements.add(stringBuilder.toString());
                    stringBuilder.replace(0 , stringBuilder.length() , "");
                    stringBuilder.append(str);
                }else {
                    stringBuilder.append(str);
                }
            }
        }
        elements.add(stringBuilder.toString());
        return elements;
    }

}
