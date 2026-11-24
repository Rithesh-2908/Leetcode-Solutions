class Solution {
    public int maxLength(int[] nums) {
        int n = nums.length;
        int maxLen = 0;

        for (int i = 0; i < n; i++) {
            long prod = 1;
            int g = 0;
            long l = 1;

            for (int j = i; j < n; j++) {
                int val = nums[j];

                // Guard against overflow beyond Long.MAX_VALUE / val
                if (prod > Long.MAX_VALUE / val) {
                    break;
                }
                prod *= val;

                g = (i == j) ? val : gcd(g, val);
                long stepGcd = gcd(l, val);
                if (l / stepGcd > Long.MAX_VALUE / val) {
                    break;
                }
                l = (l / stepGcd) * val;

                if (prod == l * (long) g) {
                    maxLen = Math.max(maxLen, j - i + 1);
                }
            }
        }

        return maxLen;
    }

    private int gcd(int a, int b) {
        while (b != 0) {
            int temp = a % b;
            a = b;
            b = temp;
        }
        return a;
    }

    private long gcd(long a, long b) {
        while (b != 0) {
            long temp = a % b;
            a = b;
            b = temp;
        }
        return a;
    }
}