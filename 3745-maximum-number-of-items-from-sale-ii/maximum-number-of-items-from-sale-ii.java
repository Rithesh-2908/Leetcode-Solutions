import java.util.*;

class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        long minPrice = Long.MAX_VALUE;
        int maxFactor = 0;

        for (int[] item : items) {
            minPrice = Math.min(minPrice, item[1]);
            maxFactor = Math.max(maxFactor, item[0]);
        }

        long[] freq = new long[maxFactor + 1];
        for (int[] item : items) {
            freq[item[0]]++;
        }

        // multCount[f] = total number of items whose factor is a multiple of f
        long[] multCount = new long[maxFactor + 1];
        for (int f = 1; f <= maxFactor; f++) {
            if (freq[f] > 0) {
                for (int mult = f; mult <= maxFactor; mult += f) {
                    multCount[f] += freq[mult];
                }
            }
        }

        // Group "value-2" boosted copy capacities by item price
        TreeMap<Long, Long> boostedByPrice = new TreeMap<>();
        long twoMinPrice = 2L * minPrice;

        for (int[] item : items) {
            int factor = item[0];
            long price = item[1];
            // Exclude self (j != i)
            long gain = multCount[factor] - 1;
            if (gain > 0 && price <= twoMinPrice) {
                boostedByPrice.merge(price, gain, Long::sum);
            }
        }

        long totalItems = 0;
        long remBudget = budget;

        for (Map.Entry<Long, Long> entry : boostedByPrice.entrySet()) {
            long price = entry.getKey();
            long availableGain = entry.getValue();
            if (remBudget < price) break;

            long take = Math.min(availableGain, remBudget / price);
            totalItems += 2L * take;
            remBudget -= take * price;
        }

        // Spend remaining budget on the cheapest item (1 item each)
        totalItems += remBudget / minPrice;

        return (int) totalItems;
    }
}