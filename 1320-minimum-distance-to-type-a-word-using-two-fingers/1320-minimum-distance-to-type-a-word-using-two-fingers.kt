class Solution {
    fun minimumDistance(word: String): Int {
                val dp = MutableList(2) { MutableList(27) { -1 } }
        fun Char.toIdx() = this.code - 'A'.code
        fun dist(i1: Int, i2: Int) = abs(i1 / 6 - i2 / 6) + abs(i1 % 6 - i2 % 6)

        word.forEachIndexed { index, ch ->
            val now = index % 2
            val pre = 1 - now
            dp[now] = MutableList(27) { Int.MAX_VALUE }
            if (index == 0) {
                dp[now][26] = 0
            } else {
                dp[pre].forEachIndexed { p2, cnt ->
                    if (cnt == Int.MAX_VALUE) return@forEachIndexed
                    val p1 = word[index - 1].toIdx()
                    dp[now][p2] = minOf(dp[now][p2], dp[pre][p2] + dist(p1, ch.toIdx()))
                    if (p2 == 26) {
                        dp[now][p1] = minOf(dp[now][p1], dp[pre][p2])
                    } else {
                        dp[now][p1] = minOf(dp[now][p1], dp[pre][p2] + dist(p2, ch.toIdx()))
                    }
                }
            }
        }
        return dp[(word.length-1) % 2].minOf { it }
    }
}