package com.example.blog.algorithm;

public class ANumberThatAppearsOnlyOnce {
    public static int singleNumber(int[] nums) {
        int result = 0;
        for (int num : nums) {
            result ^= num;
        }
        return result;
    }

    public static void main(String[] args) {
        int[] nums = {1,2,3,2,3};
        System.out.println(singleNumber(nums));
    }
}
