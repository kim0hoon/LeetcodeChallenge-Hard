class Solution {
    fun numberOfCombinations(num: String): Int {
        val mod = 1_000_000_000 + 7
        val possible = List(num.length) { MutableList(num.length) { false } }
        for (length in 1..num.length / 2) {

            val sStr = num.substring(num.run { lastIndex - 2 * length + 1..lastIndex - length })
            val eStr = num.substring(num.run { lastIndex - length + 1..lastIndex })
            var equalLength = sStr.foldIndexed(0 to true) { index, acc, c ->
                if (acc.second && eStr[index] == c) acc.run { first + 1 to second }
                else acc.run { first to false }
            }.first
            possible[num.lastIndex - length][num.lastIndex] = sStr <= eStr
            for (end in num.lastIndex - 1 downTo length * 2 - 1) {
                val startNext = num[end - 2 * length + 1]
                val endNext = num[end - length + 1]
                possible[end - length][end] = if (startNext > endNext) {
                    equalLength = 0
                    false
                } else if (startNext < endNext) {
                    equalLength = 0
                    true
                } else {
                    equalLength = minOf(length, equalLength + 1)
                    possible[end - length + 1][end + 1] || length == 1 || equalLength == length
                }
            }
        }
        val dp = List(num.length) { s -> MutableList(num.length - s + 1) { 0 } }
        dp[0].fill(if (num[0] != '0') 1 else 0)
        for (i in 0 until num.lastIndex) {
            for (j in 1 until num.length - i) {
                if (i + 2 * j - 1 > num.lastIndex) break
                if (possible[i + j - 1][i + 2 * j - 1]) {
                    dp.getOrNull(i + j)?.getOrNull(j)?.let {
                        dp[i + j][j] = (it + dp[i][j]) % mod
                    }
                } else if (num[i + j] != '0') {
                    dp.getOrNull(i + j)?.getOrNull(j + 1)?.let {
                        dp[i + j][j + 1] = (it + dp[i][j]) % mod
                    }
                }
            }
            dp[i + 1].indices.forEach {
                if (it == 0) return@forEach
                dp[i + 1][it] = (dp[i + 1][it] + dp[i + 1][it - 1]) % mod
            }
        }
        return dp.fold(0) { acc, ints -> (acc + ints.last()) % mod }
    }
}