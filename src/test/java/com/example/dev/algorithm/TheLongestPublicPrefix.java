package com.example.dev.algorithm;

import java.util.Arrays;

public class TheLongestPublicPrefix {
    public static String longestCommonPrefix(String[] strings) {
        if (strings == null || strings.length == 0) {
            return "";
        }
        for (int i = 0; i < strings[0].length(); i++) {
            for (int j = 1; j < strings.length; j++) {
                if (i >= strings[j].length() || strings[0].charAt(i) != strings[j].charAt(i)) {
                    return strings[0].substring(0, i);
                }
            }
        }
        return strings[0];
    }

    public static String longestCommonPrefixBySort(String[] strings) {
        Arrays.sort(strings);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < Math.min(strings[0].length(), strings[strings.length -1].length()); i++) {
            if (strings[0].charAt(i) == strings[strings.length - 1].charAt(i)) {
                sb.append(strings[0].charAt(i));
            } else {
                return sb.toString();
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String[] strings = {"flower","flow","flight"};
        System.out.println(longestCommonPrefix(strings));
        System.out.println(longestCommonPrefixBySort(strings));
    }
}
