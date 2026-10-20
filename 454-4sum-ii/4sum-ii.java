import java.util.HashMap;
import java.util.Map;

class Solution {
    public int fourSumCount(int[] nums1, int[] nums2, int[] nums3, int[] nums4) {
        // Store the frequency of sums of elements from nums1 and nums2
        Map<Integer, Integer> map = new HashMap<>();
        
        for (int a : nums1) {
            for (int b : nums2) {
                map.put(a + b, map.getOrDefault(a + b, 0) + 1);
            }
        }
        
        int count = 0;
        
        // Check if the negative of the sum of nums3 and nums4 exists in the map
        for (int c : nums3) {
            for (int d : nums4) {
                int target = -(c + d);
                count += map.getOrDefault(target, 0);
            }
        }
        
        return count;
    }
}