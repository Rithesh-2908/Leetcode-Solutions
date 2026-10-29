
import java.util.*;

class Solution {
    public List<Boolean> palindromePath(int n, int[][] edges, String s, String[] queries) {
        List<Integer>[] adj = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }

        for (int[] edge : edges) {
            adj[edge[0]].add(edge[1]);
            adj[edge[1]].add(edge[0]);
        }

        int LOG = 1;
        while ((1 << LOG) <= n) {
            LOG++;
        }

        int[][] up = new int[LOG][n];
        int[] depth = new int[n];
        int[] tin = new int[n];
        int[] tout = new int[n];
        int[] prefix = new int[n];
        int[] parent = new int[n];
        int[] iter = new int[n];
        int[] stack = new int[n];

        int top = 0;
        int timer = 0;

        stack[top++] = 0;
        parent[0] = 0;
        prefix[0] = 1 << (s.charAt(0) - 'a');
        tin[0] = timer++;

        while (top > 0) {
            int u = stack[top - 1];

            if (iter[u] < adj[u].size()) {
                int v = adj[u].get(iter[u]++);

                if (v == parent[u]) {
                    continue;
                }

                parent[v] = u;
                depth[v] = depth[u] + 1;
                prefix[v] = prefix[u]
                        ^ (1 << (s.charAt(v) - 'a'));

                tin[v] = timer++;
                stack[top++] = v;
            } else {
                tout[u] = timer - 1;
                top--;
            }
        }

        for (int i = 0; i < n; i++) {
            up[0][i] = parent[i];
        }

        for (int j = 1; j < LOG; j++) {
            for (int i = 0; i < n; i++) {
                up[j][i] = up[j - 1][up[j - 1][i]];
            }
        }

        Fenwick bit = new Fenwick(n);
        char[] chars = s.toCharArray();
        List<Boolean> result = new ArrayList<>();

        for (String q : queries) {
            String[] parts = q.split(" ");

            if (parts[0].equals("update")) {
                int u = Integer.parseInt(parts[1]);
                char c = parts[2].charAt(0);

                if (chars[u] == c) {
                    continue;
                }

                int diff = (1 << (chars[u] - 'a'))
                        ^ (1 << (c - 'a'));

                bit.rangeXor(tin[u], tout[u], diff);
                chars[u] = c;

            } else {
                int u = Integer.parseInt(parts[1]);
                int v = Integer.parseInt(parts[2]);

                int w = lca(u, v, depth, up);

                int maskU = prefix[u] ^ bit.pointQuery(tin[u]);
                int maskV = prefix[v] ^ bit.pointQuery(tin[v]);

                int mask = maskU ^ maskV
                        ^ (1 << (chars[w] - 'a'));

                result.add((mask & (mask - 1)) == 0);
            }
        }

        return result;
    }

    private int lca(int u, int v, int[] depth, int[][] up) {
        if (depth[u] < depth[v]) {
            int temp = u;
            u = v;
            v = temp;
        }

        int diff = depth[u] - depth[v];

        for (int j = 0; j < up.length; j++) {
            if ((diff & (1 << j)) != 0) {
                u = up[j][u];
            }
        }

        if (u == v) {
            return u;
        }

        for (int j = up.length - 1; j >= 0; j--) {
            if (up[j][u] != up[j][v]) {
                u = up[j][u];
                v = up[j][v];
            }
        }

        return up[0][u];
    }

    static class Fenwick {
        int[] tree;
        int n;

        Fenwick(int n) {
            this.n = n;
            tree = new int[n + 1];
        }

        void update(int index, int value) {
            for (int i = index + 1; i <= n; i += i & -i) {
                tree[i] ^= value;
            }
        }

        void rangeXor(int left, int right, int value) {
            update(left, value);

            if (right + 1 < n) {
                update(right + 1, value);
            }
        }

        int pointQuery(int index) {
            int result = 0;

            for (int i = index + 1; i > 0; i -= i & -i) {
                result ^= tree[i];
            }

            return result;
        }
    }
}
