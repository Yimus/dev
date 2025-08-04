package com.example.dev.algorithm;

public class MoveZero {
    public static void moveZeroes(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return;
        }
        int slow = 0;
        for (int fast = 1; fast < nums.length; fast++) {
            if (nums[slow] != 0) {
                slow++;
            } else if (nums[fast] != 0) {
                nums[slow++] = nums[fast];
                nums[fast] = 0;
            }
        }
    }

    public static void main(String[] args) {
        int[] nums = {1, 0, 2};
        moveZeroes(nums);
        for (int num : nums) {
            System.out.println(num);
        }
    }
}
