package com.echain.util;

public class Utils {
    public static String transStr(String in){
        return in.substring(0, 11) + "..." + in.substring(in.length() - 11, in.length());
    }
}
