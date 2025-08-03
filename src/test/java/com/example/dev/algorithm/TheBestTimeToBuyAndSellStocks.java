package com.example.dev.algorithm;

public class TheBestTimeToBuyAndSellStocks {
    public static int maxProfit(int[] prices) {
        int minPrice = prices[0];
        int maxProfit = 0;
        for (int i = 1; i < prices.length; i++) {
            minPrice = Math.min(minPrice, prices[i]);
            maxProfit = Math.max(maxProfit, prices[i] - minPrice);
        }
        return maxProfit;
    }

    public static void main(String[] args) {
        int[] prices = {7,6,4,3,1,5};
        System.out.println(maxProfit(prices));
    }
}
