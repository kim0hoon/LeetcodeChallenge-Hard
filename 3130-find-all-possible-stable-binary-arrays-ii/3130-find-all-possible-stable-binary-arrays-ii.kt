class Solution {
    fun numberOfStableArrays(zero: Int, one: Int, limit: Int): Int {
        val mod = 1_000_000_000 + 7
        val dp = List(zero + one + 1) { List(minOf(zero, it) + 1) { MutableList(2) { 0 } } }
        dp[1][0][1] = 1
        dp[1][1][0] = 1
        if (1 + limit <= zero + one) {
            dp[1 + limit][0][1] = -1
            dp[1 + limit].getOrNull(1 + limit)?.getOrNull(0)?.let {
                dp[1 + limit][1 + limit][0] = -1
            }
        }
        for (length in 1 until zero + one) {
            for (countOfZero in 0..minOf(length, zero)) {
                if (countOfZero < zero) {
                    dp[length + 1][countOfZero + 1][0] +=
                        (dp[length][countOfZero][0] + dp[length][countOfZero][1]) % mod
                    dp[length + 1][countOfZero + 1][0] %= mod
                }
                dp.getOrNull(length + 1 + limit)?.getOrNull(countOfZero + limit + 1)?.getOrNull(0)?.let {
                    dp[length + 1 + limit][countOfZero + limit + 1][0] += (mod + (it - dp[length][countOfZero][1])) % mod
                    dp[length + 1 + limit][countOfZero + limit + 1][0] %= mod
                }

                dp[length + 1][countOfZero][1] += (dp[length][countOfZero][1] + dp[length][countOfZero][0]) % mod
                dp[length + 1][countOfZero][1] %= mod
                dp.getOrNull(length + 1 + limit)?.getOrNull(countOfZero)?.getOrNull(1)?.let {
                    dp[length + 1 + limit][countOfZero][1] += (mod + (it - dp[length][countOfZero][0])) % mod
                    dp[length + 1 + limit][countOfZero][1] %= mod
                }
            }
        }
        return dp[zero + one][zero].sumOf { it } % mod
    }
}