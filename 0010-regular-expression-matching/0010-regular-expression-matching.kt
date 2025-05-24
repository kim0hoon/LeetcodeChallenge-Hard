class Solution {
       fun isMatch(s: String, p: String): Boolean {
        val dp = MutableList(2) { MutableList(s.length) { false } }
        var emptyMatched = true
        var idx = 0
        var seq = 0
        while (idx < p.length) {
            val target = p[idx]
            val prevSeq = (seq + 1) % 2
            if (idx < p.lastIndex && p[idx + 1] == '*') {
                dp[seq][0] = dp[prevSeq][0] || ((target == s[0] || target == '.') && emptyMatched)
                for (i in 1..s.lastIndex) {
                    dp[seq][i] = dp[prevSeq][i] || ((target == s[i] || target == '.') && (dp[seq][i - 1]))
                }
                idx += 2
            } else {
                dp[seq][0] = emptyMatched && (target == s[0] || target == '.')
                for (i in 1..s.lastIndex) {
                    dp[seq][i] = dp[prevSeq][i - 1] && (target == s[i] || target == '.')
                }
                emptyMatched = false
                idx += 1
            }
            seq = (seq + 1) % 2
        }
        return dp[(seq + 1) % 2][s.lastIndex]
    }
}