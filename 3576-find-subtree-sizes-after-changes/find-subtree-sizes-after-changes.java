import java.util.*;

class Solution {
    public int[] findSubtreeSizes(int[] parent, String s) {
        int n = parent.length;
        List<Integer>[] origTree = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            origTree[i] = new ArrayList<>();
        }
        for (int i = 1; i < n; i++) {
            origTree[parent[i]].add(i);
        }

        int[] newParent = new int[n];
        newParent[0] = -1;
        int[] lastSeen = new int[26];
        Arrays.fill(lastSeen, -1);

        dfsFindParents(0, origTree, s, parent, lastSeen, newParent);

        List<Integer>[] finalTree = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            finalTree[i] = new ArrayList<>();
        }
        for (int i = 1; i < n; i++) {
            finalTree[newParent[i]].add(i);
        }

        int[] ans = new int[n];
        dfsSubtreeSize(0, finalTree, ans);
        return ans;
    }

    private void dfsFindParents(
            int u,
            List<Integer>[] origTree,
            String s,
            int[] parent,
            int[] lastSeen,
            int[] newParent
    ) {
        int idx = s.charAt(u) - 'a';
        int prev = lastSeen[idx];
        newParent[u] = (u == 0) ? -1 : (prev != -1 ? prev : parent[u]);

        lastSeen[idx] = u;
        for (int v : origTree[u]) {
            dfsFindParents(v, origTree, s, parent, lastSeen, newParent);
        }
        lastSeen[idx] = prev;
    }

    private int dfsSubtreeSize(int u, List<Integer>[] finalTree, int[] ans) {
        int size = 1;
        for (int v : finalTree[u]) {
            size += dfsSubtreeSize(v, finalTree, ans);
        }
        ans[u] = size;
        return size;
    }
}