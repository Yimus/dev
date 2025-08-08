package com.example.blog.algorithm;

import java.util.ArrayList;
import java.util.List;

public class LetterCombinations {
    public static List<String> letterCombinations(String digits) {
        List<String> result = new ArrayList<>();
        if (digits == null || digits.isEmpty()) return result;
        String[] map = new String[10];
        map[2] = "abc";
        map[3] = "def";
        map[4] = "ghi";
        map[5] = "jkl";
        map[6] = "mno";
        map[7] = "pqrs";
        map[8] = "tuv";
        map[9] = "wxyz";
        StringBuilder sb = new StringBuilder();
        backtrack(result, sb, 0, digits, map);
        return result;
    }

    private static void backtrack(List<String> result, StringBuilder sb, int k, String digits, String[] map) {
        if (sb.length() == digits.length()) {
            result.add(sb.toString());
            return;
        }
        String current = map[digits.charAt(k) - '0'];
        for (char c : current.toCharArray()) {
            sb.append(c);
            backtrack(result, sb, k + 1, digits, map);
            sb.deleteCharAt(sb.length() - 1);
        }
    }

    public static void main(String[] args) {
        System.out.println(letterCombinations("23"));
    }
}
