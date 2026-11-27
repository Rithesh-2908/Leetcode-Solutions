class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        int n = items.length;
        int[] freeCount = new int[n];

        // Count how many other items j have factor[j] divisible by factor[i]
        for (int i = 0; i < n; i++) {
            int f1 = items[i][0];
            int cnt = 0;
            for (int j = 0; j < n; j++) {
                if (i != j && items[j][0] % f1 == 0) {
                    cnt++;
                }
            }
            freeCount[i] = cnt;
        }

        // dp[b] = max total item copies obtainable with budget b
        int[] dp = new int[budget + 1];

        for (int i = 0; i < n; i++) {
            int price = items[i][1];
            if (price > budget) continue;

            int firstGain = 1 + freeCount[i];

            // 1. First purchase of item i (0/1 transition: backward pass)
            for (int b = budget; b >= price; b--) {
                dp[b] = Math.max(dp[b], dp[b - price] + firstGain);
            }

            // 2. Additional copies of item i (unbounded transition: forward pass)
            for (int b = price; b <= budget; b++) {
                dp[b] = Math.max(dp[b], dp[b - price] + 1);
            }
        }

        int ans = 0;
        for (int b = 0; b <= budget; b++) {
            ans = Math.max(ans, dp[b]);
        }
        return ans;
    }
}