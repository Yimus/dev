package com.example.blog.algorithm;

public class Loot {
    public static int loot(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        if (nums.length == 1) {
            return nums[0];
        }
        int dp0 = nums[0];
        int dp1 = Math.max(nums[0], nums[1]);
        int result = dp1;
        for (int i = 2; i < nums.length; i++) {
            result = Math.max(dp1, dp0 + nums[i]);
            dp0 = dp1;
            dp1 = result;
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(loot(new int[]{1, 1}));
    }
}
