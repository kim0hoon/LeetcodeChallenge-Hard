class Solution {
    val Char.idx: Int get() = this.code - 'a'.code
    fun distinctSubseqII(s: String): Int {
        val mod = 1_000_000_000 + 7
        val lastIdx = MutableList(26) { -1 }
        val dp = MutableList(s.length) { 0 }
        for (i in 0..s.lastIndex) {
            val last = lastIdx[s[i].idx]
            if (last == -1) dp[i] = 1
            (maxOf(0, last) until i).forEach {
                dp[i] += dp[it]
                dp[i] %= mod
            }
            lastIdx[s[i].idx] = i
        }
        return dp.fold(0) { acc, i -> (acc + i) % mod }
    }
}