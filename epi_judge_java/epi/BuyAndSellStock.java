package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.ArrayList;
import java.util.List;

public class BuyAndSellStock {
    @EpiTest(testDataFile = "buy_and_sell_stock.tsv")
    public static double computeMaxProfit(List<Double> prices) {
        // time: O(N) / space: O(N)
        int size = prices.size();
        List<Double> mins = new ArrayList<>(size);
        List<Double> maxs = new ArrayList<>(size);
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        for (int i = 0; i < size; i++) {
            min = Math.min(min, prices.get(i));
            max = Math.max(max, prices.get(size - i - 1));
            mins.add(min);
            maxs.add(max);
        }
        double maxProfit = 0;
        for (int i = 0; i < size; i++) {
            double profit = maxs.get(size - i - 1) - mins.get(i);
            maxProfit = Math.max(maxProfit, profit);
        }
        return maxProfit;
    }

    @EpiTest(testDataFile = "buy_and_sell_stock.tsv")
    public static double computeMaxProfit2(List<Double> prices) {
        // time: O(N) / space: O(N)
        double min = Double.MAX_VALUE;
        double maxProfit = 0;
        for (Double price : prices) {
            min = Math.min(min, price);
            maxProfit = Math.max(maxProfit, price - min);
        }
        return maxProfit;
    }

    public static void main(String[] args) {
        System.exit(
                GenericTest
                        .runFromAnnotations(args, "BuyAndSellStock.java",
                                new Object() {
                                }.getClass().getEnclosingClass())
                        .ordinal());
    }
}
