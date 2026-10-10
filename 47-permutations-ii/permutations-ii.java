import java.util.*;

class Solution {
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        Arrays.sort(nums);

        boolean[] used = new boolean[nums.length];
        backtrack(nums, used, new ArrayList<>(), ans);

        return ans;
    }

    void backtrack(int[] nums, boolean[] used,
                   List<Integer> temp, List<List<Integer>> ans) {
        if (temp.size() == nums.length) {
            ans.add(new ArrayList<>(temp));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if (used[i]) continue;

            if (i > 0 && nums[i] == nums[i - 1] && !used[i - 1])
                continue;

            used[i] = true;
            temp.add(nums[i]);

            backtrack(nums, used, temp, ans);

            temp.remove(temp.size() - 1);
            used[i] = false;
        }
    }
}