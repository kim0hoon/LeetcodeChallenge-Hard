class Solution {
    fun ways(pizza: Array<String>, k: Int): Int {
        val mod = 1_000_000_000 + 7
        val row = pizza.size
        val col = pizza[0].length
        val acc = List(row) { MutableList(col) { 0 } }
        for (r in 0 until row) for (c in 0 until col) acc[r][c] =
            (if (c > 0) acc[r][c - 1] else 0) + (if (pizza[r][c] == 'A') 1 else 0)
        for (r in 1 until row) for (c in 0 until col) acc[r][c] = acc[r][c] + acc[r - 1][c]
        val dp = List(2) { List(row) { MutableList(col) { 0 } } }
        fun hasApple(topLeft: Pair<Int, Int>, bottomRight: Pair<Int, Int>): Boolean {
            val n =
                acc[bottomRight.first][bottomRight.second] - acc[bottomRight.first].getOrElse(topLeft.second - 1) { 0 } - (acc.getOrNull(
                    topLeft.first - 1
                )?.get(bottomRight.second) ?: 0) + (acc.getOrNull(topLeft.first - 1)?.getOrNull(topLeft.second - 1)
                    ?: 0)
            return n > 0
        }
        dp[0][0][0] = 1
        repeat(k - 1) { n ->
            val now = n % 2
            val next = 1 - now
            dp[next].forEach { it.fill(0) }
            for (r in 0 until row) for (c in 0 until col) {
                for (sr in r until row - 1) if (hasApple(r to c, sr to col - 1)) dp[next][sr + 1][c] =
                    (dp[next][sr + 1][c] + dp[now][r][c]) % mod
                for (sc in c until col - 1) if (hasApple(r to c, row - 1 to sc)) dp[next][r][sc + 1] =
                    (dp[next][r][sc + 1] + dp[now][r][c]) % mod
            }
        }
        var ans = 0
        dp[1 - (k % 2)].forEachIndexed { r, ints ->
            ints.forEachIndexed { c, i ->
                if (hasApple(r to c, row - 1 to col - 1)) ans = (ans + i) % mod
            }
        }
        return ans
    }
}