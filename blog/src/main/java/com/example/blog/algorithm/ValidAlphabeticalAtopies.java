package com.example.blog.algorithm;

import java.util.Arrays;

public class ValidAlphabeticalAtopies {
    public static boolean isValid(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        char[] sChars = s.toCharArray();
        char[] tChars = t.toCharArray();
        Arrays.sort(sChars);
        Arrays.sort(tChars);
        return Arrays.equals(sChars, tChars);
    }

    public static void main(String[] args) {
        System.out.println(isValid("abc", "bca"));
        System.out.println(isValid("aab", "bba"));
        System.out.println(isValid("aa", "ab"));
    }
}
