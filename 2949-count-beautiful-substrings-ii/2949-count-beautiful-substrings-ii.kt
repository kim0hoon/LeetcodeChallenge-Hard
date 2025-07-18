class Solution {
    fun beautifulSubstrings(s: String, k: Int): Long {
        var ans = 0L
        val vowelsAlp = listOf('a', 'e', 'i', 'o', 'u')
        var subLength = 1
        while (subLength * subLength % k != 0) subLength++
        subLength *= 2
        var vToc = 0 to 0
        val acc = s.map {
            vToc = vToc.run {
                if (vowelsAlp.contains(it)) first + 1 to second
                else first to second + 1
            }
            vToc
        }
        val dp = mutableMapOf<Int, Int>()
        for (start in 0 until subLength) {
            dp.clear()
            val initDiff = acc.getOrElse(start - 1) { 0 to 0 }.run { first - second }
            dp[initDiff] = 1
            for (j in start..s.lastIndex step subLength) {
                if (j + subLength - 1 > acc.lastIndex) break
                val diff = acc[j + subLength - 1].run { first - second }
                ans += dp[diff] ?: 0
                dp[diff] = (dp[diff]?:0)+1
            }
        }
        return ans
    }
}