package com.example.blog.algorithm;

public class SumOfTheMaximumSubarray {
    public static int sumOfTheMaximumSubarray(int[] nums) {
        int max = nums[0];
        int dp = nums[0];
        for (int i = 1; i < nums.length; i++) {
            dp = Math.max(nums[i], dp + nums[i]);
            max = Math.max(max, dp);
        }
        return max;
    }

    public static void main(String[] args) {
        int[] nums = {-2,1,-3,4,-1,2,1,-5,4};
        System.out.println(sumOfTheMaximumSubarray(nums));
    }
}
