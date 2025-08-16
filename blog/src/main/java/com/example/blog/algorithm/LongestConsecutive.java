package com.example.blog.algorithm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LongestConsecutive {
    public static int longestConsecutiveBySet(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }
        int maxLength = 0;
        for (int currentNum : set) {
            if (!set.contains(currentNum - 1)) {
                int currentLength = 1;
                while (set.contains(currentNum + 1)) {
                    currentNum++;
                    currentLength++;
                }
                maxLength = Math.max(maxLength, currentLength);
            }
        }
        return maxLength;
    }

    public static int longestConsecutiveByDp(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        Map<Integer, Integer> dp = new HashMap<>();
        int maxLength = 0;
        for (int num : nums) {
            if (dp.containsKey(num)) {
                continue;
            }
            int left = dp.getOrDefault(num - 1, 0);
            int right = dp.getOrDefault(num + 1, 0);
            int currentLength = left + 1 + right;
            dp.put(num, currentLength);
            dp.put(num - left, currentLength);
            dp.put(num + right, currentLength);
            maxLength = Math.max(maxLength, currentLength);
        }
        return maxLength;
    }

    public static void main(String[] args) {
        System.out.println(longestConsecutiveBySet(new int[]{1, 2, 3, 4, 5}));
        System.out.println(longestConsecutiveByDp(new int[]{1, 2, 3, 4, 5}));
    }
}
