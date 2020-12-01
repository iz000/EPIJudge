package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.ArrayList;
import java.util.List;

public class BuyAndSellStockTwice {
    @EpiTest(testDataFile = "buy_and_sell_stock_twice.tsv")
    public static double buyAndSellStockTwice(List<Double> prices) {
        // time: O(N), space: O(N)
        int size = prices.size();
        if (size <= 1) {
            return 0;
        }
        double min = Integer.MAX_VALUE;
        double maxProfit = 0;
        List<Double> maxProfits = new ArrayList<>(size);
        for (Double price : prices) {
            min = Math.min(min, price);
            maxProfit = Math.max(maxProfit, price - min);
            maxProfits.add(maxProfit);
        }
        double totalProfit = 0;
        double max = Integer.MIN_VALUE;
        for (int i = size - 1; i >= 0; i--) {
            max = Math.max(max, prices.get(i));
            totalProfit = Math.max(totalProfit, maxProfits.get(i) + max - prices.get(i));
        }
        return totalProfit;
    }

    // https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iii/solution/
    @EpiTest(testDataFile = "buy_and_sell_stock_twice.tsv")
    public static double buyAndSellStockTwice2(List<Double> prices) {
        // time: O(N), space: O(1)
        int size = prices.size();
        if (size <= 1) {
            return 0;
        }
        double t1Cost = Double.MAX_VALUE, t2Cost = Double.MAX_VALUE;
        double t1Profit = 0, t2Profit = 0;
        for (Double price : prices) {
            t1Cost = Math.min(t1Cost, price);
            t1Profit = Math.max(t1Profit, price - t1Cost);
            t2Cost = Math.min(t2Cost, price - t1Profit);
            t2Profit = Math.max(t2Profit, price - t2Cost);
        }
        return t2Profit;
    }

    public static void main(String[] args) {
        System.exit(
                GenericTest
                        .runFromAnnotations(args, "BuyAndSellStockTwice.java",
                                new Object() {
                                }.getClass().getEnclosingClass())
                        .ordinal());
    }
}
