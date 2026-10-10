class Solution {
    class TrieNode {
        TrieNode[] child = new TrieNode[26];
        String word;
    }

    TrieNode root = new TrieNode();
    List<String> ans = new ArrayList<>();

    public List<String> findWords(char[][] board, String[] words) {
        for (String word : words) {
            TrieNode node = root;
            for (char c : word.toCharArray()) {
                int i = c - 'a';
                if (node.child[i] == null)
                    node.child[i] = new TrieNode();
                node = node.child[i];
            }
            node.word = word;
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                dfs(board, i, j, root);
            }
        }

        return ans;
    }

    void dfs(char[][] board, int r, int c, TrieNode node) {
        if (r < 0 || c < 0 || r >= board.length ||
            c >= board[0].length || board[r][c] == '#')
            return;

        char ch = board[r][c];
        TrieNode next = node.child[ch - 'a'];

        if (next == null) return;

        if (next.word != null) {
            ans.add(next.word);
            next.word = null;
        }

        board[r][c] = '#';

        dfs(board, r + 1, c, next);
        dfs(board, r - 1, c, next);
        dfs(board, r, c + 1, next);
        dfs(board, r, c - 1, next);

        board[r][c] = ch;
    }
}