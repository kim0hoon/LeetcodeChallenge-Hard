class Solution {
    fun pathsWithMaxScore(board: List<String>): IntArray {
        val r = board.size
        val c = board[0].length
        val dp = List(r) { MutableList(c) { 0 to 0 } }
        val mod = 1_000_000_000 + 7
        val map = mutableMapOf<Int, Int>()
        dp[r - 1][c - 1] = 0 to 1
        for (nr in r - 1 downTo 0) for (nc in c - 1 downTo 0) {
            if (listOf('X', 'S').contains(board[nr][nc])) continue
            map.clear()
            val adj = listOf(nr + 1 to nc, nr + 1 to nc + 1, nr to nc + 1)
            adj.forEach {
                if (it.first > r - 1 || it.second > c - 1) return@forEach
                val n = dp[it.first][it.second]
                map[n.first] = (map.getOrDefault(n.first, 0) + n.second) % mod
            }
            map.maxOfOrNull { it.key }?.let { k ->
                map[k]?.let { v ->
                    dp[nr][nc] = (k + if (board[nr][nc] == 'E') 0 else board[nr][nc].digitToInt()) to v
                }
            }
        }
        return dp[0][0].run { intArrayOf(if (second == 0) 0 else first, second) }
    }
}