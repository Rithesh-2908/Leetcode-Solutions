class Solution {
    public int orangesRotting(int[][] grid) {
        Queue<int[]> q = new LinkedList<>();
        int fresh = 0;
        int time = 0;
        int m = grid.length;
        int n = grid[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                if (grid[i][j] == 2) {
                    q.add(new int[]{i, j});
                }

                if (grid[i][j] == 1) {
                    fresh++;
                }
            }
        }

        int[][] dir = {
            {1, 0},
            {-1, 0},
            {0, 1},
            {0, -1}
        };

        // BFS
        while (!q.isEmpty() && fresh > 0) {

            int size = q.size();

            for (int i = 0; i < size; i++) {

                int[] curr = q.poll();

                for (int[] d : dir) {

                    int r = curr[0] + d[0];
                    int c = curr[1] + d[1];

                    if (r >= 0 && r < m &&
                        c >= 0 && c < n &&
                        grid[r][c] == 1) {

                        grid[r][c] = 2;
                        fresh--;

                        q.add(new int[]{r, c});
                    }
                }
            }

            time++;
        }

        return fresh == 0 ? time : -1;
    }
}