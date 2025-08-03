package com.example.dev.algorithm;

public class ClimbStairs {
    public static int climbStairs(int n) {
        if (n <= 2) {
            return n;
        }
        int dp1 = 1;
        int dp2 = 2;
        int result = 0;
        for (int i = 3; i <= n; i++) {
            result = dp1 + dp2;
            dp1 = dp2;
            dp2 = result;
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(climbStairs(4));
    }
}
