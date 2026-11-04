class Solution {

    TreeMap<Integer, Integer> left = new TreeMap<>();
    TreeMap<Integer, Integer> right = new TreeMap<>();

    int leftSize = 0, rightSize = 0;
    long leftSum = 0, rightSum = 0;

    void add(int val) {
        if (left.isEmpty() || val <= left.lastKey()) {
            left.merge(val, 1, Integer::sum);
            leftSize++;
            leftSum += val;
        } else {
            right.merge(val, 1, Integer::sum);
            rightSize++;
            rightSum += val;
        }
        balance();
    }

    void remove(int val) {
        if (left.containsKey(val)) {
            decrease(left, val);
            leftSize--;
            leftSum -= val;
        } else {
            decrease(right, val);
            rightSize--;
            rightSum -= val;
        }
        balance();
    }

    void decrease(TreeMap<Integer, Integer> map, int val) {
        if (map.get(val) == 1)
            map.remove(val);
        else
            map.put(val, map.get(val) - 1);
    }

    void balance() {
        while (leftSize > rightSize + 1) {
            int val = left.lastKey();
            decrease(left, val);
            leftSize--;
            leftSum -= val;

            right.merge(val, 1, Integer::sum);
            rightSize++;
            rightSum += val;
        }

        while (leftSize < rightSize) {
            int val = right.firstKey();
            decrease(right, val);
            rightSize--;
            rightSum -= val;

            left.merge(val, 1, Integer::sum);
            leftSize++;
            leftSum += val;
        }
    }

    long getCost() {
        long median = left.lastKey();

        return median * leftSize - leftSum
             + rightSum - median * rightSize;
    }

    public long minOperations(int[] nums, int x, int k) {

        int n = nums.length;

        long[] cost = new long[n - x + 1];

        // Step 1: Calculate cost of every window
        for (int i = 0; i < n; i++) {

            add(nums[i]);

            if (i >= x) {
                remove(nums[i - x]);
            }

            if (i >= x - 1) {
                cost[i - x + 1] = getCost();
            }
        }

        // Step 2: Dynamic Programming
        long INF = Long.MAX_VALUE / 4;

        long[][] dp = new long[n + 1][k + 1];

        for (int i = 0; i <= n; i++) {
            Arrays.fill(dp[i], INF);
        }

        dp[0][0] = 0;

        for (int i = 1; i <= n; i++) {

            for (int j = 0; j <= k; j++) {

                // Option 1: Skip current element
                dp[i][j] = dp[i - 1][j];

                // Option 2: Select a window of size x
                if (j > 0 && i >= x &&
                    dp[i - x][j - 1] != INF) {

                    dp[i][j] = Math.min(
                        dp[i][j],
                        dp[i - x][j - 1] + cost[i - x]
                    );
                }
            }
        }

        return dp[n][k];
    }
}
