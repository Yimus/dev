package com.example.dev.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class PokerPlane {
    public static List<String> findBeatingPlays(String a, String b) {
        // b为空或者b牌数小于a，返回空集
        if (null == b || b.length() < a.length()) {
            return Collections.emptyList();
        }
        // b手牌3-9对应数组下标，数量为对应的值
        int[] cards = new int[10];
        for (char c : b.toCharArray()) {
            cards[c - '0']++;
        }
        // 获取a手牌起始值
        int start = a.charAt(0) - '0';
        // 获取a飞机的个数
        int count = a.length() / 3;
        StringBuilder sb = new StringBuilder();
        List<String> result = new ArrayList<>();
        for (int i = start + 1; i <= cards.length - count; i++) {
            boolean flag = true;
            // 判断b有没有跟a同样的飞机个数
            for (int j = 0; j < count; j++) {
                if (cards[i + j] < 3) {
                    flag = false;
                    break;
                }
            }
            // 组装结果
            if (flag) {
                for (int j = 0; j < count; j++) {
                    // 下标对应为具体的牌
                    int card = i + j;
                    sb.append(card).append(card).append(card);
                }
                result.add(sb.toString());
                sb.setLength(0);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(findBeatingPlays("333444555666777", "45566667778888999"));
    }
}
